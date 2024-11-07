package com.banihasanmaulid.mandiristore.view;

import android.annotation.SuppressLint;
import android.content.Intent;
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

/**
 * HomeActivity
 *
 * This activity serves as the main screen for the application, displaying a list of products and
 * allowing users to filter products by category. It includes functionality for navigating to the
 * profile screen and cart screen. Products and categories are loaded from a ViewModel connected
 * to UserRepository and ProductRepository using dependency injection.
 *
 * Key Components:
 * - RecyclerView for displaying products.
 * - Spinner for selecting categories to filter products.
 * - Button actions for accessing the cart and profile.
 * - Observes LiveData from HomeViewModel to populate and update the UI.
 */

public class HomeActivity extends AppCompatActivity {
    public static final String TAG = HomeActivity.class.getSimpleName();

    // Dependency injection for repositories
    @Inject
    UserRepository userRepository;

    @Inject
    ProductRepository productRepository;

    // ViewModel and UI components
    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private Spinner categoryProduct;
    private ImageView buttonCart, buttonProfile;
    private ProductAdapter productAdapter;

    // Lists to hold products and categories
    private final List<Product> productList = new ArrayList<>();
    private final List<String> categoriesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge layout and full-screen mode
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Inject dependencies
        ((MyApplication) getApplication()).getAppComponent().inject(this);

        // Set full-screen mode
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Initialize UI components and ViewModel
        init();
    }

    /**
     * Initializes ViewModel, RecyclerView, Spinners, and sets up UI listeners.
     */
    private void init(){
        // Set up ViewModel with factory pattern
        ViewModelFactory factory = new ViewModelFactory(userRepository, productRepository);
        homeViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);

        // Find and configure UI components
        recyclerView = findViewById(R.id.recyclerView);
        categoryProduct = findViewById(R.id.categoryProduct);
        buttonCart = findViewById(R.id.cart);
        buttonProfile = findViewById(R.id.btnProfile);

        // Configure RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up RecyclerView adapter and load data
        setAdapter();
        setData();

        // Set up button listeners for cart and profile actions
        setupButtonListeners();
    }

    /**
     * Sets the adapter for the RecyclerView.
     */
    private void setAdapter(){
        productAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productAdapter);
    }

    /**
     * Fetches product and category data.
     */
    private void setData(){
        getProduct();
        getCategories();
    }

    /**
     * Fetches product data and updates the adapter.
     */
    @SuppressLint("NotifyDataSetChanged")
    private void getProduct(){
        homeViewModel.getProducts().observe(this, response -> {
            if (response != null) {
                productList.clear();
                productList.addAll(response);
                productAdapter.notifyDataSetChanged();
                Log.d(TAG, "Success get products");
            } else {
                Log.e(TAG, "Failed to get products");
            }
        });
    }

    /**
     * Fetches categories data and configures the spinner.
     */
    private void getCategories(){
        homeViewModel.getCategories().observe(this, response -> {
            if (response != null) {
                categoriesList.addAll(response);

                // Set up category spinner adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriesList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                categoryProduct.setAdapter(adapter);
                setupCategorySelectionListener();
                Log.d(TAG, "Success get categories");
            } else {
                Log.e(TAG, "Failed to get categories");
            }
        });
    }

    /**
     * Sets up the category selection listener for filtering products by category.
     */
    private void setupCategorySelectionListener() {
        categoryProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categoriesList.get(position);
                getProductsByCategory(selectedCategory);
                Toast.makeText(HomeActivity.this, "Selected: " + selectedCategory, Toast.LENGTH_SHORT).show();
                Log.d("SpinnerSelection", "Item selected at position: " + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });
    }

    /**
     * Fetches products by category and updates the product list.
     *
     * @param category The category selected by the user.
     */
    private void getProductsByCategory(String category){
        homeViewModel.getProductsByCategory(category).observe(this, response -> {
            if (response != null) {
                productList.clear();
                productList.addAll(response);
                productAdapter.notifyDataSetChanged();
                Log.d(TAG, "Success get products by category");
            } else {
                Log.e(TAG, "Failed to get products by category");
            }
        });
    }

    /**
     * Sets up listeners for the cart and profile buttons.
     */
    private void setupButtonListeners() {
        buttonProfile.setOnClickListener(view -> {
            ProfileBottomSheet profileBottomSheet = new ProfileBottomSheet();
            profileBottomSheet.show(getSupportFragmentManager(), profileBottomSheet.getTag());
        });

        buttonCart.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), CartActivity.class);
            view.getContext().startActivity(intent);
        });
    }
}