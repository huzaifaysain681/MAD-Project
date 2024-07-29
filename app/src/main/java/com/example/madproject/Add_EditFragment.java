package com.example.madproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class Add_EditFragment extends Fragment {

    private EditText productName, productDescription;
    private Button uploadImageButton, saveButton;
    private String imagePath;

    private DBHelper dbHelper;

    private ActivityResultLauncher<String[]> requestPermissionsLauncher;
    private ActivityResultLauncher<Intent> pickImageLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        productName = view.findViewById(R.id.product_name);
        productDescription = view.findViewById(R.id.product_description);
        uploadImageButton = view.findViewById(R.id.upload_image_button);
        saveButton = view.findViewById(R.id.save_button);

        dbHelper = new DBHelper(getActivity());

        requestPermissionsLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            Boolean readGranted = result.getOrDefault(Manifest.permission.READ_EXTERNAL_STORAGE, false);

            if (readGranted) {
                openGallery();
            } else {
                Toast.makeText(requireContext(), "Permissions Denied", Toast.LENGTH_SHORT).show();
            }
        });

        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                imagePath = getPathFromUri(imageUri);
            }
        });

        uploadImageButton.setOnClickListener(v -> {
            if (checkPermission()) {
                openGallery();
            } else {
                requestPermission();
            }
        });

        saveButton.setOnClickListener(v -> {
            String name = productName.getText().toString().trim();
            String description = productDescription.getText().toString().trim();

            if (name.isEmpty() || description.isEmpty() || imagePath == null) {
                Toast.makeText(requireContext(), "Please fill all fields and upload an image", Toast.LENGTH_SHORT).show();
                return;
            }

            long id = dbHelper.addProduct(name, description, imagePath);

            if (id != -1) {
                Toast.makeText(requireContext(), "Product added successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(requireContext(), "Failed to add product", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private boolean checkPermission() {
        boolean readPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return readPermission;
    }

    private void requestPermission() {
        requestPermissionsLauncher.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(galleryIntent);
    }

    private String getPathFromUri(Uri contentUri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = requireActivity().getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) return null;

        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
    }

    private void clearFields() {
        productName.setText("");
        productDescription.setText("");
        imagePath = null;
    }
}
