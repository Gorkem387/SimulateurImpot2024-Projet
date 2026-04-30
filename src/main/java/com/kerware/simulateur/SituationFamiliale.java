package com.kerware.simulateur;

/**
 * Énumération des différentes situations familiales reconnues par le fisc.
 */
public enum SituationFamiliale {
    /** Personne seule. */
    CELIBATAIRE,
    /** Partenaires liés par un PACS. */
    PACSE,
    /** Personnes mariées. */
    MARIE,
    /** Personne divorcée. */
    DIVORCE,
    /** Personne veuve. */
    VEUF
}
