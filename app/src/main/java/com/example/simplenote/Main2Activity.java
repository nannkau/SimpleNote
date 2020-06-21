package com.example.simplenote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.simplenote.Model.DatabaseHandler;
import com.example.simplenote.Model.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    int id;
    EditText editText1;
    TextView textView1;
    Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editText= (EditText) findViewById(R.id.editText);
        textView= (TextView) findViewById(R.id.textView);
        textView1=(TextView) findViewById(R.id.textView2);
        editText1=(EditText) findViewById(R.id.editText2);
        final DatabaseHandler handler=new DatabaseHandler(Main2Activity.this);
        Intent intent = getIntent();
        id= intent.getIntExtra("noteId",-1);
        List<Note >  list = handler.getNotes();
         note= handler.findById(id);
        textView.setText("Last update: "+note.getTime());
        editText1.setText(note.getTitle());
        editText.setText(note.getText());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                String date = df.format(Calendar.getInstance().getTime());
                //Note note= new Note(MainActivity.noteList.get(id).getId(),String.valueOf(s),date,editText1.getText().toString() );
                note.setText(String.valueOf(s));
                note.setTime(date);
                note.setTitle(editText1.getText().toString() );
                //MainActivity.noteList.set(id-1,note);
                handler.updateNote(note);
                List<Note >  list = handler.getNotes();
                MainActivity.noteList.clear();
                MainActivity.noteList.addAll(list);
                MainActivity.arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                String date = df.format(Calendar.getInstance().getTime());
//                Note note= new Note(MainActivity.noteList.get(id).getId(),editText.getText().toString(),date,String.valueOf(s) );
//                MainActivity.noteList.set(id,note);
                note.setTitle(String.valueOf(s));
                note.setTime(date);
                note.setText(editText.getText().toString() );
                handler.updateNote(note);
                List<Note >  list = handler.getNotes();
                MainActivity.noteList.clear();
                MainActivity.noteList.addAll(list);
                MainActivity.arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
