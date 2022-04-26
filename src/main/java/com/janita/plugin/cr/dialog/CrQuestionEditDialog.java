package com.janita.plugin.cr.dialog;

import com.google.common.collect.Lists;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTabbedPane;
import com.intellij.ui.components.JBTextField;
import com.janita.plugin.common.constant.DataToInit;
import com.janita.plugin.common.constant.PluginConstant;
import com.janita.plugin.common.enums.CrQuestionState;
import com.janita.plugin.common.icons.CodeEditorUtil;
import com.janita.plugin.common.icons.PluginIcons;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.window.table.CrQuestionHouse;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

/**
 * 搜索弹窗
 *
 * @author zhucj
 */
public class CrQuestionEditDialog extends DialogWrapper {

    private static final String MANUAL_TIPS = "手动指派在此输入";

    private static final int DEFAULT_WIDTH = 600;

    private static final int DEFAULT_HEIGHT = 630;

    private final JBTextField manualAssignerField = new JBTextField(MANUAL_TIPS, 10);

    private final CrQuestion question;

    private final boolean add;

    private final Integer editIndex;

    private final Editor questionCodeEditor;

    private final Editor betterCodeEditor;

    private final Editor descCodeEditor;

    private final JBCheckBox sendWeChatBox = new JBCheckBox("发送消息");

    private final ComboBox<String> typeBox = new ComboBox<>(DataToInit.QUESTION_TYPE_LIST.toArray(new String[0]));

    private final ComboBox<String> levelBox = new ComboBox<>(DataToInit.LEVEL_LIST.toArray(new String[0]));

    private final ComboBox<String> stateBox = new ComboBox<>(CrQuestionState.getDescArray());

    private final ComboBox<String> assignBox;

    private final Set<String> assignToSet;

    private final Project project;

    private final String rawQuestionCode;

    public CrQuestionEditDialog(Project project, CrQuestion question, Set<String> assignToSet, boolean add, Integer editIndex) {
        super(true);
        sendWeChatBox.setSelected(add);
        this.question = question;
        this.rawQuestionCode = question.getQuestionCode();
        this.add = add;
        this.editIndex = editIndex;
        this.assignToSet = assignToSet;
        assignBox = new ComboBox<>(Lists.newArrayList(assignToSet).toArray(new String[0]));
        this.project = project;
        questionCodeEditor = CodeEditorUtil.createCodeEditor(project);
        betterCodeEditor = CodeEditorUtil.createCodeEditor(project);
        descCodeEditor = CodeEditorUtil.createCodeEditor(project);
        Disposer.register(project, () -> EditorFactory.getInstance().releaseEditor(questionCodeEditor));
        Disposer.register(project, () -> EditorFactory.getInstance().releaseEditor(betterCodeEditor));
        Disposer.register(project, () -> EditorFactory.getInstance().releaseEditor(descCodeEditor));
        // 设置尺寸
        getRootPane().setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        // 设置确定按钮标题
        setOKButtonText("保存");
        setComponentSize();
        init();

        addListener();
    }

    private void addListener() {
        manualAssignerField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (MANUAL_TIPS.equals(manualAssignerField.getText())) {
                    manualAssignerField.setText("");
                }
            }
        });
    }

    private void setComponentSize() {
        Dimension size = manualAssignerField.getSize();
        manualAssignerField.setMaximumSize(new Dimension(50, (int) size.getHeight()));

        questionCodeEditor.getComponent().setMinimumSize(new Dimension(100, 150));
        betterCodeEditor.getComponent().setMinimumSize(new Dimension(100, 150));
        descCodeEditor.getComponent().setMinimumSize(new Dimension(100, 100));

        questionCodeEditor.getComponent().setMaximumSize(new Dimension(100, 200));
        betterCodeEditor.getComponent().setMaximumSize(new Dimension(100, 200));
        descCodeEditor.getComponent().setMaximumSize(new Dimension(100, 150));
    }

    @Override
    protected void doOKAction() {
        boolean sendWeChatMsg = sendWeChatBox.isSelected();
        if (add) {
            CrQuestionHouse.add(question, sendWeChatMsg);
        } else {
            CrQuestionHouse.update(editIndex, question, sendWeChatMsg);
        }
        super.doOKAction();
        dispose();
    }

    private void rebuildQuestionWhenSave() {
        question.setType((String) typeBox.getSelectedItem());
        question.setState((String) stateBox.getSelectedItem());
        question.setLevel((String) levelBox.getSelectedItem());
        question.setAssignTo(getAssigner());
        question.setBetterCode(betterCodeEditor.getDocument().getText());
        question.setDescription(descCodeEditor.getDocument().getText());
    }

    @Override
    public @Nullable
    JComponent getPreferredFocusedComponent() {
        return betterCodeEditor.getComponent();
    }

    @NotNull
    @Override
    protected Action[] createActions() {
        return new Action[] { getOKAction(), getCancelAction() };
    }

    @Override
    @Nullable
    protected JComponent createCenterPanel() {
        // 创建弹窗顶部的搜索参数相关的组件
        JComponent topPanel = buildCenterTopPanel();
        JPanel row1 = new JPanel(new BorderLayout());
        row1.add(topPanel, BorderLayout.NORTH);
        Box row2 = Box.createVerticalBox();
        // TAB 切换区域
        JBTabbedPane questionCodeTab = new JBTabbedPane();
        questionCodeTab.addTab("问题代码(不要修改)：", PluginIcons.Description/*左侧icon*/, questionCodeEditor.getComponent());

        JBTabbedPane betterCodeTab = new JBTabbedPane();
        betterCodeTab.addTab("建议写法：", PluginIcons.Description/*左侧icon*/, betterCodeEditor.getComponent());

        JBTabbedPane descTab = new JBTabbedPane();
        descTab.addTab("其他描述：", PluginIcons.Description/*左侧icon*/, descCodeEditor.getComponent());

        row2.add(questionCodeTab);
        row2.add(betterCodeTab);
        row2.add(descTab);
        JBSplitter p = new JBSplitter(true/*垂直的*/, 0.1F/*比例*/);
        p.setFirstComponent(row1);
        p.setSecondComponent(row2);

        return p;
    }

    @Override
    protected @Nullable
    ValidationInfo doValidate() {
        rebuildQuestionWhenSave();
        if (StringUtils.isBlank(question.getType())) {
            return new ValidationInfo("请选择问题类型");
        }
        if (StringUtils.isBlank(question.getState())) {
            return new ValidationInfo("请选择问题状态");
        }
        if (StringUtils.isBlank(question.getAssignTo())) {
            return new ValidationInfo("请把该问题指派给他人或者自己");
        }
        if (StringUtils.isBlank(question.getLevel())) {
            return new ValidationInfo("请选择问题级别");
        }
        if (StringUtils.isBlank(question.getBetterCode()) && StringUtils.isBlank(question.getDescription())) {
            return new ValidationInfo("建议写法跟描述至少写一项");
        }
        if (!rawQuestionCode.equals(questionCodeEditor.getDocument().getText())) {
            return new ValidationInfo("问题代码不可修改");
        }
        if (question.getAssignTo().length() > 8) {
            return new ValidationInfo("指派人名称长度不能超过7个字符");
        }
        return null;
    }

    /**
     * 创建弹窗顶部的搜索参数相关的组件
     *
     * @return 创建弹窗顶部的搜索参数相关的组件
     */
    @NotNull
    private JComponent buildCenterTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel westPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        westPanel.add(new JBLabel("类型"));
        westPanel.add(typeBox);
        westPanel.add(new JBLabel("状态"));
        westPanel.add(stateBox);
        westPanel.add(new JBLabel("级别"));
        westPanel.add(levelBox);
        westPanel.add(new JBLabel("指派"));
        westPanel.add(assignBox);
        westPanel.add(manualAssignerField);
        westPanel.add(sendWeChatBox);
        topPanel.add(westPanel, BorderLayout.WEST);
        return topPanel;
    }

    public void open() {
        setQuestionDataToComponent();
        show();
    }

    private void setQuestionDataToComponent() {
        CodeEditorUtil.setCode(project, questionCodeEditor, question.getQuestionCode());
        CodeEditorUtil.setCode(project, betterCodeEditor, question.getBetterCode());
        CodeEditorUtil.setCode(project, descCodeEditor, question.getDescription());
        CodeEditorUtil.setEditorHighlighter(questionCodeEditor, question.getLanguage());
        CodeEditorUtil.setEditorHighlighter(betterCodeEditor, question.getLanguage());
        CodeEditorUtil.setEditorHighlighter(descCodeEditor, question.getLanguage());
        if (question.getType() != null) {
            typeBox.setSelectedItem(question.getType());
        }
        if (question.getLevel() != null) {
            levelBox.setSelectedItem(question.getLevel());
        }
        if (question.getState() != null) {
            stateBox.setSelectedItem(question.getState());
        }
        if (question.getType() != null) {
            typeBox.setSelectedItem(question.getType());
        }
        if (question.getAssignTo() != null) {
            assignBox.setSelectedItem(question.getAssignTo());
        }
        setTitle(question.getProjectName() + "-" + question.getCreateGitBranchName() + "-" + question.getFileName());
        if (add) {
            stateBox.setSelectedItem(CrQuestionState.UNSOLVED.getDesc());
            stateBox.setEnabled(false);
        }
        if (emptyAssign()) {
            setToManualAssign();
        }
    }

    private void setToManualAssign() {
        assignBox.setEnabled(false);
        manualAssignerField.setEnabled(true);
    }

    private boolean emptyAssign() {
        return assignToSet == null || (assignToSet.size() == 1 && assignToSet.contains(PluginConstant.PLEASE_MANUAL_ASSIGN));
    }

    private String getAssigner() {
        String assignTo = manualAssignerField.getText();
        if (StringUtils.isBlank(assignTo) || MANUAL_TIPS.equals(assignTo)) {
            assignTo = (String) assignBox.getSelectedItem();
        }
        if ("手动指派在此输入".equals(assignTo) || PluginConstant.PLEASE_MANUAL_ASSIGN.equals(assignTo)) {
            return null;
        }
        return assignTo;
    }
}
