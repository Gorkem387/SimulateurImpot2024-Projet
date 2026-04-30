package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;

/**
 * Calcule le nombre de parts du foyer fiscal.
 */
public final class CalculateurParts {
    /**
     * @param situation État civil.
     * @param nbE Nombre d'enfants.
     * @param nbH Enfants handicapés.
     * @param pIso Parent isolé.
     * @return Le nombre total de parts.
     */
    public double calculer(final SituationFamiliale situation,
                           final int nbE, final int nbH, final boolean pIso) {
        double pts = switch (situation) {
            case CELIBATAIRE, DIVORCE -> 1.0;
            case MARIE, PACSE -> 2.0;
            case VEUF -> (nbE > 0) ? 2.0 : 1.0;
        };

        if (nbE <= 2) {
            pts += nbE * ConstantesFiscales.UNITE_QUOTIENT;
        } else {
            pts += 1.0 + (nbE - 2);
        }

        if (pIso && nbE > 0) {
            pts += ConstantesFiscales.UNITE_QUOTIENT;
        }
        pts += nbH * ConstantesFiscales.UNITE_QUOTIENT;

        return pts;
    }
}
