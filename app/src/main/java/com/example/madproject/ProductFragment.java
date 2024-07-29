package com.example.madproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        // Initialize the components
        EditText searchProduct = view.findViewById(R.id.search_product);
        Button addProductButton = view.findViewById(R.id.add_product_button);
        recyclerView = view.findViewById(R.id.product_recycler_view);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productList = new ArrayList<>();
        // Add dummy products
        productList.add(new Product(1, "Apple iPhone 13", "Latest model with A15 Bionic chip", 999.99, 50));
        productList.add(new Product(2, "Samsung Galaxy S21", "High-end smartphone with great features", 799.99, 30));
        productList.add(new Product(3, "Google Pixel 6", "Newest Pixel phone with great camera", 599.99, 20));
        productList.add(new Product(4, "OnePlus 9", "Flagship killer with Snapdragon 888", 729.99, 40));
        productList.add(new Product(5, "Sony WH-1000XM4", "Noise-canceling wireless headphones", 349.99, 15));
        productList.add(new Product(6, "Apple MacBook Air", "M1 chip, 13-inch Retina display", 999.99, 25));
        productList.add(new Product(7, "Dell XPS 13", "High-performance ultrabook", 1199.99, 10));
        productAdapter = new ProductAdapter(productList, getContext(), this);
        recyclerView.setAdapter(productAdapter);

        // Set listeners
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Add_EditFragment
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new Add_EditFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Implement search functionality here if needed

        return view;
    }

    // Method to add, edit, and remove products
    private void manageProducts() {
        // Add product to the list
        // Edit product in the list
        // Remove product from the list
        productAdapter.notifyDataSetChanged();
    }
}
