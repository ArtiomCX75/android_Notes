package com.dmytro.notes19_2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;


public class PhotoButtonHandler {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int RESULT_OK = 0; //have no idea about this value
    private Button photoButton;
    private ImageView imgView;
    Activity mainActivity;

    //pointer to arraylist of notes
    private ArrayList<Note> notes;

    PhotoButtonHandler(Activity activity, ArrayList<Note> notes) {
        photoButton = (Button) activity.findViewById(R.id.photoButton);
        this.mainActivity = activity;
        this.notes = notes;

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(mainActivity.getPackageManager()) != null) {
            mainActivity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //todo: create Note
            notes.add(new Note(imageBitmap));
        }
    }

}