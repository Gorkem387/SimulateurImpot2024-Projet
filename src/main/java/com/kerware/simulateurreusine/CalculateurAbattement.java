package com.kerware.simulateurreusine;

/**
 * Gère le calcul de l'abattement forfaitaire de 10%.
 */
public final class CalculateurAbattement {
    /**
     * @param revenuNet Le revenu déclaré.
     * @return Le montant de l'abattement plafonné.
     */
    public double calculer(final int revenuNet) {
        double abt = revenuNet * ConstantesFiscales.TAUX_ABATTEMENT;
        if (abt > ConstantesFiscales.PLAFOND_ABATTEMENT) {
            return ConstantesFiscales.PLAFOND_ABATTEMENT;
        }
        if (abt < ConstantesFiscales.PLANCHER_ABATTEMENT) {
            return ConstantesFiscales.PLANCHER_ABATTEMENT;
        }
        return abt;
    }
}
