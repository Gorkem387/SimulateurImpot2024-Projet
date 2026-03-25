package com.kerware.simulateurreusine;

public class CalculateurAbattement {
    public double calculer(int revenuNet) {
        double abattement = revenuNet * ConstantesFiscales.TAUX_ABATTEMENT;

        if (abattement > ConstantesFiscales.PLAFOND_ABATTEMENT) {
            return ConstantesFiscales.PLAFOND_ABATTEMENT;
        }
        if (abattement < ConstantesFiscales.PLANCHER_ABATTEMENT) {
            return ConstantesFiscales.PLANCHER_ABATTEMENT;
        }
        return abattement;
    }
}
