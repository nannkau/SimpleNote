package com.example.simplenote.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "noteManager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "notes";

    private static final String KEY_ID = "id";
    private static final String KEY_TEXT = "text";
    private static final String KEY_TIME = "time";
    private static final String KEY_TITLE = "title";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEXT, note.getText());
        values.put(KEY_TIME, note.getTime());
        values.put(KEY_TITLE,note.getTitle());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void deleteNote(int noteId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(noteId) });
        db.close();
    }
    public Note findById(int noteId){

        String query = "SELECT * FROM " + TABLE_NAME+" WHERE "+KEY_ID+" = "+String.valueOf(noteId) ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        Note     note = new Note(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getString(3));



        return note;
    }
    public void updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TEXT, note.getText());
        values.put(KEY_TIME, note.getTime());
        values.put(KEY_TITLE,note.getTitle());
        db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(note.getId()) });
        db.close();
    }
    public List<Note> getNotes(){
        List<Note>  noteList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false) {
            Note note = new Note(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getString(3));
            noteList.add(note);
            cursor.moveToNext();
        }
        return noteList;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_notes_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT NOT NULL, %s TEXT)", TABLE_NAME, KEY_ID, KEY_TEXT, KEY_TIME,KEY_TITLE);
        db.execSQL(create_notes_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_notes_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_notes_table);

        onCreate(db);
    }
}
