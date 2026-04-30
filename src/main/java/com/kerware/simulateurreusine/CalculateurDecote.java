package com.kerware.simulateurreusine;

/**
 * Calcule la réduction d'impôt pour les ménages modestes.
 */
public final class CalculateurDecote {
    /**
     * @param impotBrut L'impôt avant décote.
     * @param nbPartsDeclarant 1 pour célibataire, 2 pour couple.
     * @return Le montant de la décote.
     */
    public double calculer(final double impotBrut,
                           final double nbPartsDeclarant) {
        final boolean estSeul = (nbPartsDeclarant == 1);

        final double seuil = estSeul
                ? ConstantesFiscales.SEUIL_DECOTE_SIMPLE
                : ConstantesFiscales.SEUIL_DECOTE_DUO;

        final double decoteMax = estSeul
                ? ConstantesFiscales.MAX_DECOTE_SIMPLE
                : ConstantesFiscales.MAX_DECOTE_DUO;

        if (impotBrut >= seuil) {
            return 0;
        }

        final double tDecote = ConstantesFiscales.TAUX_DECOTE;
        final double montantDecote = decoteMax - (impotBrut * tDecote);

        return Math.max(0, Math.round(montantDecote));
    }
}
