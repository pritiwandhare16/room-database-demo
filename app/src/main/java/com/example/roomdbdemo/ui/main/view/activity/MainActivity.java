package com.example.roomdbdemo.ui.main.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.roomdbdemo.DeleteNoteListener;
import com.example.roomdbdemo.data.roomdatabase.NoteEntity;
import com.example.roomdbdemo.ui.main.adapter.NoteListAdapter;
import com.example.roomdbdemo.ui.main.viewmodel.NoteViewModel;
import com.example.roomdbdemo.ui.main.viewmodelfactory.NoteViewModelFactory;
import com.example.roomdbdemo.R;
import com.example.roomdbdemo.UpdateNoteListener;
import com.example.roomdbdemo.databinding.ActivityMainBinding;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding dataBinding;
public static int NEW_NOTE_ACTIVITY_REQUEST_CODE=1;
public static int UPDATE_NOTE_ACTIVITY_REQUEST_CODE=2;
NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        dataBinding= DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        //Instantiate Noteviewmodel
        noteViewModel=new ViewModelProvider(this,new NoteViewModelFactory(this.getApplication())).get(NoteViewModel.class);

        dataBinding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, NewNoteActivity.class);
                i.putExtra("from","new_note");
                startActivityForResult(i,NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });
        final NoteListAdapter adapter=new NoteListAdapter(this, new DeleteNoteListener() {
            @Override
            public void deleteNote(String noteIdm) {
                deleteNotes(noteIdm);
            }
        }, new UpdateNoteListener() {
            @Override
            public void updateNote(String noteIdm) {
                updateNotes(noteIdm);
            }
        });
        dataBinding.rvNotes.setAdapter(adapter);
        dataBinding.rvNotes.setLayoutManager(new LinearLayoutManager(this));
        noteViewModel.getAllNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                adapter.setNotes(noteEntities);
            }
        });
    }

    private void updateNotes(String noteIdm) {
        Intent intent=new Intent(MainActivity.this,NewNoteActivity.class);
        intent.putExtra("from","update");
        intent.putExtra("noteId",noteIdm);
        startActivityForResult(intent,UPDATE_NOTE_ACTIVITY_REQUEST_CODE);
    }

    private void deleteNotes(String  mPosition) {
        noteViewModel.deleteNote(mPosition);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                final String note_id = UUID.randomUUID().toString();
                NoteEntity noteEntity = new NoteEntity(note_id, data.getStringExtra(NewNoteActivity.NOTE_ADDED));
                noteViewModel.insert(noteEntity);
                Toast.makeText(getApplicationContext(), R.string.saved, Toast.LENGTH_SHORT).show();
        }else if(requestCode==UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK)
        {
            NoteEntity noteEntity = new NoteEntity(data.getStringExtra(NewNoteActivity.NOTE_ID), data.getStringExtra(NewNoteActivity.NOTE_ADDED));
            noteViewModel.updateNote(noteEntity);
            Toast.makeText(getApplicationContext(), R.string.saved, Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(getApplicationContext(), R.string.not_save, Toast.LENGTH_SHORT).show();
        }
    }
}
