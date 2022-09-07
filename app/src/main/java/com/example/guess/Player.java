package com.example.guess;

//класс пользователя
public class Player{

    private String email;
    private String pass;
    private String score;

    //конструктор класса
    Player(){}

    //геттеры сеттеры
    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email=email; }

    public String getScore(){ return score; }
    public void setScore(String score){ this.score=score; }
}

