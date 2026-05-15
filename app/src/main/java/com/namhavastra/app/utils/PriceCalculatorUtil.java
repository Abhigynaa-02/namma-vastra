package com.namhavastra.app.utils;

public class PriceCalculatorUtil {
    public static double[] calculatePrice(double yarnCostPerMeter, double length, String border, String zari) {
        double materialCost = yarnCostPerMeter * length;
        int labourCost = 0;
        if ("Simple".equals(border)) {
            labourCost = 300;
        } else if ("Medium".equals(border)) {
            labourCost = 600;
        } else {
            labourCost = 1200;
        }

        int zariCost = 0;
        if ("None".equals(zari)) {
            zariCost = 0;
        } else if ("Partial".equals(zari)) {
            zariCost = 400;
        } else {
            zariCost = 900;
        }

        double baseCost = materialCost + labourCost + zariCost;
        double retailPrice = baseCost * 1.40;
        double minPrice = baseCost * 1.10;

        return new double[]{materialCost, labourCost, zariCost, baseCost, retailPrice, minPrice};
    }
}
