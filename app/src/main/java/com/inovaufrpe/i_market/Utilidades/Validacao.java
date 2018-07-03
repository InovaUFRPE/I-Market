package com.inovaufrpe.i_market.Utilidades;

import android.content.Context;
import android.text.TextUtils;

public class Validacao {

    public Validacao(Context context){

    }

    public boolean isCampoVazio(String campo) {
        return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
    }
}
