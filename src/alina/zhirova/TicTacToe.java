package alina.zhirova;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TicTacToe extends JFrame{

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 650);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel jBottomPanel = new JPanel();
        jBottomPanel.setLayout(new GridLayout());
        JButton jbStart = new JButton("Start new Game");
        JButton jbExit = new JButton("Exit");
        JButton jbChoosePlayer = new JButton("Choose player");

        GameField gf = new GameField();

        jBottomPanel.add(jbStart);
        jBottomPanel.add(jbExit);
        jBottomPanel.add(jbChoosePlayer);
        add(gf, BorderLayout.CENTER);
        add(jBottomPanel, BorderLayout.SOUTH);

        jbStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Map.clearMap();
                Map.clearRating();
                if (Map.humanSymbol == Map.CELL_0) {
                    GameLogic.aiTurn();
                }
                repaint();
            }
        });

        jbExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });

        jbChoosePlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
                new StartForm();
            }
        });

        setVisible(true);
    }


}
