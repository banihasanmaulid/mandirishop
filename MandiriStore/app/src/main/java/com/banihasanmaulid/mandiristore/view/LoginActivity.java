package com.banihasanmaulid.mandiristore.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.banihasanmaulid.mandiristore.R;
import com.banihasanmaulid.mandiristore.data.local.AppDatabase;
import com.banihasanmaulid.mandiristore.data.repository.ProductRepository;
import com.banihasanmaulid.mandiristore.data.repository.UserRepository;
import com.banihasanmaulid.mandiristore.di.MyApplication;
import com.banihasanmaulid.mandiristore.helpers.Utils;
import com.banihasanmaulid.mandiristore.model.Users;
import com.banihasanmaulid.mandiristore.viewmodel.LoginViewModel;
import com.banihasanmaulid.mandiristore.viewmodel.ViewModelFactory;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = LoginActivity.class.getSimpleName();

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
        LinearLayout layRegister = findViewById(R.id.layRegister);
        LinearLayout layRememberMe = findViewById(R.id.layRememberMe);
        CheckBox checkRememberMe = findViewById(R.id.cbCheck);

        boolean isRememberMe = getRememberMe(this);
        if (isRememberMe){
            Intent intent = new Intent(this, HomeActivity.class);
            this.startActivity(intent);
            finish();
        } else {
            saveRememberMe(this, false);
        }

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            boolean isValid = checkValidation(v, username, password);
            if (isValid){
                localLogin(v, username, password, checkRememberMe);
            }
        });

        layRegister.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RegisterActivity.class);
            v.getContext().startActivity(intent);
        });

        layRememberMe.setOnClickListener(v -> {
            if (checkRememberMe.isChecked()){
                checkRememberMe.setChecked(false);
            } else if (!checkRememberMe.isChecked()){
                checkRememberMe.setChecked(true);
            }
        });
    }

    private boolean checkValidation(View v, String username, String password){
        if (Utils.isEmptyOrNull(username)){
            Toast.makeText(v.getContext(), "Please input username ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Utils.isEmptyOrNull(password)){
            Toast.makeText(v.getContext(), "Please input password ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void localLogin(View v, String username, String password, CheckBox checkRememberMe){
        AppDatabase db = AppDatabase.getInstance(v.getContext());
        new Thread(() -> {
            Users userRegistered = db.usersDao().getLogin(username, password);
            ((Activity) v.getContext()).runOnUiThread(() -> {
                if (userRegistered != null) {
                    saveRememberMe(this, checkRememberMe.isChecked());

                    Intent intent = new Intent(v.getContext(), HomeActivity.class);
                    v.getContext().startActivity(intent);
                    finish();
                    Log.d(TAG, "Success Login");
                } else {
                    Toast.makeText(v.getContext(), "Username & Password is wrong", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Success Login");
                }
            });
        }).start();
    }

    public void saveRememberMe(Context context, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isUserLoggedIn", value);

        editor.apply();
    }

    public boolean getRememberMe(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isUserLoggedIn", false);
    }
}