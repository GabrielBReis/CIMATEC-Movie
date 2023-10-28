package com.example.cimatec_movie;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetalhesActivity extends AppCompatActivity {

    private Button btnCurtirFilme, btnVoltarFilmes;
    private TextView txtNome, txtAno, txtCurtida;
    private String RA_filme, IdFilme, RA;
    private filme filme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        txtNome = findViewById(R.id.txtNome);
        txtAno = findViewById(R.id.txtAno);
        txtCurtida = findViewById(R.id.txtCurtida);
        btnCurtirFilme = findViewById(R.id.btnCurtir);
        btnVoltarFilmes = findViewById(R.id.btnVoltarFilmes);

        RA_filme = getIntent().getStringExtra("RA-Filme");
        IdFilme = getIntent().getStringExtra("IdFilme");
        RA = getIntent().getStringExtra("RA-Usuario");

        DatabaseReference DetalhesFilme = FirebaseDatabase.getInstance().getReference().child("playlist").child(RA_filme).child(IdFilme);

        DetalhesFilme.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                filme = snapshot.getValue(filme.class);
                if (filme != null) {
                    txtNome.setText(filme.getNome());
                    txtAno.setText(String.valueOf(filme.getAno()));
                    txtCurtida.setText(String.valueOf(filme.getCurtida()));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Trate erros, se necessário.
            }
        });

        btnCurtirFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filme != null) {
                    int curtida = filme.getCurtida();
                    curtida +=1;
                    DetalhesFilme.child("curtida").setValue(curtida);
                    txtCurtida.setText(curtida);
                }
            }
        });

        btnVoltarFilmes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FilmesActivity.class);
                intent.putExtra("RA-Filme", RA_filme);
                intent.putExtra("MinhaPlaylist", RA);
                startActivity(intent);
            }
        });
    }
}
