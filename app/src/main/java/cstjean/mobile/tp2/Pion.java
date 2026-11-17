package cstjean.mobile.tp2;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Pion représente un pion dans le jeu de dames.
 * Chaque pion a une couleur qui peut être utilisée pour déterminer son appartenance à un joueur ou
 * une équipe spécifique.
 *
 * @author Alex Heroux
 */
public class Pion extends Piece {

    /**
     * Constructeur de la classe Pion.
     *
     * @param couleur La couleur du pion (NOIR ou BLANC).
     */
    public Pion(Couleur couleur) {
        super(couleur);
    }

    /**
     * Constructeur de la classe Pion.
     * Crée un pion Blanc.
     */
    public Pion() {
        super(Couleur.Blanc);
    }

    /**
     * Retourne la représentation graphique du pion.
     *
     * @return 'P' pour un pion noir, 'p' pour un pion blanc.
     */
    @Override
    public char getRepresentation() {
        return (getCouleur() == Couleur.Noir) ? 'P' : 'p';
    }

    /**
     * Génère la liste des mouvements valides pour ce pion.
     *
     * @param damier   Le damier actuel.
     * @param position La position actuelle du pion.
     * @return Une liste des positions cibles pour les mouvements valides.
     */
    @Override
    public List<Integer> getMouvementsValides(Damier damier, int position) {
        List<Integer> mouvements = new ArrayList<>();
        int[][] directions = getDirectionsAvance();

        for (int[] dir : directions) {
            int newPosition = damier.getAdjacentPosition(position, dir[0], dir[1]);
            if (newPosition != -1 && damier.getPiece(newPosition) == null) {
                mouvements.add(newPosition);
            }
        }

        return mouvements;
    }

    /**
     * Génère la liste des prises valides pour ce pion.
     *
     * @param damier   Le damier actuel.
     * @param position La position actuelle du pion.
     * @return Une liste des séquences de prises valides.
     */
    @Override
    public List<List<Integer>> getPrisesValides(Damier damier, int position) {
        List<List<Integer>> prises = new ArrayList<>();
        boolean[] visited = new boolean[50];
        explorePrises(damier, position, new ArrayList<>(), prises, visited);
        return prises;
    }

    /**
     * Méthode récursive pour explorer les prises multiples.
     *
     * @param damier      Le damier actuel.
     * @param position    La position actuelle du pion.
     * @param chemin      Le chemin actuel de la prise.
     * @param prises      La liste accumulée des prises valides.
     * @param visited     Tableau pour suivre les pièces déjà prises.
     */
    private void explorePrises(Damier damier, int position, List<Integer> chemin, List<List<Integer>> prises,
                               boolean[] visited) {
        boolean prisePossible = false;
        int[][] directions = getDirectionsPrise();

        for (int[] dir : directions) {
            int ennemiPosition = damier.getAdjacentPosition(position, dir[0], dir[1]);
            int apresPosition = damier.getAdjacentPosition(ennemiPosition, dir[0], dir[1]);

            if (ennemiPosition != -1 && apresPosition != -1) {
                Piece pieceEnnemi = damier.getPiece(ennemiPosition);
                Piece pieceApres = damier.getPiece(apresPosition);

                if (pieceEnnemi != null && pieceEnnemi.getCouleur() != this.getCouleur() && pieceApres == null &&
                        !visited[ennemiPosition - 1]) {
                    // Marquer l'ennemi comme visité
                    visited[ennemiPosition - 1] = true;
                    List<Integer> newChemin = new ArrayList<>(chemin);
                    newChemin.add(apresPosition); // Ajouter la position d'atterrissage

                    // Simuler la prise
                    damier.setPiece(ennemiPosition, null);
                    explorePrises(damier, apresPosition, newChemin, prises, visited);
                    // Restaurer l'état
                    damier.setPiece(ennemiPosition, pieceEnnemi);

                    // Défaire la visite
                    visited[ennemiPosition - 1] = false;
                    prisePossible = true;
                }
            }
        }

        if (!prisePossible && !chemin.isEmpty()) {
            prises.add(chemin);
        }
    }

    /**
     * Retourne les directions de déplacement en avant pour le pion.
     *
     * @return Un tableau d'entiers représentant les directions.
     */
    private int[][] getDirectionsAvance() {
        if (getCouleur() == Couleur.Blanc) {
            return new int[][] { { -1, -1 }, { -1, 1 } };
        } else {
            return new int[][] { { 1, -1 }, { 1, 1 } };
        }
    }

    /**
     * Retourne les directions de prise possibles pour le pion (avant et arrière).
     *
     * @return Un tableau de tableaux contenant les incréments de ligne et de colonne.
     */
    private int[][] getDirectionsPrise() {
        return new int[][] { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };
    }
}
