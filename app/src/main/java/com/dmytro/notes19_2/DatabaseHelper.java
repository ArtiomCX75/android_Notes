package com.dmytro.notes19_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
        * db helper
        * all work with db will be via this class
*/
public class DatabaseHelper extends SQLiteOpenHelper {

    //name of DB
    private static final String DATABASE_NAME = "notesDataBase.db";
    //version of DB
    private static final int DATABASE_VERSION = 1;

    //name of table
    public static final String DATABASE_TABLE = "notes";

    //columns
    public static final String NOTE_ID_COLUMN = "noteID";
    //    public static final String NOTE_TITLE_COLUMN = "Title";
    public static final String NOTE_TEXT_COLUMN = "noteText";
    public static final String NOTE_COLOR_COLUMN = "colorOfNote";
    public static final String NOTE_PHOTO_PATH_COLUMN = "passOfPhoto";
    public static final String CREATION_DATE_COLUMN = "creationDate";
    public static final String LAST_UPDATE_DATE_COLUMN = "lastUpdateDate";
    public static final String ARCHIVED_NOTE_COLUMN = "isInArchive";

    //scripts
    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + DATABASE_TABLE + " ("
            + NOTE_ID_COLUMN           + " integer,"
            + NOTE_TEXT_COLUMN         + " text,"
            + NOTE_COLOR_COLUMN        + " integer,"
            + NOTE_PHOTO_PATH_COLUMN   + " text,"
            + CREATION_DATE_COLUMN     + " integer,"
            + LAST_UPDATE_DATE_COLUMN  + " integer,"
            + ARCHIVED_NOTE_COLUMN     + " integer);";

public static Context tempContext;
    /**
     * creator
     *
     * @param context activity
     */

    private static SQLiteDatabase db;
    public static SQLiteDatabase getDB() {
        if (db == null) {
            db = new DatabaseHelper().getWritableDatabase();
        }
        return db;
    }

    public DatabaseHelper() {
        super(tempContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * creator
     *
     * @param context activity
     * @param name    data base name
     * @param factory cursor
     * @param version of DB
     */
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    /**
     * Creation of DB
     *
     * @param db data base
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }


    /**
     * @param db         database
     * @param oldVersion of DB (id)
     * @param newVersion of DB (id)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //delete existing table
        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
        // create new one
        onCreate(db);
    }

    public static void addValue(Context context, String tableName, ContentValues value){
        getDB().insert(DATABASE_TABLE, null, value);
    }
}
