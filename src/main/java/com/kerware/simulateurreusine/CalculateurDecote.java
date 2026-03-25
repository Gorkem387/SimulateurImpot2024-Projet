package com.kerware.simulateurreusine;

public class CalculateurDecote {
    public double calculer(double impotBrut, double nbPartsDeclarant) {
        double seuil = (nbPartsDeclarant == 1) ? 1929 : 3191;
        double decoteMax = (nbPartsDeclarant == 1) ? 873 : 1444;
        double tauxDecote = 0.4525;

        if (impotBrut >= seuil) return 0;

        double montantDecote = decoteMax - (impotBrut * tauxDecote);
        return Math.max(0, Math.round(montantDecote));
    }
}
