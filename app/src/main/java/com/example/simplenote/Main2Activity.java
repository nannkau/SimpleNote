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
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    int id;
    EditText editText1;
    TextView textView1;
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
        textView.setText("Last update: "+ MainActivity.noteList.get(id).getTime());
        editText1.setText(MainActivity.noteList.get(id).getTitle());
        editText.setText(MainActivity.noteList.get(id).getText());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                String date = df.format(Calendar.getInstance().getTime());
                Note note= new Note(id,String.valueOf(s),date,editText1.getText().toString() );
                MainActivity.noteList.set(id,note);
                handler.updateNote(note);
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
                Note note= new Note(editText.getText().toString(),date,String.valueOf(s) );
                MainActivity.noteList.set(id,note);
                handler.updateNote(note);
                MainActivity.arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
