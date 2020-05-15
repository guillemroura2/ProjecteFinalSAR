package com.example.projectefinal.ui.musica;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MusicaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MusicaViewModel() {
    }

    public LiveData<String> getText() {
        return mText;
    }
}