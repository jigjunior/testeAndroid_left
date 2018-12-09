package com.avanade.santander.testeandroid.contato;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class Cell {

    @NonNull
    @SerializedName("id")
    @Expose
    private final int id;

    @SerializedName("type")
    @Expose
    private final int type;

    @Nullable
    @SerializedName("message")
    @Expose
    private final String message;

    @SerializedName("typefield")
    @Expose
    private final String typefield;

    @SerializedName("hidden")
    @Expose
    private final boolean hidden;

    @SerializedName("topSpacing")
    @Expose
    private final int topSpacing;

    @SerializedName("show")
    @Expose
    private final int show;

    @SerializedName("required")
    @Expose
    private final boolean required;


    public Cell(@NonNull int id, int type, @Nullable String message, String typefield, boolean hidden, int topSpacing, int show, boolean required) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.typefield = typefield;
        this.hidden = hidden;
        this.topSpacing = topSpacing;
        this.show = show;
        this.required = required;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public String getTypefield() {
        return typefield;
    }

    public boolean isHidden() {
        return hidden;
    }

    public int getTopSpacing() {
        return topSpacing;
    }

    public int getShow() {
        return show;
    }

    public boolean isRequired() {
        return required;
    }
}

