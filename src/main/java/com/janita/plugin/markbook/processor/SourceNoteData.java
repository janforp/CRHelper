package com.janita.plugin.markbook.processor;

import com.janita.plugin.markbook.data.NoteData;

import java.util.List;

public interface SourceNoteData {

    String getFileName();

    String getTopic();

    List<NoteData> getNoteList();
}