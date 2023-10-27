package com.example.cimatec_movie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.zip.DataFormatException;

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

            String RA = edtRA.getText().toString();
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MinhaplaylistActivity.class);
                intent.putExtra("RA-Usuario", RA);
                startActivity(intent);
            }

        });

    }
}