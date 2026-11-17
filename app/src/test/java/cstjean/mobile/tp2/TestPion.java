package cstjean.mobile.tp2;

import junit.framework.TestCase;

/**
 * Classe qui test la classe Pion.
 *
 * @author Alex Heroux
 */
public class TestPion extends TestCase {
    /**
     * test creer.
     */
    public void testCreer() {
        Pion pion1 = new Pion(Pion.Couleur.Noir);
        Pion pion2 = new Pion(Pion.Couleur.Blanc);
        Pion pion3 = new Pion();

        assertEquals(Pion.Couleur.Noir, pion1.getCouleur());
        assertEquals(Pion.Couleur.Blanc, pion2.getCouleur());
        assertEquals(Pion.Couleur.Blanc, pion3.getCouleur());

        assertEquals('p', pion2.getRepresentation());
        assertEquals('P', pion1.getRepresentation());
    }
}
