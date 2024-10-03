/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessCore;

import ChessCore.Pieces.Bishop;
import ChessCore.Pieces.King;
import ChessCore.Pieces.Knight;
import ChessCore.Pieces.Pawn;
import ChessCore.Pieces.Piece;
import ChessCore.Pieces.Queen;
import ChessCore.Pieces.Rook;

public class PieceFactory {

    public Piece createPiece(Class pieceClass, Player player) {
        if (pieceClass.equals(Pawn.class)) {
            return new Pawn(player);
        }
        if (pieceClass.equals(Bishop.class)) {
            return new Bishop(player);
        }
        if (pieceClass.equals(Knight.class)) {
            return new Knight(player);
        }
        if (pieceClass.equals(King.class)) {
            return new King(player);
        }
        if (pieceClass.equals(Queen.class)) {
            return new Queen(player);
        }
        if (pieceClass.equals(Rook.class)) {
            return new Rook(player);
        }
        return null;
    }
}
