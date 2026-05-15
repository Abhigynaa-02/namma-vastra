package com.namhavastra.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.namhavastra.app.R;
import com.namhavastra.app.db.AppDatabase;
import com.namhavastra.app.db.WeaverEntity;

public class WeaverStoryFragment extends Fragment {

    private LinearLayout layoutForm;
    private CardView cardProfile;
    private EditText etName, etVillage, etYearsExp, etStory;
    private Spinner spSpeciality;
    private TextView tvProfileName, tvProfileVillage, tvProfileBadge, tvProfileStory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weaver_story, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutForm = view.findViewById(R.id.layoutForm);
        cardProfile = view.findViewById(R.id.cardProfile);

        etName = view.findViewById(R.id.etName);
        etVillage = view.findViewById(R.id.etVillage);
        etYearsExp = view.findViewById(R.id.etYearsExp);
        etStory = view.findViewById(R.id.etStory);
        spSpeciality = view.findViewById(R.id.spSpeciality);

        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileVillage = view.findViewById(R.id.tvProfileVillage);
        tvProfileBadge = view.findViewById(R.id.tvProfileBadge);
        tvProfileStory = view.findViewById(R.id.tvProfileStory);

        ArrayAdapter<String> specAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item,
                new String[]{"Ilkal Silk", "Molakalmuru Silk", "Cotton Khadi", "Mixed"});
        specAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSpeciality.setAdapter(specAdapter);

        Button btnSaveProfile = view.findViewById(R.id.btnSaveProfile);
        btnSaveProfile.setOnClickListener(v -> saveProfile());

        Button btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(v -> {
            cardProfile.setVisibility(View.GONE);
            layoutForm.setVisibility(View.VISIBLE);
        });

        loadProfile();
    }

    private void saveProfile() {
        String name = etName.getText().toString();
        String village = etVillage.getText().toString();
        String yearsStr = etYearsExp.getText().toString();
        String story = etStory.getText().toString();

        if (name.isEmpty() || village.isEmpty() || yearsStr.isEmpty() || story.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        WeaverEntity profile = new WeaverEntity();
        profile.id = 1; // Assuming a single profile
        profile.name = name;
        profile.village = village;
        profile.yearsExp = Integer.parseInt(yearsStr);
        profile.speciality = spSpeciality.getSelectedItem().toString();
        profile.story = story;

        new Thread(() -> {
            AppDatabase.getDatabase(requireContext()).weaverDao().insert(profile);
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), "Profile Saved", Toast.LENGTH_SHORT).show();
                displayProfile(profile);
            });
        }).start();
    }

    private void loadProfile() {
        new Thread(() -> {
            WeaverEntity profile = AppDatabase.getDatabase(requireContext()).weaverDao().getProfile();
            requireActivity().runOnUiThread(() -> {
                if (profile != null) {
                    displayProfile(profile);
                    // Pre-fill form
                    etName.setText(profile.name);
                    etVillage.setText(profile.village);
                    etYearsExp.setText(String.valueOf(profile.yearsExp));
                    etStory.setText(profile.story);
                    for (int i = 0; i < spSpeciality.getCount(); i++) {
                        if (spSpeciality.getItemAtPosition(i).equals(profile.speciality)) {
                            spSpeciality.setSelection(i);
                            break;
                        }
                    }
                } else {
                    layoutForm.setVisibility(View.VISIBLE);
                    cardProfile.setVisibility(View.GONE);
                }
            });
        }).start();
    }

    private void displayProfile(WeaverEntity profile) {
        layoutForm.setVisibility(View.GONE);
        cardProfile.setVisibility(View.VISIBLE);

        tvProfileName.setText(profile.name);
        tvProfileVillage.setText(profile.village + " | " + profile.yearsExp + " Years Exp");
        tvProfileBadge.setText(profile.speciality);
        tvProfileStory.setText("\"" + profile.story + "\"");
    }
}
