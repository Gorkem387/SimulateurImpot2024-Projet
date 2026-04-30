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

    @Test
    @DisplayName("Couverture : Cas PACSE (équivalent Marié)")
    public void testSituationPacse() {
        calculateur.setSituationFamiliale(SituationFamiliale.PACSE);
        calculateur.setRevenusNet(50000);
        calculateur.calculImpotSurRevenuNet();

        // Vérifie que le comportement est identique à MARIE
        assertEquals(2.0, calculateur.getNbPartsFoyerFiscal());
    }

    @Test
    @DisplayName("Couverture : Parent Isolé sans enfant (pas de bonus)")
    public void testParentIsoleSansEnfant() {
        calculateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        calculateur.setNbEnfantsACharge(0);
        calculateur.setParentIsole(true); // Isolé mais sans enfant
        calculateur.setRevenusNet(30000);
        calculateur.calculImpotSurRevenuNet();

        // Le bonus de 0.5 ne doit pas s'appliquer car nbEnf == 0
        assertEquals(1.0, calculateur.getNbPartsFoyerFiscal());
    }

    @Test
    @DisplayName("Couverture : Décote pour un couple (revenu bas)")
    public void testDecoteCouple() {
        calculateur.setSituationFamiliale(SituationFamiliale.MARIE);
        calculateur.setRevenusNet(25000); // Revenu bas pour un couple
        calculateur.setNbEnfantsACharge(0);
        calculateur.calculImpotSurRevenuNet();

        // Doit déclencher la branche de décote spécifique au couple (seuil 3191€)
        assertTrue(calculateur.getDecote() > 0, "Le couple devrait bénéficier d'une décote.");
    }

    @Test
    @DisplayName("Couverture : Exactement 2 enfants (limite nbEnf <= 2)")
    public void testExactementDeuxEnfants() {
        calculateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        calculateur.setNbEnfantsACharge(2);
        calculateur.calculImpotSurRevenuNet();

        // 1 part (adulte) + 0.5 (enf1) + 0.5 (enf2) = 2.0
        assertEquals(2.0, calculateur.getNbPartsFoyerFiscal());
    }

    @Test
    @DisplayName("Couverture : Revenu très faible (Branches décote et abattement)")
    public void testRevenuTresFaible() {
        // Teste le plancher d'abattement (495€) et la décote totale
        calculateur.setRevenusNet(1000);
        calculateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        calculateur.calculImpotSurRevenuNet();

        assertEquals(495, calculateur.getAbattement());
        assertEquals(0, calculateur.getImpotSurRevenuNet());
    }

    @Test
    @DisplayName("Couverture : Appel des méthodes de l'Adapter")
    public void testAppelMethodesAdapter() {
        calculateur.setRevenusNet(30000);
        calculateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        calculateur.calculImpotSurRevenuNet();

        int impotTotal = calculateur.getImpotAvantDecote();
        assertTrue(impotTotal >= calculateur.getImpotSurRevenuNet());
    }

    @Test
    @DisplayName("Couverture : Revenu extrême pour forcer la fin du barème")
    public void testRevenuExtremePourBareme() {
        calculateur.setRevenusNet(Integer.MAX_VALUE);
        calculateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        calculateur.calculImpotSurRevenuNet();

        assertTrue(calculateur.getImpotSurRevenuNet() > 0);
    }

    @Test
    @DisplayName("Couverture : Forcer la fin naturelle de la boucle du barème")
    public void testFinNaturelleBareme() {
        calculateur.setRevenusNet(Integer.MAX_VALUE);
        calculateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);

        calculateur.calculImpotSurRevenuNet();

        // On vérifie juste que le calcul s'est fait sans erreur
        assertTrue(calculateur.getImpotSurRevenuNet() > 0);
    }

    @Test
    @DisplayName("Couverture Bareme : Forcer la sortie naturelle du for")
    public void testBaremeSortieNaturelle() {
        com.kerware.simulateurreusine.CalculateurBareme bareme =
                new com.kerware.simulateurreusine.CalculateurBareme();

        double revenuEnorme = 3000000000.0; // 3 milliards
        double resultat = bareme.calculerImpotBrut(revenuEnorme);

        assertTrue(resultat > 0);
    }
}

