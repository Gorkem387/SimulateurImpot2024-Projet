package com.kerware.simulateur;

/**
 * Interface définissant le contrat de calcul de l'impôt sur le revenu.
 */
public interface ICalculateurImpot {

    /** @param rn Le revenu net. */
    void setRevenusNet(int rn);

    /** @param sf La situation familiale. */
    void setSituationFamiliale(SituationFamiliale sf);

    /** @param nbe Le nombre d'enfants. */
    void setNbEnfantsACharge(int nbe);

    /** @param nbesh Le nombre d'enfants handicapés. */
    void setNbEnfantsSituationHandicap(int nbesh);

    /** @param pi Si le parent est isolé. */
    void setParentIsole(boolean pi);

    /** Lance le calcul global. */
    void calculImpotSurRevenuNet();

    /** @return Le revenu fiscal de référence. */
    int getRevenuFiscalReference();

    /** @return Le montant de l'abattement. */
    int getAbattement();

    /** @return Le nombre de parts fiscales. */
    double getNbPartsFoyerFiscal();

    /** @return L'impôt avant application de la décote. */
    int getImpotAvantDecote();

    /** @return Le montant de la décote. */
    int getDecote();

    /** @return L'impôt final dû. */
    int getImpotSurRevenuNet();
}
