package com.dmytro.notes19_2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PhotoButtonHandler extends Activity {
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    //id of button
    private final int idPhotoButton;

    //photo path
    private String mCurrentPhotoPath;

    //pointer to MainActivity
    Activity mainActivity;

    //photo-button
    private Button photoButton;

    //file that keeps photo
    private File photoFile;

    //Notes keeper instance
    //NotesKeeper notesKeeper;

    /**
     * Constructor
     *
     * @param activity point to mainActivity
     */
    PhotoButtonHandler(Activity activity, int photoButtonId) {
        this.idPhotoButton = photoButtonId;
        photoButton = (Button) activity.findViewById(idPhotoButton);///1
        this.mainActivity = activity;
       // this.notesKeeper = NotesKeeper.getInstance(activity);
        Button photoButton = (Button) activity.findViewById(idPhotoButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

//    public handler of onResult


    /**
     * Creating file for image
     *
     * @return file for image
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }


    /**
     * starts camera activity
     * prepares file ready to save image
     */
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(mainActivity.getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                //do something if needed
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
//                mainActivity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                mainActivity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }

    /**
     * onActivityResult() from MainActivity uses this method
     * when camera maid image
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void handleResult(int requestCode, int resultCode, Intent data) {
        Note newNote = new Note(photoFile);
        NoteHelper.addNote(newNote);
        ViewCreator viewCreator = new ViewCreator(mainActivity, newNote);
    }
}



