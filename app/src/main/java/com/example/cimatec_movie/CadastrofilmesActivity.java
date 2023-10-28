package com.example.cimatec_movie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastrofilmesActivity extends AppCompatActivity {
    private Button btnAdicionar, btnVoltarMP;
    private EditText edtNome, edtAno;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cadastrofilmes);
        String RA = getIntent().getStringExtra("RA-Usuario");

        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnVoltarMP = findViewById(R.id.btnVoltarMP);
        edtAno = findViewById(R.id.edtAno);
        edtNome = findViewById(R.id.edtNome);

        btnVoltarMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MinhaplaylistActivity.class);
                startActivity(intent);
            }
        });

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = edtNome.getText().toString().trim();
                String ano = edtAno.getText().toString().trim();

                if (!nome.isEmpty() && !ano.isEmpty()) {
                    DatabaseReference filmes = reference.child("playlist");

                    filmes.child(RA).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            filme novoFilme = new filme();
                            novoFilme.setNome(nome);
                            novoFilme.setAno(Integer.parseInt(ano));
                            novoFilme.setCurtida(0);

                            long numFilmes = dataSnapshot.getChildrenCount();
                            String nomeFilme = "filme" + (numFilmes + 1);
                            //DatabaseReference filmesReference = reference.child("playlist").child(RA);
                            //String novaChave = filmesReference.push().getKey();
                            //filmesReference.child(novaChave).setValue(novoFilme);
                            filmes.child(RA).child(nomeFilme).setValue(novoFilme);
                        }
                    });
                    Toast.makeText(CadastrofilmesActivity.this, "Filme adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CadastrofilmesActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}