package com.dmytro.notes19_2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

   // NotesKeeper notesKeeper;
    PhotoButtonHandler photoButtonHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseHelper.tempContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //   notesKeeper = NotesKeeper.getInstance(this);

        ButtonsHandler textButton = new ButtonsHandler(this, R.id.addNoteByTextButton);
        ButtonsHandler voiceButton = new ButtonsHandler(this, R.id.addVoiceNoteButton);
        ButtonsHandler photoButton = new ButtonsHandler(this, R.id.addPhotoNoteButton);
        photoButtonHandler = photoButton.getPhotoButtonHandlerInstance();
    }


    @Override
    protected void onStart() {
        DatabaseHelper.tempContext = this;
        super.onStart();
      //  notesKeeper = NotesKeeper.getInstance(this);
      //  Note.drawNotes(this, notesKeeper.getAllNotes());
        NoteHelper.drawAllNotes(this);
    }


    @Override
    protected void onResume() {
        DatabaseHelper.tempContext = this;
        super.onResume();
      //  notesKeeper = NotesKeeper.getInstance(this);
     //   Note.drawNotes(this, notesKeeper.getAllNotes());
        NoteHelper.drawAllNotes(this);
    }

    @Override
    protected void onStop() {
//        notesKeeper.saveAllNotesToDisk();
        ViewCreator.hideKeyboard(this);
        super.onStop();
    }


    /**
     * processing data from camera
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            photoButtonHandler.handleResult(requestCode, resultCode, data);
        }
    }

}

