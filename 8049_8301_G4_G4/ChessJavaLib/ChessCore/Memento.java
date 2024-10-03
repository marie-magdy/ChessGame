/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessCore;

/**
 *
 * @author DELL
 */
public class Memento {
    
    private Move move;
    private ChessBoard boardBeforeMove;

    public Memento(Move move, ChessBoard boardBeforeMove) {
        this.move = move;
        this.boardBeforeMove = boardBeforeMove;
    }

    public Move getMove() {
        return move;
    }

    public ChessBoard getBoardBeforeMove() {
        return boardBeforeMove;
    }   
}
