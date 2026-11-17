package cstjean.mobile.tp2;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe pour un pion devenu Dame.
 *
 * @author Alex Heroux
 */
public class Dame extends Piece {

    /**
     * Constructeur de la classe Dame.
     *
     * @param couleur La couleur de la dame (NOIR ou BLANC).
     */
    public Dame(Couleur couleur) {
        super(couleur);
    }

    /**
     * Retourne la représentation graphique de la dame.
     *
     * @return 'D' pour une dame noire, 'd' pour une dame blanche.
     */
    @Override
    public char getRepresentation() {
        return (getCouleur() == Couleur.Noir) ? 'D' : 'd';
    }

    /**
     * Génère la liste des mouvements valides pour cette dame.
     *
     * @param damier   Le damier actuel.
     * @param position La position actuelle de la dame.
     * @return Une liste des positions cibles pour les mouvements valides.
     */
    @Override
    public List<Integer> getMouvementsValides(Damier damier, int position) {
        List<Integer> mouvements = new ArrayList<>();
        int[] directions = { -1, 1 };

        for (int rowDir : directions) {
            for (int colDir : directions) {
                int[] coords = damier.getCoordinates(position);
                int row = coords[0] + rowDir;
                int col = coords[1] + colDir;

                while (row >= 0 && row < 10 && col >= 0 && col < 10 && (row + col) % 2 == 1) {
                    int newPosition = damier.getPosition(row, col);
                    if (damier.getPiece(newPosition) == null) {
                        mouvements.add(newPosition);
                    } else {
                        break; // Ne peut pas sauter par-dessus une pièce (sauf pour la prise)
                    }
                    row += rowDir;
                    col += colDir;
                }
            }
        }

        return mouvements;
    }

    /**
     * Génère la liste des prises valides pour cette dame.
     *
     * @param damier   Le damier actuel.
     * @param position La position actuelle de la dame.
     * @return Une liste des séquences de prises valides.
     */
    @Override
    public List<List<Integer>> getPrisesValides(Damier damier, int position) {
        List<List<Integer>> prises = new ArrayList<>();
        explorePrises(damier, position, new ArrayList<>(), prises, new boolean[50]);
        return prises;
    }

    /**
     * Méthode récursive pour explorer les prises multiples pour la dame.
     *
     * @param damier      Le damier actuel.
     * @param position    La position actuelle de la dame.
     * @param chemin      Le chemin actuel de la prise.
     * @param prises      La liste accumulée des prises valides.
     */
    private void explorePrises(Damier damier, int position, List<Integer> chemin, List<List<Integer>> prises,
                               boolean[] visited) {
        boolean prisePossible = false;
        int[] directions = { -1, 1 };

        for (int rowDir : directions) {
            for (int colDir : directions) {
                int[] coords = damier.getCoordinates(position);
                int row = coords[0] + rowDir;
                int col = coords[1] + colDir;
                boolean ennemiRencontre = false;
                int ennemiPosition = -1;
                Piece pieceEnnemi = null;

                // Parcourir la diagonale dans la direction donnée
                while (row >= 0 && row < 10 && col >= 0 && col < 10 && (row + col) % 2 == 1) {
                    int currentPosition = damier.getPosition(row, col);
                    Piece piece = damier.getPiece(currentPosition);

                    if (piece == null) {
                        if (!ennemiRencontre) {
                            // Avant de rencontrer un ennemi, continuer à avancer
                            row += rowDir;
                            col += colDir;
                        } else {
                            // Après avoir sauté par-dessus un ennemi, ajouter les positions d'atterrissage possibles
                            // Case d'atterrissage possible
                            // Marquer l'ennemi comme visité
                            visited[ennemiPosition - 1] = true;
                            List<Integer> newChemin = new ArrayList<>(chemin);
                            newChemin.add(currentPosition); // Ajouter la position d'atterrissage

                            // Simuler la prise
                            damier.setPiece(ennemiPosition, null);
                            explorePrises(damier, currentPosition, newChemin, prises, visited);
                            // Restaurer l'état
                            damier.setPiece(ennemiPosition, pieceEnnemi);

                            // Défaire la visite
                            visited[ennemiPosition - 1] = false;
                            prisePossible = true;

                            // Continuer à vérifier les cases d'atterrissage plus loin
                            row += rowDir;
                            col += colDir;
                        }
                    } else if (piece.getCouleur() != this.getCouleur() && !ennemiRencontre &&
                            !visited[currentPosition - 1]) {
                        // Ennemi rencontré pour la première fois sur cette diagonale
                        ennemiRencontre = true;
                        ennemiPosition = currentPosition;
                        pieceEnnemi = piece;

                        // Avancer pour trouver les cases d'atterrissage après l'ennemi
                        row += rowDir;
                        col += colDir;
                    } else {
                        // Soit une pièce alliée, soit un deuxième ennemi sans atterrir, on ne peut pas continuer
                        break;
                    }
                }
            }
        }

        if (!prisePossible && !chemin.isEmpty()) {
            prises.add(chemin);
        }
    }
}