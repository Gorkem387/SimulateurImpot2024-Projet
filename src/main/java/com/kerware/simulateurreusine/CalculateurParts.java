package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;

public class CalculateurParts {
    public double calculer(SituationFamiliale situation, int nbEnfants, int nbHandicapes, boolean parentIsole) {
        double parts = 0;

        // Parts du/des déclarants
        parts += switch (situation) {
            case CELIBATAIRE, DIVORCE -> 1.0;
            case MARIE, PACSE -> 2.0;
            case VEUF -> (nbEnfants > 0) ? 2.0 : 1.0;
        };

        // Parts enfants
        if (nbEnfants <= 2) {
            parts += nbEnfants * 0.5;
        } else {
            parts += 1.0 + (nbEnfants - 2); // 0.5 pour les 2 premiers, 1 pour les suivants
        }

        // Bonus parent isolé et handicap
        if (parentIsole && nbEnfants > 0) parts += 0.5;
        parts += nbHandicapes * 0.5;

        return parts;
    }
}
