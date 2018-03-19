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
        int[][] tablero = generarTablero(8,10);
        pintarTablero(tablero);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public int[][] generarTablero(int largo, int minas){
        int contador = 0;
        int[][] tablero = new int[largo][largo];
        int temp=-2; //-uno es bomba, 0 es vacío y numeros bombas cercanas
        Random rand = new Random();
        for (int i=0;i<largo;i++) {
            for (int j = 0; j < largo; j++) {
                if (rand.nextInt((5-1)+1)+1==5){
                    temp=-1;
                }else{
                    temp=0;
                }
                if (contador<minas && temp==-1){
                    tablero[i][j]=temp;
                    contador++;
                }else{
                    tablero[i][j]=0;
                }
            }
        }
        return tablero;
    }

    public void pintarTablero(int[][] tablero){
        GridLayout layout = (GridLayout)findViewById(R.id.layout);
        tablero = calcularCercanas(generarTablero(8,10));
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

    public int[][] calcularCercanas(int[][] tablero){
        for (int i=0;i<tablero.length;i++){
            for (int j=0;j<tablero[i].length;j++){
                if (tablero[i][j]==0){
                    //Primero comprobar si son -uno, si no sumar ++
                    //Comprobar que existen esas celdas (esquinas)
                    try{
                        if (tablero[i-1][j-1]==-1){
                            tablero[i][j]++;
                        }
                        if (tablero[i-1][j]==-1){
                            tablero[i][j]++;
                        }
                        if (tablero[i-1][j+1]==-1){
                            tablero[i][j]++;
                        }
                        if (tablero[i][j-1]==-1){
                            tablero[i][j]++;
                        }
                        if (tablero[i][j+1]==-1){
                            tablero[i][j]++;
                        }
                        if (tablero[i+1][j-1]==-1){
                            tablero[i][j]++;
                        }
                        if (tablero[i+1][j]==-1){
                            tablero[i][j]++;
                        }
                        if (tablero[i+1][j+1]==-1){
                            tablero[i][j]++;
                        }
                    }catch (Exception e){
                        System.out.print(tablero[i][j]);
                    }

                }
            }
        }

        return tablero;
    }

    @Override
    public void onClick(View view) {
        String s = String.valueOf(view.getId());
        Toast t = Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT);
        t.show();
    }
}
