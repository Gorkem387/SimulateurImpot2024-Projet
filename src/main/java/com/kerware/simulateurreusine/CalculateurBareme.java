package com.kerware.simulateurreusine;

/**
 * Applique les tranches du barème progressif.
 */
public final class CalculateurBareme {
    /**
     * @param revenuParPart Le quotient familial.
     * @return L'impôt brut pour une part.
     */
    public double calculerImpotBrut(final double revenuParPart) {
        double impot = 0;
        final int[] limites = ConstantesFiscales.LIMITES_TRANCHES;
        final double[] taux = ConstantesFiscales.TAUX_TRANCHES;

        for (int i = 0; i < taux.length; i++) {
            if (revenuParPart > limites[i]) {
                final double maxTranche = Math.min(revenuParPart,
                        limites[i + 1]);
                final double baseTaxable = maxTranche - limites[i];
                impot += baseTaxable * taux[i];
            }
            if (revenuParPart < limites[i + 1]) {
                break;
            }
        }
        return impot;
    }
}
