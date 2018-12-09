package com.avanade.santander.testeandroid;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avanade.santander.testeandroid.contato.Cell;
import com.avanade.santander.testeandroid.contato.Formulario;
import com.avanade.santander.testeandroid.data.APIRetrofitService;
import com.avanade.santander.testeandroid.data.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {


    public static final String TAG = "MAIN ACTIVITY";
    public static final int REQUEST_CODE_PERMISSION = 101;

    private ConstraintLayout layout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.layoutFormulario);
        progressBar = findViewById(R.id.progressBar);

        solicitaPermissao(this);
    }

    public void solicitaPermissao(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permissão de Internet" + " ----------- NOT GRANTED ");
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.INTERNET)) {
                Log.i(TAG, "Permissão de Internet" + " ----------- HAS BEEN RATIONALE REQUESTED");
                new AlertDialog.Builder(activity.getApplicationContext())
                        .setMessage("Permitir acesso a Internet para usar o aplicativo!")
                        .setPositiveButton("Allow", (dialog, which) -> ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE_PERMISSION))
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .create()
                        .show();
            } else {
                Log.i(TAG, "Permissão de Internet" + "----------- HAS BEEN REQUESTED");
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE_PERMISSION);
            }
        } else {
            Log.i(TAG, "Permissão de Internet" + " ----------- OK - GRANTED ");
            carregaTela();
        }
    }


    public void carregaTela() {
        /*Create handle for the RetrofitInstance interface*/
        APIRetrofitService service = ApiClient.getClient(ApiClient.BASE_URL).create(APIRetrofitService.class);

        Call<Formulario> call = service.getFormulario();
        call.enqueue(new Callback<Formulario>() {

            @Override
            public void onResponse(Call<Formulario> call, Response<Formulario> response) {
                //Formulario formulario = response.body();
                Log.e(TAG, "-------------------- RESPONSE: " + response.body().toString());

                if (response.isSuccessful()) {
                    Log.i(TAG, "-------------------- RESPONSE: " + response.body());
                    Formulario formulario = response.body();
                    desenhaTela(formulario);
                } else {
                    Log.d(TAG, "Erro: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Formulario> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, "Erro: " + t.toString());
            }
        });
    }

    public void desenhaTela(Formulario formulario) {

        View view;
        progressBar.setVisibility(View.GONE);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        int top = 0;

        for (Cell cell : formulario.getFormulario()) {

            if (cell.getType() == Type.field.getTipo()) {
                Log.i(TAG, "Field");
                view = new EditText(this);
                ((EditText) view).setHint(cell.getMessage());
                ((EditText) view).getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);



                if(cell.getTypefield().equals(String.valueOf(TypeField.telnumber.getTipo()))
                        || cell.getTypefield().equals(TypeField.telnumber.name())){
                    Log.i(TAG, "-------------- TYPEFIELD --------- TELEFONE");
                    // TODO - validar mascara telefone
                    // TODO - isValid ((EditText) view).getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                } else if(cell.getTypefield().equals(String.valueOf(TypeField.email.getTipo()))
                        || cell.getTypefield().equals(TypeField.email.name())){
                    Log.i(TAG, "-------------- TYPEFIELD --------- E-MAIL");
                    // TODO - validar mascar email
                    // TODO - isValid ((EditText) view).getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                } else {
                    Log.i(TAG, "-------------- TYPEFIELD --------- TEXTO");
                    ((EditText) view).getBackground().clearColorFilter();
                }


            } else if (cell.getType() == Type.text.getTipo()) {
                Log.i(TAG, "Text");
                view = new TextView(this);
                ((TextView) view).setText(cell.getMessage());

            } else if (cell.getType() == Type.image.getTipo()) {
                Log.i(TAG, "Image");
                view = new ImageView(this);

            } else if (cell.getType() == Type.checkbox.getTipo()) {
                Log.i(TAG, "Checkbox");
                view = new CheckBox(this);
                ((CheckBox) view).setText(" " + cell.getMessage());
                ((CheckBox) view).setChecked(true);
                ((CheckBox) view).setButtonDrawable(R.drawable.checkbox);
                ((CheckBox) view).setTextColor(Color.DKGRAY);
                //((CheckBox) view).setTextAppearance(R.style.din_pro);

            } else if (cell.getType() == Type.send.getTipo()) {
                Log.i(TAG, "Button");
                view = new Button(this);
                ((Button) view).setText(cell.getMessage());
                ((Button) view).setBackgroundResource(R.drawable.rouded_button);
                ((Button) view).setTextColor(Color.WHITE);
            } else {
                view = new View(this);
                Log.i(TAG, "View Type");
            }


            // view.setId(View.generateViewId());
            view.setId(cell.getId());

            int width = PixelToDensity.convertToPixels(300, this);
            int height = PixelToDensity.convertToPixels(50, this);
            top += PixelToDensity.convertToPixels(cell.getTopSpacing() + 50, this);

            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(width, height);
            lp.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            lp.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            lp.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            lp.setMargins(8, top, 8, 8);

            view.setLayoutParams(lp);

            layout.addView(view);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                Log.i(TAG, "Request Result: Permission Internet");
                solicitaPermissao(getParent());
                break;

            default:
                Log.i(TAG, "Request Code != ALL");
        }
    }
}

