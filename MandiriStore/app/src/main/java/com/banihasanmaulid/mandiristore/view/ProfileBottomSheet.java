package com.banihasanmaulid.mandiristore.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.banihasanmaulid.mandiristore.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * ProfileBottomSheet
 *
 * This BottomSheetDialogFragment displays the user's profile information, including their username,
 * email, and profile picture, and provides a logout button. The profile details are fetched from
 * SharedPreferences. Upon clicking the logout button, the app navigates the user back to the
 * LoginActivity and resets the "Remember Me" status.
 *
 * Key Features:
 * - Displays user profile with username, email, and profile image.
 * - Allows user to log out, clearing the "Remember Me" setting.
 * - Uses SharedPreferences to store and retrieve user information.
 */
public class ProfileBottomSheet extends BottomSheetDialogFragment {

    private TextView tvName, tvEmail;
    private ImageView imgProfile;
    private Button btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile_bottom_sheet, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        imgProfile = view.findViewById(R.id.imgProfile);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        btnLogout = view.findViewById(R.id.btnLogout);

        // Set user details from SharedPreferences
        tvName.setText(getUsername(view.getContext()));
        tvEmail.setText(getEmail(view.getContext()));
        imgProfile.setImageResource(R.drawable.ic_user);

        // Set logout button action
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), LoginActivity.class);
            v.getContext().startActivity(intent);

            saveRememberMe(v.getContext(), false);
        });
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
     * Retrieves the username from SharedPreferences.
     *
     * @param context The application context
     * @return The stored username, or a default if none is set
     */
    private String getUsername(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", "Your Name");
    }

    /**
     * Retrieves the email from SharedPreferences.
     *
     * @param context The application context
     * @return The stored email, or a default if none is set
     */
    private String getEmail(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "your.email@example.com");
    }

    /**
     * Closes the bottom sheet dialog.
     */
    private void closeBottomSheet() {
        dismiss();
    }
}