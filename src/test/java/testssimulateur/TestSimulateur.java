package testssimulateur;

import com.kerware.simulateur.AdaptateurVersCodeHerite;
import com.kerware.simulateur.ICalculateurImpot;
import com.kerware.simulateur.SituationFamiliale;
import com.kerware.simulateurreusine.AdaptateurVersCodeReusine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSimulateur {
    static final int CODE_HERITE = 1;
    static final int CODE_REUSINE = 2;
    static final int CODE = CODE_REUSINE;
    ICalculateurImpot calculateur;
    @BeforeEach
    public void prepareCalculateurImpot() {
        switch( CODE ) {
            case  CODE_HERITE -> calculateur = new AdaptateurVersCodeHerite();
            case  CODE_REUSINE -> calculateur = new AdaptateurVersCodeReusine();
        }
    }

    @Test
    @DisplayName( "Test du calcul de l'impot pour un célibataire sans enfant")
    public void testImpotSurRevenuNetPourUnCelibataireSansEnfant() {
        // Arrange
        calculateur.setRevenusNet(35000);
        calculateur.setSituationFamiliale( SituationFamiliale.CELIBATAIRE );
        calculateur.setNbEnfantsACharge(0);
        calculateur.setNbEnfantsSituationHandicap(0);
        calculateur.setParentIsole( false );

        // Act
        calculateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals( 2736 , calculateur.getImpotSurRevenuNet());
        assertEquals( 1 , calculateur.getNbPartsFoyerFiscal());

    }

    @Test
    @DisplayName("Test abattement standard de 10%")
    public void testAbattementStandardDixPourcent() {
        // Arrange
        calculateur.setRevenusNet(30000);
        calculateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);

        // Act
        calculateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals(3000, calculateur.getAbattement(), "L'abattement doit être de 10% du revenu net.");
        assertEquals(27000, calculateur.getRevenuFiscalReference(), "RFR = Revenu Net - Abattement.");
    }

    @Test
    @DisplayName("Test abattement plafonné au maximum (14 171€)")
    public void testAbattementPlafonneAuMaximum() {
        // Arrange
        calculateur.setRevenusNet(200000);
        calculateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);

        // Act
        calculateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals(14171, calculateur.getAbattement(), "L'abattement ne peut pas dépasser 14 171€.");
    }

    @Test
    @DisplayName("Test abattement minimum (495€)")
    public void testAbattementMinimum() {
        // Arrange
        calculateur.setRevenusNet(2000);
        calculateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);

        // Act
        calculateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals(495, calculateur.getAbattement(), "L'abattement ne peut pas être inférieur à 495€.");
    }

    @Test
    @DisplayName("Test Famille standard (Couple + 2 enfants)")
    public void testPartsCoupleDeuxEnfants() {
        // Arrange
        calculateur.setRevenusNet(50000);
        calculateur.setSituationFamiliale(SituationFamiliale.MARIE);
        calculateur.setNbEnfantsACharge(2);

        // Act
        calculateur.setNbEnfantsSituationHandicap(0);
        calculateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals(3.0, calculateur.getNbPartsFoyerFiscal(), "Couple + 2 enfants = 3 parts.");
    }

    @Test
    @DisplayName("Test Famille nombreuse (Célibataire + 3 enfants)")
    public void testPartsCelibataireTroisEnfants() {
        // Arrange
        calculateur.setRevenusNet(40000);
        calculateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        calculateur.setNbEnfantsACharge(3);

        // Act
        calculateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals(3.0, calculateur.getNbPartsFoyerFiscal(), "1 part + 0.5 + 0.5 + 1.0 = 3 parts.");
    }

    @Test
    @DisplayName("Test Handicap et Parent Isolé")
    public void testPartsHandicapEtParentIsole() {
        // Arrange
        calculateur.setRevenusNet(30000);
        calculateur.setSituationFamiliale(SituationFamiliale.DIVORCE);
        calculateur.setNbEnfantsACharge(1);
        calculateur.setNbEnfantsSituationHandicap(1);
        calculateur.setParentIsole(true);

        // Act
        calculateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals(2.5, calculateur.getNbPartsFoyerFiscal(), "Calcul incluant handicap et parent isolé.");
    }

    @Test
    @DisplayName("Test d'un très haut revenu (Tranche 45%)")
    public void testHautRevenuTranche45() {
        // Arrange
        calculateur.setRevenusNet(250000);
        calculateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        calculateur.setNbEnfantsACharge(0);
        calculateur.setParentIsole(false);

        // Act
        calculateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals(83268, calculateur.getImpotSurRevenuNet());
    }

    @Test
    @DisplayName("Test de la décote (Revenu modeste)")
    public void testDecoteRevenuModeste() {
        // Arrange
        calculateur.setRevenusNet(22000);
        calculateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        calculateur.setNbEnfantsACharge(0);

        // Act
        calculateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals(486, calculateur.getImpotSurRevenuNet());
        assertEquals(450, calculateur.getDecote(), "La décote réelle du code hérité est 450");
    }

    @Test
    @DisplayName("Test du plafonnement du quotient familial")
    public void testPlafonnementReductionImpot() {
        // Arrange
        calculateur.setRevenusNet(150000);
        calculateur.setSituationFamiliale(SituationFamiliale.MARIE);
        calculateur.setNbEnfantsACharge(5);

        // Act
        calculateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals(13249, calculateur.getImpotSurRevenuNet());
    }

    @Test
    @DisplayName("Test de la contribution exceptionnelle (Très hauts revenus)")
    public void testContributionHautsRevenus() {
        // Arrange
        calculateur.setRevenusNet(600000);
        calculateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        calculateur.setNbEnfantsACharge(0);

        // Act
        calculateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals(240768, calculateur.getImpotSurRevenuNet());
    }

    @Test
    @DisplayName("Couverture : Veuf sans enfant")
    public void testVeufSansEnfant() {
        calculateur.setSituationFamiliale(SituationFamiliale.VEUF);
        calculateur.setNbEnfantsACharge(0);
        calculateur.setRevenusNet(30000);
        calculateur.calculImpotSurRevenuNet();

        assertEquals(1, calculateur.getNbPartsFoyerFiscal());
    }

    @Test
    @DisplayName("Couverture : Veuf avec enfants")
    public void testVeufAvecEnfants() {
        calculateur.setSituationFamiliale(SituationFamiliale.VEUF);
        calculateur.setNbEnfantsACharge(2);
        calculateur.setRevenusNet(40000);
        calculateur.calculImpotSurRevenuNet();

        assertEquals(3.0, calculateur.getNbPartsFoyerFiscal());
    }

    @Test
    @DisplayName("Couverture : Plafond non atteint")
    public void testPlafondNonAtteint() {

        calculateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        calculateur.setRevenusNet(20000);
        calculateur.setNbEnfantsACharge(1);
        calculateur.calculImpotSurRevenuNet();

        assertTrue(calculateur.getImpotSurRevenuNet() >= 0);
    }

}

