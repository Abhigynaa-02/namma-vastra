package com.namhavastra.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.namhavastra.app.R;
import com.namhavastra.app.adapters.PatternAdapter;
import com.namhavastra.app.adapters.TrendColorAdapter;
import com.namhavastra.app.models.Pattern;
import com.namhavastra.app.models.TrendColor;
import java.util.ArrayList;
import java.util.List;

public class TrendBoardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trend_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvTrendColors = view.findViewById(R.id.rvTrendColors);
        rvTrendColors.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        List<TrendColor> colors = new ArrayList<>();
        colors.add(new TrendColor("Pastel Pink", "#F4C0D1", "RISING"));
        colors.add(new TrendColor("Sage Green", "#C0DD97", "HOT"));
        colors.add(new TrendColor("Warm Grey", "#D3D1C7", "STABLE"));
        colors.add(new TrendColor("Dusty Blue", "#B5D4F4", "RISING"));
        colors.add(new TrendColor("Terracotta", "#F0997B", "HOT"));
        colors.add(new TrendColor("Ivory", "#FAF7F0", "STABLE"));

        rvTrendColors.setAdapter(new TrendColorAdapter(requireContext(), colors));

        RecyclerView rvPatterns = view.findViewById(R.id.rvPatterns);
        rvPatterns.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        List<Pattern> patterns = new ArrayList<>();
        patterns.add(new Pattern("Checks", "Classic checks are back in demand", android.R.drawable.ic_menu_crop));
        patterns.add(new Pattern("Stripes", "Vertical stripes for elegant look", android.R.drawable.ic_menu_sort_by_size));
        patterns.add(new Pattern("Floral Border", "Intricate florals are trending", android.R.drawable.ic_menu_gallery));
        patterns.add(new Pattern("Geometric", "Modern geometric pallu styles", android.R.drawable.ic_menu_mapmode));

        rvPatterns.setAdapter(new PatternAdapter(requireContext(), patterns));
    }
}
