package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;

public class Simulateur {
    private final CalculateurAbattement calcAbt = new CalculateurAbattement();
    private final CalculateurParts calcParts = new CalculateurParts();
    private final CalculateurBareme calcBareme = new CalculateurBareme();
    private final CalculateurDecote calcDecote = new CalculateurDecote();
    private double derniereDecoteCalculee;

    public long calculImpot(int revNet, SituationFamiliale sit, int nbE, int nbH, boolean parI) {
        // Abattement
        double abattement = calcAbt.calculer(revNet);
        double rfr = revNet - abattement;

        // Parts
        double partsDecl = (sit == SituationFamiliale.MARIE || sit == SituationFamiliale.PACSE) ? 2.0 : 1.0;
        double partsTotales = calcParts.calculer(sit, nbE, nbH, parI);

        // Calcul de l'impôt (avec plafonnement du quotient familial)
        double impBrutDecl = calcBareme.calculerImpotBrut(rfr / partsDecl) * partsDecl;
        double impBrutFoyer = calcBareme.calculerImpotBrut(rfr / partsTotales) * partsTotales;

        double impotFinal = appliquerPlafonnement(impBrutDecl, impBrutFoyer, partsTotales - partsDecl);

        // Décote
        double montantDecote = calcDecote.calculer(impotFinal, partsDecl);
        impotFinal = Math.max(0, Math.round(impotFinal) - montantDecote);

        this.derniereDecoteCalculee = montantDecote;
        return (long) impotFinal;
    }

    private double appliquerPlafonnement(double impDecl, double impFoyer, double ecartParts) {
        double plafond = (ecartParts / 0.5) * ConstantesFiscales.PLAFOND_DEMI_PART;
        if ((impDecl - impFoyer) > plafond) {
            return impDecl - plafond;
        }
        return impFoyer;
    }

    public int getDerniereDecote() {
        return (int) Math.round(derniereDecoteCalculee);
    }
}