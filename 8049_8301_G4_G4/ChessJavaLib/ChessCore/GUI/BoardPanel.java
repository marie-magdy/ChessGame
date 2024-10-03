/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ChessCore.GUI;

import ChessCore.BoardFile;
import ChessCore.BoardRank;
import ChessCore.ClassicChessGame;
import ChessCore.Pieces.King;
import ChessCore.Pieces.Piece;
import ChessCore.Player;
import ChessCore.Square;
import ChessCore.Utilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author DELL
 */
public class BoardPanel extends javax.swing.JPanel {

    private List<PieceImage> pieces;
    private Input input;
    private ClassicChessGame game;

    /**
     * Creates new form BoardPanel
     */
    public BoardPanel() {
        initComponents();
        setPreferredSize(new Dimension(8 * 50, 8 * 50));
        game = new ClassicChessGame();
        initializePieces();
        input = new Input(game, this);
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
    }

    @Override
    protected void paintComponent(Graphics g) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(new Color(227,198,181));                    
                } else {
                    g.setColor(new Color(175,105,53));
                }
                g.fillRect(i * 50, j * 50, 50, 50);
            }
        }

        if (input.getSelectedPiece() != null) {  
            System.out.println("input != null");
            Square square = input.createSquare(input.getSelectedPiece().getRow(), input.getSelectedPiece().getCol());
            List<Square> validMoves = game.getAllValidMovesFromSquare(square);
            for (int i = 0; i < validMoves.size(); i++) {
                g.setColor(Color.red);
                if (game.getWhoseTurn().equals(Player.WHITE)) {
                    g.fillRect((validMoves.get(i).getFile().getValue()) * 50, (7 - validMoves.get(i).getRank().getValue()) * 50, 50, 50);
                } else {
                    g.fillRect((7 - validMoves.get(i).getFile().getValue()) * 50, (validMoves.get(i).getRank().getValue()) * 50, 50, 50);
                }
            }
            Square sq = getKingSquare(game.getWhoseTurn());

            if (Utilities.isInCheck(game.getWhoseTurn(), game.getBoard())) {
                g.setColor(Color.RED);
                if (game.getWhoseTurn().equals(Player.WHITE)) {
                    g.fillRect(sq.getFile().getValue() * 50, (7 - sq.getRank().getValue()) * 50, 50, 50);
                } else {
                    g.fillRect((7 - sq.getFile().getValue()) * 50, sq.getRank().getValue() * 50, 50, 50);
                }
            }
        }
        paintPieces(g);    // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    private void initializePieces() {
        pieces = new ArrayList<>();// new PieceImage[8][8];
        BufferedImage wRook;
        try {
            wRook = ImageIO.read(getClass().getClassLoader().getResource("ressources/WhiteRook.png"));
            Image wRook2 = wRook.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
            pieces.add(new PieceImage(wRook2, 0, 7 * 50, 0, 0));
            pieces.add(new PieceImage(wRook2, 7 * 50, 7 * 50, 0, 7));

            BufferedImage wKnight = ImageIO.read(getClass().getClassLoader().getResource("ressources/WhiteKnight.png"));
            Image wKnightScaled = wKnight.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
            pieces.add(new PieceImage(wKnightScaled, 1 * 50, 7 * 50, 0, 1));
            pieces.add(new PieceImage(wKnightScaled, 6 * 50, 7 * 50, 0, 6));

            BufferedImage wBishop = ImageIO.read(getClass().getClassLoader().getResource("ressources/WhiteBishop.png"));
            Image wBishopScaled = wBishop.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
            pieces.add(new PieceImage(wBishopScaled, 2 * 50, 7 * 50, 0, 2));
            pieces.add(new PieceImage(wBishopScaled, 5 * 50, 7 * 50, 0, 5));

            BufferedImage wQueen = ImageIO.read(getClass().getClassLoader().getResource("ressources/WhiteQueen.png"));
            Image wQueenScaled = wQueen.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
            pieces.add(new PieceImage(wQueenScaled, 3 * 50, 7 * 50, 0, 3));

            BufferedImage wKing = ImageIO.read(getClass().getClassLoader().getResource("ressources/WhiteKing.png"));
            Image wKingScaled = wKing.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
            pieces.add(new PieceImage(wKingScaled, 4 * 50, 7 * 50, 0, 4));

            BufferedImage wPawn = ImageIO.read(getClass().getClassLoader().getResource("ressources/WhitePawn.png"));
            Image wPawnScaled = wPawn.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
            for (int i = 0; i < 8; i++) {
                pieces.add(new PieceImage(wPawnScaled, i * 50, 6 * 50, 1, i));
            }

            BufferedImage bRook = ImageIO.read(getClass().getClassLoader().getResource("ressources/BlackRook.png"));
            Image bRookScaled = bRook.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
            pieces.add(new PieceImage(bRookScaled, 0 * 50, 0 * 50, 7, 0));
            pieces.add(new PieceImage(bRookScaled, 7 * 50, 0 * 50, 7, 7));

            BufferedImage bKnight = ImageIO.read(getClass().getClassLoader().getResource("ressources/BlackKnight.png"));
            Image bKnightSCaled = bKnight.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
            pieces.add(new PieceImage(bKnightSCaled, 1 * 50, 0 * 50, 7, 1));
            pieces.add(new PieceImage(bKnightSCaled, 6 * 50, 0 * 50, 7, 6));

            BufferedImage bBishop = ImageIO.read(getClass().getClassLoader().getResource("ressources/BlackBishop.png"));
            Image bBishopScaled = bBishop.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
            pieces.add(new PieceImage(bBishopScaled, 2 * 50, 0 * 50, 7, 2));
            pieces.add(new PieceImage(bBishopScaled, 5 * 50, 0 * 50, 7, 5));

            BufferedImage bQueen = ImageIO.read(getClass().getClassLoader().getResource("ressources/BlackQueen.png"));
            Image bQueenScaled = bQueen.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
            pieces.add(new PieceImage(bQueenScaled, 3 * 50, 0 * 50, 7, 3));

            BufferedImage bKing = ImageIO.read(getClass().getClassLoader().getResource("ressources/BlackKing.png"));
            Image bKingScaled = bKing.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
            pieces.add(new PieceImage(bKingScaled, 4 * 50, 0 * 50, 7, 4));

            BufferedImage bPawn = ImageIO.read(getClass().getClassLoader().getResource("ressources/BlackPawn.png"));
            Image bPawnScaled = bPawn.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
            for (int i = 0; i < 8; i++) {
                pieces.add(new PieceImage(bPawnScaled, i * 50, 1 * 50, 6, i));
            }
        } catch (IOException ex) {
            System.out.println("Eror reading image");

        }
    }

    private void paintPieces(Graphics g) {
        for (PieceImage piece : pieces) {
            piece.paint(g);
        }
    }

    void flipBoard() {
        for (PieceImage piece : pieces) {
            piece.setRow(7 - piece.getRow());
            piece.setCol(7 - piece.getCol());
            piece.setX(350 - piece.getX());
            piece.setY(350 - piece.getY());
        }
    }

    PieceImage getPieceAtSquare(int row, int col) {
        for (PieceImage piece : pieces) {
            if (piece.getCol() == col && piece.getRow() == row) {
                return piece;
            }
        }
        return null;
    }

    void addPieceAtSquare(PieceImage piece){
        System.out.println("add piece");
        pieces.add(piece);
        
    }
    
    void removePieceAtSquare(int row, int col) {
        System.out.println("removed" + row + " : " +  col);
        pieces.remove(getPieceAtSquare(row, col));
    }

    void removePiece(PieceImage piece){
        System.out.println("remove");
        pieces.remove(piece);
    }
    
    public Square getKingSquare(Player player) {
        BoardFile[] files = BoardFile.values();
        BoardRank[] ranks = BoardRank.values();
        for (BoardFile file : files) {
            for (BoardRank rank : ranks) {
                Square sq = new Square(file, rank);
                Piece p = game.getPieceAtSquare(sq);
                if (p instanceof King && p.getOwner() == player) {
                    return sq;
                }
            }
        }
        return null;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        jButton1.setText("Undo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(322, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jButton1)
                .addContainerGap(245, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        input.undoMove();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
