package com.dmytro.notes19_2;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Class responsible for buttons initialization
 */
public class ButtonsHandler {
    private Button makeNewNote;
    private SpeechHandler speechHandler;
    private PhotoButtonHandler photoHandler;
    private boolean isSpeechRecognizerStart=false; //additional verification of speech recognizer

    private final int TEXT_BUTTON_ID = R.id.addNoteByTextButton;
    private final int VOICE_BUTTON_ID = R.id.addVoiceNoteButton;
    private final int PHOTO_BUTTON_ID = R.id.addPhotoNoteButton;

   // NotesKeeper notesKeeper;

    /**
     * Constructor
     * depends on button ID initialize button as
     * text button, voice button or photo button
     *
     * @param activity that keeps button
     * @param buttonId id of button
     */
    ButtonsHandler(Activity activity, int buttonId) {
        switch (buttonId) {
            case TEXT_BUTTON_ID:
                setTextButtonHandler(activity, TEXT_BUTTON_ID);
                break;
            case VOICE_BUTTON_ID:
                setVoiceButtonHandler(activity, VOICE_BUTTON_ID);
                break;
            case PHOTO_BUTTON_ID:
                setPhotoButtonHandler(activity, PHOTO_BUTTON_ID);
                break;
            default:
                Log.d("ButtonsHandler", "wrong ID of button");
        }
    }

    private void setTextButtonHandler(final Activity activity, int text_button_id) {
        // notesKeeper = NotesKeeper.getInstance(activity);
        makeNewNote = (Button) activity.findViewById(R.id.addNoteByTextButton);

        makeNewNote.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             * Create new empty Note,
             * pass it to ViewCreator
             * asks ViewCreator to show EditText
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Note newNote = new Note("");
           //     notesKeeper.add(newNote);
                NoteHelper.addNote(newNote);
                ViewCreator viewCreator = new ViewCreator(activity, newNote);
                viewCreator.changeToEditText(activity);
            }
        });

    }

    /**
     * Initializing voice button actions
     *
     * @param activity activity
     * @param voice_button_id id of voice button
     */
    private void setVoiceButtonHandler(Activity activity, int voice_button_id) {
        speechHandler = new SpeechHandler(activity);

        makeNewNote = (Button) activity.findViewById(voice_button_id);
        assert makeNewNote != null;

        makeNewNote.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_DOWN)&&!isSpeechRecognizerStart) {
                    isSpeechRecognizerStart=true;
                    startSpeech();
                } else if ((event.getAction() == MotionEvent.ACTION_UP)&&isSpeechRecognizerStart) {
                    stopSpeech();
                    isSpeechRecognizerStart=false;
                }
                return false;
            }
        });
    }

    /**
     * start of listening for voice recognition
     */
    private void startSpeech() {
        speechHandler.startListening();
    }

    /**
     * stops listening speech
     */
    private void stopSpeech() {
        speechHandler.stopListening();
    }


    private void setPhotoButtonHandler(Activity activity, int photo_button_id) {
        photoHandler = new PhotoButtonHandler(activity, photo_button_id);
    }



    private void setTextButtonHandler(final Activity activity, int text_button_id) {
        notesKeeper = NotesKeeper.getInstance(activity);
        makeNewNote = (Button) activity.findViewById(text_button_id);

        makeNewNote.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             * Create new empty Note,
             * pass it to ViewCreator
             * asks ViewCreator to show EditText
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Note newNote = new Note("");
                notesKeeper.add(newNote);
                ViewCreator viewCreator = new ViewCreator(activity, newNote);
                viewCreator.changeToEditText(activity);
            }
        });

    }


    public PhotoButtonHandler getPhotoButtonHandlerInstance() {
        return photoHandler;
    }

}
