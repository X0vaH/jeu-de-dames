package cstjean.mobile.dames;

import java.util.*;

/**
* La classe Damier représente un plateau de jeu de dames de taille 10x10.
* Elle permet de gérer l'initialisation des pions, l'accès aux pions et
* l'affichage du plateau.
*
* @author Alex Heroux
*/
public class Damier {
    /**
     * Tableau privé représentant les 50 cases jouables du damier.
     */
    private final Piece[] board;

    /**
     * Historique des mouvements effectués.
     */
    private final Stack<Move> historique;

    /**
     * Le joueur courant.
     */
    private Piece.Couleur joueurCourant;

    /**
     * Map des positions Manoury vers les coordonnées (ligne, colonne).
     */
    private Map<Integer, int[]> positionToCoordinates;

    /**
     * Tableau des coordonnées (ligne, colonne) vers les positions Manoury.
     */
    private int[][] coordinatesToPosition;

    /**
     * Constructeur de la classe Damier.
     */
    public Damier() {
        board = new Piece[50];
        historique = new Stack<>();
        joueurCourant = Piece.Couleur.Blanc; // Les Blancs commencent

        // Initialisation des mappages
        initialiserMappages();
    }

    /**
     * Initialise les mappages entre les positions Manoury et les coordonnées.
     */
    private void initialiserMappages() {
        positionToCoordinates = new HashMap<>();
        coordinatesToPosition = new int[10][10];

        int pos = 1;
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if ((row + col) % 2 == 1) { // Cases jouables
                    positionToCoordinates.put(pos, new int[]{row, col});
                    coordinatesToPosition[row][col] = pos;
                    pos++;
                } else {
                    coordinatesToPosition[row][col] = -1;
                }
            }
        }
    }

    /**
     * Retourne les coordonnées (ligne, colonne) correspondant à une position Manoury.
     *
     * @param position La position Manoury (1 à 50).
     * @return Un tableau contenant la ligne et la colonne, ou null si la position est invalide.
     */
    public int[] getCoordinates(int position) {
        // Vérifier que la position est valide
        if (position < 1 || position > 50) {
            return null;
        }
        return positionToCoordinates.get(position);
    }

    /**
     * Retourne la position Manoury correspondant à des coordonnées (ligne, colonne).
     *
     * @param row La ligne (0 à 9).
     * @param col La colonne (0 à 9).
     * @return La position Manoury (1 à 50), ou -1 si les coordonnées ne correspondent pas à une case jouable.
     */
    public int getPosition(int row, int col) {
        if (row < 0 || row >= 10 || col < 0 || col >= 10) {
            return -1;
        }
        return coordinatesToPosition[row][col];
    }

    /**
     * Retourne la position adjacente en fonction de la direction.
     *
     * @param position La position actuelle.
     * @param rowDir   L'incrément de ligne (-1, 1).
     * @param colDir   L'incrément de colonne (-1, 1).
     * @return La position adjacente, ou -1 si elle est invalide.
     */
    public int getAdjacentPosition(int position, int rowDir, int colDir) {
        int[] coords = getCoordinates(position);
        if (coords == null) {
            return -1;
        }
        int newRow = coords[0] + rowDir;
        int newCol = coords[1] + colDir;

        if (newRow < 0 || newRow >= 10 || newCol < 0 || newCol >= 10) {
            return -1; // Hors du damier
        }
        if ((newRow + newCol) % 2 == 0) {
            return -1; // Case non jouable
        }
        return getPosition(newRow, newCol);
    }

    /**
     * Initialise le damier avec les pions aux positions de départ.
     */
    public void initialiser() {
        // Placement des pions Noirs
        for (int i = 0; i < 20; i++) {
            board[i] = new Pion(Piece.Couleur.Noir);
        }
        // Cases vides
        for (int i = 20; i < 30; i++) {
            board[i] = null;
        }
        // Placement des pions Blancs
        for (int i = 30; i < 50; i++) {
            board[i] = new Pion(Piece.Couleur.Blanc);
        }
        historique.clear();
        joueurCourant = Piece.Couleur.Blanc;
    }

    /**
     * Retourne la pièce présente à une position donnée sur le damier.
     *
     * @param position Le numéro de la case selon la notation Manoury (1 à 50).
     * @return La pièce présente sur la case, ou {@code null} si la case est vide.
     */
    public Piece getPiece(int position) {
        if (position < 1 || position > 50) {
            return null;
        }
        return board[position - 1];
    }

    /**
     * Place une pièce sur une position donnée du damier.
     *
     * @param position Le numéro de la case selon la notation Manoury (1 à 50).
     * @param piece    La pièce à placer sur la case (ou {@code null} pour vider la case).
     */
    public void setPiece(int position, Piece piece) {
        if (position >= 1 && position <= 50) {
            board[position - 1] = piece;
        }
    }

    /**
     * Compte le nombre total de pièces présentes sur le damier.
     *
     * @return Le nombre de pièces actuellement sur le damier.
     */
    public int countPieces() {
        int count = 0;
        for (Piece piece : board) {
            if (piece != null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Effectue un déplacement de pièce s'il est valide.
     *
     * @param fromPosition La position de départ.
     * @param toPosition   La position d'arrivée.
     * @return {@code true} si le déplacement a été effectué, {@code false} sinon.
     */
    public boolean deplacer(int fromPosition, int toPosition) {
        Piece piece = getPiece(fromPosition);
        if (piece == null || piece.getCouleur() != joueurCourant) {
            return false;
        }

        List<Integer> mouvements = piece.getMouvementsValides(this, fromPosition);
        if (mouvements.contains(toPosition)) {
            // Déplacement simple
            setPiece(toPosition, piece);
            setPiece(fromPosition, null);
            promotionSiPossible(toPosition); // Vérifier la promotion après le déplacement
            historique.push(new Move(fromPosition, toPosition, null));
            changerJoueur();
            return true;
        }

        // Vérifier les prises
        List<List<Integer>> prises = piece.getPrisesValides(this, fromPosition);
        for (List<Integer> sequence : prises) {
            if (sequence.contains(toPosition)) {
                // Effectuer la prise
                effectuerPrise(fromPosition, toPosition, sequence);
                promotionSiPossible(toPosition); // Vérifier la promotion après la prise
                changerJoueur();
                return true;
            }
        }

        return false;
    }

    /**
     * Effectue une prise.
     *
     * @param fromPosition La position de départ.
     * @param toPosition   La position d'arrivée.
     */
    private void effectuerPrise(int fromPosition, int toPosition, List<Integer> chemin) {
        Piece piece = getPiece(fromPosition);
        setPiece(toPosition, piece);
        setPiece(fromPosition, null);

        List<Integer> piecesPrises = new ArrayList<>();

        int currentPos = fromPosition;
        for (int landingPos : chemin) {
            int[] fromCoords = getCoordinates(currentPos);
            int[] toCoords = getCoordinates(landingPos);
            int rowDir = Integer.compare(toCoords[0], fromCoords[0]);
            int colDir = Integer.compare(toCoords[1], fromCoords[1]);

            int row = fromCoords[0] + rowDir;
            int col = fromCoords[1] + colDir;

            while (row != toCoords[0] || col != toCoords[1]) {
                int pos = getPosition(row, col);
                Piece p = getPiece(pos);
                if (p != null && p.getCouleur() != piece.getCouleur()) {
                    setPiece(pos, null);
                    piecesPrises.add(pos);
                    break; // On a trouvé et capturé le pion ennemi
                }
                row += rowDir;
                col += colDir;
            }
            currentPos = landingPos; // Mettre à jour la position actuelle
        }

        historique.push(new Move(fromPosition, toPosition, piecesPrises));
    }

    /**
     * Change le joueur courant.
     */
    public void changerJoueur() {
        joueurCourant = (joueurCourant == Piece.Couleur.Blanc) ? Piece.Couleur.Noir : Piece.Couleur.Blanc;
    }

    /**
     * Vérifie et effectue la promotion en dame si possible.
     *
     * @param position La position de la pièce qui vient d'être déplacée.
     */
    private void promotionSiPossible(int position) {
        Piece piece = getPiece(position);
        if (piece instanceof Pion) {
            if ((piece.getCouleur() == Piece.Couleur.Blanc && position <= 5) ||
                    (piece.getCouleur() == Piece.Couleur.Noir && position >= 46)) {
                setPiece(position, new Dame(piece.getCouleur()));
            }
        }
    }

    /**
     * Vérifie si le jeu est terminé.
     *
     * @return {@code true} si le jeu est terminé, {@code false} sinon.
     */
    public boolean estTermine() {
        return !peutSeDeplacer(Piece.Couleur.Blanc) || !peutSeDeplacer(Piece.Couleur.Noir);
    }

    /**
     * Vérifie si un joueur peut se déplacer.
     *
     * @param couleur La couleur du joueur.
     * @return {@code true} si le joueur peut se déplacer, {@code false} sinon.
     */
    private boolean peutSeDeplacer(Piece.Couleur couleur) {
        for (int i = 1; i <= 50; i++) {
            Piece piece = getPiece(i);
            if (piece != null && piece.getCouleur() == couleur) {
                if (!piece.getMouvementsValides(this, i).isEmpty() ||
                        !piece.getPrisesValides(this, i).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Annule le dernier mouvement effectué.
     */
    public void annulerDernierMouvement() {
        if (!historique.isEmpty()) {
            Move dernierMouvement = historique.pop();
            setPiece(dernierMouvement.getFrom(), getPiece(dernierMouvement.getTo()));
            setPiece(dernierMouvement.getTo(), null);

            if (dernierMouvement.getPiecesPrises() != null) {
                for (int pos : dernierMouvement.getPiecesPrises()) {
                    // Restaurer les pièces prises (supposons qu'elles soient des pions adverses).
                    // joueurCourant est encore l'adversaire du joueur qui a capturé : c'est sa couleur.
                    setPiece(pos, new Pion(joueurCourant));
                }
            }
            changerJoueur();
        }
    }

    /**
     * Retourne le joueur courant.
     *
     * @return Le joueur courant.
     */
    public Piece.Couleur getJoueurCourant() {
        return joueurCourant;
    }
}



