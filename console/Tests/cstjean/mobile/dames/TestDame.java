package cstjean.mobile.dames;

/**
 * Classe qui test la classe Dame.
 *
 * @author Alex Heroux
 */
public class TestDame extends TestPion {
    /**
     * Test creer.
     */
    public void testCreer() {
        Dame dame1 = new Dame(Piece.Couleur.Blanc);
        Dame dame2 = new Dame(Pion.Couleur.Noir);

        assertEquals('D', dame2.getRepresentation());
        assertEquals('d', dame1.getRepresentation());
    }
}
