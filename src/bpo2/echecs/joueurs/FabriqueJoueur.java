package bpo2.echecs.joueurs;

import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.IFabriqueJoueur;
import bpo2.echecs.jeu.IJoueur;

/***
 * Implémentation d'une fabrique de joueur
 */
public class FabriqueJoueur implements IFabriqueJoueur {
    private final CategorieJoueur categoriePremierJoueur;
    private final CategorieJoueur categorieSecondJoueur;

    /***
     * Crée la fabrique de joueurs
     * @param categoriePremierJoueur la catégorie du premier joueur (Humain ou IA)
     * @param categorieSecondJoueur la catégorie du second joueur (Humain ou IA)
     */
    public FabriqueJoueur(CategorieJoueur categoriePremierJoueur, CategorieJoueur categorieSecondJoueur){
        this.categoriePremierJoueur = categoriePremierJoueur;
        this.categorieSecondJoueur = categorieSecondJoueur;
    }

    /***
     * Crée un joueur
     * @param categorieJoueur catégorie du joueur à créer
     * @param couleurJoueur couleur du joueur à créer
     * @return le joueur crée
     */
    private IJoueur fabriquerJoueur(CategorieJoueur categorieJoueur, Couleur couleurJoueur) {
        if(categorieJoueur == CategorieJoueur.AI)
            return new JoueurIA(couleurJoueur);
        else
            return new JoueurHumain(couleurJoueur);
    }

    @Override
    public IJoueur fabriquerPremierJoueur() {
        return fabriquerJoueur(categoriePremierJoueur, Couleur.BLANC);
    }

    @Override
    public IJoueur fabriquerSecondJoueur(){
        return fabriquerJoueur(categorieSecondJoueur, Couleur.NOIR);
    }
}
