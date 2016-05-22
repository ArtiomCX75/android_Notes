package com.dmytro.notes19_2;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by faa11 on 21.05.2016.
 * Class for making easier work with notes and db
 */
public class NoteHelper {
    public static ArrayList<Note> noteList = new ArrayList<>();

    public static void addNote(Note note){
        DatabaseHelper.getDB().insert(DatabaseHelper.DATABASE_TABLE, null, note.toContentValue());
        updateAllNotes();
    }


    public static void updateNote(Note note){
        String where = DatabaseHelper.NOTE_ID_COLUMN+"="+note.getID();
        DatabaseHelper.getDB().update(DatabaseHelper.DATABASE_TABLE, note.toContentValue(), where, null);
        updateAllNotes();
    }


    public static void deleteNote(Note note){
        String where = DatabaseHelper.NOTE_ID_COLUMN+"="+note.getID();
        DatabaseHelper.getDB().delete(DatabaseHelper.DATABASE_TABLE, where, null);
        updateAllNotes();
    }


    public static void updateAllNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        Cursor cursor =DatabaseHelper.getDB().query(DatabaseHelper.DATABASE_TABLE,
                new String[]{DatabaseHelper.NOTE_ID_COLUMN, DatabaseHelper.NOTE_TEXT_COLUMN,
                        DatabaseHelper.NOTE_COLOR_COLUMN, DatabaseHelper.NOTE_PHOTO_PATH_COLUMN},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            int noteId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.NOTE_ID_COLUMN));
            int colorOfNote = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.NOTE_COLOR_COLUMN));
            String textOfNote = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NOTE_TEXT_COLUMN));
            String pathToPhoto = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NOTE_PHOTO_PATH_COLUMN));

            notes.add(new Note().setID(noteId).setColor(colorOfNote).setText(textOfNote).setPhotoFilePath(pathToPhoto));
        }
        cursor.close();
        noteList = notes;
    }

    /**
     * Draw all notes in array,
     * if they haven't drawn yet
     *
     * @param activity we are working with
     */
    public static void drawAllNotes(Activity activity) {
        updateAllNotes(); //temporary
        ((LinearLayout) activity.findViewById(R.id.layoutNotesKeeper)).removeAllViews();
        //null and empty array handler
        if (noteList == null || noteList.isEmpty()) {
            return;
        }

        //check are there any notes on the layout
        //if no - draw all notes
        if (activity.findViewById(noteList.get(0).getID()) == null) {
            for (Note note : noteList) {
                ViewCreator viewCreator = new ViewCreator(activity, note);
            }
        }
    }

}
