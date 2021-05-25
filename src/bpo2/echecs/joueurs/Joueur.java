package bpo2.echecs.joueurs;

import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.IJoueur;

/***
 * Implémentation de fonctionnalités communes à un joueur (classe abstraite)
 */
public abstract class Joueur implements IJoueur {
    private Couleur couleur; //la couleur du joueur

    /***
     * Crée un joueur
     * @param couleur la couleur du joueur à créer
     */
    public Joueur(Couleur couleur){
        this.couleur = couleur;
    }

    /***
     * Renvoie la couleur du joueur
     * @return la couleur du joueur
     */
    public Couleur getCouleur(){
        return couleur;
    }
}
