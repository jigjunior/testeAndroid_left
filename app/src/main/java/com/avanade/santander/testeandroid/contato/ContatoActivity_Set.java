//package com.avanade.santander.testeandroid.contato;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.graphics.PorterDuff;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.constraint.ConstraintLayout;
//import android.support.constraint.ConstraintSet;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.res.ResourcesCompat;
//import android.text.InputType;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//
//import com.avanade.santander.testeandroid.R;
//import com.avanade.santander.testeandroid.contato.domain.model.Cell;
//import com.avanade.santander.testeandroid.contato.domain.model.Formulario;
//import com.avanade.santander.testeandroid.contato.domain.model.Type;
//import com.avanade.santander.testeandroid.contato.domain.model.TypeField;
//import com.avanade.santander.testeandroid.data.APIRetrofitService;
//import com.avanade.santander.testeandroid.data.ApiClient;
//import com.avanade.santander.testeandroid.util.PixelToDensity;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
//public class ContatoActivity_Set extends Activity {
//
//
//    public static final String TAG = "MAIN ACTIVITY";
//    public static final int REQUEST_CODE_PERMISSION = 101;
//
//    private ConstraintLayout constraintLayout;
//    private ProgressBar progressBar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_contato);
//
//        constraintLayout = findViewById(R.id.layoutFormulario);
//        progressBar = findViewById(R.id.progressBar);
//
//        solicitaPermissao(this);
//    }
//
//    public void solicitaPermissao(Activity activity) {
//        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
//            Log.i(TAG, "Permissão de Internet" + " ----------- NOT GRANTED ");
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.INTERNET)) {
//                Log.i(TAG, "Permissão de Internet" + " ----------- HAS BEEN RATIONALE REQUESTED");
//                new AlertDialog.Builder(activity.getApplicationContext())
//                        .setMessage("Permitir acesso a Internet para usar o aplicativo!")
//                        .setPositiveButton("Allow", (dialog, which) -> ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE_PERMISSION))
//                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
//                        .create()
//                        .show();
//            } else {
//                Log.i(TAG, "Permissão de Internet" + "----------- HAS BEEN REQUESTED");
//                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE_PERMISSION);
//            }
//        } else {
//            Log.i(TAG, "Permissão de Internet" + " ----------- OK - GRANTED ");
//            carregaTela();
//        }
//    }
//
//
//    public void carregaTela() {
//        /*Create handle for the RetrofitInstance interface*/
//        APIRetrofitService service = ApiClient.getClient(ApiClient.BASE_URL).create(APIRetrofitService.class);
//
//        Call<Formulario> call = service.getFormulario();
//        call.enqueue(new Callback<Formulario>() {
//
//            @Override
//            public void onResponse(Call<Formulario> call, Response<Formulario> response) {
//                //Formulario formulario = response.body();
//                Log.e(TAG, "-------------------- RESPONSE: " + response.body().toString());
//
//                if (response.isSuccessful()) {
//                    Log.i(TAG, "-------------------- RESPONSE: " + response.body());
//                    Formulario formulario = response.body();
//                    desenhaTela(formulario);
//                } else {
//                    Log.d(TAG, "Erro: " + response.errorBody().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Formulario> call, Throwable t) {
//                // Log error here since request failed
//                Log.e(TAG, "Erro: " + t.toString());
//            }
//        });
//    }
//
//    public void desenhaTela(Formulario formulario) {
//
//        progressBar.setVisibility(View.GONE);
//
//
//
//        // Titulo
//        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
////        lp.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
////        lp.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
////        lp.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
////        int topoContato = PixelToDensity.convertToPixels(43, this);
////        lp.setMargins(0, topoContato,0,0);
//
//        TextView txtContato = new TextView(this);
//        txtContato.setId(View.generateViewId());
//        txtContato.setText("Contato");
//        Typeface typeface = ResourcesCompat.getFont(this, R.font.din_pro_medium);
//        txtContato.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
//        txtContato.setTypeface(typeface);
//        txtContato.setLayoutParams(lp);
//        constraintLayout.addView(txtContato);
//
//        ConstraintSet constraintSet = new ConstraintSet();
//        constraintSet.clone(constraintLayout);
//
//        constraintSet.connect(txtContato.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
//        constraintSet.connect(txtContato.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);
//        int top = PixelToDensity.convertToPixels(43, this);
//        constraintSet.connect(txtContato.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, top);
//
//        //constraintSet.applyTo(constraintLayout);
//
//        // Consumindo JSON
//        int width = PixelToDensity.convertToPixels(300, this);
//        int height = PixelToDensity.convertToPixels(47, this);
//        int clearSize = PixelToDensity.convertToPixels(30, this);
//        lp = new ConstraintLayout.LayoutParams(width, height);
//
//
//        View lastView = txtContato;
//
//        for (int i = 0; i < formulario.getFormulario().size(); i++){
//            Cell cell = formulario.getFormulario().get(i);
//
//            if (cell.getType() == Type.field.getTipo()) {
//                Log.i(TAG, "----------- Field");
//                EditText editText = new EditText(this);
//                editText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
//
//                if (cell.getTypefield().equals(String.valueOf(TypeField.telnumber.getTipo()))
//                        || cell.getTypefield().equals(TypeField.telnumber.name())) {
//                    Log.i(TAG, "-------------- TYPEFIELD --------- TELEFONE");
//                    editText.setInputType(InputType.TYPE_CLASS_PHONE);
//                    // TODO - validar mascara telefone - isValid ((EditText) view).getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
//
//                } else if (cell.getTypefield().equals(String.valueOf(TypeField.email.getTipo()))
//                        || cell.getTypefield().equals(TypeField.email.name())) {
//                    Log.i(TAG, "-------------- TYPEFIELD --------- E-MAIL");
//                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
//                    editText.setVisibility(View.GONE);
//                    // TODO - validar mascara email - isValid ((EditText) view).getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
//
//                } else {
//                    Log.i(TAG, "-------------- TYPEFIELD --------- TEXTO");
//                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
//                    editText.getBackground().clearColorFilter();
//                }
//
//                editText.setId(cell.getId());
//                editText.setHint(cell.getMessage());
//                editText.setLayoutParams(lp);
//
//                constraintLayout.addView(editText);
//
//                constraintSet.clone(constraintLayout);
//
//                constraintSet.connect(editText.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
//                constraintSet.connect(editText.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);
//                top = PixelToDensity.convertToPixels(cell.getTopSpacing()+ 47, this);
//                constraintSet.connect(editText.getId(), ConstraintSet.TOP, lastView.getId(), ConstraintSet.BOTTOM, top);
//
//                constraintSet.applyTo(constraintLayout);
//
//                lastView = editText;
//
////                // TODO - Label
////                ConstraintLayout.LayoutParams lpLabel = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
////                lpLabel.startToStart = editText.getLeft();
////                lpLabel.topToTop = editText.getTop();
////                lpLabel.bottomToBottom = editText.getBottom();
////                lpLabel.setMargins(2, 0, 0, 0);
////
////                TextView label = new TextView(this);
////                label.setId(4000 + cell.getId());
////                label.setText(cell.getMessage());
////                label.setLayoutParams(lpLabel);
////
////                // TODO - Clear Button
////                ConstraintLayout.LayoutParams lpClear = new ConstraintLayout.LayoutParams(clearSize, clearSize);
////                lpClear.endToEnd = editText.getRight();
////                lpClear.topToTop = editText.getTop();
////                lpClear.bottomToBottom = editText.getBottom();
////                lpClear.setMargins(0, 0, 8, 0);
////
////                ImageButton clear = new ImageButton(this);
////                clear.setId(View.generateViewId());
////                clear.setBackground(getResources().getDrawable(R.drawable.rouded_button_white));
////                clear.setImageResource(android.R.drawable.presence_offline);
////                clear.setOnClickListener(v1 -> editText.setText(""));
////                clear.setLayoutParams(lpClear);
//
//
////                constraintLayout.addView(label);
////                constraintLayout.addView(clear);
//
//            } else {
//                View view = new View(this);
//
//                if (cell.getType() == Type.text.getTipo()) {
//                    Log.i(TAG, "----------- Text");
//                    TextView txtView = new TextView(this);
//                    txtView.setText(cell.getMessage());
//                    view = txtView;
//
//                } else if (cell.getType() == Type.image.getTipo()) {
//                    Log.i(TAG, "----------- Image");
//                    ImageView imageView = new ImageView(this);
//                    view = imageView;
//
//                } else if (cell.getType() == Type.checkbox.getTipo()) {
//                    Log.i(TAG, "----------- Checkbox");
//                    CheckBox checkBox = new CheckBox(this);
//                    checkBox.setText(" " + cell.getMessage());
//                    checkBox.setChecked(false);
//                    checkBox.setButtonDrawable(R.drawable.checkbox);
//                    checkBox.setTextColor(Color.DKGRAY);
//                    checkBox.setOnClickListener(v -> {
//                        EditText editText = findViewById(cell.getShow());
//                        if(checkBox.isChecked())
//                            editText.setVisibility(View.VISIBLE);
//                        else
//                            editText.setVisibility(View.GONE);
//                    });
//                    //((CheckBox) view).setTextAppearance(R.style.din_pro);
//                    view = checkBox;
//
//                } else if (cell.getType() == Type.send.getTipo()) {
//                    Log.i(TAG, "----------- Button");
//                    Button btn = new Button(this);
//                    btn.setText(cell.getMessage());
//                    btn.setBackgroundResource(R.drawable.rouded_button_red);
//                    btn.setTextColor(Color.WHITE);
//                    view = btn;
//                } else {
//                    Log.i(TAG, "----------- View Type");
//                }
//
//                // view.setId(View.generateViewId());
//                view.setId(cell.getId());
//                view.setLayoutParams(lp);
//
//                //TODO constraintLayout.addView(view);
//
////                constraintSet = new ConstraintSet();
////                constraintSet.clone(constraintLayout);
////
////                constraintSet.connect(view.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
////                constraintSet.connect(view.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);
////                int topSpacing = PixelToDensity.convertToPixels(cell.getTopSpacing(), this);
////                constraintSet.connect(view.getId(), ConstraintSet.TOP, lastView.getId(), ConstraintSet.BOTTOM, topSpacing);
////
////                constraintSet.applyTo(constraintLayout);
//
//                //lastView = view;
//
//            }
//        }
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case REQUEST_CODE_PERMISSION:
//                Log.i(TAG, "Request Result: Permission Internet");
//                solicitaPermissao(getParent());
//                break;
//
//            default:
//                Log.i(TAG, "Request Code != ALL");
//        }
//    }
//}
//
