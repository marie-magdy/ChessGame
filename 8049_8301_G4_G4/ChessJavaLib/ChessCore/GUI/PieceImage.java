/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessCore.GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author DELL
 */
class PieceImage {

    private Image image;
    private int x, y;
    private int row, col;//old

    public PieceImage(Image image, int x, int y, int row, int col) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.col = col;
        this.row = row;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }    

    void paint(Graphics g) {
        g.drawImage(image, x, y, null);
    }
}
