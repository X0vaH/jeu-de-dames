package cstjean.mobile.tp2;

import java.util.Stack;

/**
 * Singleton pour le damier.
 */
public class SingletonDames {

    /**
     * Singleton.
     */
    private static SingletonDames instance = null;
    /**
     * Nopm du joueur 1.
     */
    private String nomJoueur1;
    /**
     * Nopm du joueur 2.
     */
    private String nomJoueur2;
    /**
     * Pion a changer de place.
     */
    private Integer pionadeplacer = null;
    /**
     * Damier.
     */
    private final Damier damier;

    private SingletonDames() {
        damier = new Damier();
        damier.initialiser();
    }

    /**
     * Si la partie est fini.
     */
    public void partieGagner() {
        instance = new SingletonDames();
        nomJoueur1 = null;
        nomJoueur2 = null;
        pionadeplacer = null;
    }

    /**
     * get instance du singleton.
     *
     * @return instance
     */
    public static SingletonDames getInstance() {
        if (instance == null) {
            instance = new SingletonDames();
        }
        return instance;
    }

    public Piece[] getListePiece() {
        return damier.getListePiece();
    }

    protected Piece.Couleur getTourJoueur() {
        return damier.getJoueurCourant();
    }

    public Integer getPionadeplacer() {
        return pionadeplacer;
    }

    public void setPionadeplacer(Integer caseNum) {
        pionadeplacer = caseNum;
    }

    public Stack<Move> getHistorique() {
        return damier.getHistorique();
    }

    public String getNomJoueur2() {
        return nomJoueur2;
    }

    public void setNomJoueur2(String nomJoueur2) {
        this.nomJoueur2 = nomJoueur2;
    }

    public String getNomJoueur1() {
        return nomJoueur1;
    }

    public void setNomJoueur1(String nomJoueur1) {
        this.nomJoueur1 = nomJoueur1;
    }

    public Damier getDamier() {
        return damier;
    }
}
