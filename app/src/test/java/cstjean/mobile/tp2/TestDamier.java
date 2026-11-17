package cstjean.mobile.tp2;

import java.util.List;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test pour la classe Damier.
 * Teste les fonctionnalités du damier telles que l'initialisation,
 * la gestion des pions, et l'affichage.
 *
 * @author Alex Heroux
 */
public class TestDamier extends TestCase {
    /**
     * Damier.
     */
    private Damier damier;
    /**
     * Affichage damier.
     */
    private AffichageDamier affichageDamier;

    /**
     * Avant chaque test.
     */
    @Before
    public void setUp() {
        damier = new Damier();
        affichageDamier = new AffichageDamier();
        damier.initialiser();
    }

    /**
     * Teste l'initialisation du damier.
     */
    @Test
    public void testInitialisationDamier() {
        int pieceCount = damier.countPieces();
        Assert.assertEquals(40, pieceCount);

        String expectedRepresentation =
                "-P-P-P-P-P\nP-P-P-P-P-\n-P-P-P-P-P\nP-P-P-P-P-\n-x-x-x-x-x\n" +
                        "x-x-x-x-x-\n-p-p-p-p-p\np-p-p-p-p-\n-p-p-p-p-p\np-p-p-p-p-\n";

        String actualRepresentation = affichageDamier.afficherDamier(damier);
        Assert.assertEquals(expectedRepresentation, actualRepresentation);
    }

    /**
     * Teste le déplacement simple d'un pion blanc.
     */
    @Test
    public void testDeplacementPionBlanc() {
        boolean deplacementValide = damier.deplacer(35, 30);
        Assert.assertTrue(deplacementValide);
        Assert.assertNotNull(damier.getPiece(30));
        Assert.assertNull(damier.getPiece(35));
        Assert.assertEquals(Piece.Couleur.Noir, damier.getJoueurCourant());
    }

    /**
     * Teste le déplacement simple d'un pion noir.
     */
    @Test
    public void testDeplacementPionNoir() {
        // Faire jouer un pion blanc pour passer le tour aux noirs
        damier.deplacer(35, 30);

        boolean deplacementValide = damier.deplacer(16, 21);
        Assert.assertTrue(deplacementValide);
        Assert.assertNotNull(damier.getPiece(21));
        Assert.assertNull(damier.getPiece(16));
        Assert.assertEquals(Piece.Couleur.Blanc, damier.getJoueurCourant());
    }

    /**
     * Teste un déplacement invalide (mouvement non autorisé).
     */
    @Test
    public void testDeplacementInvalide() {
        boolean deplacementValide = damier.deplacer(35, 31); // Déplacement non diagonal
        Assert.assertFalse(deplacementValide);
        Assert.assertNotNull(damier.getPiece(35));
    }

    /**
     * Teste la prise d'un pion adverse.
     */
    @Test
    public void testPrisePion() {
        // Préparation du damier pour permettre une prise
        damier.setPiece(30, new Pion(Piece.Couleur.Noir)); // Placer un pion noir en position 24

        System.out.println(affichageDamier.afficherDamier(damier));
        // Le pion blanc en position 35 peut prendre le pion noir en position 30 en atterrissant en 24
        List<List<Integer>> prises = damier.getPiece(35).getPrisesValides(damier, 35);
        boolean prisePossible = false;

        for (List<Integer> sequence : prises) {
            System.out.println("Prise:" + sequence);
            if (!sequence.isEmpty() && sequence.get(sequence.size() - 1) == 24) {
                prisePossible = true;
                break;
            }
        }

        Assert.assertTrue(prisePossible);

        // Effectuer la prise
        boolean deplacementValide = damier.deplacer(35, 24);

        // Vérifications après la prise
        Assert.assertTrue(deplacementValide);
        Assert.assertNull(damier.getPiece(35));
        Assert.assertNotNull(damier.getPiece(24));
        System.out.println(affichageDamier.afficherDamier(damier));
        Assert.assertEquals(Piece.Couleur.Blanc, damier.getPiece(24).getCouleur());
        Assert.assertNull(damier.getPiece(30));
    }

    /**
     * Teste la prise d'un pion adverse par une dame.
     */
    @Test
    public void testPriseDame() {
        // Promouvoir un pion blanc en dame
        damier.setPiece(14, new Dame(Piece.Couleur.Blanc));

        // Positionner un pion noir pour la prise
        damier.setPiece(23, new Pion(Piece.Couleur.Noir));
        damier.setPiece(32, null); // S'assurer que la case derrière est libre
        damier.setPiece(19, null); // S'assurer que la case derrière est libre

        Piece dameBlanche = damier.getPiece(14);
        System.out.println(affichageDamier.afficherDamier(damier));
        List<List<Integer>> prises = dameBlanche.getPrisesValides(damier, 14);

        boolean prisePossible = false;
        for (List<Integer> chemin : prises) {
            System.out.println("Prise possible : " + chemin.toString());
            if (chemin.contains(28)) {
                prisePossible = true;
                break;
            }
        }

        Assert.assertTrue(prisePossible);

        // Effectuer la prise
        boolean deplacementValide = damier.deplacer(14, 28);

        // Vérifications après la prise
        Assert.assertTrue(deplacementValide);
        Assert.assertNull(damier.getPiece(14));
        Assert.assertNotNull(damier.getPiece(28));
        System.out.println(affichageDamier.afficherDamier(damier));
        Assert.assertEquals(Piece.Couleur.Blanc, damier.getPiece(28).getCouleur());
        Assert.assertNull(damier.getPiece(23));
    }

    @Test
    public void testGetListePice() {
        assertNotNull(damier.getListePiece());
    }

    @Test
    public void testGetHistorique() {
        assertNotNull(damier.getHistorique());
    }

    /**
     * Teste la promotion d'un pion blanc en dame.
     */
    @Test
    public void testPromotionPionBlanc() {
        // Vider le chemin pour le pion
        for (int i = 30; i > 5; i -= 5) {
            damier.setPiece(i, null);
        }
        damier.setPiece(5, null); // S'assurer que la destination est vide

        // Placer le pion blanc à promouvoir
        damier.setPiece(10, new Pion(Piece.Couleur.Blanc));
        damier.deplacer(10, 5); // Déplacer le pion à la dernière rangée

        Piece piece = damier.getPiece(5);
        Assert.assertTrue(piece instanceof Dame);
        Assert.assertEquals(Piece.Couleur.Blanc, piece.getCouleur());
    }

    /**
     * Teste la promotion d'un pion noir en dame.
     */
    @Test
    public void testPromotionPionNoir() {
        // Faire jouer un pion blanc pour passer le tour aux noirs
        damier.deplacer(35, 30);

        // Vider le chemin pour le pion noir
        for (int i = 21; i < 46; i += 5) {
            damier.setPiece(i, null);
        }
        damier.setPiece(46, null); // S'assurer que la destination est vide

        // Placer le pion noir à promouvoir
        damier.setPiece(41, new Pion(Piece.Couleur.Noir));
        damier.deplacer(41, 46); // Déplacer le pion à la dernière rangée

        Piece piece = damier.getPiece(46);
        Assert.assertTrue(piece instanceof Dame);
        Assert.assertEquals(Piece.Couleur.Noir, piece.getCouleur());
    }

    /**
     * Teste la fin de la partie lorsqu'un joueur n'a plus de pièces.
     */
    @Test
    public void testFinDePartieSansPieces() {
        // Retirer toutes les pièces noires
        for (int i = 1; i <= 50; i++) {
            Piece piece = damier.getPiece(i);
            if (piece != null && piece.getCouleur() == Piece.Couleur.Noir) {
                damier.setPiece(i, null);
            }
        }

        Assert.assertTrue(damier.estTermine());
    }

    /**
     * Teste la fin de la partie lorsqu'un joueur ne peut plus se déplacer.
     */
    @Test
    public void testFinDePartieSansMouvements() {
        // Bloquer les pions noirs
        damier = new Damier();
        damier.initialiser();
        damier.deplacer(35, 30); // Blanc joue
        damier.deplacer(16, 21); // Noir joue
        damier.deplacer(30, 24); // Blanc joue
        damier.deplacer(21, 26); // Noir joue
        damier.deplacer(24, 19); // Blanc joue
        damier.deplacer(26, 31); // Noir joue

        // Retirer toutes les pièces sauf un pion noir bloqué
        for (int i = 1; i <= 50; i++) {
            if (i != 31) {
                damier.setPiece(i, null);
            }
        }

        // Le pion noir est bloqué, le jeu devrait être terminé
        Assert.assertTrue(damier.estTermine());
    }

    /**
     * Teste l'annulation du dernier mouvement.
     */
    @Test
    public void testAnnulerDernierMouvement() {
        damier.deplacer(35, 30); // Blanc joue
        damier.annulerDernierMouvement();

        Assert.assertNotNull(damier.getPiece(35));
        Assert.assertNull(damier.getPiece(30));
        Assert.assertEquals(Piece.Couleur.Blanc, damier.getJoueurCourant());
    }

    /**
     * Teste l'obtention des mouvements valides pour un pion.
     */
    @Test
    public void testGetMouvementsValidesPion() {
        Piece pionBlanc = damier.getPiece(34);
        List<Integer> mouvements = pionBlanc.getMouvementsValides(damier, 34);
        System.out.println(affichageDamier.afficherDamier(damier));

        for (int i : mouvements) {
            System.out.println("Mouv. : " + i);
        }
        Assert.assertTrue(mouvements.contains(30));
        Assert.assertTrue(mouvements.contains(29));
    }

    /**
     * Teste l'obtention des prises valides pour un pion.
     */
    @Test
    public void testGetPrisesValidesPion() {
        // Positionner les pièces pour permettre une prise
        damier.setPiece(30, new Pion(Piece.Couleur.Noir));
        damier.setPiece(24, null);

        System.out.println(affichageDamier.afficherDamier(damier));

        Piece pionBlanc = damier.getPiece(35);
        List<List<Integer>> prises = pionBlanc.getPrisesValides(damier, 35);

        boolean prisePossible = false;
        for (List<Integer> chemin : prises) {
            if (chemin.contains(24)) {
                prisePossible = true;
                break;
            }
        }
        damier.deplacer(35, 24);
        System.out.println(affichageDamier.afficherDamier(damier));
        Assert.assertTrue(prisePossible);
    }

    /**
     * Teste le cas où un mouvement invalide est tenté (hors limites).
     */
    @Test
    public void testDeplacementHorsLimites() {
        damier.deplacer(35, 51); // Position hors limites
    }

    /**
     * Teste le cas où on tente de déplacer une pièce qui n'existe pas.
     */
    @Test
    public void testDeplacementDepuisCaseVide() {
        boolean deplacementValide = damier.deplacer(25, 20); // Aucune pièce en 25
        Assert.assertFalse(deplacementValide);
    }

    /**
     * Teste que le joueur ne peut pas déplacer une pièce de l'adversaire.
     */
    @Test
    public void testDeplacementPieceAdversaire() {
        boolean deplacementValide = damier.deplacer(16, 21); // Noir tente de jouer au tour des blancs
        Assert.assertFalse(deplacementValide);
    }

    /**
     * Teste le déplacement d'une dame.
     */
    @Test
    public void testDeplacementDame() {
        // Promouvoir un pion blanc en dame à la position 14
        damier.setPiece(14, new Dame(Piece.Couleur.Blanc));
        damier.setPiece(19, null); // S'assurer que le chemin est libre
        damier.setPiece(23, null); // S'assurer que le chemin est libre
        damier.setPiece(28, null); // S'assurer que le chemin est libre

        // Déplacer la dame de 14 à 28 (plusieurs cases en diagonale)
        boolean deplacementValide = damier.deplacer(14, 28);

        Assert.assertTrue(deplacementValide);
        Assert.assertNull(damier.getPiece(14));
        Piece piece = damier.getPiece(28);
        Assert.assertNotNull(piece);
        Assert.assertTrue(piece instanceof Dame);
        Assert.assertEquals(Piece.Couleur.Blanc, piece.getCouleur());
    }

    /**
     * Teste les mouvements valides d'une dame.
     */
    @Test
    public void testGetMouvementsValidesDame() {
        // Promouvoir un pion blanc en dame à la position 28
        damier.setPiece(28, new Dame(Piece.Couleur.Blanc));

        // S'assurer que le chemin est libre
        damier.setPiece(22, null);
        damier.setPiece(23, null);
        damier.setPiece(32, null);
        damier.setPiece(37, null);
        damier.setPiece(19, null);
        damier.setPiece(10, null);
        damier.setPiece(14, null);

        // Afficher le damier pour visualisation (optionnel)
        System.out.println("Damier avec la dame en position 28 :");
        System.out.println(affichageDamier.afficherDamier(damier));

        Piece dameBlanche = damier.getPiece(28);
        List<Integer> mouvements = dameBlanche.getMouvementsValides(damier, 28);

        // Afficher les mouvements pour vérification
        System.out.println("Mouvements valides pour la dame en position 28 : " + mouvements);

        // Vérifier que la dame peut se déplacer sur toutes les diagonales libres
        Assert.assertTrue(mouvements.contains(10));
        Assert.assertTrue(mouvements.contains(22));
        Assert.assertTrue(mouvements.contains(23));
        Assert.assertTrue(mouvements.contains(19));
        Assert.assertTrue(mouvements.contains(14));
        Assert.assertTrue(mouvements.contains(32));
        Assert.assertTrue(mouvements.contains(37));

        // Vérifier le nombre total de mouvements
        Assert.assertEquals(7, mouvements.size());
    }

    /**
     * Teste les prises valides d'une dame.
     */
    @Test
    public void testGetPrisesValidesDame() {
        // Promouvoir un pion blanc en dame
        damier.setPiece(14, new Dame(Piece.Couleur.Blanc));

        // Positionner un pion noir pour la prise
        damier.setPiece(23, new Pion(Piece.Couleur.Noir));
        damier.setPiece(32, null); // S'assurer que la case derrière est libre
        damier.setPiece(19, null); // S'assurer que la case derrière est libre

        Piece dameBlanche = damier.getPiece(14);
        System.out.println(affichageDamier.afficherDamier(damier));
        List<List<Integer>> prises = dameBlanche.getPrisesValides(damier, 14);

        boolean prisePossible = false;
        for (List<Integer> chemin : prises) {
            System.out.println("Prise possible : " + chemin.toString());
            if (chemin.contains(28)) {
                prisePossible = true;
                break;
            }
        }

        Assert.assertTrue(prisePossible);
    }
}

