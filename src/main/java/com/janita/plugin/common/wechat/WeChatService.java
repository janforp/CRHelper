package com.janita.plugin.common.wechat;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.janita.plugin.common.constant.PluginConstant;
import com.janita.plugin.common.util.WeChatUtils;
import com.janita.plugin.common.wechat.domain.MsgColor;
import com.janita.plugin.common.wechat.domain.MsgTip;
import com.janita.plugin.common.wechat.msg.MarkdownMsg;
import com.janita.plugin.common.wechat.msg.TextMsg;
import com.janita.plugin.cr.domain.CrQuestion;

import java.util.List;

/**
 * WeChatService
 *
 * @author zhucj
 * @since 20220324
 */
@SuppressWarnings("all")
public class WeChatService {

    private static void sendByText(String content, List<String> mentionedList, List<String> mentionedMobileList) {
        TextMsg textMsg = new TextMsg();
        TextMsg.Text text = new TextMsg.Text();
        text.setMentionedList(mentionedList);
        text.setMentionedMobileList(mentionedMobileList);
        text.setContent(content);
        textMsg.setText(text);
        WeChatUtils.sendPost(PluginConstant.WE_CHAT_GROUP_ROBOT_ID, JSON.toJSONString(textMsg));
    }

    public static void sendByMarkDown(CrQuestion question) {
        MarkdownMsg markdownMsg = new MarkdownMsg();
        MarkdownMsg.MarkDown markDown = new MarkdownMsg.MarkDown();
        markDown.setMentionedList(Lists.newArrayList(""));
        markDown.setMentionedMobileList(Lists.newArrayList("13738053603"));
        markdownMsg.setMarkdown(markDown);
        String content = buildMarkdownContent("CodeReview问题指派", Lists.newArrayList(
                new MsgTip("工程", MsgColor.info, question.getProjectName()),
                new MsgTip("文件", MsgColor.info, question.getFileName()),
                new MsgTip("分支", MsgColor.info, question.getCreateGitBranchName()),
                new MsgTip("类型", MsgColor.warning, question.getType()),
                new MsgTip("级别", MsgColor.warning, question.getLevel()),
                new MsgTip("指派给", MsgColor.info, question.getAssignTo()),
                new MsgTip("创建人", MsgColor.info, question.getAssignFrom())));
        markDown.setContent(content);
        WeChatUtils.sendPost(PluginConstant.WE_CHAT_GROUP_ROBOT_ID, JSON.toJSONString(markdownMsg));
        sendByText("你有新的CodeReview问题，请注意", null, Lists.newArrayList("13738053603", "17858963631", "18738322951"));
    }

    private static String buildMarkdownContent(String title, List<MsgTip> tipList) {
        StringBuilder content = new StringBuilder(title);
        for (MsgTip msgTip : tipList) {
            content.append(buildOneTip(msgTip));
        }
        return content.toString();
    }

    private static String buildOneTip(MsgTip tip) {
        return "\n" + tip.getTitle() + "：<font color=\\\"" + tip.getColor().name() + "\\\">" + tip.getContent() + "</font>";
    }
}
