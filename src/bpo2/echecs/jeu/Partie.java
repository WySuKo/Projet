package bpo2.echecs.jeu;

import bpo2.echecs.exceptions.CaseInvalideException;
import bpo2.echecs.exceptions.FormatCoupInvalideException;
import bpo2.echecs.exceptions.PieceNonDeplacableException;

import java.util.regex.Pattern;

public class Partie {
    private final Plateau plateau;
    private final IJoueur joueurBlanc;
    private final IJoueur joueurNoir;
    private IJoueur joueurActif;

    private boolean abandon;
    private static Pattern regexFormatCoup = Pattern.compile("([a-h][1-8]){2}");
    private boolean propositionNulle;
    private boolean propositionNulleAcceptee;

    public Partie(IFabriqueJoueur fabriqueJoueur){
        joueurBlanc = fabriqueJoueur.fabriquerPremierJoueur();
        joueurNoir = fabriqueJoueur.fabriquerSecondJoueur();
        joueurActif = joueurBlanc;
        plateau = new Plateau();
        abandon = false;
        propositionNulle = false;
        propositionNulleAcceptee = false;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public boolean abandonnee() {
        return abandon;
    }

    public boolean propositionNulleAcceptee() {
        return propositionNulleAcceptee;
    }

    public IJoueur getJoueurActif() {
        return joueurActif;
    }


    public IJoueur getJoueurBlanc() {
        return joueurBlanc;
    }

    public void ajouterPiece(IPiece piece) throws CaseInvalideException {
        plateau.ajouterPiece(piece);
    }

    public boolean peutContinuer(){
        return !abandon && !getPlateau().echecEtMat(joueurActif == joueurBlanc ? CouleurPiece.BLANC : CouleurPiece.NOIRE) && !propositionNulleAcceptee;
    }

    public boolean jouerCoup() throws FormatCoupInvalideException, PieceNonDeplacableException {
        String coup = joueurActif.obtenirCoup(plateau);

        if(coup.equals("abandon")){
            abandon = true;
            return true; //retourner true ici permet de ne pas changer le joueur actif et donc de l'afficher dans le message
        }
        if(coup.equals("nulle")){
            if(propositionNulle)
                propositionNulleAcceptee = true;
            else
                propositionNulle = true;

            joueurActif = joueurActif == joueurBlanc ? joueurNoir : joueurBlanc;
            return true;
        }

        propositionNulle = false;
        propositionNulleAcceptee = false;

        if(!regexFormatCoup.matcher(coup).matches()){
            throw new FormatCoupInvalideException("Le coup que vous avez entré est invalide ! Veuillez entrer un coup de format a1b1.");
        }

        Case caseDepart = new Case(coup.substring(0, 2));
        Case caseArrivee = new Case(coup.substring(2, 4));

        //pour pas que le joueur se mette en échec lui-même après un déplacement
        if(getPlateau().echecApresDeplacement(joueurActif == joueurBlanc? CouleurPiece.BLANC : CouleurPiece.NOIRE, caseDepart, caseArrivee))
            throw new PieceNonDeplacableException("Déplacer cette pièce mettrait votre Roi en échec.");

        try {
            plateau.deplacer(caseDepart, caseArrivee);
        }

        joueurActif = joueurActif == joueurBlanc ? joueurNoir : joueurBlanc;

        return true;
    }
}
