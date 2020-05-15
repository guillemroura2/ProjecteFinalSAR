package com.example.projectefinal.ui.moviments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovimentsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MovimentsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is moviments fragment");
    }



    /*TODO: public LiveData<String> getText() {
        return mText;
    }*/
}