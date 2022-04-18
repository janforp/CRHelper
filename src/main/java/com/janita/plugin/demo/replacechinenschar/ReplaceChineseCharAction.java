package com.janita.plugin.demo.replacechinenschar;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;

/**
 * 在写代码的时候自动替换中文字符，如中文都会，。。。
 *
 * @author zhucj
 * @since 20220324
 */
public class ReplaceChineseCharAction extends AnAction {

    static {
        final EditorActionManager editorActionManager = EditorActionManager.getInstance();
        final TypedAction typedAction = editorActionManager.getTypedAction();
        typedAction.setupHandler(new ReplaceChineseCharTypedHandler(typedAction.getHandler()));
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
    }
}
