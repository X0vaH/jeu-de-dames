package cstjean.mobile.tp2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Main Activity.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String player1Name = getIntent().getStringExtra("PLAYER_1_NAME");
        String player2Name = getIntent().getStringExtra("PLAYER_2_NAME");

        SingletonDames.getInstance().setNomJoueur1(player1Name);
        SingletonDames.getInstance().setNomJoueur2(player2Name);
    }
}