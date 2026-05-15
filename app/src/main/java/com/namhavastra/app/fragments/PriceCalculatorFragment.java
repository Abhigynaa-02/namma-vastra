package com.namhavastra.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.namhavastra.app.R;
import com.namhavastra.app.utils.PriceCalculatorUtil;

public class PriceCalculatorFragment extends Fragment {

    private Spinner spCalcMaterial, spBorder, spZari;
    private EditText etYarnCost, etLength;
    private CardView cardResult;
    private TextView tvMaterialCost, tvLabourCost, tvZariCost, tvBaseCost, tvRetailPrice, tvMinPrice;
    private double currentRetailPrice = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_price_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spCalcMaterial = view.findViewById(R.id.spCalcMaterial);
        spBorder = view.findViewById(R.id.spBorder);
        spZari = view.findViewById(R.id.spZari);
        etYarnCost = view.findViewById(R.id.etYarnCost);
        etLength = view.findViewById(R.id.etLength);
        cardResult = view.findViewById(R.id.cardResult);

        tvMaterialCost = view.findViewById(R.id.tvMaterialCost);
        tvLabourCost = view.findViewById(R.id.tvLabourCost);
        tvZariCost = view.findViewById(R.id.tvZariCost);
        tvBaseCost = view.findViewById(R.id.tvBaseCost);
        tvRetailPrice = view.findViewById(R.id.tvRetailPrice);
        tvMinPrice = view.findViewById(R.id.tvMinPrice);

        ArrayAdapter<String> matAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"Silk", "Cotton"});
        matAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCalcMaterial.setAdapter(matAdapter);

        ArrayAdapter<String> borderAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"Simple", "Medium", "Intricate"});
        borderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBorder.setAdapter(borderAdapter);

        ArrayAdapter<String> zariAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"None", "Partial", "Full"});
        zariAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spZari.setAdapter(zariAdapter);

        Button btnCalculate = view.findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(v -> calculatePrice());

        Button btnShare = view.findViewById(R.id.btnShare);
        btnShare.setOnClickListener(v -> {
            if (currentRetailPrice > 0) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello! The recommended retail price for this saree is ₹" + String.format("%.2f", currentRetailPrice));
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share Price"));
            }
        });
    }

    private void calculatePrice() {
        String yarnStr = etYarnCost.getText().toString();
        String lenStr = etLength.getText().toString();

        if (yarnStr.isEmpty() || lenStr.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter yarn cost and length", Toast.LENGTH_SHORT).show();
            return;
        }

        double yarnCost = Double.parseDouble(yarnStr);
        double length = Double.parseDouble(lenStr);
        String border = spBorder.getSelectedItem().toString();
        String zari = spZari.getSelectedItem().toString();

        double[] costs = PriceCalculatorUtil.calculatePrice(yarnCost, length, border, zari);

        tvMaterialCost.setText("Material Cost: ₹" + String.format("%.2f", costs[0]));
        tvLabourCost.setText("Labour Cost: ₹" + String.format("%.2f", costs[1]));
        tvZariCost.setText("Zari Cost: ₹" + String.format("%.2f", costs[2]));
        tvBaseCost.setText("Total Base Cost: ₹" + String.format("%.2f", costs[3]));
        tvRetailPrice.setText("✅ Recommended Retail Price: ₹" + String.format("%.2f", costs[4]));
        tvMinPrice.setText("⚠️ Minimum Price: ₹" + String.format("%.2f", costs[5]));

        currentRetailPrice = costs[4];
        cardResult.setVisibility(View.VISIBLE);
    }
}
