package bpo2.echecs.joueurs;

import bpo2.echecs.jeu.CouleurPiece;
import bpo2.echecs.jeu.IFabriqueJoueur;
import bpo2.echecs.jeu.IJoueur;

public class FabriqueJoueur implements IFabriqueJoueur {
    private final CategorieJoueur categoriePremierJoueur;
    private final CategorieJoueur categorieSecondJoueur;

    public FabriqueJoueur(CategorieJoueur categoriePremierJoueur, CategorieJoueur categorieSecondJoueur){
        this.categoriePremierJoueur = categoriePremierJoueur;
        this.categorieSecondJoueur = categorieSecondJoueur;
    }

    private IJoueur fabriquerJoueur(CategorieJoueur categorieJoueur, CouleurPiece couleurJoueur) {
        if(categorieJoueur == CategorieJoueur.AI)
            return new JoueurAI(couleurJoueur);
        else
            return new JoueurHumain(couleurJoueur);
    }

    @Override
    public IJoueur fabriquerPremierJoueur() {
        return fabriquerJoueur(categoriePremierJoueur, CouleurPiece.BLANC);
    }

    @Override
    public IJoueur fabriquerSecondJoueur(){
        return fabriquerJoueur(categorieSecondJoueur, CouleurPiece.NOIRE);
    }
}
