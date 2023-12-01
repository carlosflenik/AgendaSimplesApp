package com.wh.agendasimplesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.wh.agendasimplesapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.textCadastro.setOnClickListener(view -> {//Campo "Cadastro".
            startActivity(new Intent(this, CadastroActivity.class));
        });

        binding.textRecuperaConta.setOnClickListener(view ->//Campo "RecuperarConta".
                startActivity(new Intent(this, RecuperaContaActivity.class)));

        binding.btnLogin.setOnClickListener(v -> validaDados());

    }

    private void validaDados() {
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editSenha.getText().toString().trim();

        if(!email.isEmpty()){
            if(!senha.isEmpty()){

                binding.progressBar.setVisibility(View.VISIBLE);//Mostra a Barra de Progresso.

                loginFirebase(email,senha);

            }else{//Tratamento de ERROS.
                Toast.makeText(this, "informe a senha.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "informe o e-mail.", Toast.LENGTH_SHORT).show();
        }

    }

    private void loginFirebase(String email, String senha){
        mAuth.signInWithEmailAndPassword(//Login com e-mail e senha.
                email,senha
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                finish();
                startActivity(new Intent(this, MainActivity.class));// Caso correto vai para tela MainActivity.

            }else{// Tratamento de Erro.
                binding.progressBar.setVisibility(View.GONE);//Caso errado "msg ERRO".
                Toast.makeText(this, "erro.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}