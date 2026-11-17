package cstjean.mobile.tp2;

import java.util.List;

/**
 * Classe pour le mouvment des pieces.
 */
class Move {

    /**
     * Num case de depart.
     */
    private final int from;
    /**
     * Num case de fin.
     */
    private final int to;
    /**
     * Liste de pieces prises.
     */
    private final List<Integer> piecesPrises;

    /**
     * Constructeur Move.
     *
     * @param from num case depart.
     * @param to num case fin.
     * @param piecesPrises liste piece prises.
     */
    public Move(int from, int to, List<Integer> piecesPrises) {
        this.from = from;
        this.to = to;
        this.piecesPrises = piecesPrises;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public List<Integer> getPiecesPrises() {
        return piecesPrises;
    }
}
