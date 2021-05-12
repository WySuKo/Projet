package Pieces;

public class Main {
    public static void main(String[] args) {
        Partie partie = new Partie(new JoueurHumain(), new JoueurHumain());

        Tour tour_blanche = new Tour(Couleur.BLANC, 4, 4);
        Tour tour_blanche_2 = new Tour(Couleur.BLANC, 4, 1);
        Tour tour_noire = new Tour(Couleur.NOIRE, 4, 6);
        Tour tour_noire_2 = new Tour(Couleur.NOIRE, 1, 4);

        partie.ajouterPieces(tour_blanche, tour_blanche_2, tour_noire, tour_noire_2);

        while(true){
            System.out.println(partie.getPlateau());
            System.out.print("> ");
            while(!partie.jouerCoup()) {
                System.out.print("> ");
            }
        }
    }
}
