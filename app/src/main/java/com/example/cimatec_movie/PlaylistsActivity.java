package com.example.cimatec_movie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlaylistsActivity extends AppCompatActivity {
    private Button btnVoltar;
    private ListView lstview;
    private List<String> usuarios;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);
        btnVoltar = findViewById(R.id.btnVoltar);
        lstview = findViewById(R.id.lstPlaylists);
        usuarios = new ArrayList<>();

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MinhaplaylistActivity.class);
                startActivity(intent);
            }
        });

        DatabaseReference playlist = reference.child("playlist");

        playlist.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot playlistnapshot : dataSnapshot.getChildren()) {
                    String matricula = playlistnapshot.getKey();
                    usuarios.add(matricula);
                }
                updateList();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usuarios);
        lstview.setAdapter(adapter);

        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String matricula = usuarios.get(position);
                Intent intent = new Intent(PlaylistsActivity.this, FilmesActivity.class);
                intent.putExtra("matricula", matricula);
                startActivity(intent);
            }
        });
    }
}