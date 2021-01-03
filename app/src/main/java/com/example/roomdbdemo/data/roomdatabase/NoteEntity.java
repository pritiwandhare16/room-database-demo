package com.example.roomdbdemo.data.roomdatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class NoteEntity {
    @PrimaryKey
    @NonNull
    private String noteId;
    private String note;

    public NoteEntity(String noteId, String note) {
        this.noteId = noteId;
        this.note = note;
    }

    public String getNoteId() {
        return noteId;
    }

    public String getNote() {
        return note;
    }
}
