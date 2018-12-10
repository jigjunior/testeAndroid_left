package com.avanade.santander.testeandroid.data;

import com.avanade.santander.testeandroid.contato.domain.model.Cell;
import com.avanade.santander.testeandroid.contato.domain.model.Formulario;
import com.avanade.santander.testeandroid.fundos.domain.Fundos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface APIRetrofitService {

    /* Retorna uma lista de objetos Cell */
    @GET("/cells.json")
    Call<Formulario> getFormulario();

    /* Retorna um objeto Fundos */
    @GET("/fund.json")
    Call<Fundos> getFundos();


}