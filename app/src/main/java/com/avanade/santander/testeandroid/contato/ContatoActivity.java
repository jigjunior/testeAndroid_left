package com.avanade.santander.testeandroid.contato;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.avanade.santander.testeandroid.MailUtil;
import com.avanade.santander.testeandroid.MaskEditUtil;
import com.avanade.santander.testeandroid.PixelToDensity;
import com.avanade.santander.testeandroid.R;
import com.avanade.santander.testeandroid.Type;
import com.avanade.santander.testeandroid.TypeField;
import com.avanade.santander.testeandroid.data.APIRetrofitService;
import com.avanade.santander.testeandroid.data.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContatoActivity extends Activity {

    public static final String TAG = "CONTATO ACTIVITY";

    private ConstraintLayout layout;
    private ConstraintLayout layoutEnvio;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        layoutEnvio = findViewById(R.id.layout_envio);
        layout = findViewById(R.id.layout_formulario);
        progressBar = findViewById(R.id.progress_bar);

        carregaTela();
    }

    public void novaMensagem(View view) {
        carregaTela();
    }

    public void carregaTela() {

        layoutEnvio.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

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

        progressBar.setVisibility(View.GONE);

        Typeface typefaceTxt = ResourcesCompat.getFont(this, R.font.din_pro_medium);
        Typeface typefaceBox = ResourcesCompat.getFont(this, R.font.din_pro_regular);

        // Titulo
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        lp.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        lp.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        lp.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        int top = PixelToDensity.convertToPixels(43, this);
        lp.setMargins(0, top, 0, 0);

        TextView txtContato = new TextView(this);
        txtContato.setId(View.generateViewId());
        txtContato.setText("Contato");
        txtContato.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        txtContato.setTypeface(typefaceTxt);
        txtContato.setLayoutParams(lp);
        layout.addView(txtContato);

        // Consumindo JSON
        int width = PixelToDensity.convertToPixels(300, this);
        int height = PixelToDensity.convertToPixels(47, this);
        int clearSize = PixelToDensity.convertToPixels(30, this);
        top = 0;

        View lastView = txtContato;

        for (Cell cell : formulario.getFormulario()) {

            lp = new ConstraintLayout.LayoutParams(width, height);
            lp.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            lp.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            lp.topToTop = lastView.getBottom();
            top += PixelToDensity.convertToPixels(cell.getTopSpacing() + 47, this);
            lp.setMargins(8, top, 8, 8);

            if (cell.getType() == Type.field.getTipo()) {
                Log.i(TAG, "----------- Field");

                EditText editText = new EditText(this);
                editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                editText.setTypeface(typefaceTxt);
                editText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                if (cell.getTypefield().equals(String.valueOf(TypeField.telnumber.getTipo()))
                        || cell.getTypefield().equals(TypeField.telnumber.name())) {
                    Log.i(TAG, "-------------- TYPEFIELD --------- TELEFONE");
                    editText.setInputType(InputType.TYPE_CLASS_PHONE);
                    editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                    editText.addTextChangedListener(MaskEditUtil.insert(editText));

                } else if (cell.getTypefield().equals(String.valueOf(TypeField.email.getTipo()))
                        || cell.getTypefield().equals(TypeField.email.name())) {
                    Log.i(TAG, "-------------- TYPEFIELD --------- E-MAIL");
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    editText.setVisibility(View.GONE);
                    editText.addTextChangedListener(MailUtil.insert(editText));

                } else {
                    Log.i(TAG, "-------------- TYPEFIELD --------- TEXTO");
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                    editText.getBackground().clearColorFilter();
                }

                editText.setId(cell.getId());
                editText.setHint(cell.getMessage());

                editText.setLayoutParams(lp);

                layout.addView(editText);

                lastView = editText;

//                // TODO - Label
//                ConstraintLayout.LayoutParams lpLabel = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
//                lpLabel.startToStart = editText.getLeft();
//                lpLabel.topToTop = editText.getTop();
//                lpLabel.bottomToBottom = editText.getBottom();
//                lpLabel.setMargins(2, 0, 0, 0);
//
//                TextView label = new TextView(this);
//                label.setId(4000 + cell.getId());
//                label.setText(cell.getMessage());
//                label.setLayoutParams(lpLabel);
//
//                // TODO - Clear Button
//                ConstraintLayout.LayoutParams lpClear = new ConstraintLayout.LayoutParams(clearSize, clearSize);
//                lpClear.endToEnd = editText.getRight();
//                lpClear.topToTop = editText.getTop();
//                lpClear.bottomToBottom = editText.getBottom();
//                lpClear.setMargins(0, 0, 8, 0);
//
//                ImageButton clear = new ImageButton(this);
//                clear.setId(View.generateViewId());
//                clear.setBackground(getResources().getDrawable(R.drawable.rouded_button_white));
//                clear.setImageResource(android.R.drawable.presence_offline);
//                clear.setOnClickListener(v1 -> editText.setText(""));
//                clear.setLayoutParams(lpClear);


//                layout.addView(label);
//                layout.addView(clear);

            } else {
                View view = new View(this);

                if (cell.getType() == Type.text.getTipo()) {
                    Log.i(TAG, "----------- Text");
                    TextView txtView = new TextView(this);
                    txtView.setText(cell.getMessage());
                    txtView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    txtView.setTypeface(typefaceTxt);
                    view = txtView;

                } else if (cell.getType() == Type.image.getTipo()) {
                    Log.i(TAG, "----------- Image");
                    ImageView imageView = new ImageView(this);
                    view = imageView;

                } else if (cell.getType() == Type.checkbox.getTipo()) {
                    Log.i(TAG, "----------- Checkbox");
                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setText(" " + cell.getMessage());
                    checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    checkBox.setTypeface(typefaceBox);
                    checkBox.setChecked(false);
                    checkBox.setButtonDrawable(R.drawable.checkbox);
                    checkBox.setTextColor(Color.DKGRAY);
                    checkBox.setOnClickListener(v -> {
                        EditText editText = findViewById(cell.getShow());
                        if (checkBox.isChecked())
                            editText.setVisibility(View.VISIBLE);
                        else
                            editText.setVisibility(View.GONE);
                    });
                    //((CheckBox) view).setTextAppearance(R.style.din_pro);
                    view = checkBox;

                } else if (cell.getType() == Type.send.getTipo()) {
                    Log.i(TAG, "----------- Button");
                    Button btn = new Button(this);
                    btn.setText(cell.getMessage());
                    btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    btn.setTypeface(typefaceTxt);
                    btn.setBackgroundResource(R.drawable.rouded_button_red);
                    btn.setTextColor(Color.WHITE);
                    btn.setOnClickListener((v1) -> {

                        EditText nome = (EditText) layout.getViewById(2);
                        EditText fone = (EditText) layout.getViewById(6);
                        EditText mail = (EditText) layout.getViewById(4);

                        if (nome.getText().toString().length() < 3) {
                            Toast.makeText(getApplicationContext(), "Preencha o campo nome correntamente", Toast.LENGTH_LONG).show();
                        } else if (fone.getText().toString().length() < 14) {
                            Toast.makeText(getApplicationContext(), "Preencha o campo telefone corretamente", Toast.LENGTH_LONG).show();
                        } else if (!MailUtil.isValid(mail.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Preencha o campo e-mail corretamente", Toast.LENGTH_LONG).show();
                        } else {
                            layoutEnvio.setVisibility(View.VISIBLE);
                            layout.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            nome.setText("");
                            fone.setText("");
                            mail.setText("");
                        }


                    });
                    view = btn;
                } else {
                    Log.i(TAG, "----------- View Type");
                }

                // view.setId(View.generateViewId());
                view.setId(cell.getId());
                view.setLayoutParams(lp);

                layout.addView(view);
                lastView = view;
            }


        }


    }


}