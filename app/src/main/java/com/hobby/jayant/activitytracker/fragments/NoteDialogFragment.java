package com.hobby.jayant.activitytracker.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hobby.jayant.activitytracker.R;

/**
 * Created by I329687 on 8/21/2017.
 */

public  class  NoteDialogFragment extends DialogFragment {

    static private final String TAG = "NoteDialogFragment";
    private EditText noteText ;
    /* The activity that creates an instance of this dialog fragment must
    * implement this interface in order to receive event callbacks.
    * Each method passes the DialogFragment in case the host needs to query it.
    * */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String noteText);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;



    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            try {
                // Instantiate the NoticeDialogListener so we can send events to the host
                mListener = (NoticeDialogListener) context;
            } catch (ClassCastException e) {
                // The activity doesn't implement the interface, throw exception
                throw new ClassCastException(context.toString()
                        + " must implement NoticeDialogListener");
            }
        }

    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View diaogView = inflater.inflate(R.layout.note_popup_window, null);
        noteText = (EditText) diaogView.findViewById(R.id.noteText);
        builder.setView(diaogView)
                .setMessage("Add Note")
                .setPositiveButton(R.string.addNote, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        Log.d(TAG, "onDialogPositiveClick call");

                        String noteTextValue = noteText.getText().toString();
                        mListener.onDialogPositiveClick(NoteDialogFragment.this,noteTextValue);
                        Log.d(TAG, "onDialogPositiveClick end");
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(NoteDialogFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.note_popup_window, container, false);
        getDialog().setTitle(R.string.notePopupTitle);

        return rootView;
    }

}
