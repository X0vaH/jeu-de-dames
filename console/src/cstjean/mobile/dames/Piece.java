package cstjean.mobile.dames;

import java.util.List;

/**
 * Classe abstraite représentant une pièce dans le jeu de dames.
 * Cette classe sert de classe de base pour les différents types de pièces,
 * comme les pions et les dames. Elle contient les propriétés communes à toutes les pièces.
 *
 * @author Alex Heroux
 */
public abstract class Piece {
    /**
     * Énumération des couleurs possibles pour une pièce.
     */
    public enum Couleur {
        /**
         * Représente la couleur noire.
         */
        Noir,
        /**
         * Représente la couleur blanche.
         */
        Blanc
    }

    /**
     * La couleur de la pièce.
     */
    private final Couleur couleur;

    /**
     * Constructeur de la classe Piece.
     *
     * @param couleur La couleur de la pièce (NOIR ou BLANC).
     */
    public Piece(Couleur couleur) {
        this.couleur = couleur;
    }

    /**
     * Retourne la couleur de la pièce.
     *
     * @return La couleur de la pièce.
     */
    public Couleur getCouleur() {
        return couleur;
    }

    /**
     * Retourne la représentation graphique de la pièce.
     * Cette méthode doit être implémentée par les classes dérivées
     * pour fournir la représentation spécifique de chaque type de pièce.
     *
     * @return Un caractère représentant la pièce (par exemple, 'P' ou 'p').
     */
    public abstract char getRepresentation();

    /**
     * Génère la liste des mouvements valides pour cette pièce sur le damier donné.
     *
     * @param damier   Le damier actuel.
     * @param position La position actuelle de la pièce.
     * @return Une liste des mouvements valides sous forme de positions cibles.
     */
    public abstract List<Integer> getMouvementsValides(Damier damier, int position);

    /**
     * Génère la liste des prises valides pour cette pièce sur le damier donné.
     *
     * @param damier   Le damier actuel.
     * @param position La position actuelle de la pièce.
     * @return Une liste des mouvements de prise valides sous forme de positions cibles.
     */
    public abstract List<List<Integer>> getPrisesValides(Damier damier, int position);
}
