package bpo2.echecs.exceptions;

public class PieceNonTrouveeException extends ErreurJeuException{
    public PieceNonTrouveeException(String raison){
        super(raison);
    }
}
