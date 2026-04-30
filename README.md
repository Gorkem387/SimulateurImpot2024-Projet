# 🧮 Simulateur d'Impôt sur le Revenu 2024

> Transformer un code legacy en code professionnel : mission accomplie à 100% de couverture

## 🎯 Le projet en bref

Un simulateur d'impôt "qui marche mais qui fait peur" transformé en code propre, testé et maintenable.

**Challenge relevé :**
- ✅ 100% de couverture de code (objectif : 90% minimum)
- ✅ 100% des tests passent
- ✅ Conformité Checkstyle
- ✅ Architecture modulaire avec adaptateurs

## 🚀 Démarrage rapide

### Prérequis
- Java 17+
- Maven 3.9+

### Installation et lancement

```bash
# Cloner le projet
git clone https://github.com/Gorkem387/SimulateurImpot2024-Projet.git
cd SimulateurImpot2024-Projet

# Lancer tests + rapports
./automatisation-qualite-code.bat    # Windows
# ou
mvn clean verify site                # Commande Maven directe
```

### Voir les rapports

Ouvrir dans votre navigateur : `target/site/index.html`

Vous y trouverez :
- 📊 Résultats des tests (Surefire)
- 🎯 Couverture de code (JaCoCo)
- 🔍 Analyse qualité (Checkstyle)

## 📐 Règles métier implémentées

Le simulateur calcule l'impôt 2024 sur les revenus 2023 :

| Exigence | Description |
|----------|-------------|
| **EXG_IMPOT_01** | Arrondis à l'euro |
| **EXG_IMPOT_02** | Abattement 10% (min 495€, max 14 171€) |
| **EXG_IMPOT_03** | Parts fiscales (famille, handicap, parent isolé) |
| **EXG_IMPOT_04** | Barème progressif (0%, 11%, 30%, 41%, 45%) |
| **EXG_IMPOT_05** | Plafonnement quotient familial (1 759€/demi-part) |
| **EXG_IMPOT_06** | Décote revenus modestes |
| **EXG_IMPOT_07** | Contribution exceptionnelle hauts revenus |

## 💻 Utilisation

```java
ICalculateurImpot calc = new AdaptateurVersCodeHerite();

calc.setRevenusNet(35000);
calc.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
calc.setNbEnfantsACharge(0);
calc.calculImpotSurRevenuNet();

int impot = calc.getImpotSurRevenuNet();
System.out.println("Impôt : " + impot + "€");
```

## 🏗️ Architecture

**Pattern utilisé :** Adaptateur (permet de tester le code legacy sans le modifier)

## 📊 Métriques qualité

| Critère | Objectif | ✅ Résultat |
|---------|----------|-------------|
| Couverture de code | ≥ 90% | **100%** |
| Tests réussis | 100% | **100%** |
| Build Maven | SUCCESS | **SUCCESS** |

## 🛠️ Stack technique

- **Java 17** - Langage
- **JUnit 5** - Tests unitaires  
- **Maven** - Build & dépendances
- **JaCoCo** - Couverture de code
- **Checkstyle** - Analyse statique

## 📚 Contexte

Projet réalisé dans le cadre du module **R4.02 - Qualité de développement** (IUT Caen).

**Compétences démontrées :**
- Tests unitaires et TDD
- Refactoring de code legacy
- Automatisation qualité avec Maven
- Gestion de projet avec Git

## 🤝 Auteur

**Gorkem Yildiz**  
**Mylan Baudin--Marie**
