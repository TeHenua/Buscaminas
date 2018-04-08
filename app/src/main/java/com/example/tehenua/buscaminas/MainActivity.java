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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {

    private int[][] tablero;
    private ImageButton[][] botones;
    private GridLayout layout;
    private Dificultad dificultad;
    private Coleccion coleccion;
    int minasRestantes;
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

        layout = (GridLayout) findViewById(R.id.layout);
        layout.removeAllViews();
        if (dificultad == null){
            dificultad = new Dificultad();
        }
        if (coleccion == null){
            coleccion = Coleccion.emojis;
        }
        minasRestantes = dificultad.minas;
        TextView textView = findViewById(R.id.textView);
        textView.setText("Minas restantes "+minasRestantes);
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
        botones = new ImageButton[dificultad.largo][dificultad.largo];
        //int temp = -2; //-uno es bomba, 0 es vacío y numeros bombas cercanas
        Random rand = new Random();
        while (contador<dificultad.minas){
            int tempI = rand.nextInt((dificultad.largo -1)+1) +1;
            int tempJ = rand.nextInt((dificultad.largo -1)+1) +1;
            try{
                if (tablero[tempI][tempJ]!=-1){
                    tablero[tempI][tempJ]=-1;
                    contador++;
                }
            }catch (Exception e){
                continue;
            }

        }
        for (int i = 0; i < dificultad.largo; i++) {
            for (int j = 0; j < dificultad.largo; j++) {
                if (tablero[i][j]!=-1){
                    tablero[i][j]=0;
                }
            }
        }
    }

    public void pintarTablero() {
        layout.setColumnCount(dificultad.largo);
        layout.setRowCount(dificultad.largo);
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                ImageButton b = new ImageButton(this);
                //String id = String.valueOf(i) + String.valueOf(j);
                //b.setId(Integer.parseInt(id));
                botones[i][j] = b;
                b.setLayoutParams(new RelativeLayout.LayoutParams(dificultad.layout,dificultad.layout));
                b.setImageResource(R.mipmap.cuadro);
                b.setPadding(0,0,0,0);
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
                    for (int i2 = i-1; i2 <= i+1;i2++){
                        for (int j2 = j-1; j2 <= j+1;j2++){
                            try{
                                if (tablero[i2][j2]==-1){
                                    tablero[i][j]++;
                                }
                            }catch (Exception e ){continue;}
                        }
                    }
                }
            }
        }
    }

    public Coordenada calcularCoord(ImageButton b){
        Coordenada c = new Coordenada();
        for (int i=0; i<dificultad.largo;i++) {
            for (int j = 0; j < dificultad.largo; j++) {
                if (botones[i][j] == b) {
                    c = new Coordenada(i,j);
                    return c;
                }
            }
        }
        return c;
    }


    public void despejarTablero(ImageButton b){
        b.setImageResource(R.mipmap.vacio);
        Coordenada c = calcularCoord(b);
        if (c!=null) {
            tablero[c.i][c.j] = 9;
            for (int i = c.i - 1; i <= c.i + 1; i++) {
                for (int j = c.j - 1; j <= c.j + 1; j++) {
                    try {
                        int valor = tablero[i][j];
                        ImageButton b2 = botones[i][j];
                        switch (valor) {
                            case 0:
                                despejarTablero(b2);
                                break;
                            case 1:
                                b2.setImageResource(R.mipmap.uno);
                                break;
                            case 2:
                                b2.setImageResource(R.mipmap.dos);
                                break;
                            case 3:
                                b2.setImageResource(R.mipmap.tres);
                                break;
                            case 4:
                                b2.setImageResource(R.mipmap.cuatro);
                                break;
                            case 5:
                                b2.setImageResource(R.mipmap.cinco);
                                break;
                            case 6:
                                b2.setImageResource(R.mipmap.seis);
                                break;
                            case 7:
                                b2.setImageResource(R.mipmap.siete);
                                break;
                            case 8:
                                b2.setImageResource(R.mipmap.ocho);
                                break;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
    }


    public void juegoPerdido(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Has perdido!!").setMessage("(╯°□°）╯︵ ┻━┻");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nuevoJuego();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void juegoGanado(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Has ganado!!").setMessage("┳━┳ ヽ(ಠل͜ಠ)ﾉ");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nuevoJuego();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        ImageButton b = (ImageButton) view;
        Coordenada c = calcularCoord(b);
        int valor = tablero[c.i][c.j];
        switch (valor) {
            case -1:
                switch (coleccion){
                    case emojis:
                        b.setImageResource(R.mipmap.triste);
                        break;
                    case pez:
                        b.setImageResource(R.mipmap.agua);
                        break;
                    case dragon:
                        b.setImageResource(R.mipmap.dragon);
                        break;
                    case bomba:
                        b.setImageResource(R.mipmap.bomba);
                        break;
                }
                juegoPerdido();
                break;
            case 0:
                despejarTablero(b);
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
        ImageButton b = (ImageButton) view;
        Coordenada c = calcularCoord(b);
        int valor = tablero[c.i][c.j];
        switch (valor) {
            case -1:
                switch (coleccion){
                    case emojis:
                        b.setImageResource(R.mipmap.feliz);
                        break;
                    case bomba:
                        b.setImageResource(R.mipmap.bandera);
                        break;
                    case dragon:
                        b.setImageResource(R.mipmap.caballero);
                        break;
                    case pez:
                        b.setImageResource(R.mipmap.pez);
                        break;
                }
                minasRestantes--;
                TextView textView = findViewById(R.id.textView);
                textView.setText("Minas restantes "+minasRestantes);
                if (minasRestantes==0){
                    juegoGanado();
                }
                break;
            default:
                juegoPerdido();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.personaje:
                final String[] muñecos = {"Emojis", "Clásico","Dragón","Pez"};
                AlertDialog.Builder mu = new AlertDialog.Builder(this);
                mu.setTitle("Elige un personaje");
                mu.setSingleChoiceItems(muñecos, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (muñecos[i]){
                            case "Emojis":
                                coleccion = Coleccion.emojis;
                                break;
                            case "Clásico":
                                coleccion = Coleccion.bomba;
                                break;
                            case "Dragón":
                                coleccion = Coleccion.dragon;
                                break;
                            case "Pez":
                                coleccion = Coleccion.pez;
                                break;
                        }
                        dialogInterface.dismiss();
                        nuevoJuego();
                    }
                }).setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog al = mu.create();
                al.show();
                break;
            case R.id.nuevo:
                nuevoJuego();
                break;
            case R.id.configurar:
                final String[] items = {"Principiante","Amateur","Avanzado"};
                AlertDialog.Builder cf = new AlertDialog.Builder(this);
                cf.setTitle("Elige un nivel de dificultad");
                cf.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (items[i]){
                            case "Principiante":
                                dificultad.setPrincipiante();
                                break;
                            case "Amateur":
                                dificultad.setAmateur();
                                break;
                            case "Avanzado":
                                dificultad.setAvanzado();
                                break;
                        }
                        dialogInterface.dismiss();
                        nuevoJuego();
                    }
                }).setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = cf.create();
                dialog.show();
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


