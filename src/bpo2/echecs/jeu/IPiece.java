package bpo2.echecs.jeu;

import java.util.ArrayList;

/***
 * Définit un contrat pour l'implémentation d'une pièce
 */
public interface IPiece {
    /***
     * Vérifie que la pièce est déplacable sur une case d'un plateau
     * @param plateau le plateau sur lequel s'effectue le déplacement
     * @param caseArrivee la case sur laquelle on teste le déplacement
     * @return true si la pièce est déplacable, false sinon
     */
    boolean deplacable(Plateau plateau, Case caseArrivee);

    /***
     * Met à jour les coordonnées d'une pièce
     * @param destination les nouvelles coordonnées de la pièce
     */
    void deplacer(Case destination);

    /***
     * Renvoie la liste des déplacements possibles de la pièce sur un plateau
     * @param plateau le plateau sur lequel on cherche les déplacements
     * @return la liste des déplacements possibles de la pièce sur un plateau
     */
    ArrayList<Case> deplacementsPossibles(Plateau plateau);

    /***
     * Renvoie la représentation de la pièce
     * @return le caractère correspondant à la représentation
     */
    char getRepresentation();

    /***
     * Teste si la pièce est critique (<=> roi)
     * @return true si la pièce est critique, false sinon
     */
    boolean estCritique();

    /***
     * Renvoie la couleur de la pièce
     * @return la couleur de la pièce
     */
    Couleur getCouleur();

    /***
     * Renvoie la position de la pièce
     * @return la position de la pièce
     */
    Case getPosition();

    /***
     * Effectue une copie profonde de la pièce
     * @return la copie profonde de la pièce
     */
    IPiece copieProfonde();
}
