/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessCore.GUI;

import ChessCore.ChessGame;
import ChessCore.PawnPromotion;
import static ChessCore.PawnPromotion.Bishop;
import static ChessCore.PawnPromotion.Knight;
import static ChessCore.PawnPromotion.Queen;
import static ChessCore.PawnPromotion.Rook;
import ChessCore.Player;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PromotedPieceFactory {

    public PromotedPieceFactory() {
    }

    public PieceImage createPromotedPiece(ChessGame game, int row, int col, PawnPromotion pawnPromotion) {
        if (pawnPromotion.equals(Bishop)) {
            return createBishopPiece(game, row, col);
        }
        if (pawnPromotion.equals(Rook)) {
            return createRookPiece(game, row, col);
        }
        if (pawnPromotion.equals(Knight)) {
            return createKnightPiece(game, row, col);
        }
        if (pawnPromotion.equals(Queen)) {
            return createQueenPiece(game, row, col);
        }
        return null;
    }

    private PieceImage createBishopPiece(ChessGame game, int row, int col) {
        BufferedImage bishop;
        try {
            if (game.getWhoseTurn().equals(Player.WHITE)) {
                bishop = ImageIO.read(getClass().getClassLoader().getResource("ressources/WhiteBishop.png"));
                Image bBishopScaled = bishop.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
                PieceImage promotedImage = new PieceImage(bBishopScaled, col * 50, (7 - row) * 50, row, col);
                return promotedImage;
            } else {
                bishop = ImageIO.read(getClass().getClassLoader().getResource("ressources/BlackBishop.png"));
                Image bBishopScaled = bishop.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
                PieceImage promotedImage = new PieceImage(bBishopScaled, (7-col) * 50, (row) * 50, 7 - row, 7-col);
                return promotedImage;
            }
        } catch (IOException ex) {
            System.out.println("invalid promotion");
        }
        return null;
    }

    private PieceImage createRookPiece(ChessGame game, int row, int col) {
        BufferedImage rook;
        try {
            if (game.getWhoseTurn().equals(Player.WHITE)) {
                rook = ImageIO.read(getClass().getClassLoader().getResource("ressources/WhiteRook.png"));
                Image RookScaled = rook.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
                PieceImage promotedImage = new PieceImage(RookScaled, col * 50, (7 - row) * 50, row, col);
                return promotedImage;
            } else {
                rook = ImageIO.read(getClass().getClassLoader().getResource("ressources/BlackRook.png"));
                Image RookScaled = rook.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
                PieceImage promotedImage = new PieceImage(RookScaled, (7-col) * 50, (row) * 50, 7-row, 7-col);
                return promotedImage;
            }

        } catch (IOException ex) {
            System.out.println("invalid promotion");
        }
        return null;
    }

    private PieceImage createKnightPiece(ChessGame game, int row, int col) {
        BufferedImage knight;
        try {
            if (game.getWhoseTurn().equals(Player.WHITE)) {
                knight = ImageIO.read(getClass().getClassLoader().getResource("ressources/WhiteKnight.png"));
                Image KnightScaled = knight.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
            PieceImage promotedImage = new PieceImage(KnightScaled, col * 50, (7 - row) * 50, row, col);
            return promotedImage;
            } else {
                knight = ImageIO.read(getClass().getClassLoader().getResource("ressources/BlackKnight.png"));
                Image KnightScaled = knight.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
            PieceImage promotedImage = new PieceImage(KnightScaled, (7-col) * 50, row * 50, (7-row), 7-col);
            return promotedImage;
            }
        } catch (IOException ex) {
            System.out.println("invalid promotion");
        }
        return null;
    }

    private PieceImage createQueenPiece(ChessGame game, int row, int col) {
        BufferedImage queen;
        try {
            if (game.getWhoseTurn().equals(Player.WHITE)) {
                queen = ImageIO.read(getClass().getClassLoader().getResource("ressources/WhiteQueen.png"));
                Image QueenScaled = queen.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
            PieceImage promotedImage = new PieceImage(QueenScaled, col * 50, (7 - row) * 50, row, col);
            return promotedImage;
            } else {
                queen = ImageIO.read(getClass().getClassLoader().getResource("ressources/BlackQueen.png"));
                Image QueenScaled = queen.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
            PieceImage promotedImage = new PieceImage(QueenScaled, (7-col) * 50, row * 50, (7-row), 7-col);
            return promotedImage;
            }
        } catch (IOException ex) {
            System.out.println("invalid promotion");
        }
        return null;
    }
}
