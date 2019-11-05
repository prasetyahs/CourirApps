package com.syncode.courirapps.utils;

public class Formula {


    public static double haversineFormula(double lon1, double lon2, double lat1, double lat2) {
        int radius = 6371;
        double lon1ToRadians = Math.toRadians(lon1);
        double lon2ToRadians = Math.toRadians(lon2);
        double lat1ToRadians = Math.toRadians(lat1);
        double lat2ToRadians = Math.toRadians(lat2);
        double x = (lon2ToRadians - lon1ToRadians) * Math.cos((lat1ToRadians + lat2ToRadians) / 2);
        double y = lat2ToRadians - lat1ToRadians;
        return Math.sqrt((x * x) + (y * y)) * radius;
    }
}
