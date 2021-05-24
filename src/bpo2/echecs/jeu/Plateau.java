package bpo2.echecs.jeu;

import bpo2.echecs.exceptions.CaseInvalideException;
import bpo2.echecs.exceptions.PieceNonDeplacableException;

import java.util.ArrayList;

public class Plateau {
    private final IPiece[][] plateau;
    public static final int TAILLE = 8;

    private final ArrayList<IPiece> piecesBlanches;
    private final ArrayList<IPiece> piecesNoires;

    public Plateau() { //TODO fabrique de pièces + vérifier que 2 rois sont présents
        this.plateau = new IPiece [TAILLE][TAILLE];
        piecesBlanches = new ArrayList<>();
        piecesNoires = new ArrayList<>();
    }

    public ArrayList<IPiece> getPiecesBlanches() {
        return piecesBlanches;
    }

    public ArrayList<IPiece> getPiecesNoires() {
        return piecesNoires;
    }

    public boolean caseValide(Case c){
        return c.getX() >= 0 && c.getX() < TAILLE && c.getY() >= 0 && c.getY() < TAILLE;
    }

    public Plateau copieProfonde(){
        Plateau plateau = new Plateau();
        for(IPiece[] pieces : this.plateau)
            for(IPiece piece : pieces)
                if(piece != null)
                    plateau.ajouterPiece(piece.copieProfonde());
        return plateau;
    }

    public ArrayList<IPiece> piecesMenacantes(IPiece pieceMenacee){
        ArrayList<IPiece> pieces = new ArrayList<>();
        ArrayList<IPiece> piecesEnnemies = pieceMenacee.getCouleur() == Couleur.BLANC ? piecesNoires : piecesBlanches;

        for(IPiece piece : piecesEnnemies)
            if(piece.deplacable(this, pieceMenacee.getPosition()))
                pieces.add(piece);

        return pieces;
    }

    public void ajouterPiece(IPiece piece) {
        //La case est déjà vérifiée lors de l'appel
        Case destination = piece.getPosition();

        if(getPiece(destination) != null)
            retirerPiece(destination);

        plateau[destination.getX()][destination.getY()] = piece;

        if(piece.getCouleur() == Couleur.BLANC)
            piecesBlanches.add(piece);
        else
            piecesNoires.add(piece);
    }

    private void retirerPiece(Case source){
        //Tout est vérifié avant l'appel

        IPiece piece = plateau[source.getX()][source.getY()];
        if(piece.getCouleur() == Couleur.BLANC)
            piecesBlanches.remove(piece);
        else
            piecesNoires.remove(piece);

        plateau[source.getX()][source.getY()] = null;
    }

    private void deplacerPiece(IPiece piece, Case caseArrivee) {
        //Tout est vérifié avant l'appel
        Case caseDepart = piece.getPosition();

        plateau[caseDepart.getX()][caseDepart.getY()] = null;

        if(caseOccupee(caseArrivee))
            retirerPiece(caseArrivee);

        plateau[caseArrivee.getX()][caseArrivee.getY()] = piece;
        piece.deplacer(caseArrivee);
    }

    public void deplacer(Case caseDepart, Case caseArrivee) throws CaseInvalideException, PieceNonDeplacableException {
        IPiece pieceADeplacer = getPiece(caseDepart);
        if(pieceADeplacer == null){
            throw new CaseInvalideException("Erreur lors du déplacement d'une pièce: la pièce à déplacer n'existe pas !");
        }

        if(!pieceADeplacer.deplacable(this, caseArrivee))
            throw new PieceNonDeplacableException("Erreur lors du déplacement d'une pièce: la pièce n'est pas déplacable !");

        deplacerPiece(pieceADeplacer, caseArrivee);
    }

    public IPiece getPiece(Case source) {
        if(!caseValide(source)) return null;
        return plateau[source.getX()][source.getY()];
    }

    public boolean caseOccupee(Case source) {
        return plateau[source.getX()][source.getY()] != null;
    }

    public IPiece getRoi(Couleur couleurPiece){
        ArrayList<IPiece> listeAChercher = couleurPiece == Couleur.BLANC ? piecesBlanches : piecesNoires;
        for(IPiece piece : listeAChercher)
            if(piece.estCritique())
                return piece;
        return null;
    }

    public boolean echecApresDeplacement(Couleur couleurRoiATester, Case caseDepart, Case caseDestination) throws CaseInvalideException, PieceNonDeplacableException{
        Plateau plateauCopie = this.copieProfonde();

        try {
            plateauCopie.deplacer(caseDepart, caseDestination);
        }catch (CaseInvalideException | PieceNonDeplacableException e){
            throw e;
        }

        return plateauCopie.echec(couleurRoiATester);
    }

    public boolean echec(Couleur couleur) {
        IPiece roi = getRoi(couleur);

        ArrayList<IPiece> piecesATester = couleur == Couleur.BLANC? piecesNoires : piecesBlanches;
        for(IPiece piece : piecesATester)
            if(piece.deplacable(this, roi.getPosition()))
                return true;
        return false;
    }

    public boolean echecEtMat(Couleur couleur) { //TODO à tester + exception?

        //ArrayList<Case> caseEchecRoi = new ArrayList<>();

        //On récupère le roi que l'on souhaite tester
        IPiece roi = getRoi(couleur);
        ArrayList<Case> deplacementsRoi = roi.deplacementsPossibles(this);

        //pour sauver le roi de l'échec
            //1 - soit une seule pièce le menace et on la mange
            //2 - soit on bouge le roi sur une case safe
            //3 - soit on sacrifie une pièce

        //pour chaque pièce ennemie (traitement cas 1)
            //si la pièce ennemie peut manger le roi, on l'ajoute à la liste des pièces ennemies
        //si la liste est vide, le roi n'est pas en échec -> false
        //s'il n'y a qu'une seule pièce menacante
            //pour chaque pièce alliée, si elle peut manger la pièce menacante et ne pas créer d'ouverture -> false

        //Si on arrive ici, ça veut dire qu'on est obligé de déplacer le roi ou de sacrifier une pièce pour le sauver
        //(traitement cas 2 et 3)
        //pour chaque case autour du roi
            //pour chaque piece adverse
                //si la piece adverse est déplacable sur la case testée
                    //on l'ajoute à la liste de pièces adverses déplacables sur la case
            //si la liste de pièces adverses déplacables est vide, la case est safe -> le roi n'est pas en échec et mat

            //protection

        ArrayList<IPiece> piecesAdverses = couleur == Couleur.BLANC? piecesNoires : piecesBlanches;
        ArrayList<IPiece> piecesAlliees = couleur == Couleur.BLANC? piecesBlanches : piecesNoires;

        ArrayList<IPiece> piecesDeplacablesCaseActuelle = new ArrayList<>();

        for(IPiece piece : piecesAdverses){
            if(piece.deplacable(this, roi.getPosition())){
                piecesDeplacablesCaseActuelle.add(piece);
            }
        }
        if(piecesDeplacablesCaseActuelle.isEmpty()) //aucune pièce ne menace le roi, il n'est pas en échec
            return false;
        if(piecesDeplacablesCaseActuelle.size() == 1){
            Case positionPieceEnnemie = piecesDeplacablesCaseActuelle.get(0).getPosition();
            for(IPiece piece : piecesAlliees){
                try {
                    if(piece.deplacable(this, positionPieceEnnemie) && !echecApresDeplacement(couleur, piece.getPosition(), positionPieceEnnemie)){
                        //la seule pièce qui menace le roi peut être mangée, le roi n'est pas en échec et mat
                        return false;
                    }
                } catch (CaseInvalideException | PieceNonDeplacableException e){;}
            }
        }
        piecesDeplacablesCaseActuelle.clear();
        //traitement cas 2 & 3
        for(Case c : deplacementsRoi){
            for(IPiece pieceAdverse : piecesAdverses){
                if(pieceAdverse.deplacable(this, c))
                    piecesDeplacablesCaseActuelle.add(pieceAdverse);
            }
            if(piecesDeplacablesCaseActuelle.isEmpty()) //une case ne peut pas être atteinte par les pièces, le roi peut se déplacer dessus
                return false;
            //CODE ICI
            piecesDeplacablesCaseActuelle.clear();
        }
        return true;
    }

    /* Génération de la représentation du plateau */

    private String genererLigne(){
        return "   " + "--- --- --- --- --- --- --- ---\n";
    }

    public String toString(){
        StringBuilder aff = new StringBuilder();
        aff.append("    a   b   c   d   e   f   g   h\n");

        for(int i = 0; i < TAILLE; i++){
            aff.append(genererLigne());
            aff.append(TAILLE - i);
            for(int j = 0; j < TAILLE; j++){
                aff.append(" | ").append(plateau[i][j] != null? plateau[i][j].getRepresentation() : " ");
            }
            aff.append(" | ").append(TAILLE-i).append("\n");
        }
        aff.append(genererLigne());
        aff.append("    a   b   c   d   e   f   g   h\n");

        return aff.toString();
    }
}
