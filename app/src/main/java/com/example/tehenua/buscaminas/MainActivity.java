package com.example.tehenua.buscaminas;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {

    private int[][] tablero;
    private GridLayout layout;
    private boolean continuarJuego;
    private Dificultad dificultad;
    //Tablero de 16 y 60 minas ancho de button 90.
    //12 y 30 minas, ancho de 120
    //8 y 10 minas, ancho de 180

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nuevoJuego();
    }

    public void nuevoJuego(){
        dificultad = new Dificultad();
        generarTablero(dificultad);
        pintarTablero();
        calcularCercanas();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void generarTablero(Dificultad dificultad) {
        int contador = 0;
        tablero = new int[dificultad.largo][dificultad.largo];
        int temp = -2; //-uno es bomba, 0 es vacío y numeros bombas cercanas
        Random rand = new Random();
        for (int i = 0; i < dificultad.largo; i++) {
            for (int j = 0; j < dificultad.largo; j++) {
                if (rand.nextInt((5 - 1) + 1) + 1 == 5) {
                    temp = -1;
                } else {
                    temp = 0;
                }
                if (contador < dificultad.minas && temp == -1) {
                    tablero[i][j] = temp;
                    contador++;
                } else {
                    tablero[i][j] = 0;
                }
            }
        }

    }

    public void pintarTablero() {
        layout = (GridLayout) findViewById(R.id.layout);
        layout.setColumnCount(dificultad.largo);
        layout.setRowCount(dificultad.largo);
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                ImageButton b = new ImageButton(this);
                String id = String.valueOf(i) + String.valueOf(j);
                b.setId(Integer.parseInt(id));
                b.setLayoutParams(new RelativeLayout.LayoutParams(180,180));
                b.setImageResource(R.mipmap.cuadro);
                b.setPadding(0, 0, 0, 0);
                b.setOnClickListener(this);
                b.setOnLongClickListener(this);
                layout.addView(b);
            }
        }
    }

    public void calcularCercanas() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == 0) {
                    //Primero comprobar si son -uno, si no sumar ++
                    //Comprobar que existen esas celdas (esquinas)
                    try {
                        if (tablero[i - 1][j - 1] == -1) {
                            tablero[i][j]++;
                        }
                    } catch (Exception e) {}
                    try {
                        if (tablero[i - 1][j] == -1) {
                            tablero[i][j]++;
                        }
                    } catch (Exception e) {}
                    try {
                        if (tablero[i - 1][j + 1] == -1) {
                            tablero[i][j]++;
                        }
                    } catch (Exception e) {}
                    try {
                        if (tablero[i][j - 1] == -1) {
                            tablero[i][j]++;
                        }
                    } catch (Exception e) {}
                    try {
                        if (tablero[i][j + 1] == -1) {
                            tablero[i][j]++;
                        }
                    } catch (Exception e) {}
                    try {
                        if (tablero[i + 1][j - 1] == -1) {
                            tablero[i][j]++;
                        }
                    } catch (Exception e) {}
                    try {
                        if (tablero[i + 1][j] == -1) {
                            tablero[i][j]++;
                        }
                    } catch (Exception e) {}
                    try {
                        if (tablero[i + 1][j + 1] == -1) {
                            tablero[i][j]++;
                        }
                    } catch (Exception e) {}
                }
            }
        }
    }

    public int calcularId(int i,int j){
        return Integer.parseInt(String.valueOf(i)+String.valueOf(j));
    }

    public void despejarTablero(int i,int j,ImageButton b){
        b.setImageResource(R.mipmap.vacio);
        int i2;
        int j2;
        try {
            if (tablero[i - 1][j - 1] == 0) {
                i2 = i-1;
                j2 = j-1;
                b = (ImageButton) findViewById(calcularId(i2,j2));
                despejarTablero(i2,j2,b);
            }
        } catch (Exception e) {}
        try {
            if (tablero[i - 1][j] == 0) {
                i2 = i-1;
                j2 = j;
                b = (ImageButton) findViewById(calcularId(i2,j2));
                despejarTablero(i2,j2,b);
            }
        } catch (Exception e) {}
        try {
            if (tablero[i - 1][j + 1] == 0) {
                i2=i-1;
                j2=j+1;
                b = (ImageButton) findViewById(calcularId(i2,j2));
                despejarTablero(i2,j2,b);
            }
        } catch (Exception e) {}
        try {
            if (tablero[i][j - 1] == 0) {
                i2=i;
                j2=j-1;
                b = (ImageButton) findViewById(calcularId(i2,j2));
                despejarTablero(i2,j2,b);
            }
        } catch (Exception e) {}
        try {
            if (tablero[i][j + 1] == 0) {
                i2=i;
                j2=j+1;
                b = (ImageButton) findViewById(calcularId(i2,j2));
                despejarTablero(i2,j2,b);
            }
        } catch (Exception e) {}
        try {
            if (tablero[i + 1][j - 1] == 0) {
                i2=i+1;
                j2=j-1;
                b = (ImageButton) findViewById(calcularId(i2,j2));
                despejarTablero(i2,j2,b);
            }
        } catch (Exception e) {}
        try {
            if (tablero[i + 1][j] == 0) {
                i2=i+1;
                j2=j;
                b = (ImageButton) findViewById(calcularId(i2,j2));
                despejarTablero(i2,j2,b);
            }
        } catch (Exception e) {}
        try {
            if (tablero[i + 1][j + 1] == 0) {
                i2=i+1;
                j2=j+1;
                b = (ImageButton) findViewById(calcularId(i2,j2));
                despejarTablero(i2,j2,b);
            }
        } catch (Exception e) {}

    }

    public void juegoPerdido(){
        Toast.makeText(this,"Lo siento, has perdido.",Toast.LENGTH_LONG).show();
        recreate();
    }

    public void juegoGanado(){
        Toast.makeText(this,"¡¡Enhorabuena!! Has ganado.",Toast.LENGTH_LONG).show();
        recreate();
    }

    @Override
    public void onClick(View view) {
        String s = String.valueOf(view.getId());
        int i, j;
        int contLetras = s.length();
        if (contLetras == 1) { //J 0
            j = 0;
            i = Integer.parseInt(s);
        } else {
            int temp = Integer.parseInt(s);
            j = temp / 10;
            i = temp % 10;
        }
        ImageButton b = (ImageButton) findViewById(Integer.parseInt(s));
        int valor = tablero[i][j];
        switch (valor) {
            case -1:
                b.setImageResource(R.mipmap.triste);
                juegoPerdido();
                break;
            case 0:
                despejarTablero(i,j,b);
                break;
            case 1:
                b.setImageResource(R.mipmap.uno);
                break;
            case 2:
                b.setImageResource(R.mipmap.dos);
                break;
            case 3:
                b.setImageResource(R.mipmap.tres);
                break;
            case 4:
                b.setImageResource(R.mipmap.cuatro);
                break;
            case 5:
                b.setImageResource(R.mipmap.cinco);
                break;
            case 6:
                b.setImageResource(R.mipmap.seis);
                break;
            case 7:
                b.setImageResource(R.mipmap.siete);
                break;
            case 8:
                b.setImageResource(R.mipmap.ocho);
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        String s = String.valueOf(view.getId());
        int i, j;
        int contLetras = s.length();
        if (contLetras == 1) { //J 0
            j = 0;
            i = Integer.parseInt(s);
        } else {
            int temp = Integer.parseInt(s);
            j = temp / 10;
            i = temp % 10;
        }
        ImageButton b = (ImageButton) findViewById(Integer.parseInt(s));
        int valor = tablero[i][j];
        switch (valor) {
            case -1:
                b.setImageResource(R.mipmap.feliz);
                dificultad.minas--;
                if (dificultad.minas==0){
                    juegoGanado();
                }
                break;
            default:
                juegoPerdido();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.personaje:

                break;
            case R.id.nuevo:
                recreate();
                break;
            case R.id.configurar:
                ConfigurarDialog cf = new ConfigurarDialog();

                break;
            case R.id.instrucciones:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.instrucciones);
                builder.setMessage(R.string.instrucDetalle).setCancelable(false)
                .setPositiveButton(R.string.entendido, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}


