package bpo2.echecs.tests;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.pieces.Cavalier;
import bpo2.echecs.pieces.Roi;
import bpo2.echecs.pieces.Tour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPieces {
    @Test
    public void testCreationPieces(){
        Roi roiBlanc = new Roi(Couleur.BLANC, new Case(2, 2));
        Tour tourNoire = new Tour(Couleur.NOIR, new Case(3, 4));
        Cavalier cavalierBlanc = new Cavalier(Couleur.BLANC, new Case(1, 6));

        assertEquals(roiBlanc.getCouleur(), Couleur.BLANC);
        assertTrue(roiBlanc.estCritique());
        assertEquals(roiBlanc.getRepresentation(), 'R');
        assertEquals(roiBlanc.getPosition(), new Case(2, 2));

        assertEquals(tourNoire.getCouleur(), Couleur.NOIR);
        assertFalse(tourNoire.estCritique());
        assertEquals(tourNoire.getRepresentation(), 't');
        assertEquals(tourNoire.getPosition(), new Case(3, 4));

        assertEquals(cavalierBlanc.getCouleur(), Couleur.BLANC);
        assertFalse(cavalierBlanc.estCritique());
        assertEquals(cavalierBlanc.getRepresentation(), 'C');
        assertEquals(cavalierBlanc.getPosition(), new Case(1, 6));

        roiBlanc.deplacer(new Case(3, 2));
        tourNoire.deplacer(new Case(7, 4));
        cavalierBlanc.deplacer(new Case(3, 7));

        assertEquals(roiBlanc.getPosition(), new Case(3, 2));
        assertEquals(tourNoire.getPosition(), new Case(7, 4));
        assertEquals(cavalierBlanc.getPosition(), new Case(3, 7));
    }
}
