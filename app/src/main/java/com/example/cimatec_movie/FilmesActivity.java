package com.example.cimatec_movie;

import androidx.appcompat.app.AppCompatActivity;
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

public class FilmesActivity extends AppCompatActivity {

    private ListView lstview2;
    private Button btnVoltar;
    private List<filme> filmesList = new ArrayList<>();
    private String RA_filme, RA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes);

        lstview2 = findViewById(R.id.lstFilmes);
        btnVoltar = findViewById(R.id.btnVPlaylist);
        RA_filme = getIntent().getStringExtra("RA-Playlist");
        RA = getIntent().getStringExtra("RA-Usuario");

        DatabaseReference filmesRef = FirebaseDatabase.getInstance().getReference().child("playlist").child(RA_filme);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlaylistsActivity.class);
                intent.putExtra("MinhaPlaylist", RA);
                startActivity(intent);
            }
        });

        filmesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                filmesList.clear();
                for (DataSnapshot filmeSnapshot : dataSnapshot.getChildren()) {
                    filme filme = filmeSnapshot.getValue(filme.class);
                    filmesList.add(filme);
                }
                updateList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Trate erros, se necessário.
            }
        });
    }

    private void updateList() {
        ArrayAdapter<filme> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filmesList);
        lstview2.setAdapter(adapter);
        lstview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetalhesActivity.class);
                String IdFilme = "filme" + (position + 1);
                intent.putExtra("RA-Filme", RA_filme);
                intent.putExtra("IdFilme", IdFilme);
                startActivity(intent);
            }
        });
    }
}
