package com.example.guess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthReg extends AppCompatActivity
{
    // инициализация нужных объектов
    private Button enter;
    private Button registrate;
    private EditText login;
    private EditText password;

    //необходимые подключения к бд для добавления пользователя
    FirebaseAuth authentication=FirebaseAuth.getInstance(); //авторизация
    FirebaseDatabase database=FirebaseDatabase.getInstance(); //подключение к базе данных
    DatabaseReference dao=database.getReference("Date of users"); //работа с табличками под (date of users)

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authreg_activity);

        //определение переменных под конкретные объекты с xml
        enter=findViewById(R.id.enter);
        registrate=findViewById(R.id.registrate);
        login=findViewById(R.id.login);
        password=findViewById(R.id.password);

        //слушатель кнопки регистрации
        registrate.setOnClickListener(v->
        {
            //добавление юзера
            authentication.createUserWithEmailAndPassword(login.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>(){
                @Override
                public void onSuccess(AuthResult authResult){

                    //создаем объект класса для нового юзера
                    Player pl=new Player();

                    //назначаем нужны поля
                    pl.setEmail(login.getText().toString());
                    pl.setScore("0");

                    //добавляем пользователя в бд
                    dao.child(authentication.getCurrentUser().getUid()).setValue(pl).addOnSuccessListener(new OnSuccessListener<Void>(){
                        @Override
                        public void onSuccess(Void unused){
                            Toast.makeText(AuthReg.this,"успешно",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        });

        //слушатель кнопки входа
        enter.setOnClickListener(v->{
            authentication.signInWithEmailAndPassword(login.getText().toString(),password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(AuthReg.this, "успешна", Toast.LENGTH_SHORT).show();

                        //переход на новое окно
                        Intent intent = new Intent(AuthReg.this, Guess_Animal.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(AuthReg.this, "Aвторизация провалена", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

    }


}