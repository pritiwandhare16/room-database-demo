package com.example.roomdbdemo.data.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = NoteEntity.class,version = 1)
public abstract class NoteRoomDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static volatile NoteRoomDatabase noteRoomDatabase;

    public static NoteRoomDatabase getNoteRoomDatabase(final Context context)
    {
        if(noteRoomDatabase==null)
        {
            synchronized ((NoteRoomDatabase.class))
            {
                if(noteRoomDatabase==null)
                {
                    noteRoomDatabase= Room.databaseBuilder(context.getApplicationContext(),NoteRoomDatabase.class,"note_database").build();
                }
            }
        }
       return noteRoomDatabase;
    }
}
