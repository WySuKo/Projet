package bpo2.echecs.exceptions;

public class PieceNonDeplacableException extends ErreurJeuException{
    public PieceNonDeplacableException(String raison){
        super(raison);
    }
}
