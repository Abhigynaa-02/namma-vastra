package com.namhavastra.app.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.namhavastra.app.R;
import com.namhavastra.app.adapters.GalleryAdapter;
import com.namhavastra.app.db.AppDatabase;
import com.namhavastra.app.db.GalleryEntity;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LoomGalleryFragment extends Fragment {

    private ImageView ivPreview;
    private EditText etDescription, etPrice;
    private Spinner spMaterial;
    private RecyclerView rvGallery;
    private GalleryAdapter adapter;
    private Uri currentPhotoUri;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loom_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivPreview = view.findViewById(R.id.ivPreview);
        etDescription = view.findViewById(R.id.etDescription);
        etPrice = view.findViewById(R.id.etPrice);
        spMaterial = view.findViewById(R.id.spMaterial);
        rvGallery = view.findViewById(R.id.rvGallery);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, new String[]{"Silk", "Cotton"});
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMaterial.setAdapter(spinnerAdapter);

        rvGallery.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new GalleryAdapter(requireContext(), null);
        rvGallery.setAdapter(adapter);

        Button btnTakePhoto = view.findViewById(R.id.btnTakePhoto);
        Button btnChooseGallery = view.findViewById(R.id.btnChooseGallery);
        Button btnAddGallery = view.findViewById(R.id.btnAddGallery);

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        ivPreview.setVisibility(View.VISIBLE);
                        ivPreview.setImageURI(currentPhotoUri);
                    }
                }
        );

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        currentPhotoUri = result.getData().getData();
                        ivPreview.setVisibility(View.VISIBLE);
                        ivPreview.setImageURI(currentPhotoUri);
                    }
                }
        );

        btnTakePhoto.setOnClickListener(v -> dispatchTakePictureIntent());
        btnChooseGallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncher.launch(intent);
        });

        btnAddGallery.setOnClickListener(v -> saveToGallery());

        preloadSampleData();
        loadGallery();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            photoFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException ex) {
            Toast.makeText(requireContext(), "Error creating file", Toast.LENGTH_SHORT).show();
        }
        if (photoFile != null) {
            currentPhotoUri = FileProvider.getUriForFile(requireContext(),
                    requireContext().getApplicationContext().getPackageName() + ".provider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri);
            cameraLauncher.launch(takePictureIntent);
        }
    }

    private void saveToGallery() {
        String desc = etDescription.getText().toString();
        String priceStr = etPrice.getText().toString();
        String material = spMaterial.getSelectedItem().toString();

        if (desc.isEmpty() || priceStr.isEmpty() || currentPhotoUri == null) {
            Toast.makeText(requireContext(), "Please fill all details and select image", Toast.LENGTH_SHORT).show();
            return;
        }

        GalleryEntity item = new GalleryEntity();
        item.description = desc;
        item.price = Double.parseDouble(priceStr);
        item.material = material;
        item.imagePath = currentPhotoUri.toString();
        item.timestamp = System.currentTimeMillis();

        new Thread(() -> {
            AppDatabase.getDatabase(requireContext()).galleryDao().insert(item);
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), "Added to Gallery", Toast.LENGTH_SHORT).show();
                etDescription.setText("");
                etPrice.setText("");
                ivPreview.setVisibility(View.GONE);
                currentPhotoUri = null;
                loadGallery();
            });
        }).start();
    }

    private void preloadSampleData() {
        SharedPreferences prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        boolean isPreloaded = prefs.getBoolean("is_preloaded", false);
        if (!isPreloaded) {
            new Thread(() -> {
                if (AppDatabase.getDatabase(requireContext()).galleryDao().getCount() == 0) {
                    GalleryEntity item1 = new GalleryEntity();
                    item1.description = "Red Ilkal with Tope Teni";
                    item1.material = "Silk";
                    item1.price = 5000;
                    item1.timestamp = System.currentTimeMillis();
                    AppDatabase.getDatabase(requireContext()).galleryDao().insert(item1);

                    GalleryEntity item2 = new GalleryEntity();
                    item2.description = "Green Checks with Border";
                    item2.material = "Cotton";
                    item2.price = 1500;
                    item2.timestamp = System.currentTimeMillis();
                    AppDatabase.getDatabase(requireContext()).galleryDao().insert(item2);
                }
                prefs.edit().putBoolean("is_preloaded", true).apply();
                loadGallery();
            }).start();
        }
    }

    private void loadGallery() {
        new Thread(() -> {
            List<GalleryEntity> items = AppDatabase.getDatabase(requireContext()).galleryDao().getAllSarees();
            requireActivity().runOnUiThread(() -> adapter.updateData(items));
        }).start();
    }
}
