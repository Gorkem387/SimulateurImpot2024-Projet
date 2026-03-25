package com.kerware.simulateurreusine;

public class CalculateurBareme {
    public double calculerImpotBrut(double revenuParPart) {
        double impot = 0;
        int[] limites = ConstantesFiscales.LIMITES_TRANCHES;
        double[] taux = ConstantesFiscales.TAUX_TRANCHES;

        for (int i = 0; i < taux.length; i++) {
            if (revenuParPart > limites[i]) {
                double baseTaxable = Math.min(revenuParPart, limites[i+1]) - limites[i];
                impot += baseTaxable * taux[i];
            }
            if (revenuParPart < limites[i+1]) break;
        }
        return impot;
    }
}
