package com.wh.agendasimplesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.wh.agendasimplesapp.databinding.ActivityCadastroBinding;
import com.wh.agendasimplesapp.databinding.ActivityLoginBinding;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.btnCriarConta.setOnClickListener(view -> validaDados());
    }

    private void validaDados() {
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editSenha.getText().toString().trim();

        if(!email.isEmpty()){
            if(!senha.isEmpty()){

                binding.progressBar.setVisibility(View.VISIBLE);//Mostra a Barra de Progresso.

                CriarContaFirebase(email,senha);

            }else{
            Toast.makeText(this, "informe a senha.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "informe o e-mail.", Toast.LENGTH_SHORT).show();
        }

    }

    private void CriarContaFirebase(String email, String senha){
        mAuth.createUserWithEmailAndPassword(
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