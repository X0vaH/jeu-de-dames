package cstjean.mobile.tp2;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe de Dames Fragment.
 *
 * @author alexh
 */
public class DamesFragment extends Fragment {

    /**
     * Grid.
     */
    private GridLayout gridBoutons;
    /**
     * Mouvements possible.
     */
    private TextView mouvements;
    /**
     * Tour joueur.
     */
    private TextView tour;
    /**
     * Button pour annuler coup.
     */
    private Button annulerBtn;
    /**
     * Liste de buttons.
     */
    private final List<Button> listeBtn = new ArrayList<>(100);
    /**
     * Liste de buttons pour le jeu.
     */
    private final List<Button> listeBtnDames = new ArrayList<>(50);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dames, container, false);

        gridBoutons = view.findViewById(R.id.layout_gridBoutons);
        mouvements = view.findViewById(R.id.mouvements);
        mouvements.setText("Mouvements possible : ");
        tour = view.findViewById(R.id.tour);
        annulerBtn = view.findViewById(R.id.annulerBt);

        AffichageDamier affichageDamier = new AffichageDamier();
        Log.d("Damier", affichageDamier.afficherDamier(SingletonDames.getInstance().getDamier()));

        creerBtn();
        creationGrid();
        updateBoard();

        return view;
    }

    /**
     * Création du positionnement des boutons.
     */
    private void creationGrid() {
        boolean isLandscape =
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        for (Button b : listeBtn) {
            // LayoutParams pour le positionnement par rapport au layout
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            if (isLandscape) {
                params.height = 105;
                params.width = 105;
            } else {
                params.height = 128;
                params.width = 128;
            }
            b.setLayoutParams(params);

            // Ajout dans le layout
            gridBoutons.addView(b);
        }
    }

    /**
     * Creation des boutons et reset btn.
     */
    private void creerBtn() {
        Set<Integer> caseBlanche = new HashSet<>(Arrays.asList(
                0, 2, 4, 6, 8, 11, 13, 15, 17, 19,
                20, 22, 24, 26, 28, 31, 33, 35, 37, 39,
                40, 42, 44, 46, 48, 51, 53, 55, 57, 59,
                60, 62, 64, 66, 68, 71, 73, 75, 77, 79,
                80, 82, 84, 86, 88, 91, 93, 95, 97, 99
        ));

        for (int i = 0; i < 100; i++) {
            Button btn = new Button(getContext());
            btn.setAllCaps(false);
            btn.setBackgroundColor(Color.WHITE);

            if (!caseBlanche.contains(i)) {
                listeBtnDames.add(btn);
                btn.setBackgroundColor(Color.GRAY);
            }
            listeBtn.add(btn);
        }

        for (int j = 0; j < 50; j++) {
            int finalJ = j;
            listeBtnDames.get(j).setOnClickListener(v -> {
                if (!listeBtnDames.get(finalJ).getText().toString().equals("") &&
                        SingletonDames.getInstance().getListePiece()[finalJ].getCouleur() ==
                                SingletonDames.getInstance().getTourJoueur()) {
                    gererClick(SingletonDames.getInstance().getListePiece()[finalJ], finalJ + 1);
                }

                // Vérifier la couleur de l'arrière-plan
                Drawable background = listeBtnDames.get(finalJ).getBackground();
                int backgroundColor = Color.TRANSPARENT;
                if (background instanceof ColorDrawable) {
                    backgroundColor = ((ColorDrawable) background).getColor();
                }
                if (backgroundColor == Color.GREEN) {
                    deplacerPion(finalJ + 1);
                }
            });
        }
        annulerBtn.setOnClickListener(v -> {
            SingletonDames.getInstance().getDamier().annulerDernierMouvement();
            updateBoard();
        });
    }

    /**
     * Si la partie est gagner.
     */
    private void partieGagner() {
        if (SingletonDames.getInstance().getDamier().estTermine()) {
            SingletonDames.getInstance().getDamier().changerJoueur();
            if (SingletonDames.getInstance().getTourJoueur() == Piece.Couleur.Blanc) {
                tour.setText("Partie terminée!\nGagnant : " +
                        SingletonDames.getInstance().getNomJoueur1());
            } else {
                tour.setText("Partie terminée!\nGagnant : " +
                        SingletonDames.getInstance().getNomJoueur2());
            }

            SingletonDames.getInstance().partieGagner();
            Intent intent = new Intent(getActivity(), MenuActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }
    }

    /**
     * Deplace le pion choisi.
     *
     * @param numCase le num de la case.
     */
    private void deplacerPion(Integer numCase) {
        SingletonDames.getInstance().getDamier()
                .deplacer(SingletonDames.getInstance().getPionadeplacer(), numCase);
        updateBoard();
        partieGagner();
        mouvements.setText("Mouvements possible : ");
        annulerBtn.setEnabled(true);
    }

    /**
     * Gere lorsque on clique sur un btn.
     *
     * @param pion pion.
     * @param numCase num de la case.
     */
    private void gererClick(Piece pion, int numCase) {
        updateBoard();
        StringBuilder sb = new StringBuilder();
        List<Integer> mouvementsValides =
                pion.getMouvementsValides(SingletonDames.getInstance().getDamier(), numCase);
        List<List<Integer>> prisesValides =
                pion.getPrisesValides(SingletonDames.getInstance().getDamier(), numCase);

        if (mouvementsValides.isEmpty() && prisesValides.isEmpty()) {
            sb.append("Aucun");
        }
        for (Integer i : mouvementsValides) {
            sb.append(notationManoury(pion, numCase, i, false));
            sb.append("\n");
            listeBtnDames.get(i - 1).setBackgroundColor(Color.GREEN);
        }

        for (List<Integer> prises : prisesValides) {
            for (Integer numPion : prises) {
                sb.append(notationManoury(pion, numCase, numPion, true));
                sb.append("\n");
                listeBtnDames.get(numPion - 1).setBackgroundColor(Color.GREEN);
            }
        }
        SingletonDames.getInstance().setPionadeplacer(numCase);
        mouvements.setText("Mouvements possible : \n" + sb);

    }

    /**
     * Update le board.
     */
    private void updateBoard() {
        Piece[] liste;
        if (SingletonDames.getInstance() != null) {
            liste = SingletonDames.getInstance().getListePiece();
        } else {
            liste = SingletonDames.getInstance().getDamier().getListePiece();
        }

        for (int i = 0; i < 50; i++) {
            listeBtnDames.get(i).setBackgroundColor(getResources().getColor(R.color.gray));
            if (liste[i] == null) {
                listeBtnDames.get(i).setText("");
            } else if (liste[i].getRepresentation() == 'P') {
                listeBtnDames.get(i).setText("n");
            } else if (liste[i].getRepresentation() == 'p') {
                listeBtnDames.get(i).setText("b");
            } else if (liste[i].getRepresentation() == 'D') {
                listeBtnDames.get(i).setText("N");
            } else if (liste[i].getRepresentation() == 'd') {
                listeBtnDames.get(i).setText("B");
            }
        }

        if (SingletonDames.getInstance().getTourJoueur() == Piece.Couleur.Blanc) {
            tour.setText("Tour : " + SingletonDames.getInstance().getNomJoueur1());
        } else {
            tour.setText("Tour : " + SingletonDames.getInstance().getNomJoueur2());
        }

        if (SingletonDames.getInstance().getHistorique().isEmpty()) {
            annulerBtn.setEnabled(false);
        }
    }

    /**
     * Notation manoury.
     *
     * @param pion pion choisi.
     * @param num num de la case.
     * @param nextCase next case pour le deplacement.
     * @param estPrise si c une prise ou un mouv.
     * @return les deplacements en notation manoury.
     */
    private String notationManoury(Piece pion, Integer num, Integer nextCase, boolean estPrise) {
        StringBuilder sb = new StringBuilder();
        if (pion.getCouleur() == Piece.Couleur.Noir) {
            sb.append("(").append(num);
            if (estPrise) {
                sb.append("x");
            } else {
                sb.append("-");
            }
            sb.append(nextCase).append(")");
        } else {
            sb.append(num);
            if (estPrise) {
                sb.append("x");
            } else {
                sb.append("-");
            }
            sb.append(nextCase);
        }

        return sb.toString();
    }
}
