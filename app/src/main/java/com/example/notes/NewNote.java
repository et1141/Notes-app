package com.example.notes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewNote#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewNote extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewNote() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewNote.
     */
    // TODO: Rename and change types and number of parameters
    public static NewNote newInstance(String param1, String param2) {
        NewNote fragment = new NewNote();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_note, container, false);

        // Retrieve the note's content and title from arguments or your data source


        Button saveButton = view.findViewById(R.id.buttonSaveEditor);
        Button backButton = view.findViewById(R.id.buttonBackEditor);

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                EditText editTitle = view.findViewById(R.id.editTitleEditText);
                EditText editNote = view.findViewById(R.id.editNoteEditText);
                String checked = "False";
                if(editTitle.getText().toString().compareTo("") == 0 || editNote.getText().toString().compareTo("") == 0){
                    Toast.makeText(getActivity(),"Enter Title and Note!",Toast.LENGTH_SHORT).show();
                }else{
                    ((MainActivity) getActivity()).switchToNoteViewer("-1", editTitle.getText().toString(), editNote.getText().toString(), checked);
                }


            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).switchToNoteViewerNoArgs();
            }
        });

        return view;
    }
}