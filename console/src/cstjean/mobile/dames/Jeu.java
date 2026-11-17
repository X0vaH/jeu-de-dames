package cstjean.mobile.dames;

import java.util.Scanner;

/**
 * Point d'entrée console : permet de jouer une partie à deux joueurs.
 * Les coups se saisissent en notation Manoury, ex. « 32-28 ».
 *
 * @author Alex Heroux
 */
public class Jeu {

    /**
     * Lance une partie en console.
     *
     * @param args non utilisé
     */
    public static void main(String[] args) {
        Damier damier = new Damier();
        damier.initialiser();
        AffichageDamier affichage = new AffichageDamier();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Jeu de dames (10x10) ===");
        System.out.println("p = pion blanc, P = pion noir, d/D = dames, x = case vide, - = case non jouable");
        System.out.println("Coup : depart-arrivee (ex. 32-28) | u = annuler | q = quitter");

        while (!damier.estTermine()) {
            System.out.println();
            System.out.print(affichage.afficherDamier(damier));
            System.out.print("Au tour des " + damier.getJoueurCourant() + "s > ");
            if (!scanner.hasNextLine()) {
                return;
            }
            String saisie = scanner.nextLine().trim();

            if (saisie.equalsIgnoreCase("q")) {
                return;
            }
            if (saisie.equalsIgnoreCase("u")) {
                damier.annulerDernierMouvement();
                continue;
            }

            String[] parties = saisie.split("[-x ]+");
            try {
                int depart = Integer.parseInt(parties[0]);
                int arrivee = Integer.parseInt(parties[1]);
                if (!damier.deplacer(depart, arrivee)) {
                    System.out.println("Coup invalide.");
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Format attendu : depart-arrivee (ex. 32-28).");
            }
        }

        System.out.println();
        System.out.print(affichage.afficherDamier(damier));
        System.out.println("Partie terminée !");
    }
}
