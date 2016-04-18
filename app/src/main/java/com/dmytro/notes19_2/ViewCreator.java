package com.dmytro.notes19_2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

/**
 * ViewCreator
 * ask for note should be shown
 * and adds it to specified layout
 */
//todo change layout naming and usage
public class ViewCreator {
    String textOfNote;
    int colorOfNote;
    int id;

    //Note's text keepers
    private ViewSwitcher vs;
    private EditText editText;
    private TextView textView;

    //Note's photo keeper
    ImageView imageNote;

    /**
     * Constructor of class
     * for text and voice notes
     *
     * @param activity - current activity
     * @param note     - the note has to be shown
     */
    ViewCreator(Activity activity, Note note) {
        this.id = note.getID();

        /**
         * checking is this note text/from voice or a photo note
         * if bitmap is null so it is text/voice note and we create TextView(ViewSwitcher)
         * else it's photo note and we creating ImageView
         */
        if (note.bitmap == null) {
            this.colorOfNote = note.getColor();
            this.textOfNote = note.getText();

            createViewSwitcher(activity);
            LinearLayout layout = (LinearLayout) activity.findViewById(R.id.layout1);
            layout.addView(vs);
        } else {
            createImageNote(activity, note.bitmap);
        }
    }

    /**
     * creating ImageNote
     * and adds it to layout
     *
     * @param activity current
     * @param bitmap   image of note
     */
    private void createImageNote(Activity activity, Bitmap bitmap) {
        imageNote.setImageBitmap(bitmap);
        LinearLayout layout = (LinearLayout) activity.findViewById(R.id.layout1);
        layout.addView(imageNote);
    }

    /**
     * Constructor of class
     * for photo note
     *
     * @param activity  current view
     * @param imageView photo note
     */
    ViewCreator(Activity activity, ImageView imageView) {
        LinearLayout layout = (LinearLayout) activity.findViewById(R.id.layout1);
        layout.addView(imageView);
    }

    /**
     * asking current ViewText change TextView To EditText
     */
    public void changeToEditText(Activity activity) {
        editText.setText(textOfNote);
        vs.showNext();
        editText.requestFocus();
        //todo clean up it!!!
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, 0);
        //todo: rewrite it: handle situation when vs.showNext() is null, i.e. vs is already shows EditText
    }

    /**
     * ViewSwitcher changing displaying views between
     * EditText and TextView
     */
    private void createViewSwitcher(Activity activity) {
        vs = new ViewSwitcher(activity);
        vs.setId(id);
        vs.setBackgroundColor(colorOfNote);
        vs.setPadding(0, 10, 0, 1);

        //set params: width: MATCH_PARENT, height - WRAP_CONTENT
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 10);
        vs.setLayoutParams(layoutParams);


        createTextView(activity);
        vs.addView(textView);

        createEditText(activity);
        vs.addView(editText);
    }

    /**
     * creating TextView
     */
    private void createTextView(final Activity activity) {
        textView = new TextView(activity);
        textView.setText(textOfNote);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);

        textView.setTextSize(17);


        //onClick listener. Changing TextView to EditText when clicked
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo change next 2 lines to changeToEditText()
                editText.setText(textOfNote);
                vs.showNext();
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, 0);
                //todo cleanup this!
            }
        });
    }

    /**
     * creating EditText
     */
    private void createEditText(Activity activity) {
        editText = new EditText(activity);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(layoutParams);
        editText.setFocusableInTouchMode(true);
        View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //if unfocus EditText field, editText becomes textView again
                if (!hasFocus) {
                    //todo write showTextView, handling situation when TextView is already shown
                    textOfNote = editText.getText().toString();
                    textView.setText(textOfNote);
                    vs.showPrevious();
                }
            }
        };
        editText.setOnFocusChangeListener(focusChangeListener);
    }


}