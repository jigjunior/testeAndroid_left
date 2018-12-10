package com.avanade.santander.testeandroid.fundos.domain;

import android.app.Activity;
import android.os.Bundle;

import com.avanade.santander.testeandroid.R;
import com.avanade.santander.testeandroid.fundos.domain.model.Screen;
import com.google.gson.annotations.Expose;

public class Fundos {

    @Expose
    private final Screen screen;

    public Fundos(Screen screen) {
        this.screen = screen;
    }

    public Screen getScreen() {
        return screen;
    }
}
