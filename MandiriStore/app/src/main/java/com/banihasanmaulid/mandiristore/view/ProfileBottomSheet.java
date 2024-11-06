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

        tvName.setText(getUsername(view.getContext()));
        tvEmail.setText(getEmail(view.getContext()));
        imgProfile.setImageResource(R.drawable.ic_user);

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), LoginActivity.class);
            v.getContext().startActivity(intent);

            saveRememberMe(v.getContext(), false);
        });
    }

    public void saveRememberMe(Context context, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isUserLoggedIn", value);

        editor.apply();
    }

    private String getUsername(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", "john doexx");
    }

    private String getEmail(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "johnv.doe@example.com");
    }

    private void closeBottomSheet() {
        dismiss();
    }
}