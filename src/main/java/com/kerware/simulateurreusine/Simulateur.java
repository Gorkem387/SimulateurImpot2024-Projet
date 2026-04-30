package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;

/**
 * Classe principale orchestrant le calcul complet de l'impôt.
 */
public final class Simulateur {
    /** Calculateur d'abattement. */
    private final CalculateurAbattement calcAbt = new CalculateurAbattement();
    /** Calculateur de parts. */
    private final CalculateurParts calcParts = new CalculateurParts();
    /** Calculateur de barème. */
    private final CalculateurBareme calcBareme = new CalculateurBareme();
    /** Calculateur de décote. */
    private final CalculateurDecote calcDecote = new CalculateurDecote();
    /** Valeur de la dernière décote calculée. */
    private double derniereDecote;

    /**
     * Calcule l'impôt total.
     * @param rev Revenu net.
     * @param sit Situation familiale.
     * @param nbE Nombre d'enfants.
     * @param nbH Enfants handicapés.
     * @param pI Parent isolé.
     * @return Impôt final.
     */
    public long calculImpot(final int rev, final SituationFamiliale sit,
                            final int nbE, final int nbH, final boolean pI) {
        final double rfr = rev - calcAbt.calculer(rev);
        final boolean estCouple = (sit == SituationFamiliale.MARIE
                || sit == SituationFamiliale.PACSE);
        final double pDecl = estCouple ? 2.0 : 1.0;
        final double pTot = calcParts.calculer(sit, nbE, nbH, pI);

        final double iDecl = calcBareme.calculerImpotBrut(rfr / pDecl) * pDecl;
        final double iFoyer = calcBareme.calculerImpotBrut(rfr / pTot) * pTot;

        double res = appliquerPlafonnement(iDecl, iFoyer, pTot - pDecl);
        this.derniereDecote = calcDecote.calculer(res, pDecl);

        return (long) Math.max(0, Math.round(res) - derniereDecote);
    }

    /**
     * Applique le plafond du quotient familial.
     * @param iD Impôt déclarant.
     * @param iF Impôt foyer.
     * @param diffP Écart de parts.
     * @return Impôt plafonné.
     */
    private double appliquerPlafonnement(final double iD, final double iF,
                                         final double diffP) {
        final double plafond = (diffP / ConstantesFiscales.UNITE_QUOTIENT)
                * ConstantesFiscales.PLAFOND_DEMI_PART;
        return ((iD - iF) > plafond) ? (iD - plafond) : iF;
    }

    /** @return La dernière décote. */
    public int getDerniereDecote() {
        return (int) Math.round(derniereDecote);
    }
}
