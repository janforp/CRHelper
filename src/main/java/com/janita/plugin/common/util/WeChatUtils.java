package com.janita.plugin.common.util;

import com.alibaba.fastjson.JSONObject;
import com.janita.plugin.common.constant.PersistentKeys;
import com.janita.plugin.common.constant.PluginConstant;
import com.janita.plugin.cr.domain.CrQuestion;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ObjectUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=80403b99-179c-4a9c-a0cc-3160c705f53a
 *
 * @author zhucj
 * @since 20220324
 */
@UtilityClass
public class WeChatUtils {

    public static String buildContent(CrQuestion question) {

        String content = "CodeReview 问题指派："
                + "\n工程：<font color=\\\"info\\\">" + question.getProjectName() + "</font>"
                + "\n文件：<font color=\\\"info\\\">" + question.getFileName() + "</font>"
                + "\n分支：<font color=\\\"info\\\">" + question.getCreateGitBranchName() + "</font>"
                + "\n类型：<font color=\\\"error\\\">" + question.getType() + "</font>"
                + "\n级别：<font color=\\\"warning\\\">" + question.getLevel() + "</font>"
                + "\n指派给：<font color=\\\"comment\\\">" + question.getAssignTo() + "</font>"
                + "\n创建人：<font color=\\\"comment\\\">" + question.getAssignFrom() + "</font>";

        return "{\n" +
                "  \"msgtype\": \"markdown\",\n" +
                "  \"markdown\": {\n" +
                "    \"content\": \"" + content + "\"\n" +
                "  }\n" +
                "}";
    }

    public static void setWeChatMsg(CrQuestion question) {
        String content = buildContent(question);
        String weChatGroupRobotId = SingletonBeanFactory.getPropertiesComponent().getValue(PersistentKeys.WeChatRobotConfig.WE_CHAT_GROUP_ROBOT_ID);
        weChatGroupRobotId = ObjectUtils.defaultIfNull(weChatGroupRobotId, PluginConstant.WE_CHAT_GROUP_ROBOT_ID);
        try {
            JSONObject result = sendPost(weChatGroupRobotId, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送post请求
     *
     * @param robotKey 机器人key
     * @param content 参数
     * @return 发送结果
     */
    public static JSONObject sendPost(String robotKey, String content) {
        String url = PluginConstant.WE_CHAT_SEND_URL + robotKey;
        PrintWriter out = null;
        BufferedReader in = null;
        JSONObject jsonObject = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流（设置请求编码为UTF-8）
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8));
            // 发送请求参数
            out.print(content);
            // flush输出流的缓冲
            out.flush();
            // 获取请求返回数据（设置返回数据编码为UTF-8）
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            jsonObject = JSONObject.parseObject(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return jsonObject;
    }
}