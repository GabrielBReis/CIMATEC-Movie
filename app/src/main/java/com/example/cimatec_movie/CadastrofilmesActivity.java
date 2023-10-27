package com.example.cimatec_movie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                    // Aqui você pode adicionar lógica para obter a "matricula" ou outra chave relevante
                    // para onde você deseja salvar o filme.

                    // Crie um objeto "filme" com os dados inseridos pelo usuário
                    filme novoFilme = new filme();
                    novoFilme.setNome(nome);
                    novoFilme.setAno(Integer.parseInt(ano));
                    novoFilme.setCurtida(0);

                    // Use a referência do Firebase para salvar o novo filme
                    DatabaseReference filmesReference = reference.child("playlist").child(RA);
                    String novaChave = filmesReference.push().getKey();
                    filmesReference.child(novaChave).setValue(novoFilme);

                    // Exiba uma mensagem de sucesso
                    Toast.makeText(CadastrofilmesActivity.this, "Filme adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    // Caso os campos estejam vazios, você pode tratar isso de acordo com sua lógica.
                    Toast.makeText(CadastrofilmesActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
