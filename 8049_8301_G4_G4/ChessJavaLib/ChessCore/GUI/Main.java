/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessCore.GUI;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author DELL
 */
public class Main {
    public static void main(String args[]){
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(500, 500));
        
        JPanel panel = new BoardPanel();
        frame.add(panel);
        frame.setVisible(true);
    }
}
