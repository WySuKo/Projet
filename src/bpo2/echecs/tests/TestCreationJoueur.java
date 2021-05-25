package bpo2.echecs.tests;

import bpo2.echecs.jeu.IFabriqueJoueur;
import bpo2.echecs.jeu.IJoueur;
import bpo2.echecs.joueurs.CategorieJoueur;
import bpo2.echecs.joueurs.FabriqueJoueur;
import bpo2.echecs.joueurs.JoueurIA;
import bpo2.echecs.joueurs.JoueurHumain;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCreationJoueur {
    @Test
    public void testCreationJoueur(){
        IFabriqueJoueur fabriqueJoueur = new FabriqueJoueur(CategorieJoueur.HUMAIN, CategorieJoueur.AI);
        IJoueur joueur1 = fabriqueJoueur.fabriquerPremierJoueur();
        IJoueur joueur2 = fabriqueJoueur.fabriquerSecondJoueur();

        assertTrue(joueur1 instanceof JoueurHumain);
        assertTrue(joueur2 instanceof JoueurIA);
    }
}
