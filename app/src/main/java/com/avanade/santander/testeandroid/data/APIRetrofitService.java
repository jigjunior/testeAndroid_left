package com.avanade.santander.testeandroid.data;

import com.avanade.santander.testeandroid.contato.Cell;
import com.avanade.santander.testeandroid.contato.Formulario;
import com.avanade.santander.testeandroid.fundo.Fundos;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface APIRetrofitService {

    /* Retorna uma lista de objetos Cell */
    @GET("/cells.json")
    Call<Formulario> getFormulario();

    /* Retorna uma lista de objetos Cell */
    @GET("/cells.json")
    Call<List<Cell>> getCells();

    /* Retorna um objeto Fundos */
    @GET("/fund.json")
    Call<Fundos> getFundos();


}