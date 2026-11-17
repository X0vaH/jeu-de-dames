package cstjean.mobile.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Classe de Menu Activity pour prendre le nom des joueurs.
 */
public class MenuActivity extends AppCompatActivity {

    /**
     * Nom du joueur 1.
     */
    private EditText player1NameEditText;
    /**
     * Nom du joueur 2.
     */
    private EditText player2NameEditText;
    /**
     * Btn pour start la game.
     */
    private Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        player1NameEditText = findViewById(R.id.player1Name);
        player2NameEditText = findViewById(R.id.player2Name);
        startGameButton = findViewById(R.id.startGameButton);

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player1Name = player1NameEditText.getText().toString().trim();
                String player2Name = player2NameEditText.getText().toString().trim();

                // Vérifier que les noms ne sont pas vides
                if (TextUtils.isEmpty(player1Name) || TextUtils.isEmpty(player2Name)) {
                    Toast.makeText(MenuActivity.this,
                            "Veuillez entrer les noms des deux joueurs",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Passer à l'activité principale du jeu
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra("PLAYER_1_NAME", player1Name);
                intent.putExtra("PLAYER_2_NAME", player2Name);
                startActivity(intent);

                // Fermer cette activité pour ne pas revenir dessus
                finish();
            }
        });
    }
}
