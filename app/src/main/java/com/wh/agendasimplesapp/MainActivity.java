package com.wh.agendasimplesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wh.agendasimplesapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private EditText editText;
    private ActivityMainBinding binding;

    private String stringDateSelected;

    private DatabaseReference databaseReference;//Variável Acessa o Banco de Dados.
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        calendarView = findViewById(R.id.calendarView);// Elemento Calendário.
        editText = findViewById(R.id.editText); //Campo (Adicionar Novo Evento).

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                stringDateSelected = Integer.toString(year)+Integer.toString(month+1)+Integer.toString(dayOfMonth);
                //Janeiro é o MÊS "0" Deve se somar +1 aos Meses P/ Calcular Corretamente.
                calendarClicked();
            }
        });

        binding.btnSair.setOnClickListener(view ->{//Botão para Sair do Aplicativo.
            mAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));//Retorna para a Tela de LOGIN.
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");
    }

    private void calendarClicked(){//Médoto de Click no Calendário.
        databaseReference.child(stringDateSelected).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null){
                    editText.setText(snapshot.getValue().toString());
                }else{
                    editText.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void buttonSaveEvent(View view){//Metodo Atributo (Salvar conteudo do editText).
        databaseReference.child(stringDateSelected).setValue(editText.getText().toString());
    }
    public void buttonDeletEvent(View view){// Deleta o conteúdo do EditText.
        databaseReference.child(stringDateSelected).removeValue();
        editText.setText("");
    }
}