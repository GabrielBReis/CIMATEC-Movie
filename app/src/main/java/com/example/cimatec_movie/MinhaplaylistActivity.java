package com.example.cimatec_movie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class MinhaplaylistActivity extends AppCompatActivity {
    private ListView lstMP;
    private List<filme> filmesList1 = new ArrayList<>();
    private Button btnAdicionarFilme, btnTodasPL;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhaplaylist);

        String RA = getIntent().getStringExtra("RA-Usuario");
        Log.d("FirebaseData", "RA-Usuario: " + RA);

        lstMP = findViewById(R.id.lstMP);
        btnAdicionarFilme = findViewById(R.id.btnAdicionarFilme);
        btnTodasPL = findViewById(R.id.btnTodasPL);

        DatabaseReference MinhaPlaylist = FirebaseDatabase.getInstance().getReference().child("playlist").child(RA);

        MinhaPlaylist.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                filmesList1.clear();
                for (DataSnapshot filmeSnapshot : dataSnapshot.getChildren()) {
                    filme filme = filmeSnapshot.getValue(filme.class);
                    filmesList1.add(filme);
                    Log.d("FirebaseData", "\nNome: " + filme.getNome() + ",\n Ano: " + filme.getAno() + ",\n Curtida: " + filme.getCurtida());
                }
                updateList();
                Log.d("FirebaseData", "MinhaPlaylist Reference: " + MinhaPlaylist.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Trate erros, se necessário.
            }
        });

        btnAdicionarFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CadastrofilmesActivity.class);
                intent.putExtra("RA-Usuario", RA);
                startActivity(intent);
            }
        });

        btnTodasPL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlaylistsActivity.class);
                intent.putExtra("MinhaPlaylist", RA);
                startActivity(intent);
            }
        });
    }

    private void updateList() {
        ArrayAdapter<filme> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filmesList1);
        lstMP.setAdapter(adapter);

    }
}