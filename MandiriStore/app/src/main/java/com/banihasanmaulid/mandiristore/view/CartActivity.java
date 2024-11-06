package com.banihasanmaulid.mandiristore.view;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Product> cartList;
    private TextView totalAmount, totalItem, selectedItem;
    private Button checkout;
    private ImageView btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        AppDatabase db = AppDatabase.getInstance(this);

        recyclerView = findViewById(R.id.cartRecyclerView);
        totalAmount = findViewById(R.id.totalAmount);
        totalItem = findViewById(R.id.totalItem);
        selectedItem = findViewById(R.id.selectedItem);
        btnback = findViewById(R.id.back);
        checkout = findViewById(R.id.checkout);
        cartList = new ArrayList<>();

        new Thread(() -> {
            cartList = db.productDao().getAllCartProducts();
            runOnUiThread(() -> {
                cartAdapter = new CartAdapter(cartList, productId -> {
                    new Thread(() -> {
                        db.productDao().deleteProductById(productId);
                        runOnUiThread(() -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                cartList.removeIf(p -> p.getId() == productId);
                            }
                            cartAdapter.notifyDataSetChanged();
                            calculateTotal();
                        });
                    }).start();
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(cartAdapter);
            });
            calculateTotal();
        }).start();

        checkout.setOnClickListener(v -> showCheckoutDialog());
        btnback.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }

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

    private void showCheckoutDialog() {
        List<Product> selectedProducts = cartAdapter.getSelectedProducts();
        if (selectedProducts.isEmpty()) {
            Toast.makeText(this, "Pilih setidaknya satu produk", Toast.LENGTH_SHORT).show();
            return;
        }

        int totalPrice = 0;
        for (Product product : selectedProducts) {
            totalPrice += product.getPrice();
        }
        int itemCount = selectedProducts.size();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Checkout")
                .setMessage("Jumlah item: " + itemCount + "\nTotal harga: $" + totalPrice)
                .setPositiveButton("Bayar", (dialog, which) -> {
                    Toast.makeText(this, "Pembayaran berhasil!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                })
                .setNegativeButton("Batalkan", (dialog, which) -> dialog.dismiss())
                .show();
    }
}