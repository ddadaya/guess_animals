package com.example.guess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.Vector;

public class Guess_Animal extends AppCompatActivity
{
    // инициализация нужных объектов
    private Button yes;
    private Button no;
    private ImageView image;
    private TextView que;
    private TextView sc;

    //id - id картинки, score- счет, ranque - рандом число, who - рандом животное, all_animals - вектор названий животных
    int id;
    int score;
    int ranque;
    String who;
    Vector all_animals=new Vector<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_animal);

        //определение переменных под конкретные объекты с xml
        yes=findViewById(R.id.yes);
        no=findViewById(R.id.no);
        image=findViewById(R.id.anim);
        que=findViewById(R.id.que);
        sc=findViewById(R.id.sc);

        //заполнение вектора животных
        all_animals.add("cat");
        all_animals.add("rat");
        all_animals.add("dog");
        all_animals.add("krokodil");
        all_animals.add("lion");
        all_animals.add("enot");
        all_animals.add("begemot");

        //получение счета и нового задания
        getscore();
        newtask();
        //слушатель кнопки yes
        yes.setOnClickListener(v->
        {
            //если ответ верный
            if(who==all_animals.get(ranque))
            {
                score++;
                getbd().setValue(score);
                sc.setText("Счет: "+score);
                all_animals.remove(ranque);
                newtask();
            }
            //если ответ неверный
            else
            {
                score--;
                getbd().setValue(score);
                sc.setText("Счет: "+score);
                newtask();
            }
        });
        //слушатель кнопки no
        no.setOnClickListener(v->
        {

            //если ответ верный
            if(who!=all_animals.get(ranque))
            {
                score++;
                getbd().setValue(score);
                sc.setText("Счет: "+score);
                all_animals.remove(ranque);
                newtask();
            }

            //если ответ неверный
            else
            {
                score--;
                getbd().setValue(score);
                sc.setText("Счет: "+score);
                newtask();
            }
        });
    }

    //функция генератор рандом числа
    public int getran(int min, int max)
    {
        Random random = new Random();
        return random.ints(min, max).findFirst().getAsInt();
    }

    //функция генерации нового задания
    public void newtask()
    {
        if(all_animals.size()!=1)
        {
            ranque=getran(0,all_animals.size() - 1);
            who=all_animals.get(getran(0,all_animals.size() - 1)).toString();
            id=getResources().getIdentifier("com.example.guess:drawable/" + who,null,null);
            image.setImageResource(id);
            que.setText("На картинке " + all_animals.get(ranque) + "?");
        }
        else
        {
            Toast.makeText(Guess_Animal.this,"на этом все",Toast.LENGTH_SHORT).show();
            yes.setEnabled(false);
            no.setEnabled(false);
        }
    }

    //функция получения счета пользователя из бд
    public void getscore()
    {
        getbd().addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                score=Integer.valueOf(dataSnapshot.getValue().toString());
                sc.setText("Счет: "+dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }

    //функция подключающаяся к бд и возвращающая поле score конкретного пользователя
    public DatabaseReference getbd()
    {
        FirebaseAuth authentication = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dao = database.getReference().child("Date of users").child(authentication.getCurrentUser().getUid()).child("score");
        return dao;
    }

}

/*
























 */
































