package com.example.notes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class NotesViewModel extends ViewModel {

    private final MutableLiveData<List<List<String>>> uiState =
            new MutableLiveData(new ArrayList<>());
    public LiveData<List<List<String>>> getUiState() {
        return uiState;
    }
}
