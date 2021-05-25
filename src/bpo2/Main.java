package bpo2;

import bpo2.echecs.exceptions.CreationPlateauInvalideException;
import bpo2.echecs.exceptions.ErreurJeuException;
import bpo2.echecs.jeu.Partie;
import bpo2.echecs.joueurs.CategorieJoueur;
import bpo2.echecs.joueurs.FabriqueJoueur;
import bpo2.echecs.pieces.*;

import java.util.Scanner;

public class Main {
    /***
     * Méthode utilitaire qui permet de vérifier si un argument est présent dans une liste
     * @param liste la liste dans laquelle chercher
     * @param element l'élément à trouver
     * @return true si l'élément est présent, false sinon
     */
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
            System.out.print("Veuillez entrer votre choix pour démarrer la partie: ");
            choix = scanner.nextLine().trim();
            System.out.println();

            if(choix.equals("4")){
                System.out.println("Bienvenue dans notre jeu d'échecs ! Pour déplacer une pièce, utilisez le format convenu dans le sujet.\n" +
                        "Ecrivez \"nulle\" pour proposer une partie nulle à votre adversaire. Celui-ci devra écrire la même chose durant le tour suivant" +
                        "pour que la partie s'arrête.\nEcrivez \"abandon\" pour abandonner la partie.\n");
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

        Partie partie = null;
        try{
             partie = new Partie(new FabriqueJoueur(categorieJoueur1, categorieJoueur2), new FabriquePiece());
        }catch (CreationPlateauInvalideException e){
            System.out.println(e);
            //Créer des pièces invalides ne permet pas de lancer le jeu; on quitte
            System.exit(0);
        }

        while(partie.peutContinuer()){
            System.out.println(partie.getPlateau());
            try{
                partie.jouerCoup();
            }catch (ErreurJeuException e){
                System.out.println(e);
            }
        }
        //La partie s'est terminée
        if(partie.abandonnee()){
            String joueurPerdant = partie.getJoueurActif() == partie.getJoueurBlanc()? "Blanc" : "Noir";
            System.out.println("Le joueur " + joueurPerdant + " a abandonné la partie !");
        }
        else if(partie.propositionNulleAcceptee()){
            System.out.println("La partie est déclarée nulle par les deux joueurs !");
        }
        else if(partie.getNombreTours() == 300){
            System.out.println("Egalité, la partie se termine car elle dure trop longtemps !");
        }
        else if(partie.duelDeRois()){
            System.out.println("Egalite, un duel de Rois ne peut se terminer sur une victoire !");
        }
        else{
            System.out.println("Echec et Mat !");
        }
    }
}
