package com.janita.plugin.demo.mybatislog2sql.other;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.janita.plugin.common.domain.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 类说明：
 *
 * @author zhucj
 * @since 2020/3/8 - 下午3:46
 */
public class LogParse {

    public static String toSql(String log) {
        Pair<String, String> pair = getLogPair(log);
        String sqlLine = pair.getLeft();
        String valueLine = pair.getRight();

        // 二者任一为空的时候
        boolean failOfInvalidLog = Objects.isNull(sqlLine) || Objects.isNull(valueLine);
        if (failOfInvalidLog) {
            showNotification(sqlLine);
            return LogConstants.EMPTY;
        }

        int sqlPrefixIndex = sqlLine.indexOf(LogConstants.PREFIX_SQL);
        String originSql = sqlLine.substring(sqlPrefixIndex + LogConstants.PREFIX_SQL.length());
        // 参数列表
        int paramPrefixIndex = valueLine.indexOf(LogConstants.PREFIX_PARAMS);
        String paramValues = valueLine.substring(paramPrefixIndex + LogConstants.PREFIX_PARAMS.length());

        List<String> originSqlSections = Arrays.asList(originSql.split(LogConstants.PARAM_PLACEHOLDER));
        List<String> paramValuesSections = Arrays.asList(paramValues.split(LogConstants.PARAM_SEPARATOR));
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (originSqlSections.size() > i && paramValuesSections.size() > i) {
            sb.append(originSqlSections.get(i));
            sb.append(parseParam(paramValuesSections.get(i)));
            i++;
        }

        while (originSqlSections.size() > i) {
            sb.append(originSqlSections.get(i));
            i++;
        }

        return sb.toString();
    }

    /**
     * 解析参数值
     *
     * @param paramValue 参数值字符串
     * @return 参数值
     */
    private static String parseParam(String paramValue) {
        // 如果是空字符串直接返回
        if (paramValue.length() == 0) {
            return LogConstants.EMPTY;
        }

        // 如果是null 直接返回null
        if (paramValue.trim().equals(LogConstants.NULL)) {
            return LogConstants.NULL;
        }

        // 括号的索引
        int lastLeftBracketIndex = paramValue.lastIndexOf(LogConstants.LEFT_BRACKET);
        int lastRightBracketIndex = paramValue.lastIndexOf(LogConstants.RIGHT_BRACKET);
        // 参数值
        String param = paramValue.substring(0, lastLeftBracketIndex);
        // 参数类型
        String type = paramValue.substring(lastLeftBracketIndex + 1, lastRightBracketIndex);
        return LogConstants.NON_QUOTED_TYPES.contains(type) ? param : String.format("'%s'", param);
    }

    private static Pair<String, String> getLogPair(String log) {
        String sqlLine = null;
        String valueLine = null;
        String[] logArr = log.split(LogConstants.BREAK_LINE);
        for (String line : logArr) {
            if (line.contains(LogConstants.PREFIX_SQL)) {
                sqlLine = line;
            } else if (line.contains(LogConstants.PREFIX_PARAMS)) {
                valueLine = line;
            } else if (line.contains(LogConstants.PREFIX_PARAMS_WITHOUT_SPACE)) {
                valueLine = line + LogConstants.SPACE;
            }
        }
        return Pair.of(sqlLine, valueLine);
    }

    private static void showNotification(String sqlLine) {
        String content = Objects.isNull(sqlLine) ? "selected log with \"Preparing:\" line, nothing will send to clipboard" : "selected log with \"Parameters:\" line, nothing will send to clipboard";
        Notification notification = new Notification(
                "Copy As Executable Sql",
                "Copy As executable sql", content,
                NotificationType.WARNING
        );
        new NotificationThread(notification).start();
    }
}
