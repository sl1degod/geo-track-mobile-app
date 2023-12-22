package com.sl1degod.kursovaya.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.window.SplashScreen;

import com.google.android.material.textfield.TextInputLayout;
import com.sl1degod.kursovaya.App;
import com.sl1degod.kursovaya.Fragments.ProfileFragment;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.Viewmodels.LoginViewModel;
import com.sl1degod.kursovaya.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    LoginViewModel viewModel;
    boolean result = false;

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
            if (loginTIED.getText().toString().isEmpty()) {
                loginTIL.setError("Поле Логин не может быть пустым");
                passwordTIL.setErrorEnabled(false);
            } else if (passwordTIED.getText().toString().isEmpty()) {
                passwordTIL.setError("Поле Пароль не может быть пустым");
                loginTIL.setErrorEnabled(false);
            }
            else {
                loginTIL.setErrorEnabled(false);
                passwordTIL.setErrorEnabled(false);
                getUser(loginTIED.getText().toString().trim(), passwordTIED.getText().toString().trim());
            }
        });

    }

    private void getUser(String user_login, String user_password) {
        viewModel.getLoadUserData().observe(this, users -> {
            boolean userFound = false;
            for (int i = 0; i < users.size(); i++) {
                if (user_login.equals(users.get(i).getLogin()) && user_password.equals(users.get(i).getPassword())) {
                    // Показываем сплеш-скрин
                    setContentView(R.layout.activity_spash_screen);
                    int finalI = i;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            App.getInstance().setUser_id(users.get(finalI).getId());
                            intent.putExtra("user_id", users.get(finalI).getId());
                            Log.d("login", users.get(finalI).getId());
                            startActivity(intent);
                            finish();
                        }
                    }, 2000);
                    userFound = true;
                    break;
                }
            }
            if (!userFound) {
                loginTIL.setError(" ");
                passwordTIL.setError("Такого пользователя нет");
            }
        });
        viewModel.getUsers(user_login);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}