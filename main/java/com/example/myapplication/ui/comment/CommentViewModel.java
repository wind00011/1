package com.example.myapplication.ui.comment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CommentViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CommentViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is comment fragment");  // 正確設置值
    }

    public LiveData<String> getText() {
        return mText;
    }
}