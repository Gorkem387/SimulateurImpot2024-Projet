package com.kerware.simulateurreusine;

import com.kerware.simulateur.ICalculateurImpot;
import com.kerware.simulateur.SituationFamiliale;

/**
 * Adaptateur pour le nouveau simulateur réusiné.
 */
public final class AdaptateurVersCodeReusine implements ICalculateurImpot {
    /** Instance du simulateur. */
    private final Simulateur sim = new Simulateur();
    /** Revenu net. */
    private int rev;
    /** Situation familiale. */
    private SituationFamiliale sit;
    /** Nombre enfants. */
    private int nbe;
    /** Nombre handicapés. */
    private int nbh;
    /** Parent isolé. */
    private boolean iso;
    /** Résultat calcul. */
    private int res;

    @Override
    public void setRevenusNet(final int rn) {
        this.rev = rn;
    }

    @Override
    public void setSituationFamiliale(final SituationFamiliale sf) {
        this.sit = sf;
    }

    @Override
    public void setNbEnfantsACharge(final int n) {
        this.nbe = n;
    }

    @Override
    public void setNbEnfantsSituationHandicap(final int n) {
        this.nbh = n;
    }

    @Override
    public void setParentIsole(final boolean p) {
        this.iso = p;
    }

    @Override
    public void calculImpotSurRevenuNet() {
        this.res = (int) sim.calculImpot(rev, sit, nbe, nbh, iso);
    }

    @Override
    public int getRevenuFiscalReference() {
        return rev - getAbattement();
    }

    @Override
    public int getAbattement() {
        return (int) new CalculateurAbattement().calculer(rev);
    }

    @Override
    public double getNbPartsFoyerFiscal() {
        return new CalculateurParts().calculer(sit, nbe, nbh, iso);
    }

    @Override
    public int getImpotAvantDecote() {
        return getImpotSurRevenuNet() + getDecote();
    }

    @Override
    public int getDecote() {
        return sim.getDerniereDecote();
    }

    @Override
    public int getImpotSurRevenuNet() {
        return res;
    }
}
