package cstjean.mobile.dames;

import java.util.List;

/**
 * Classe pour le mouvment des pieces.
 */
class Move {
    private final int from;
    private final int to;
    private final List<Integer> piecesPrises;

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
