package com.banihasanmaulid.mandiristore.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banihasanmaulid.mandiristore.R;
import com.banihasanmaulid.mandiristore.data.local.AppDatabase;
import com.banihasanmaulid.mandiristore.model.Product;
import com.banihasanmaulid.mandiristore.view.adapter.CartAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the cart activity where users can view, manage, and proceed to checkout with items in their cart.
 * It displays a list of selected products, calculates totals, and allows users to confirm checkout.
 */
public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Product> cartList = new ArrayList<>();
    private TextView totalAmount, totalItem, selectedItem;
    private Button checkout;
    private ImageView btnBack;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        db = AppDatabase.getInstance(this);
        initializeViews();
        loadCartProducts();

        checkout.setOnClickListener(v -> showCheckoutDialog());
        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }

    /**
     * Initializes the views used in the activity.
     */
    private void initializeViews() {
        recyclerView = findViewById(R.id.cartRecyclerView);
        totalAmount = findViewById(R.id.totalAmount);
        totalItem = findViewById(R.id.totalItem);
        selectedItem = findViewById(R.id.selectedItem);
        btnBack = findViewById(R.id.back);
        checkout = findViewById(R.id.checkout);
    }

    /**
     * Loads the cart products from the database in a background thread.
     */
    private void loadCartProducts() {
        new Thread(() -> {
            cartList = db.productDao().getAllCartProducts();
            runOnUiThread(() -> setupRecyclerView());
            calculateTotal();
        }).start();
    }

    /**
     * Sets up the RecyclerView with the CartAdapter and initializes the deletion functionality.
     */
    private void setupRecyclerView() {
        cartAdapter = new CartAdapter(cartList, this::removeProductFromCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);
    }

    /**
     * Removes a product from the cart by productId in a background thread and updates the UI.
     * @param productId The ID of the product to be removed.
     */
    private void removeProductFromCart(int productId) {
        new Thread(() -> {
            db.productDao().deleteProductById(productId);
            runOnUiThread(() -> {
                cartList.removeIf(p -> p.getId() == productId);
                cartAdapter.notifyDataSetChanged();
                calculateTotal();
            });
        }).start();
    }

    /**
     * Calculates the total price of items in the cart and updates UI with total amount and item count.
     */
    private void calculateTotal() {
        int total = 0;
        if (cartList != null){
            for (Product product : cartList) {
                total += product.getPrice();
            }
            totalAmount.setText("Total: $" + total);
            totalItem.setText("Total Item: " + cartList.size());
        }

    }

    /**
     * Shows a dialog for checkout confirmation with item count and total price.
     * If "Pay" is selected, a success message is displayed.
     */
    private void showCheckoutDialog() {
        List<Product> selectedProducts = cartAdapter.getSelectedProducts();
        if (selectedProducts.isEmpty()) {
            Toast.makeText(this, getString(R.string.please_select_product), Toast.LENGTH_SHORT).show();
            return;
        }

        int totalPrice = 0;
        for (Product product : selectedProducts) {
            totalPrice += product.getPrice();
        }
        int itemCount = selectedProducts.size();

        new AlertDialog.Builder(this)
                .setTitle("Checkout")
                .setMessage("Total Items: " + itemCount + "\nTotal Amount: $" + totalPrice)
                .setPositiveButton("Pay", (dialog, which) ->
                        Toast.makeText(this, getString(R.string.payment_successful), Toast.LENGTH_SHORT).show()
                )
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}