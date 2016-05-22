package com.dmytro.notes19_2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Class that is responsible for interaction with DataBase
 */
@Deprecated
public class DataSaverDB {
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;
    private static DataSaverDB instance;


    /**
     * Constructor
     *
     * @param context
     */
    private DataSaverDB(Context context) {
        mDatabaseHelper = new DatabaseHelper();
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    /**
     * get instance of DataSaverDB
     */
    public static DataSaverDB getInstance(Context context) {
        if (instance == null) {
            instance = new DataSaverDB(context);
        }
        return instance;
    }

    /**
     * Adds specified Note to DB
     *
     * @param note Note instance need to be added to DB
     */
    public void addNoteToDB(Note note) {
        ContentValues value = mGetContentFormNote(note);
        //add to DB
        mDatabase.insert(DatabaseHelper.DATABASE_TABLE, null, value);
    }


    /**
     * Adds all notes from array to DB
     *
     * @param notes Array of notes
     */
    public void saveAllNotesToDB(ArrayList<Note> notes) {
        for (Note note : notes) {
            addNoteToDB(note);
        }
    }

    /**
     * Updating note in DB
     * changing note with id of specified note in DB
     * with specified note
     *
     * @param note need to be updated
     */
    public void updateNote(Note note) {
        ContentValues value = mGetContentFormNote(note);
        mDatabase.update(DatabaseHelper.DATABASE_TABLE,
                value,
                DatabaseHelper.NOTE_ID_COLUMN + "= ?", new String[]{Integer.toString(note.getID())});
    }

    /**
     * Deleting specified note from DB
     *
     * @param note have to be deleted
     */
    public void deleteNote(Note note) {
        mDatabase.delete(DatabaseHelper.DATABASE_TABLE,
                DatabaseHelper.NOTE_ID_COLUMN + "= ?",
                new String[]{Integer.toString(note.getID())});
    }

    /**
     * Getting all notes from data base
     *
     * @return array of notes from DB
     */
    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<>();

        Cursor cursor = mDatabase.query(DatabaseHelper.DATABASE_TABLE,
                new String[]{DatabaseHelper.NOTE_ID_COLUMN, DatabaseHelper.NOTE_TEXT_COLUMN,
                        DatabaseHelper.NOTE_COLOR_COLUMN, DatabaseHelper.NOTE_PHOTO_PATH_COLUMN},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            int noteId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.NOTE_ID_COLUMN));
            int colorOfNote = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.NOTE_COLOR_COLUMN));
            String textOfNote = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NOTE_TEXT_COLUMN));
            String pathToPhoto = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NOTE_PHOTO_PATH_COLUMN));
            notes.add(new Note(noteId, colorOfNote, textOfNote, pathToPhoto));
        }

        cursor.close();

        return notes;
    }

    /**
     * Find max id in DB
     *
     * @return maximum note's id in DB
     */
    public int getIdOfLastNote() {
        int maxID = 0;

        Cursor cursor = mDatabase.query(DatabaseHelper.DATABASE_TABLE,
                new String[]{DatabaseHelper.NOTE_ID_COLUMN, DatabaseHelper.NOTE_TEXT_COLUMN,
                        DatabaseHelper.NOTE_COLOR_COLUMN, DatabaseHelper.NOTE_PHOTO_PATH_COLUMN},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            int noteId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.NOTE_ID_COLUMN));
            if (maxID < noteId) {
                maxID = noteId;
            }
        }

        cursor.close();

        return maxID;
    }

    /**
     * Forming Content to DB from Note
     * (preparing data to put in DB)
     *
     * @param note to get data from
     * @return ContentValues - data ready to put in DB
     */
    private ContentValues mGetContentFormNote(Note note) {
        ContentValues value = new ContentValues();

        value.put(DatabaseHelper.NOTE_ID_COLUMN, note.getID());
        value.put(DatabaseHelper.NOTE_TEXT_COLUMN, note.getText());
        value.put(DatabaseHelper.NOTE_COLOR_COLUMN, note.getColor());
        value.put(DatabaseHelper.NOTE_PHOTO_PATH_COLUMN, note.getPhotoPath());
        //temporary
        value.put(DatabaseHelper.CREATION_DATE_COLUMN, 0);
        value.put(DatabaseHelper.LAST_UPDATE_DATE_COLUMN, 0);
        value.put(DatabaseHelper.ARCHIVED_NOTE_COLUMN, 0);

        return value;
    }


}
