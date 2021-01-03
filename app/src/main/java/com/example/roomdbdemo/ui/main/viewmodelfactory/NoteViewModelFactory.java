package com.example.roomdbdemo.ui.main.viewmodelfactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.roomdbdemo.ui.main.viewmodel.NoteViewModel;

public class NoteViewModelFactory implements ViewModelProvider.Factory {
    private Application application;

    public NoteViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new NoteViewModel(application);
    }
}
