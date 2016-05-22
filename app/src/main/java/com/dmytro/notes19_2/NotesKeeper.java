package com.dmytro.notes19_2;

import android.content.Context;

import java.util.ArrayList;
@Deprecated
public class NotesKeeper {
    private static NotesKeeper instance;
    private static ArrayList<Note> notes;
    private static DataSaverDB dataSaverDB;

    private NotesKeeper(Context context) {
        dataSaverDB = DataSaverDB.getInstance(context);
        notes = dataSaverDB.getAllNotes();
    }

    //public methods

    /**
     * Returning instance of NotesKeeper
     *
     * @return instance of NotesKeeper
     */
    public static NotesKeeper getInstance(Context context) {
        if (instance == null) {
            instance = new NotesKeeper(context);
        }
        return instance;
    }

    /**
     * adds specified note to NotesKeeper
     *
     * @param note has to be added
     */
    public void add(Note note) {
        notes.add(note);
        dataSaverDB.addNoteToDB(note);
    }

    /**
     * Removes specified note from NotesKeeper
     *
     * @param note has to be removed
     */
    public static void remove(Note note) {
        notes.remove(note);
        dataSaverDB.deleteNote(note);
    }

    /**
     * Returning all notes
     *
     * @return array of notes
     */
    public ArrayList<Note> getAllNotes() {
        return notes;
    }

    public static void update(Note note) {
        dataSaverDB.updateNote(note);
    }

//    /**
//     * Saving notes to disk
//     */
//    public void saveAllNotesToDisk() {
//        DataSaver.saveNotes(notes);
//    }
}
