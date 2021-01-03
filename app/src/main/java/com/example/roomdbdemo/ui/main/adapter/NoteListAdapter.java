package com.example.roomdbdemo.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdbdemo.DeleteNoteListener;
import com.example.roomdbdemo.R;
import com.example.roomdbdemo.UpdateNoteListener;
import com.example.roomdbdemo.data.roomdatabase.NoteEntity;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<NoteEntity> noteEntitiesList;
    private DeleteNoteListener deleteListener;
    private UpdateNoteListener updateNoteListener;

    public NoteListAdapter(Context context, DeleteNoteListener deleteListener,UpdateNoteListener updateNoteListener) {
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        this.deleteListener=deleteListener;
        this.updateNoteListener=updateNoteListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=layoutInflater.inflate(R.layout.list_item,parent,false);
        NoteViewHolder viewHolder=new NoteViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if(noteEntitiesList!=null)
        {
            NoteEntity noteEntity=noteEntitiesList.get(position);
            holder.setData(noteEntity.getNote(),noteEntity.getNoteId(),position);
        }

    }

    @Override
    public int getItemCount() {
        if(noteEntitiesList!=null)
           return noteEntitiesList.size();
        else return 0;
    }

    public void setNotes(List<NoteEntity> noteEntities) {
        this.noteEntitiesList=noteEntities;
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

       private TextView tvNote;
       private ImageView ivUpdate,ivDelete;
       private int mPosition;
       private String noteIdm;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNote=itemView.findViewById(R.id.tv_note);
            ivDelete=itemView.findViewById(R.id.iv_delete);
            ivUpdate=itemView.findViewById(R.id.iv_update);
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteListener.deleteNote(noteIdm);
                }
            });
            ivUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateNoteListener.updateNote(noteIdm);
                }
            });
        }


        public void setData(String note,String noteId, int position) {
            tvNote.setText(note);
            mPosition=position;
            noteIdm=noteId;
        }
    }


}
