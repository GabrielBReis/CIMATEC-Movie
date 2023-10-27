package com.example.cimatec_movie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button btnConectar;
    private EditText edtRA;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConectar = findViewById(R.id.btnConectar);
        edtRA = findViewById(R.id.edtRA);

        btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String RA = edtRA.getText().toString();
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                // Verifique se o "RA" já existe no Firebase
                reference.child("playlist").child(RA).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // O "RA" já existe, vá para a MinhaplaylistActivity
                            Intent intent = new Intent(getApplicationContext(), MinhaplaylistActivity.class);
                            intent.putExtra("RA-Usuario", RA);
                            startActivity(intent);
                        } else {
                            // O "RA" não existe, crie um novo nó na playlist
                            reference.child("playlist").child(RA).setValue("novo_valor_inicial");

                            // Agora vá para a MinhaplaylistActivity
                            Intent intent = new Intent(getApplicationContext(), MinhaplaylistActivity.class);
                            intent.putExtra("RA-Usuario", RA);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Trate erros, se necessário.
                    }
                });
            }
        });
    }
}
