package cstjean.mobile.tp2;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test tout les tests.
 *
 * @author Alex Heroux
 */
public class TestComplet extends TestCase {
    /**
     * Classe qui effectue tous les tests.
     *
     * @return les tests
     */
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TestPion.class);
        suite.addTestSuite(TestDamier.class);
        suite.addTestSuite(TestDame.class);
        suite.addTestSuite(TestSingletonDames.class);
        return suite;
    }
}
