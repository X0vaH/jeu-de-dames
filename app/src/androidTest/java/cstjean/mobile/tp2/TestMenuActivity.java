package cstjean.mobile.tp2;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test pour la classe Menu Activity pour le input des noms de joueurs.
 */
@RunWith(AndroidJUnit4.class)
public class TestMenuActivity {

    /**
     * Avant chaque test.
     */
    @Before
    public void setUp() {
        Intents.init(); // Initialisation des intentions
        ActivityScenario.launch(MenuActivity.class); // Lancer l'activité de test
    }

    /**
     * Apres chaque test.
     */
    @After
    public void tearDown() {
        Intents.release(); // Libération des intentions
    }

    @Test
    public void testValidPlayerNamesStartsGame() {
        // Entrer les noms des joueurs
        onView(withId(R.id.player1Name)).perform(ViewActions.typeText("Alice"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.player2Name)).perform(ViewActions.typeText("Bob"), ViewActions.closeSoftKeyboard());

        // Cliquer sur le bouton
        onView(withId(R.id.startGameButton)).perform(ViewActions.click());

        // Vérifier que l'intention pour lancer MainActivity est appelée
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void testPlayer1NameFieldAcceptsInput() {
        // Taper un nom dans le champ du joueur 1
        String player1Name = "Alice";
        onView(withId(R.id.player1Name)).perform(ViewActions.typeText(player1Name), ViewActions.closeSoftKeyboard());

        // Vérifier que le texte saisi est correct
        onView(withId(R.id.player1Name)).check(matches(withText(player1Name)));
    }

    @Test
    public void testPlayer2NameFieldAcceptsInput() {
        // Taper un nom dans le champ du joueur 2
        String player2Name = "Bob";
        onView(withId(R.id.player2Name)).perform(ViewActions.typeText(player2Name), ViewActions.closeSoftKeyboard());

        // Vérifier que le texte saisi est correct
        onView(withId(R.id.player2Name)).check(matches(withText(player2Name)));
    }
}

