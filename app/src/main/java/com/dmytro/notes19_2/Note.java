package com.dmytro.notes19_2;


import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Color;
import android.widget.LinearLayout;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


/**
 * Note is class for keeping notes
 * each note has either id, color, text
 * or id, photo
 */

public class Note implements Serializable {
    //keeps last added note's id
    private static int idGenerator = 0;

    //array of note's colors
    private final int[] colors = {Color.rgb(64, 249, 138), Color.rgb(255, 255, 102)};

    //note's id
    private int id;
    private int colorOfNote;
    private String textOfNote;

    //for photo note
    String photoFilePath = null;

    /**
     * Constructor
     * for empty notes
     *
     */
    public Note() {
    }

    /**
     * Constructor
     * for text notes
     *
     * @param text of the note
     */
    public Note(String text) {
        setID();
        setBackgroundColorOfNote();
        setText(text);
    }


    /**
     * Constructor of photo note
     *
     * @param photoFile keeps file with photo
     */
    public Note(File photoFile) {
        setID();
        this.photoFilePath = photoFile.getPath();
        setText(null);
    }

    /**
     * Constructor
     * from DB
     *
     * @param id            of Note
     * @param colorOfNote   of Note
     * @param textOfNote    of Note
     * @param photoFilePath of Note
     */

    @Deprecated
    public Note(int id, int colorOfNote, String textOfNote, String photoFilePath) {
        this.id = id;
        this.colorOfNote = colorOfNote;
        this.textOfNote = textOfNote;
        this.photoFilePath = photoFilePath;
    }

    //protected methods
    /**
     *
     *
     * @param  id of note
     * @return note
     */
    protected Note setID(int id) {
        this.id=id;
        return this;
    }

    //public methods

    /**
     * getID()
     *
     * @return ID of note
     */
    public int getID() {
        return id;
    }

    /**
     * getText()
     *
     * @return text of the note
     */
    public String getText() {
        return textOfNote;
    }

    /**
     * getColor()
     *
     * @return color of the note
     */
    public int getColor() {
        return colorOfNote;
    }

    /**
     * returns path to photo of note
     * of null if it's not a photo note
     *
     * @return path of photo
     */
    public String getPhotoPath() {
        return photoFilePath;
    }

    //public static methods



    /**
     * Set idGenerator - the variable thats sets our id initialization
     *
     * @param lastId - the id of the last note in our list
     */
    //todo here could be problem: should find out how works id adding after app reboot in case of DB
    public static void setIdGenerator(int lastId) {
        idGenerator = lastId;
    }

    /**
     * needs fo manual (outside of constructor) changing of the text of note
     *
     * @param text of the note
     * @return note
     */
    public Note setText(String text) {
        this.textOfNote = text;
        return this;
    }

    /**
     * setting photoFilePath of note
     * @param photoFilePath of the note
     * @return note
     */
    public Note setPhotoFilePath(String photoFilePath) {
        this.photoFilePath=photoFilePath;
        return this;
    }

    /**
     * Needs for manual (outside of constructor) changing of color of note
     *
     * @param color color we want to set for note
     * @return note
     */
    public Note setColor(int color) {
        this.colorOfNote = color;
        return this;
    }

    //private methods

    /**
     * sets random background color (from array) for note
     * @return note
     */
    private Note setBackgroundColorOfNote() {
        Random rnd = new Random();
//        getting random color from array of colors
        colorOfNote = colors[Math.abs(rnd.nextInt()) % colors.length];
        return this;
    }

    /**
     * setting id of note
     * @return note
     */
    private Note setID() {
        id = 1 + idGenerator++;
        return this;
    }




    /**
     * Forming Content to DB from Note
     * (preparing data to put in DB)
     *
     * @return ContentValues - data ready to put in DB
     */
    public ContentValues toContentValue() {
        ContentValues value = new ContentValues();

        value.put(DatabaseHelper.NOTE_ID_COLUMN, getID());
        value.put(DatabaseHelper.NOTE_TEXT_COLUMN, getText());
        value.put(DatabaseHelper.NOTE_COLOR_COLUMN, getColor());
        value.put(DatabaseHelper.NOTE_PHOTO_PATH_COLUMN, getPhotoPath());
        //temporary
        value.put(DatabaseHelper.CREATION_DATE_COLUMN, 0);
        value.put(DatabaseHelper.LAST_UPDATE_DATE_COLUMN, 0);
        value.put(DatabaseHelper.ARCHIVED_NOTE_COLUMN, 0);

        return value;
    }

}
