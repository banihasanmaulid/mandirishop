package com.banihasanmaulid.mandiristore.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.banihasanmaulid.mandiristore.R;
import com.banihasanmaulid.mandiristore.data.repository.ProductRepository;
import com.banihasanmaulid.mandiristore.data.repository.UserRepository;
import com.banihasanmaulid.mandiristore.viewmodel.LoginViewModel;
import com.banihasanmaulid.mandiristore.viewmodel.ViewModelFactory;
import com.google.gson.Gson;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;

    @Inject
    UserRepository userRepository;

    @Inject
    ProductRepository productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ((MyApplication) getApplication()).getAppComponent().inject(this);

        ViewModelFactory factory = new ViewModelFactory(userRepository, productRepository);
        loginViewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);

        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login_button);

//        loginButton.setOnClickListener(v -> {
//            String username = usernameEditText.getText().toString();
//            String password = passwordEditText.getText().toString();
//            loginViewModel.login(username, password).observe(this, response -> {
//                if (response != null) {
//                    Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show();
//                }
//            });
//        });

        loginButton.setOnClickListener(v -> {
            loginViewModel.getProducts().observe(this, response -> {
                if (response != null) {
                    Log.d("xxx", new Gson().toJson(response));
                    Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}