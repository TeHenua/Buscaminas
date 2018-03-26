package com.example.tehenua.buscaminas;

/**
 * Created by 8fdam02 on 26/03/2018.
 */

public class Dificultad {
    int largo;
    int minas;

    public Dificultad() {
        this.largo = 8;
        this.minas = 10;
    }

    public void setPrincipiante(){
        largo = 8;
        minas = 10;
    }

    public void setAmateur(){
        largo=12;
        minas= 30;
    }

    public void setAvanzado(){
        largo = 16;
        minas = 60;
    }
}
