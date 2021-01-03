package com.example.roomdbdemo.ui.main.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.roomdbdemo.data.roomdatabase.NoteDao;
import com.example.roomdbdemo.data.roomdatabase.NoteEntity;
import com.example.roomdbdemo.data.roomdatabase.NoteRoomDatabase;
import com.example.roomdbdemo.ui.main.view.activity.MainActivity;
import com.example.roomdbdemo.ui.main.view.activity.NewNoteActivity;

import java.util.List;

import static com.example.roomdbdemo.ui.main.view.activity.MainActivity.NEW_NOTE_ACTIVITY_REQUEST_CODE;

public class NoteViewModel extends AndroidViewModel {

    private String TAG=this.getClass().getSimpleName();
    private NoteDao noteDao;
    private NoteRoomDatabase noteRoomDatabase;
    private LiveData<List<NoteEntity>> mAllNotes;
    private LiveData<NoteEntity> mNote;
    private Application application;
    MutableLiveData<String> errorNote=new MutableLiveData<>();

    public NoteViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        noteRoomDatabase= NoteRoomDatabase.getNoteRoomDatabase(application);
        noteDao=noteRoomDatabase.noteDao();
        mAllNotes=noteDao.getAllNotes();
    }


    /*Task related to database operation start*/
    public LiveData<List<NoteEntity>> getAllNotes()
    {
        return mAllNotes;
    }

    public void insert(NoteEntity noteEntity)
    {
        new InsertAsyncTaskClass(noteDao).execute(noteEntity);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG,"Viewmodel destroy");
    }

    public void deleteNote(String id) {
        new DeleteAsyncTask(noteDao).execute(id);

    }

    public LiveData<NoteEntity> getNote(String noteId) {
        mNote=noteDao.getNote(noteId);
        return  mNote;
    }

    public void updateNote(NoteEntity noteEntity) {
        new UpdateAsyncTaskClass(noteDao).execute(noteEntity);
    }


    private class InsertAsyncTaskClass extends AsyncTask<NoteEntity,Void,Void> {

        NoteDao mnoteDao;
        public InsertAsyncTaskClass(NoteDao noteDao)
        {
            this.mnoteDao=noteDao;
        }
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            mnoteDao.insert(noteEntities[0]);
            return null;
        }
    }


    private class DeleteAsyncTask extends AsyncTask<String,Void,Void>{
        NoteDao noteDao;
        public DeleteAsyncTask(NoteDao noteDao) {
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(String... id) {
            noteDao.deleteNote(id[0]);
            return null;
        }
    }

    private class UpdateAsyncTaskClass extends AsyncTask<NoteEntity,Void,Void> {
        NoteDao mnoteDao;
        public UpdateAsyncTaskClass(NoteDao noteDao) {
            mnoteDao=noteDao;
        }

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            mnoteDao.updateNote(noteEntities[0]);
            return null;
        }
    }
    /* Task related to database operation end */


}
