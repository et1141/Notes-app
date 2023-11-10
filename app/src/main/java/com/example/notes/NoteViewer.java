package com.example.notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class NoteViewer extends Fragment {
    private List<List<String>> updatedListOfLists = null;
    private CSVManager csvManager;

    private final String fileName = "cache5.csv";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Initialize CSVManager with the fragment's context
        csvManager = new CSVManager(context);
    }

    public void setUpdatedListOfLists(String id, String title, String note, String checked) {
        for(List<String> s : updatedListOfLists){
            if(s.get(0).compareTo(id) == 0){
                s.set(1,title);
                s.set(2, note);
                s.set(3, checked);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_viewer, container, false);

        int newNoteId = 0;

        Bundle arguments = getArguments();

        try {
            Log.d("arguments", arguments.toString());
        } catch(Exception e){
            Log.d("no arguments", "no");
        }
        if (arguments != null) { // we are seeing it after new note adding
            // ako kliknem cancel search, samo da ide na no args rendering
            // kad kliknem na search moram vidjeti sta se desava, ovo je scenario da se nesto
            // stvarno pretrazilo
            Log.d("MOJE", "argumenti nisu null");

            String searchString = "";
            String searching = "";
            // ne mogu imati i dodavanje i search u isto vr
            try {
                searchString = arguments.getString("search").toLowerCase();
                searching = arguments.getString("searching");
            } catch (Exception e){ }

            // ako je prazan search, pokreni bez args
            // i to bi trebalo da je ok
            if (searching.equals("y")) {

                // there is searching
                List<List<String>> listOfLists = csvManager.readCSVFromFile(fileName);
                List<List<String>> searchedList = new ArrayList<>();
                for (List<String> note: listOfLists){
                    String noteTitle = note.get(1).toLowerCase();
                    String noteContent = note.get(2).toLowerCase();
                    if (noteTitle.contains(searchString))
                        searchedList.add(note);
                }
                LinearLayout notesLayout = view.findViewById(R.id.linearLayoutNotes);
                setupNotes(view, notesLayout, searchedList, true, searchString, searchedList.size());

            }
                else {
                String id_n = arguments.getString("id");
                String title_n = arguments.getString("title");
                String content_n = arguments.getString("content");
                String checked_n = arguments.getString("checked");
                // puknuce ako nema
                //String ser = arguments.getString("search", null);
                //CSVManager csvManager = new CSVManager(getResources());

                try {
                    //List<List<String>> listOfLists = csvManager.readCSVFromRawResource(R.raw.cache);
                    List<List<String>> listOfLists = csvManager.readCSVFromFile(fileName);
                    Boolean found = false;
                    String t_id = "";
                    for (List<String> s : listOfLists) {
                        if (s.get(0).compareTo(id_n) == 0) {
                            found = true;
                            t_id = s.get(0);
                            s.set(1, title_n);
                            s.set(2, content_n);
                            s.set(3, checked_n);
                        }
                    }
                    Toast.makeText(getActivity(), "Modified: " + found.toString() + "\nID: " + t_id, Toast.LENGTH_SHORT).show();
                    if (found == false) {

                        Toast.makeText(getActivity(), "New note created", Toast.LENGTH_SHORT).show();

                        List<String> temp = new ArrayList<>();
                        String lastID = listOfLists.get(listOfLists.size() - 1).get(0);
                        Integer lastIdInt = Integer.parseInt(lastID);
                        lastIdInt++;
                        String id = lastIdInt.toString();
                        temp.add(id);
                        temp.add(title_n);
                        temp.add(content_n);
                        temp.add(checked_n);
                        listOfLists.add(temp);

                    }
                    //write back to the file
                    csvManager.writeCSVToFile(fileName, listOfLists);
                    LinearLayout notesLayout = view.findViewById(R.id.linearLayoutNotes);
                    setupNotes(view, notesLayout, listOfLists, false, "", 0);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            //CSVManager csvManager = new CSVManager(getResources());

            try {

                List<List<String>> listOfLists = csvManager.readCSVFromFile(fileName);

                if (updatedListOfLists == null){
                    updatedListOfLists = listOfLists;
                }
                if (updatedListOfLists != null && listOfLists != updatedListOfLists){
                    listOfLists = updatedListOfLists;
                }

                LinearLayout notesLayout = view.findViewById(R.id.linearLayoutNotes);
                setupNotes(view, notesLayout, listOfLists, false, "", 0);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Button newNote = view.findViewById(R.id.newNoteButton);
        newNote.setOnClickListener(v -> ((MainActivity) getActivity()).switchToAddNote());

        Button searchNotes = view.findViewById(R.id.buttonSearch);
        searchNotes.setOnClickListener(v -> ((MainActivity) getActivity()).searchNotes(""));


        return view;
    }

    private void setupNotes(View view, LinearLayout notesLayout, List<List<String>> listOfLists, boolean searched, String str, int results) {
        if (searched) {
            TextView tv = view.findViewById(R.id.search_results_id);
            String text = "Displaying " + results + " search results for: " + str;
            tv.setText(text);
            tv.setVisibility(View.VISIBLE);
            TextView cs = view.findViewById(R.id.clearSearch);
            cs.setVisibility(View.VISIBLE);

        }
        for (List<String> row : listOfLists) {
            // Ensure that there are at least two elements (title and content)

            if (row.size() >= 3) {
                String id = row.get(0);
                String title = row.get(1);
                String content = row.get(2);
                String checked = row.get(3); // The third element

                // Create a LinearLayout for each note
                LinearLayout noteLayout = new LinearLayout(this.getContext());
                noteLayout.setOrientation(LinearLayout.VERTICAL);

                noteLayout.setBackgroundResource(R.color.black);
                noteLayout.setGravity(Gravity.CENTER);
                int paddingInDp = 16; // Adjust the padding size as needed
                float scale = getResources().getDisplayMetrics().density;
                int paddingInPixels = (int) (paddingInDp * scale);
                noteLayout.setPadding(paddingInPixels, paddingInPixels, paddingInPixels, paddingInPixels);
                if (notesLayout.getChildCount() > 0) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    );
                    layoutParams.setMargins(paddingInPixels, paddingInPixels, paddingInPixels, paddingInPixels);
                    noteLayout.setLayoutParams(layoutParams);
                }

                TextView titleTextView = new TextView(this.getContext());
                titleTextView.setText(title);
                titleTextView.setTextSize(18); // You can adjust the text size
                titleTextView.setGravity(Gravity.CENTER); // Center the title
                titleTextView.setTextColor(getResources().getColor(R.color.white));

                noteLayout.addView(titleTextView);

                // Add the content as a TextView below the title
                TextView contentTextView = new TextView(this.getContext());
                contentTextView.setText(content);
                contentTextView.setGravity(Gravity.CENTER);
                contentTextView.setTextColor(getResources().getColor(R.color.white));
                noteLayout.addView(contentTextView);

                // Set an onClickListener for the LinearLayout
                noteLayout.setOnClickListener(v -> ((MainActivity) getActivity()).switchToNoteEditor(id, title, content, checked));

                // maybe this is the issue, then i'd need new final for every change
                List<List<String>> finalListOfLists = listOfLists;
                noteLayout.setOnLongClickListener(
                        new View.OnLongClickListener() {
                            @Override

                            public boolean onLongClick(View v) { // TODO: Launch Image Picker and Save Image as Avatar return false;
                                Log.d("Challenge2", "open dialog message");
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Do you want to edit or delete this note?").setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int idd) {
                                                List<List<String>> withDeleted = new ArrayList<>();
                                                for (List<String> s : finalListOfLists) {
                                                    if (s.get(0).compareTo(id) != 0) {
                                                        withDeleted.add(s);
                                                    }
                                                }

                                                csvManager.writeCSVToFile(fileName, withDeleted);

                                                ((MainActivity) getActivity()).switchToNoteViewerNoArgs();
                                            }
                                        })
                                        .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int idd) {
                                                ((MainActivity) getActivity()).switchToNoteEditor(id, title, content, checked);
                                            }
                                        });
                                // Create the AlertDialog object and return it.
                                builder.create();
                                builder.show();
                                return true;
                            }
                        }
                );
                notesLayout.addView(noteLayout);

            }
        }
    }




}