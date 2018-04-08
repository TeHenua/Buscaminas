package com.example.tehenua.buscaminas;

/**
 * Created by 8fdam02 on 26/03/2018.
 */

public class Dificultad {
    int largo;
    int minas;
    int layout;

    public Dificultad() {
        this.largo = 8;
        this.minas = 10;
        this.layout = 130;
    }

    public void setPrincipiante(){
        largo = 8;
        minas = 10;
        layout = 130;
    }

    public void setAmateur(){
        largo=12;
        minas= 30;
        layout = 90;
    }

    public void setAvanzado(){
        largo = 16;
        minas = 60;
        layout = 65;
    }
}
