package alina.zhirova;

import org.w3c.dom.css.RGBColor;
import sun.plugin2.util.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StartForm extends JFrame {

    public StartForm() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel jBottomPanel = new JPanel();
        jBottomPanel.setLayout(new GridLayout());

        JButton jbSymbolX = new JButton();
        jbSymbolX.setIcon(new ImageIcon("src/images/cymbol_x.jpg"));
        jBottomPanel.add(jbSymbolX);

        JButton jbSymbol0 = new JButton();
        jbSymbol0.setIcon(new ImageIcon("src/images/cymbol_0.jpg"));
        jBottomPanel.add(jbSymbol0);

        JPanel jTextPanel = new JPanel();
        jTextPanel.setLayout(new GridLayout());

        JLabel startText = new JLabel("CHOOSE:");
        startText.setOpaque(true);
        startText.setBackground(new Color(241, 228, 17));

        Font curFont = new Font("Times new roman", Font.BOLD, 100);
        startText.setFont(curFont);
        startText.setForeground(new Color(0, 22, 187));
        startText.setHorizontalAlignment(JLabel.CENTER);
        jTextPanel.add(startText);

        add(jTextPanel, BorderLayout.NORTH);
        add(jBottomPanel, BorderLayout.CENTER);

        jbSymbolX.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Map.humanSymbol = Map.CELL_X;
                Map.aiSymbol = Map.CELL_0;
                dispose();
                new TicTacToe();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                jbSymbolX.setIcon(new ImageIcon("src/images/cymbol_x_focus.jpg"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jbSymbolX.setIcon(new ImageIcon("src/images/cymbol_x.jpg"));
            }
        });

        jbSymbol0.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Map.humanSymbol = Map.CELL_0;
                Map.aiSymbol = Map.CELL_X;
                dispose();
                new TicTacToe();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                jbSymbol0.setIcon(new ImageIcon("src/images/cymbol_0_focus.jpg"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jbSymbol0.setIcon(new ImageIcon("src/images/cymbol_0.jpg"));
            }
        });

        setVisible(true);
    }



}
