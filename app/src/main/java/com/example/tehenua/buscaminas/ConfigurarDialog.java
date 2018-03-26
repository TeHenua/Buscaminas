package com.example.tehenua.buscaminas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

/**
 * Created by 8fdam02 on 26/03/2018.
 */

public class ConfigurarDialog extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final CharSequence[] items = {"Principiante","Amateur","Avanzado"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Selecciona el nivel de dificultad").setSingleChoiceItems(items, STYLE_NORMAL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0:

                        break;
                    case 1:
                        break;
                    case 2:
                        break;

                }
            }
        });
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return super.onCreateDialog(savedInstanceState);
    }
}
