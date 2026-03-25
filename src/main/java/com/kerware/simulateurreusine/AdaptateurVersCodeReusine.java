package com.kerware.simulateurreusine;

import com.kerware.simulateur.ICalculateurImpot;
import com.kerware.simulateur.SituationFamiliale;

public class AdaptateurVersCodeReusine implements ICalculateurImpot {

    private final Simulateur simulateurReusine = new Simulateur();

    private int revenusNet;
    private SituationFamiliale situationFamiliale;
    private int nbEnfants;
    private int nbEnfantsHandicapes;
    private boolean parentIsole;

    private int impotCalcule;

    @Override
    public void setRevenusNet(int rn) {
        this.revenusNet = rn;
    }

    @Override
    public void setSituationFamiliale(SituationFamiliale sf) {
        this.situationFamiliale = sf;
    }

    @Override
    public void setNbEnfantsACharge(int nbe) {
        this.nbEnfants = nbe;
    }

    @Override
    public void setNbEnfantsSituationHandicap(int nbesh) {
        this.nbEnfantsHandicapes = nbesh;
    }

    @Override
    public void setParentIsole(boolean pi) {
        this.parentIsole = pi;
    }

    @Override
    public void calculImpotSurRevenuNet() {
        this.impotCalcule = (int) simulateurReusine.calculImpot(
                revenusNet,
                situationFamiliale,
                nbEnfants,
                nbEnfantsHandicapes,
                parentIsole
        );
    }

    @Override
    public int getRevenuFiscalReference() {
        return revenusNet - getAbattement();
    }

    @Override
    public int getAbattement() {
        return (int) new CalculateurAbattement().calculer(revenusNet);
    }

    @Override
    public double getNbPartsFoyerFiscal() {
        return new CalculateurParts().calculer(situationFamiliale, nbEnfants, nbEnfantsHandicapes, parentIsole);
    }

    @Override
    public int getImpotAvantDecote() {
        return getImpotSurRevenuNet() + getDecote();
    }

    @Override
    public int getDecote() {
        double partsDecl = (situationFamiliale == SituationFamiliale.MARIE || situationFamiliale == SituationFamiliale.PACSE) ? 2.0 : 1.0;
        return (int) new CalculateurDecote().calculer(impotCalcule, partsDecl);
    }

    @Override
    public int getImpotSurRevenuNet() {
        return impotCalcule;
    }
}