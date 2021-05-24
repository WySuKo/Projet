package bpo2;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.Partie;
import bpo2.echecs.joueurs.CategorieJoueur;
import bpo2.echecs.joueurs.FabriqueJoueur;
import bpo2.echecs.pieces.Cavalier;
import bpo2.echecs.jeu.CouleurPiece;
import bpo2.echecs.pieces.Piece;
import bpo2.echecs.pieces.Roi;
import bpo2.echecs.pieces.Tour;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static boolean contient(String[] liste, String element){
        for(String elem : liste)
            if(elem.equals(element))
                return true;

        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quel mode de jeu souhaitez-vous lancer?");
        System.out.println("1. Humain contre humain\n" +
                           "2. Humain contre Ordinateur\n" +
                           "3. Ordinateur contre Ordinateur\n" +
                           "4. Afficher les informations du jeu\n" +
                           "99. Quitter\n");
        String[] options = {"1", "2", "3", "99"}; //le choix 4 n'est pas présent dans cette liste pour pouvoir redemander un choix après avoir affiché les règles
        String choix;

        do{
            System.out.print("> ");
            choix = scanner.nextLine().trim();
            System.out.println();

            if(choix.equals("4")){
                ;
            }
        }while(!contient(options, choix));

        CategorieJoueur categorieJoueur1 = CategorieJoueur.HUMAIN;
        CategorieJoueur categorieJoueur2 = CategorieJoueur.AI;
        switch (Integer.parseInt(choix)){
            case 1:
                categorieJoueur2 = CategorieJoueur.HUMAIN;
                break;
            case 3:
                categorieJoueur1 = CategorieJoueur.AI;
                break;
            case 99:
                System.exit(0);
                break;
        }

        Partie partie = new Partie(new FabriqueJoueur(categorieJoueur1, categorieJoueur2));
        ArrayList<Piece> pieces = new ArrayList<>();

        pieces.add(new Tour(CouleurPiece.BLANC, new Case(2, 2)));
        pieces.add(new Cavalier(CouleurPiece.BLANC, new Case(5, 7)));
        pieces.add(new Roi(CouleurPiece.NOIRE, new Case(1, 6)));
        pieces.add(new Tour(CouleurPiece.NOIRE, new Case(3, 3)));
        pieces.add(new Roi(CouleurPiece.BLANC, new Case(4, 1)));
        pieces.add(new Cavalier(CouleurPiece.NOIRE, new Case(1, 2)));

        for(Piece piece : pieces)
            partie.ajouterPiece(piece);

        while(partie.peutContinuer()){
            System.out.println(partie.getPlateau());
            System.out.print("> ");
            while(!partie.jouerCoup()) {
                System.out.print("> ");
            }
        }

        if(partie.abandonnee()){
            String joueurPerdant = partie.getJoueurActif() == partie.getJoueurBlanc()? "Blanc" : "Noir";
            System.out.println("Le joueur " + joueurPerdant + " a abandonné la partie !");
        }
        if(partie.propositionNulleAcceptee()){
            System.out.println("La partie est déclarée nulle par les deux joueurs !");
        }
    }
}
