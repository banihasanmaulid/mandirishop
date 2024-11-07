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

/**
 * LoginActivity
 *
 * This activity manages the login functionality for the app. It allows users to enter their credentials,
 * validates them, and redirects to the HomeActivity upon successful login. Additionally, it provides a "Remember Me"
 * feature to keep users logged in across sessions, managed through SharedPreferences.
 *
 * Key Features:
 * - Validates user input fields for username and password.
 * - Supports local login with credentials stored in the app's local database.
 * - "Remember Me" functionality stores login status in SharedPreferences.
 * - Redirects to RegisterActivity if the user needs to create a new account.
 *
 * Dependencies:
 * - UserRepository and ProductRepository are injected for use with the ViewModel.
 * - AppDatabase is used for local authentication.
 */
public class LoginActivity extends AppCompatActivity {
    public static final String TAG = LoginActivity.class.getSimpleName();

    // ViewModel and Repository
    private LoginViewModel loginViewModel;

    @Inject
    UserRepository userRepository;

    @Inject
    ProductRepository productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inject dependencies
        ((MyApplication) getApplication()).getAppComponent().inject(this);

        // Initialize ViewModel with factory pattern
        ViewModelFactory factory = new ViewModelFactory(userRepository, productRepository);
        loginViewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);

        // Initialize UI components
        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login_button);
        LinearLayout layRegister = findViewById(R.id.layRegister);
        LinearLayout layRememberMe = findViewById(R.id.layRememberMe);
        CheckBox checkRememberMe = findViewById(R.id.cbCheck);

        // Check if user has selected "Remember Me" and navigate to HomeActivity if true
        if (getRememberMe(this)) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {
            saveRememberMe(this, false);
        }

        // Set up login button click listener
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (checkValidation(v, username, password)) {
                localLogin(v, username, password, checkRememberMe);
            }
        });

        // Set up register layout click listener
        layRegister.setOnClickListener(v -> {
            startActivity(new Intent(v.getContext(), RegisterActivity.class));
        });

        // Toggle Remember Me checkbox state
        layRememberMe.setOnClickListener(v -> checkRememberMe.setChecked(!checkRememberMe.isChecked()));
    }

    /**
     * Validates user input for username and password.
     *
     * @param v The current view
     * @param username The entered username
     * @param password The entered password
     * @return true if input is valid; false otherwise
     */
    private boolean checkValidation(View v, String username, String password){
        if (Utils.isEmptyOrNull(username)) {
            Toast.makeText(v.getContext(), getString(R.string.input_username), Toast.LENGTH_SHORT).show();
            return false;
        } else if (Utils.isEmptyOrNull(password)) {
            Toast.makeText(v.getContext(), getString(R.string.input_password), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Attempts to log in the user locally by verifying credentials in the database.
     *
     * @param v The current view
     * @param username The entered username
     * @param password The entered password
     * @param checkRememberMe The Remember Me checkbox state
     */
    private void localLogin(View v, String username, String password, CheckBox checkRememberMe){
        AppDatabase db = AppDatabase.getInstance(v.getContext());
        new Thread(() -> {
            Users userRegistered = db.usersDao().getLogin(username, password);
            ((Activity) v.getContext()).runOnUiThread(() -> {
                if (userRegistered != null) {
                    saveRememberMe(this, checkRememberMe.isChecked());
                    startActivity(new Intent(v.getContext(), HomeActivity.class));
                    finish();
                    Toast.makeText(v.getContext(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Failed Login");
                }
            });
        }).start();
    }

    /**
     * Saves the Remember Me option to SharedPreferences.
     *
     * @param context The application context
     * @param value The Remember Me status to save
     */
    public void saveRememberMe(Context context, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isUserLoggedIn", value).apply();
    }

    /**
     * Retrieves the Remember Me option from SharedPreferences.
     *
     * @param context The application context
     * @return true if Remember Me was previously selected; false otherwise
     */
    public boolean getRememberMe(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isUserLoggedIn", false);
    }
}