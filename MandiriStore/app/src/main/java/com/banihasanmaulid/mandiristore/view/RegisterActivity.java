package com.banihasanmaulid.mandiristore.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.banihasanmaulid.mandiristore.R;
import com.banihasanmaulid.mandiristore.data.local.AppDatabase;
import com.banihasanmaulid.mandiristore.data.repository.ProductRepository;
import com.banihasanmaulid.mandiristore.data.repository.UserRepository;
import com.banihasanmaulid.mandiristore.di.MyApplication;
import com.banihasanmaulid.mandiristore.helpers.Utils;
import com.banihasanmaulid.mandiristore.model.Users;
import com.banihasanmaulid.mandiristore.viewmodel.RegisterViewModel;
import com.banihasanmaulid.mandiristore.viewmodel.ViewModelFactory;

import javax.inject.Inject;

/**
 * RegisterActivity
 *
 * This activity allows users to create a new account by providing a username, password, and email.
 * It validates the inputs and stores the user information locally in the database upon successful
 * registration. The class also saves the user's username and email to SharedPreferences.
 *
 * Key Features:
 * - User registration with input validation for username, password, and email.
 * - Saves the user's credentials to the local database.
 * - Stores username and email in SharedPreferences for later retrieval.
 */
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

        // Register button click action
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
        });

        // Back button action
        backButton.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }

    /**
     * Validates the registration fields to ensure none are empty.
     *
     * @param v The view context for showing toasts
     * @param username The entered username
     * @param password The entered password
     * @param email The entered email
     * @return true if all fields are filled, otherwise false
     */
    private boolean checkValidation(View v, String username, String password, String email){
        if (Utils.isEmptyOrNull(username)){
            Toast.makeText(v.getContext(), getString(R.string.input_username), Toast.LENGTH_SHORT).show();
            return false;
        } else if (Utils.isEmptyOrNull(password)){
            Toast.makeText(v.getContext(), getString(R.string.input_password), Toast.LENGTH_SHORT).show();
            return false;
        } else if (Utils.isEmptyOrNull(email)){
            Toast.makeText(v.getContext(), getString(R.string.input_email), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Saves user information locally and stores username and email in SharedPreferences.
     *
     * @param v The view context
     * @param users The user details to register
     */
    private void localRegister(View v, Users users){
        AppDatabase db = AppDatabase.getInstance(v.getContext());
        new Thread(() -> {
            db.usersDao().insertUsers(users);
            ((Activity) v.getContext()).runOnUiThread(() -> {
                saveUserNameAndEmail(v.getContext(), users.getUsername(), users.getEmail());
                finish();
                Toast.makeText(v.getContext(), getString(R.string.success_registered), Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    /**
     * Saves the username and email in SharedPreferences.
     *
     * @param context The application context
     * @param username The username to save
     * @param email The email to save
     */
    public void saveUserNameAndEmail(Context context, String  username, String email) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("email", email);
        editor.apply();
    }
}