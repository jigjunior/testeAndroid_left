package com.avanade.santander.testeandroid;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avanade.santander.testeandroid.contato.Cell;
import com.avanade.santander.testeandroid.contato.ContatoActivity;
import com.avanade.santander.testeandroid.contato.Formulario;
import com.avanade.santander.testeandroid.data.APIRetrofitService;
import com.avanade.santander.testeandroid.data.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    public static final String TAG = "MAIN ACTIVITY";
    public static final int REQUEST_CODE_PERMISSION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            startActivity(new Intent(this, ContatoActivity.class));
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

