package com.example.simplenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.simplenote.Model.DatabaseHandler;
import com.example.simplenote.Model.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    DatabaseHandler handler;
    static List<Note> noteList = new ArrayList<Note>();
    static ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.add_note)
        {
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            String date = df.format(Calendar.getInstance().getTime());

            Note note = new Note("",date,"No title");
            MainActivity.noteList.add(note);
            int id=MainActivity.noteList.size()-1;
            handler.addNote(note);
            MainActivity.arrayAdapter.notifyDataSetChanged();
            Intent intent= new Intent(MainActivity.this,Main2Activity.class);
            intent.putExtra("noteId",noteList.get(id).getId());
            startActivity(intent);
            return true;
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= (ListView) findViewById(R.id.listView);
        handler= new DatabaseHandler(MainActivity.this);
        List<Note > list= handler.getNotes();
        noteList.addAll(list);
        arrayAdapter=new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, noteList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("noteId",noteList.get(position).getId());
                startActivity(intent);
            }



        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final int itemDelete=position;
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Are you sure delete this note?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                noteList.remove(position);

                                arrayAdapter.notifyDataSetChanged();
                                handler.deleteNote(noteList.get(position).getId());
                            }
                        }).setNegativeButton("no",null).show();

                return true;
            }
        });
    }


}
