package bpo2.echecs.pieces;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.IPiece;
import bpo2.echecs.jeu.Plateau;

/***
 * Implémentation de fonctionnalités communes à une pièce (classe abstraite)
 */
public abstract class Piece implements IPiece {
    private final char caractere; //la représentation de la pièce
    private final Couleur couleur; //la couleur de la pièce
    private Case caseActuelle; //la case sur laquelle est la pièce

    /***
     * Crée une pièce
     * @param couleur la couleur de la pièce à créer
     * @param caractere la représentation de la pièce
     * @param caseDepart la case de départ de la pièce
     */
    public Piece(Couleur couleur, char caractere, Case caseDepart){
        this.couleur = couleur;
        this.caractere = couleur == Couleur.BLANC ? Character.toUpperCase(caractere) : caractere;
        this.caseActuelle = caseDepart;
    }

    /***
     * Renvoie la représentation de la pièce
     * @return le caractère correspondant à la représentation
     */
    @Override
    public char getRepresentation() {
        return caractere;
    }

    /***
     * Renvoie la couleur de la pièce
     * @return la couleur de la pièce
     */
    @Override
    public Couleur getCouleur() {
        return couleur;
    }

    /***
     * Renvoie la position de la pièce
     * @return la position de la pièce
     */
    @Override
    public Case getPosition() {
        return caseActuelle;
    }

    /***
     * Vérifie que la pièce est déplacable sur une case d'un plateau
     * @param plateau le plateau sur lequel s'effectue le déplacement
     * @param caseArrivee la case sur laquelle on teste le déplacement
     * @return true si la pièce est déplacable, false sinon
     */
    @Override
    public boolean deplacable(Plateau plateau, Case caseArrivee){
        if(!plateau.caseValide(caseArrivee)) return false;

        if(caseActuelle.equals(caseArrivee))
            return false;

        return !plateau.caseOccupee(caseArrivee) || plateau.getPiece(caseArrivee).getCouleur() != getCouleur();
    }

    /***
     * Met à jour les coordonnées d'une pièce
     * @param destination les nouvelles coordonnées de la pièce
     */
    @Override
    public void deplacer(Case destination){
        caseActuelle = destination;
    }

    /***
     * Teste si la pièce est critique (<=> roi)
     * @return true si la pièce est critique, false sinon
     */
    @Override
    public boolean estCritique() {
        return false;
    }
}


