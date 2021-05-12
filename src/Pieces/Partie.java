package Pieces;

import java.util.regex.Pattern;

public class Partie {
    public Plateau getPlateau() {
        return plateau;
    }

    private Plateau plateau;
    private Pattern patternFormatInput;
    private IJoueur joueur_blanc;
    private IJoueur joueur_noir;
    private IJoueur joueur_actif;

    public Partie(IJoueur joueur_blanc, IJoueur joueur_noir){
        this.patternFormatInput = Pattern.compile("([a-h][1-8]){2}");
        this.joueur_blanc = joueur_blanc;
        this.joueur_noir = joueur_noir;
        this.joueur_actif = joueur_blanc;
        this.plateau = new Plateau();
    }

    public void ajouterPieces(Piece... pieces){
        for(Piece p : pieces)
            plateau.ajouterPiece(p);
    }

    private boolean formatCoupValide(String coup){
        return patternFormatInput.matcher(coup).matches(); //matches renvoie true que si TOUTE la chaîne match la regex
    }

    public boolean jouerCoup(){
        String coup = joueur_actif.obtenirCoup();
        if(coup.equals("quit"))
            System.exit(0);
        //format valide
        // "a2b7" -> 4 caractères de long; 1er et 3e carac entre a et h; 2e et 4e chiffre entre 1 et 8

        // transformation
        if(!formatCoupValide(coup)) return false;

        int xDepart = -Integer.parseInt(coup.substring(1, 2)) + 8;
        int yDepart = (int)coup.charAt(0) - 97;

        int xArrivee = -Integer.parseInt(coup.substring(3)) + 8;
        int yArrivee = (int)coup.charAt(2) - 97;

        //envoi dans le plateau
        Piece p = plateau.getPlateau()[xDepart][yDepart];
        if(p == null) return false;
        if(p.getCouleur() == Couleur.BLANC && joueur_actif != joueur_blanc) return false;
        if(p.getCouleur() == Couleur.NOIRE && joueur_actif != joueur_noir) return false;

        if(!plateau.deplacerPiece(p, xArrivee, yArrivee)) return false;

        //changement de joueur
        joueur_actif = joueur_actif == joueur_blanc? joueur_noir : joueur_blanc;
        return true;
    }
}
