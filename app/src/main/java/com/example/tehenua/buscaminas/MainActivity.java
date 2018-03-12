package com.example.tehenua.buscaminas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean[][] tablero = generarTablero(8,10);
        pintarTablero(tablero);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean[][] generarTablero(int largo, int minas){
        int contador = 0;
        boolean[][] tablero = new boolean[largo][largo];
        boolean temp=false;
        Random rand = new Random();
        for (int i=0;i<largo;i++) {
            for (int j = 0; j < largo; j++) {
                if (rand.nextInt((5-1)+1)+1==5){
                    temp=true;
                }else{
                    temp=false;
                }
                if (contador<minas && temp){
                    tablero[i][j]=temp;
                    contador++;
                }else{
                    tablero[i][j]=false;
                }
            }
        }
        return tablero;
    }

    public void pintarTablero(boolean[][] tablero){
        GridLayout layout = (GridLayout)findViewById(R.id.layout);
        generarTablero(8,10);
        for (int i=0;i<tablero.length;i++){
            for (int j=0;j<tablero[i].length;j++){
                ImageButton b = new ImageButton(this);
                String id = String.valueOf(i)+String.valueOf(j);
                b.setId(Integer.parseInt(id));
                b.setImageResource(R.mipmap.vacio);
                b.setPadding(0,0,0,0);
                b.setOnClickListener(this);
                layout.addView(b);
            }
        }
    }

    @Override
    public void onClick(View view) {
        String s = String.valueOf(view.getId());
        Toast t = Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT);
        t.show();
    }
}
