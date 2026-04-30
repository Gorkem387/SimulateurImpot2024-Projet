package com.kerware.simulateurreusine;

/**
 * Centralise toutes les constantes fiscales pour l'année 2024.
 * Évite la duplication et facilite les mises à jour réglementaires.
 */
public final class ConstantesFiscales {

    /** Constructeur privé pour empêcher l'instanciation. */
    private ConstantesFiscales() { }

    /** Taux de l'abattement forfaitaire. */
    public static final double TAUX_ABATTEMENT = 0.1;
    /** Plafond maximum de l'abattement. */
    public static final int PLAFOND_ABATTEMENT = 14171;
    /** Plancher minimum de l'abattement. */
    public static final int PLANCHER_ABATTEMENT = 495;

    /** Seuils des tranches d'imposition 2024. */
    public static final int[] LIMITES_TRANCHES = {
            0, 11294, 28797, 82341, 177106, Integer.MAX_VALUE
    };
    /** Taux applicables aux tranches d'imposition. */
    public static final double[] TAUX_TRANCHES = {0.0, 0.11, 0.3, 0.41, 0.45};

    /** Montant du plafond par demi-part fiscale. */
    public static final double PLAFOND_DEMI_PART = 1759.0;
    /** Valeur d'une demi-part de quotient familial. */
    public static final double UNITE_QUOTIENT = 0.5;

    /** Seuil de décote pour une personne seule. */
    public static final int SEUIL_DECOTE_SIMPLE = 1929;
    /** Seuil de décote pour un couple. */
    public static final int SEUIL_DECOTE_DUO = 3191;
    /** Montant maximum de la décote pour une personne seule. */
    public static final int MAX_DECOTE_SIMPLE = 873;
    /** Montant maximum de la décote pour un couple. */
    public static final int MAX_DECOTE_DUO = 1444;
    /** Taux appliqué pour le calcul de la décote. */
    public static final double TAUX_DECOTE = 0.4525;
}
