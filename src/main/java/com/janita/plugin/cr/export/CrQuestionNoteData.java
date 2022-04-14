package com.janita.plugin.cr.export;

import com.janita.plugin.cr.domain.CrQuestion;

import java.util.List;

/**
 * CrQuestionNoteData
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionNoteData {

    private final String fileName;

    private String topic;

    private List<CrQuestion> questionList;

    public CrQuestionNoteData(String fileName, String topic, List<CrQuestion> questionList) {
        this.fileName = fileName;
        this.topic = topic;
        this.questionList = questionList;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTopic() {
        return fileName;
    }

    public List<CrQuestion> getNoteList() {
        return questionList;
    }
}
