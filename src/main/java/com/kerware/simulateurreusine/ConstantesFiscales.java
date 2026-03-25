package com.kerware.simulateurreusine;

public class ConstantesFiscales {
    // Abattement
    public static final double TAUX_ABATTEMENT = 0.1;
    public static final int PLAFOND_ABATTEMENT = 14171;
    public static final int PLANCHER_ABATTEMENT = 495;

    // Barème 2024
    public static final int[] LIMITES_TRANCHES = {0, 11294, 28797, 82341, 177106, Integer.MAX_VALUE};
    public static final double[] TAUX_TRANCHES = {0.0, 0.11, 0.3, 0.41, 0.45};

    // Plafonnement quotient familial
    public static final double PLAFOND_DEMI_PART = 1759.0;
}
