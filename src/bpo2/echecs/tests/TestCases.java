package bpo2.echecs.tests;

import bpo2.echecs.jeu.Case;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCases {
    @Test
    public void testEgaliteCases(){
        Case premiere = new Case(1, 1);
        Case deuxieme = new Case(7, 6);

        assertEquals(premiere.getX(), 1);
        assertEquals(premiere.getY(), 1);
        assertEquals(premiere, new Case(1, 1));

        assertEquals(deuxieme.getX(), 7);
        assertEquals(deuxieme.getY(), 6);
        assertEquals(deuxieme, new Case(7, 6));

        assertEquals((new Case("a2")), new Case(6, 0));
        assertEquals((new Case("c6")), new Case(2, 2));
        assertNotEquals((new Case("h3")), new Case(4, 7));
        assertNotEquals((new Case("b4")), new Case(1, 3));

        assertEquals("a2", (new Case(6, 0)).toString());
        assertEquals("c6", (new Case(2, 2)).toString());
        assertNotEquals("h3", (new Case(4, 7)).toString());
        assertNotEquals("b4", (new Case(1, 3)).toString());
    }


}
