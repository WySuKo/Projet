package Pieces;
// Test de Push sur git
public class Tour extends Piece{
    public Tour(PieceCouleur couleur, int x, int y){
        super(couleur,'t', x, y);
    }

    @Override
    public boolean deplacable(int x, int y, Plateau plateau) {
        if(!super.deplacable(x, y, plateau))
            return false;

        if(x != getX() && y != getY())
            return false;

        if(x != getX())
        {
            //movement vertical
            if(x > getX())
            {
                //descend
                for(int i = getX() + 1; i < x; i++)
                    if(plateau.getPlateau()[i][y] != null)
                        return false;
            }
            else
            {
                //monte
                for(int i = getX() - 1; i > x; i--)
                    if(plateau.getPlateau()[i][y] != null)
                        return false;
            }
        }
        else
        {
            //mouvement horizontal
            if(y > getY())
            {
                //droite
                for(int i = getY() + 1; i < y; i++)
                    if(plateau.getPlateau()[x][i] != null)
                        return false;
            }
            else
            {
                //gauche
                for(int i = getY() - 1; i > y; i--)
                    if(plateau.getPlateau()[x][i] != null)
                        return false;
            }

        }

        return true;
    }
}
