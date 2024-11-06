package com.banihasanmaulid.mandiristore.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.banihasanmaulid.mandiristore.R;
import com.banihasanmaulid.mandiristore.data.api.request.LoginRequest;
import com.banihasanmaulid.mandiristore.data.api.request.UserRequest;
import com.banihasanmaulid.mandiristore.data.api.response.UserResponse;
import com.banihasanmaulid.mandiristore.data.local.AppDatabase;
import com.banihasanmaulid.mandiristore.data.repository.ProductRepository;
import com.banihasanmaulid.mandiristore.data.repository.UserRepository;
import com.banihasanmaulid.mandiristore.di.MyApplication;
import com.banihasanmaulid.mandiristore.helpers.Utils;
import com.banihasanmaulid.mandiristore.model.Users;
import com.banihasanmaulid.mandiristore.view.adapter.CartAdapter;
import com.banihasanmaulid.mandiristore.viewmodel.RegisterViewModel;
import com.banihasanmaulid.mandiristore.viewmodel.ViewModelFactory;

import javax.inject.Inject;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = RegisterActivity.class.getSimpleName();

    private RegisterViewModel registerViewModel;

    @Inject
    UserRepository userRepository;

    @Inject
    ProductRepository productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ((MyApplication) getApplication()).getAppComponent().inject(this);

        ViewModelFactory factory = new ViewModelFactory(userRepository, productRepository);
        registerViewModel = new ViewModelProvider(this, factory).get(RegisterViewModel.class);

        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        EditText emailEditText = findViewById(R.id.email);
        Button registerButton = findViewById(R.id.login_button);
        ImageView backButton = findViewById(R.id.back);

        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String email = emailEditText.getText().toString();

            Users users = new Users();
            users.setUsername(username);
            users.setPassword(password);
            users.setEmail(email);
            boolean isValid = checkValidation(v, username, password, email);
            if (isValid){
                localRegister(v, users);
            }

            /*registerViewModel.register(new UserRequest(username, password)).observe(this, response -> {
                if (response != null) {
                    Log.d(TAG, "Success Register");
                } else {
                    Log.e(TAG, "Failed Register");
                }
            });*/
        });

        backButton.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }

    private boolean checkValidation(View v, String username, String password, String email){
        if (Utils.isEmptyOrNull(username)){
            Toast.makeText(v.getContext(), "Please input username ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Utils.isEmptyOrNull(password)){
            Toast.makeText(v.getContext(), "Please input password ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Utils.isEmptyOrNull(email)){
            Toast.makeText(v.getContext(), "Please input email ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void localRegister(View v, Users users){
        AppDatabase db = AppDatabase.getInstance(v.getContext());
        new Thread(() -> {
            db.usersDao().insertUsers(users);
            ((Activity) v.getContext()).runOnUiThread(() -> {
                saveUserNameAndEmail(v.getContext(), users.getUsername(), users.getEmail());
                finish();
                Toast.makeText(v.getContext(), "Success Registered", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    public void saveUserNameAndEmail(Context context, String  username, String email) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("email", email);
        editor.apply();
    }
}