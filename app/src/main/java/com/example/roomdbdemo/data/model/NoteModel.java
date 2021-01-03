package com.example.roomdbdemo.data.model;

public class NoteModel {
    private String noteId;
    private String note;

    public NoteModel(String note) {
        this.note = note;
    }


    public String getNote() {
        if(note==null)
            return "";
        return note;
    }



}
