package com.example.notes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**

 * create an instance of this fragment.
 */
//public class DeleteDialogFragment extends DialogFragment {
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the Builder class for convenient dialog construction.
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMessage("Do you want to edit or delete this note?")
//                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // START THE GAME!
//                    }
//                })
//                .setNegativeButton("delete", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User cancels the dialog.
//                    }
//                });
//        // Create the AlertDialog object and return it.
//        return builder.create();
//    }
//}