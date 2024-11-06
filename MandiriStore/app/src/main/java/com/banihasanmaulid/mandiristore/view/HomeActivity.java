package com.banihasanmaulid.mandiristore.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banihasanmaulid.mandiristore.R;
import com.banihasanmaulid.mandiristore.di.MyApplication;
import com.banihasanmaulid.mandiristore.model.Product;
import com.banihasanmaulid.mandiristore.data.repository.ProductRepository;
import com.banihasanmaulid.mandiristore.data.repository.UserRepository;
import com.banihasanmaulid.mandiristore.view.adapter.ProductAdapter;
import com.banihasanmaulid.mandiristore.viewmodel.HomeViewModel;
import com.banihasanmaulid.mandiristore.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {
    public static final String TAG = HomeActivity.class.getSimpleName();

    @Inject
    UserRepository userRepository;

    @Inject
    ProductRepository productRepository;

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private Spinner categoryProduct;
    private ImageView buttonCart, buttonProfile;
    private ProductAdapter productAdapter;
    private final List<Product> productList = new ArrayList<>();;
    private final List<String> categoriesList = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ((MyApplication) getApplication()).getAppComponent().inject(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
    }

    private void init(){
        ViewModelFactory factory = new ViewModelFactory(userRepository, productRepository);
        homeViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);

        recyclerView = findViewById(R.id.recyclerView);
        categoryProduct = findViewById(R.id.categoryProduct);
        buttonCart = findViewById(R.id.cart);
        buttonProfile = findViewById(R.id.btnProfile);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setAdapter();
        setData();

        buttonProfile.setOnClickListener(view -> {
            ProfileBottomSheet profileBottomSheet = new ProfileBottomSheet();
            profileBottomSheet.show(getSupportFragmentManager(), profileBottomSheet.getTag());

//            Intent intent = new Intent(view.getContext(), LoginActivity.class);
//            view.getContext().startActivity(intent);
//            finish();
//
//            saveRememberMe(this, false);
        });

        buttonCart.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), CartActivity.class);
            view.getContext().startActivity(intent);
        });
    }

    private void setAdapter(){
        productAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productAdapter);
    }

    private void setData(){
        getProduct();
        getCategories();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getProduct(){
        homeViewModel.getProducts().observe(this, response -> {
            if (response != null) {
                productList.clear();
                productList.addAll(response);
                productAdapter.notifyDataSetChanged();
                Log.d(TAG, "Success get products");
            } else {
                Log.e(TAG, "Failed get products");
            }
        });
    }

    private void getCategories(){
        homeViewModel.getCategories().observe(this, response -> {
            if (response != null) {
                categoriesList.addAll(response);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriesList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                categoryProduct.setAdapter(adapter);
                categoryProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("SpinnerSelection", "Item selected at position: " + position);
                        String selectedCategory = categoriesList.get(position);
                        getProductsByCategory(selectedCategory);
                        Toast.makeText(HomeActivity.this, "Selected: " + selectedCategory, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                Log.d(TAG, "Success get categories");
            } else {
                Log.e(TAG, "Failed get products");
            }
        });
    }

    private void getProductsByCategory(String category){
        homeViewModel.getProductsByCategory(category).observe(this, response -> {
            if (response != null) {
                productList.clear();
                productList.addAll(response);
                productAdapter.notifyDataSetChanged();
                Log.d(TAG, "Success get products By Category");
                Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "Failed get products By Category");
            }
        });
    }

    public void saveRememberMe(Context context, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isUserLoggedIn", value);

        editor.apply();
    }
}