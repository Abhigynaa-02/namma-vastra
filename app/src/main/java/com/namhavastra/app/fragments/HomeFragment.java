package com.namhavastra.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.namhavastra.app.R;
import com.namhavastra.app.db.AppDatabase;
import com.namhavastra.app.db.WeaverEntity;

public class HomeFragment extends Fragment {

    private TextView tvGreeting, tvVillage, tvSpeciality;
    private CardView cardTrendBoard, cardLoomGallery, cardPriceCalculator, cardWeaverStory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        tvGreeting = view.findViewById(R.id.tvGreeting);
        tvVillage = view.findViewById(R.id.tvVillage);
        tvSpeciality = view.findViewById(R.id.tvSpeciality);
        
        cardTrendBoard = view.findViewById(R.id.cardTrendBoard);
        cardLoomGallery = view.findViewById(R.id.cardLoomGallery);
        cardPriceCalculator = view.findViewById(R.id.cardPriceCalculator);
        cardWeaverStory = view.findViewById(R.id.cardWeaverStory);

        cardTrendBoard.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.trendBoardFragment));
        cardLoomGallery.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.loomGalleryFragment));
        cardPriceCalculator.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.priceCalculatorFragment));
        cardWeaverStory.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.weaverStoryFragment));

        loadProfile();
    }

    private void loadProfile() {
        new Thread(() -> {
            WeaverEntity profile = AppDatabase.getDatabase(requireContext()).weaverDao().getProfile();
            if (profile != null) {
                requireActivity().runOnUiThread(() -> {
                    tvGreeting.setText("Welcome, " + profile.name + "!");
                    tvVillage.setText("Village: " + profile.village);
                    tvSpeciality.setText("Speciality: " + profile.speciality);
                });
            }
        }).start();
    }
}
