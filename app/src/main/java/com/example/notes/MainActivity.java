package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FragmentTransaction fragmentTransaction;
    MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String fileName = "cache5.csv"; // Replace with the desired file name
        String fileContent = "0,Note,Notes,False";

        NotesViewModel model = new ViewModelProvider(this).get(NotesViewModel.class);
        //populateAnimals(model);
        model.getUiState().observe(this, uiState -> {
            // update UI
        });

        File file = new File(getFilesDir(), fileName);
        if (!file.exists()) {
            try {
                FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                fos.write(fileContent.getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_view, NoteViewer.class, null);
        fragmentTransaction.replace(R.id.fragment_view, new login_view());
        fragmentTransaction.commit();


    }

    public void switchToNoteEditor(String id, String title, String content, String checked){
        NoteEditor noteFragment = new NoteEditor();

        // Pass the note's title and content as arguments
        Bundle args = new Bundle();
        args.putString("id",id);
        args.putString("title", title);
        args.putString("content", content);
        args.putString("checked", checked);
        noteFragment.setArguments(args);

        // Open the NoteFragment using a FragmentTransaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_view, noteFragment); // R.id.fragment_container is a placeholder in your layout where the fragment will be displayed
        transaction.addToBackStack(null); // Allow back navigation
        transaction.commit();
    };

    public void switchToNoteViewer(String id, String title, String content, String checked) {
        NoteViewer noteFragment = new NoteViewer();

        Bundle args = new Bundle();
        args.putString("id",id);
        args.putString("title", title);
        args.putString("content", content);
        args.putString("checked", checked);
        noteFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_view, noteFragment); // R.id.fragment_container is a placeholder in your layout where the fragment will be displayed
        transaction.addToBackStack(null); // Allow back navigation
        transaction.commit();
    }

    public void switchToNoteViewerNoArgs(){
        NoteViewer noteFragment = new NoteViewer();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_view, noteFragment); // R.id.fragment_container is a placeholder in your layout where the fragment will be displayed
        transaction.addToBackStack(null); // Allow back navigation
        transaction.commit();

    }

    public void switchToAddNote() {
        NewNote noteFragment = new NewNote();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_view, noteFragment); // R.id.fragment_container is a placeholder in your layout where the fragment will be displayed
        transaction.addToBackStack(null); // Allow back navigation
        transaction.commit();
    }
    public void switchRegister() {
        register_view register_view = new register_view();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_view, register_view); // R.id.fragment_container is a placeholder in your layout where the fragment will be displayed
        //  transaction.addToBackStack(null); // Allow back navigation
        transaction.commit();
    }
    public void switchLogin() {
        login_view login_view = new login_view();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_view, login_view); // R.id.fragment_container is a placeholder in your layout where the fragment will be displayed
        //  transaction.addToBackStack(null); // Allow back navigation
        transaction.commit();
    }

    public void searchNotes(String searchStr){
        EditText editSearch = findViewById(R.id.editSearch);
        searchStr = editSearch.getText().toString();

        if (searchStr.equals("")){
            switchToNoteViewerNoArgs();
            return;
        }

        Toast.makeText(getApplicationContext(), "Trazimo", Toast.LENGTH_SHORT).show();
        NoteViewer noteFragment = new NoteViewer();

        // Pass the note's title and content as arguments
        Bundle args = new Bundle();
        args.putString("search", searchStr);
        args.putString("searching", "y");
        noteFragment.setArguments(args);

        // Open the NoteFragment using a FragmentTransaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_view, noteFragment); // R.id.fragment_container is a placeholder in your layout where the fragment will be displayed
        transaction.addToBackStack(null); // Allow back navigation
        transaction.commit();

    }
    public void addUser(SQLiteDatabase db, String username, String password, int permission) {
        ContentValues values = new ContentValues();
        values.put("user", username.toLowerCase());
        values.put("password", password.toLowerCase());
        values.put("permission", permission);
        db.insert("Users", null, values);
    }
    public String hash_password(String password) {
        try {
            return EncryptionManager.toHexString(EncryptionManager.getSHA(password));//encoder.encode(password);
            }
        catch (NoSuchAlgorithmException e)
        {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }
        return "";
    }

    public void clearSearch(View view) {
       switchToNoteViewerNoArgs();
    }


}
