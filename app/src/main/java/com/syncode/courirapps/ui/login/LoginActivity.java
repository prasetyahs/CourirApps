package com.syncode.courirapps.ui.login;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.syncode.courirapps.R;
import com.syncode.courirapps.data.local.SystemDataLocal;
import com.syncode.courirapps.data.model.Courier;
import com.syncode.courirapps.data.network.repository.LoginRepository;
import com.syncode.courirapps.data.response.LoginResponse;
import com.syncode.courirapps.ui.maps.MapsActivity;
import com.syncode.courirapps.utils.DialogClass;
import com.syncode.courirapps.utils.SwitchActivity;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog alertDialog;

    private LoginRepository loginRepository;

    private EditText edtUsername, edtPassword;

    private SystemDataLocal systemDataLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginRepository = new LoginRepository();
        Button btnLogin = findViewById(R.id.btnLogin);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin.setOnClickListener(view -> login());
        edtPassword.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(edtPassword.getWindowToken(), 0);
                }
                login();
                return true;
            }
            return false;
        });
    }


    private void login() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.loading_alert, null, false);
        alertDialog = DialogClass.dialog(LoginActivity.this, v).create();
        alertDialog.show();
        loginRepository.login(username, password).observe(LoginActivity.this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse != null) {
                    if (loginResponse.getStatus()) {
                        Courier courier = loginResponse.getCourier();
                        systemDataLocal = new SystemDataLocal(LoginActivity.this, courier);
                        systemDataLocal.setSessionLogin();
                        alertDialog.dismiss();
                        SwitchActivity.mainSwitch(LoginActivity.this, MapsActivity.class);
                        finish();
                    } else {
                        alertDialog.dismiss();
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        systemDataLocal = new SystemDataLocal(this);
        if (systemDataLocal.getCheckLogin()){
            SwitchActivity.mainSwitch(this,MapsActivity.class);
            finish();
        }
    }
}
