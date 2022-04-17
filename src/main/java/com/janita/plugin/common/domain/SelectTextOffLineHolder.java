package com.janita.plugin.common.domain;

import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.VisualPosition;
import lombok.Builder;
import lombok.Data;

/**
 * SelectTextOffLineHolder
 *
 * @author zhucj
 * @since 20220324
 */
@Data
@Builder
public class SelectTextOffLineHolder {

    /**
     * 选择文本
     */
    private String selectedText;

    /**
     * 开始行
     */
    private int documentStartLine;

    /**
     * 开始行
     */
    private int documentEndLine;

    /**
     * 开始offset
     *
     * @see SelectionModel#getLeadSelectionOffset()
     */
    private int leadSelectionOffset;

    /**
     * @see SelectionModel#getSelectionStartPosition()
     * @see VisualPosition#getLine()
     */
    private int selectionStartPositionLine;

    /**
     * @see SelectionModel#getSelectionStartPosition()
     * @see VisualPosition#getColumn()
     */
    private int selectionStartPositionColumn;

    /**
     * @see SelectionModel#getSelectionEndPosition()
     * @see VisualPosition#getLine()
     */
    private int selectionEndPositionLine;

    /**
     * @see SelectionModel#getSelectionEndPosition()
     * @see VisualPosition#getColumn()
     */
    private int selectionEndPositionColumn;

    /**
     * 文件的全名称
     */
    private String fileFullName;

    public int getDocumentEndLine() {
        if (selectedText == null) {
            this.documentStartLine = 0;
            return 0;
        }
        String[] split = selectedText.split("\n");
        if (split.length == 0 || split.length == 1) {
            this.documentEndLine = documentStartLine;
        }
        return documentStartLine + split.length;
    }
}
