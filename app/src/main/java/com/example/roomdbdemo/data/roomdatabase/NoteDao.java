package com.example.roomdbdemo.data.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insert(NoteEntity noteEntity);

    @Query("SELECT * FROM notes")
    LiveData<List<NoteEntity>> getAllNotes();


    @Query("DELETE FROM notes WHERE noteId=:id")
    void deleteNote(String id);

    @Query("SELECT * FROM notes WHERE noteId=:noteId")
    LiveData<NoteEntity> getNote(String noteId);

    @Update
    void updateNote(NoteEntity noteEntity);
}
