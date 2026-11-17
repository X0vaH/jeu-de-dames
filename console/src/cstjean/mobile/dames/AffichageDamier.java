package cstjean.mobile.dames;

/**
 * Classe responsable de la génération de la représentation graphique du damier.
 * Cette classe suit le principe de responsabilité unique en se concentrant
 * uniquement sur la création de la représentation du damier pour l'affichage.
 *
 * @author Alex Heroux
 */
public class AffichageDamier {

    /**
     * Génère une représentation textuelle du damier pour affichage en console.
     *
     * @param damier Le damier à représenter.
     * @return Une chaîne de caractères représentant le damier.
     */
    public String afficherDamier(Damier damier) {
        StringBuilder sb = new StringBuilder();
        int position = 1; // Position Manoury allant de 1 à 50

        for (int row = 0; row < 10; row++) {
            StringBuilder line = new StringBuilder();
            for (int col = 0; col < 10; col++) {
                if ((row + col) % 2 == 0) {
                    // Case non jouable
                    line.append('-');
                } else {
                    // Case jouable
                    if (position <= 50) {
                        Piece piece = damier.getPiece(position++);
                        line.append(piece == null ? 'x' : piece.getRepresentation());
                    } else {
                        line.append('-'); // En cas de dépassement (ne devrait pas se produire)
                    }
                }
            }
            sb.append(line.toString()).append('\n');
        }
        sb.append("-");
        sb.deleteCharAt(sb.lastIndexOf("-"));
        return sb.toString();
    }
}
