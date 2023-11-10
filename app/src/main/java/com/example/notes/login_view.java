package com.example.notes;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class login_view extends Fragment {
    public  TextView loginDialog_TextView;
    private EditText username_EdidText, password_EditText;
    MainActivity activity;

    //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_view, container, false);

        activity= (MainActivity) getActivity();
        loginDialog_TextView = view.findViewById(R.id.login_information);
        EditText username_EdidText = view.findViewById(R.id.username_txt);
        EditText password_EditText = view.findViewById(R.id.password_txt);

        usersOpenHelper users_helper = new usersOpenHelper(requireContext()); // Użyj requireContext() zamiast "this"
        SQLiteDatabase db = users_helper.getWritableDatabase();
        activity.addUser(db, "Benedek", activity.hash_password("admin"), 2);
        activity.addUser(db, "Smiljana", activity.hash_password("admin"), 2);
        activity.addUser(db, "Adrian", activity.hash_password("admin"), 2);
        activity.addUser(db, "nasa42", activity.hash_password("user"), 1);
        db.close();

        Button btn_login = view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username_EdidText.getText().toString();
                String password = password_EditText.getText().toString();

                usersOpenHelper users_helper = new usersOpenHelper(requireContext());
                SQLiteDatabase db = users_helper.getWritableDatabase();
                String password_hashed = activity.hash_password(password);
                Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE user = ?", new String[]{username.toLowerCase()});
                if (cursor.moveToFirst() && password_hashed.equals(cursor.getString(cursor.getColumnIndex("password")))) {
                    loginDialog_TextView.setText("Sucess");
                    loginDialog_TextView.setTextColor(Color.WHITE);
                    activity.switchToNoteViewerNoArgs();
                } else {
                    loginDialog_TextView.setText("wrong password/username");
                    loginDialog_TextView.setTextColor(Color.RED);
                }
            db.close();
            }});

        Button btn_register = view.findViewById(R.id.register_btn);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.switchRegister();
            }

        });
        return view;
    }

}