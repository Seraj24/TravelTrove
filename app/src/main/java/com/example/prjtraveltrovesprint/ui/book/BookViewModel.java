package com.example.prjtraveltrovesprint.ui.book;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BookViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("TO DO: book view");
    }

    public LiveData<String> getText() {
        return mText;
    }
}