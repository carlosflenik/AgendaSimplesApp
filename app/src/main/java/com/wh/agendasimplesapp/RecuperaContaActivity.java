package com.wh.agendasimplesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.wh.agendasimplesapp.databinding.ActivityCadastroBinding;
import com.wh.agendasimplesapp.databinding.ActivityRecuperaContaBinding;

public class RecuperaContaActivity extends AppCompatActivity {

    private ActivityRecuperaContaBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperaContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.btnRecuperaConta.setOnClickListener(v -> validaDados());

    }
    private void validaDados() {
        String email = binding.editEmail.getText().toString().trim();

        if(!email.isEmpty()){

            binding.progressBar.setVisibility(View.VISIBLE);//Mostra a Barra de Progresso.

            recuperaContaFirebase(email);

        }else{
            Toast.makeText(this, "informe o e-mail.", Toast.LENGTH_SHORT).show();
        }

    }

    private void recuperaContaFirebase(String email){
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {//Envia Senha para o E-mail.
            if(task.isSuccessful()){

                Toast.makeText(this,"e-mail enviado, favor verificar.",Toast.LENGTH_SHORT).show();

            }else{// Tratamento de Erro.

                Toast.makeText(this, "erro.", Toast.LENGTH_SHORT).show();
            }
            binding.progressBar.setVisibility(View.GONE);//Caso errado "msg ERRO".
        });
    }

}