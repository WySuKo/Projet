package bpo2.echecs.jeu;

import bpo2.echecs.exceptions.CaseInvalideException;
import bpo2.echecs.exceptions.CreationRoiInvalideException;
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
    private int nombreTours;

    public Partie(IFabriqueJoueur fabriqueJoueur, IFabriquePiece fabriquePiece) throws CreationRoiInvalideException{
        joueurBlanc = fabriqueJoueur.fabriquerPremierJoueur();
        joueurNoir = fabriqueJoueur.fabriquerSecondJoueur();
        joueurActif = joueurBlanc;
        plateau = new Plateau();
        abandon = false;
        propositionNulle = false;
        propositionNulleAcceptee = false;
        nombreTours = 0;

        fabriquerPieces(fabriquePiece);
    }

    private void fabriquerPieces(IFabriquePiece fabriquePiece) throws CreationRoiInvalideException {
        boolean roiBlancAjoute = false;
        boolean roiNoirAjoute = false;

        for(int i = 0; i < fabriquePiece.getNombrePieces(); ++i){
            IPiece piece = fabriquePiece.fabriquerPiece();
            if(piece.estCritique()){
                if(piece.getCouleur() == Couleur.BLANC){
                    if(roiBlancAjoute)
                        throw new CreationRoiInvalideException("Un roi blanc existe déjà !");
                    else
                        roiBlancAjoute = true;
                }
                else{
                    if(roiNoirAjoute)
                        throw new CreationRoiInvalideException("Un roi noir existe déjà !");
                    else
                        roiNoirAjoute = true;
                }
            }
            plateau.ajouterPiece(piece);
        }
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
        if(!plateau.caseValide(piece.getPosition()))
            throw new CaseInvalideException("Impossible d'ajouter une pièce dans une case en-dehors du plateau !");

        plateau.ajouterPiece(piece);
    }

    public int getNombreTours() {
        return nombreTours;
    }

    public boolean peutContinuer(){
        return !abandon && !getPlateau().echecEtMat(joueurActif == joueurBlanc ? Couleur.BLANC : Couleur.NOIR) && !propositionNulleAcceptee && nombreTours != 300;
    }

    public void jouerCoup() throws FormatCoupInvalideException, PieceNonDeplacableException, CaseInvalideException {
        String coup = joueurActif.obtenirCoup(plateau);

        if(coup.equals("abandon")){
            abandon = true;
            return; //quitter la fonction ici permet de ne pas changer le joueur actif et donc de l'afficher dans le message
        }
        if(coup.equals("nulle")){
            if(propositionNulle)
                propositionNulleAcceptee = true;
            else
                propositionNulle = true;

            //une proposition de nulle ne compte pas comme un tour
            joueurActif = joueurActif == joueurBlanc ? joueurNoir : joueurBlanc;
            return;
        }

        propositionNulle = false;
        propositionNulleAcceptee = false;

        if(!regexFormatCoup.matcher(coup).matches()){
            throw new FormatCoupInvalideException("Le coup que vous avez entré est invalide ! Veuillez entrer un coup de format a1b1.");
        }

        Case caseDepart = new Case(coup.substring(0, 2));
        Case caseArrivee = new Case(coup.substring(2, 4));

        //pour pas que le joueur se mette en échec lui-même après un déplacement
        if(plateau.echecApresDeplacement(plateau.getPiece(caseDepart).getCouleur(), caseDepart, caseArrivee))
            throw new PieceNonDeplacableException("Le coup que vous avez entré est invalide ! Déplacer cette pièce mettrait votre Roi en échec.");

        try {
            plateau.deplacer(caseDepart, caseArrivee);
        }catch (CaseInvalideException e){
            throw e;
        }

        //on échange les joueurs
        nombreTours++;
        joueurActif = joueurActif == joueurBlanc ? joueurNoir : joueurBlanc;
    }
}
