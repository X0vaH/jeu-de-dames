package cstjean.mobile.tp2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

/**
 * Test pour la classe Dame.
 */
public class TestDame extends TestCase {

    @Test
    public void testGetRepresentation() {
        Dame dameNoire = new Dame(Piece.Couleur.Noir);
        assertEquals('D', dameNoire.getRepresentation());

        Dame dameBlanche = new Dame(Piece.Couleur.Blanc);
        assertEquals('d', dameBlanche.getRepresentation());
    }

    @Test
    public void testGetMouvementsValides_EmptyBoard() {
        Damier damier = new Damier();
        Dame dameNoire = new Dame(Piece.Couleur.Noir);
        int position = 25; // Position centrale
        damier.setPiece(position, dameNoire);

        List<Integer> mouvements = dameNoire.getMouvementsValides(damier, position);

        // Calculer les mouvements attendus
        int[] directions = {-1, 1};
        Set<Integer> expectedPositions = new HashSet<>();

        int[] coords = damier.getCoordinates(position);
        int row = coords[0];
        int col = coords[1];

        for (int rowDir : directions) {
            for (int colDir : directions) {
                int r = row + rowDir;
                int c = col + colDir;

                while (r >= 0 && r < 10 && c >= 0 && c < 10 && (r + c) % 2 == 1) {
                    int newPosition = damier.getPosition(r, c);
                    expectedPositions.add(newPosition);
                    r += rowDir;
                    c += colDir;
                }
            }
        }

        assertEquals(expectedPositions.size(), mouvements.size());
        assertTrue(mouvements.containsAll(expectedPositions));
    }

    @Test
    public void testGetMouvementsValides_BlockedByOwnPieces() {
        Damier damier = new Damier();
        Dame dameNoire = new Dame(Piece.Couleur.Noir);
        int position = 25;
        damier.setPiece(position, dameNoire);

        // Bloquer avec ses propres pièces
        int[] directions = {-1, 1};
        for (int rowDir : directions) {
            for (int colDir : directions) {
                int[] coords = damier.getCoordinates(position);
                int row = coords[0] + rowDir;
                int col = coords[1] + colDir;
                if (row >= 0 && row < 10 && col >= 0 && col < 10 && (row + col) % 2 == 1) {
                    int newPosition = damier.getPosition(row, col);
                    damier.setPiece(newPosition, new Dame(Piece.Couleur.Noir));
                }
            }
        }

        List<Integer> mouvements = dameNoire.getMouvementsValides(damier, position);
        assertTrue(mouvements.isEmpty());
    }

    @Test
    public void testGetMouvementsValides_BlockedByEnemyPieces() {
        Damier damier = new Damier();
        Dame dameNoire = new Dame(Piece.Couleur.Noir);
        int position = 25;
        damier.setPiece(position, dameNoire);

        // Bloquer avec des pièces ennemies
        int[] directions = {-1, 1};
        for (int rowDir : directions) {
            for (int colDir : directions) {
                int[] coords = damier.getCoordinates(position);
                int row = coords[0] + rowDir;
                int col = coords[1] + colDir;
                if (row >= 0 && row < 10 && col >= 0 && col < 10 && (row + col) % 2 == 1) {
                    int newPosition = damier.getPosition(row, col);
                    damier.setPiece(newPosition, new Dame(Piece.Couleur.Blanc));
                }
            }
        }

        List<Integer> mouvements = dameNoire.getMouvementsValides(damier, position);
        assertTrue(mouvements.isEmpty());
    }

    @Test
    public void testGetPrisesValides_NoCaptures() {
        Damier damier = new Damier();
        Dame dameNoire = new Dame(Piece.Couleur.Noir);
        int position = 25;
        damier.setPiece(position, dameNoire);

        List<List<Integer>> prises = dameNoire.getPrisesValides(damier, position);
        assertTrue(prises.isEmpty());
    }
}
