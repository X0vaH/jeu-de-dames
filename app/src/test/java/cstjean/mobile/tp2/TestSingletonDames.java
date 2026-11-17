package cstjean.mobile.tp2;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.jupiter.api.Test;

/**
 * Test pour la classe SingletonDamier.
 */
public class TestSingletonDames extends TestCase {

    /**
     * Avant chaque test.
     */
    @Before
    public void resetSingleton() {
        // Réinitialise l'instance entre chaque test
        SingletonDames.getInstance().partieGagner();
    }

    @Test
    public void testGetInstanceReturnsSameInstance() {
        SingletonDames instance1 = SingletonDames.getInstance();
        SingletonDames instance2 = SingletonDames.getInstance();

        assertSame(instance1, instance2);
    }

    @Test
    public void testGetListePiece() {
        SingletonDames instance = SingletonDames.getInstance();

        assertNotNull(instance.getListePiece());

    }

    @Test
    public void testSetAndGetNomJoueur1() {
        SingletonDames instance = SingletonDames.getInstance();
        instance.setNomJoueur1("Alice");

        assertSame("Alice", instance.getNomJoueur1());
    }

    @Test
    public void testSetAndGetNomJoueur2() {
        SingletonDames instance = SingletonDames.getInstance();
        instance.setNomJoueur2("Bob");

        assertSame("Bob", instance.getNomJoueur2());
    }

    @Test
    public void testSetAndGetPionadeplacer() {
        SingletonDames instance = SingletonDames.getInstance();
        instance.setPionadeplacer(5);

        assertSame(5, instance.getPionadeplacer());
    }

    @Test
    public void testPartieGagnerResetsSingleton() {
        SingletonDames instance = SingletonDames.getInstance();
        instance.setNomJoueur1("Alice");
        instance.setNomJoueur2("Bob");
        instance.setPionadeplacer(5);

        instance.partieGagner();

        assertNull(instance.getNomJoueur1());
        assertNull(instance.getNomJoueur2());
        assertNull(instance.getPionadeplacer());
    }

    @Test
    public void testDamierNotNull() {
        SingletonDames instance = SingletonDames.getInstance();

        assertNotNull(instance.getDamier());
    }

    @Test
    public void testHistoriqueNotNull() {
        SingletonDames instance = SingletonDames.getInstance();

        assertNotNull(instance.getHistorique());
    }

    @Test
    public void testTourJoueur() {
        SingletonDames instance = SingletonDames.getInstance();

        assertNotNull(instance.getTourJoueur());
    }
}
