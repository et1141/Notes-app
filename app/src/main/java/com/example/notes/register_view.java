package com.example.notes;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class register_view extends Fragment {
    public TextView registerDialog_TextView;
    private EditText username_EdidText, password_EditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_view, container, false);

        registerDialog_TextView = view.findViewById(R.id.register_information);
        EditText username_EdidText = view.findViewById(R.id.username_txt);
        EditText password_EditText = view.findViewById(R.id.password_txt);
        Button back_btn = view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).switchLogin();
            }

        });

        Button register_btn = view.findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username_EdidText.getText().toString();
                String password = password_EditText.getText().toString();
                if (check_if_correct_input(username)&& check_if_correct_input(password)){
                    usersOpenHelper users_helper = new usersOpenHelper(requireContext());
                    SQLiteDatabase db = users_helper.getWritableDatabase();
                    MainActivity activity = (MainActivity) getActivity();
                    activity.addUser(db,username,activity.hash_password(password),1);
                    activity.switchLogin();
                }
                else {
                    registerDialog_TextView.setText("Username&Password restrictions: min len: 3, max len: 20");
                    registerDialog_TextView.setTextColor(Color.RED);
                }
            }

        });

        return view;
    }
    boolean check_if_correct_input(String text){
        return true;
    }
}