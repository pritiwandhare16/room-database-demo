package com.example.roomdbdemo.ui.main.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.roomdbdemo.data.roomdatabase.NoteEntity;
import com.example.roomdbdemo.ui.main.viewmodel.NoteViewModel;
import com.example.roomdbdemo.ui.main.viewmodelfactory.NoteViewModelFactory;
import com.example.roomdbdemo.R;
import com.example.roomdbdemo.databinding.ActivityNewNoteBinding;

public class NewNoteActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String NOTE_ADDED ="note_added" ;
    public static final String NOTE_ID ="note_id" ;
    ActivityNewNoteBinding binding;
    NoteViewModel noteViewModel;
    String noteId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(NewNoteActivity.this, R.layout.activity_new_note);


        //Instantiate Noteviewmodel
        noteViewModel=new ViewModelProvider(this,new NoteViewModelFactory(this.getApplication())).get(NoteViewModel.class);

        String callFrom=getIntent().getStringExtra("from");
        if(callFrom!=null && callFrom.equals("update"))
        {
            binding.btnAdd.setVisibility(View.GONE);
            binding.llUpdate.setVisibility(View.VISIBLE);
             noteId=getIntent().getStringExtra("noteId");
            noteViewModel.getNote(noteId).observe(this, new Observer<NoteEntity>() {
                @Override
                public void onChanged(NoteEntity noteEntity) {
                    binding.etNote.setText(noteEntity.getNote());
                }
            });
        }
        binding.btnAdd.setOnClickListener(this);


        if(binding.llUpdate.getVisibility()==View.VISIBLE)
        {
            binding.btnCancel.setOnClickListener(this);
            binding.btnUpdate.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_add:
                  Intent resultIntent=new Intent();
                if(TextUtils.isEmpty(binding.etNote.getText()))
                {
                    setResult(RESULT_CANCELED,resultIntent);
                    Toast.makeText(getApplicationContext(),"please add note",Toast.LENGTH_SHORT).show();
                }else {
                    String note=binding.etNote.getText().toString();
                    resultIntent.putExtra(NOTE_ADDED,note);
                    setResult(RESULT_OK,resultIntent);
                    finish();
                }
                break;
            case R.id.btn_cancel: finish();
                break;
            case R.id.btn_update:
                Intent resultIntentUpdate=new Intent();
                if(TextUtils.isEmpty(binding.etNote.getText()))
                {
                    setResult(RESULT_CANCELED,resultIntentUpdate);
                    Toast.makeText(getApplicationContext(),"please add note",Toast.LENGTH_SHORT).show();
                }else {
                    String note=binding.etNote.getText().toString();
                    resultIntentUpdate.putExtra(NOTE_ADDED,note);
                    resultIntentUpdate.putExtra(NOTE_ID,noteId);
                    setResult(RESULT_OK,resultIntentUpdate);
                    finish();
                }
                break;
        }
    }
}
