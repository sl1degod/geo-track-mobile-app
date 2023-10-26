package com.sl1degod.kursovaya.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.Viewmodels.LoginViewModel;
import com.sl1degod.kursovaya.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    LoginViewModel viewModel;
    boolean result = true;

    TextInputLayout loginTIL, passwordTIL;

    EditText loginTIED, passwordTIED;

    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.button_login);
        loginTIL = findViewById(R.id.login_input);
        loginTIED = findViewById(R.id.login_tiet);
        passwordTIL = findViewById(R.id.password_input);
        passwordTIED = findViewById(R.id.password_tiet);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginButton.setOnClickListener(e -> {
            Toast.makeText(this, "1323", Toast.LENGTH_SHORT).show();
            if (loginTIED.getText().toString().isEmpty()) {
                loginTIL.setError("Введите верные данные");
                passwordTIL.setErrorEnabled(false);
            } else if (passwordTIED.getText().toString().isEmpty()) {
                passwordTIL.setError("Введите верные данные");
                loginTIL.setErrorEnabled(false);
            } else {
                loginTIL.setErrorEnabled(false);
                passwordTIL.setErrorEnabled(false);
                getUser(loginTIED.getText().toString(), passwordTIED.getText().toString());
            }
        });
    }

    private void getUser(String user_login, String user_password) {
        viewModel.getLoadUserData().observe(this, users -> {
            for (int i = 0; i < users.size(); i++) {
                if (user_login.equals(users.get(i).getLogin()) && user_password.equals(users.get(i).getPassword())) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("user_id", users.get(i).getId());
                    Toast.makeText(this,"Успешно",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    result = false;
                }
            }
            if (!result) {
                Toast.makeText(this, "Такого пользователя нет", Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getUsers(user_login);
    }
}