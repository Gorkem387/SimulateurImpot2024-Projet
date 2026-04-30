package com.kerware.simulateur;

/**
 * Adaptateur faisant le pont entre l'interface standard
 * et la vieille classe Simulateur.
 */
public final class AdaptateurVersCodeHerite implements ICalculateurImpot {

    /** Instance du vieux simulateur à tester. */
    private final Simulateur simulateurHerite = new Simulateur();

    @Override
    public void setRevenusNet(final int rn) {
        simulateurHerite.setRevenusNet(rn);
    }

    @Override
    public void setSituationFamiliale(final SituationFamiliale sf) {
        simulateurHerite.setSituationFamilliale(sf);
    }

    @Override
    public void setNbEnfantsACharge(final int nbe) {
        simulateurHerite.setNbEnfantsACharge(nbe);
    }

    @Override
    public void setNbEnfantsSituationHandicap(final int nbesh) {
        simulateurHerite.setNbEnfantsEnSituationDeHandicap(nbesh);
    }

    @Override
    public void setParentIsole(final boolean pi) {
        simulateurHerite.setParentIsole(pi);
    }

    @Override
    public void calculImpotSurRevenuNet() {
        simulateurHerite.calculImpotSurRevenuNet();
    }

    @Override
    public int getRevenuFiscalReference() {
        return simulateurHerite.getRevenuFiscalDeReference();
    }

    @Override
    public int getAbattement() {
        return simulateurHerite.getAbattement();
    }

    @Override
    public double getNbPartsFoyerFiscal() {
        return simulateurHerite.getNbPartsFoyerFiscal();
    }

    @Override
    public int getImpotAvantDecote() {
        return simulateurHerite.getImpotSurRevenuNet()
                + simulateurHerite.getDecote();
    }

    @Override
    public int getDecote() {
        return simulateurHerite.getDecote();
    }

    @Override
    public int getImpotSurRevenuNet() {
        return simulateurHerite.getImpotSurRevenuNet();
    }
}
