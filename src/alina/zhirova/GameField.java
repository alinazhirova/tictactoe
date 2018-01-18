package alina.zhirova;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameField extends JPanel {

    private int width;
    private int height;
    private int cellWidth;
    private int cellHeight;

    private static Image background;
    private static Image pictureX;
    private static Image picture0;


    public GameField() {
        background = new ImageIcon("src/images/back_1.jpg").getImage();
        pictureX = new ImageIcon("src/images/x_in_game.png").getImage();
        picture0 = new ImageIcon("src/images/0_in_game.png").getImage();

        Map.initMap();
        Map.initRating();

        if (Map.humanSymbol == Map.CELL_0) {
            GameLogic.aiTurn();
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e){
                int clX = e.getX()/cellWidth;
                int clY = e.getY()/cellHeight;

                if (GameLogic.isCellValid(clX, clY)) {
                    Map.curMap[clY][clX] = Map.humanSymbol;
                    GameLogic.aiTurn();
                    //GameLogic.printMap();
                    //GameLogic.printRating();
                }
                repaint();
            }
        });
    }


    public void sendMessage(Graphics g, String text, int textX, int textY) {
        g.setColor(new Color(94, 93, 92));
        g.fillRoundRect(width/4, height/3, width/2, height/3, width/8, height/8);
        g.setColor(new Color(251, 214, 198));
        g.setFont(new Font("Tahoma", 10, 40));
        g.drawString(text, textX, textY);
    }


    protected void paintComponent(Graphics g) { //вызывается для отрисовки JPanel.
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);

        width = getWidth();
        height = getHeight();
        cellWidth = width / Map.SIZE;
        cellHeight = height / Map.SIZE;

        for (int i = 0; i < Map.SIZE; i++) {
            for (int j = 0; j < Map.SIZE; j++) {
                if (Map.curMap[i][j] == Map.CELL_X) {
                    g.drawImage(pictureX, j*cellWidth, i*cellHeight, null);
                }
                else if (Map.curMap[i][j] == Map.CELL_0) {
                    g.drawImage(picture0, j*cellWidth, i*cellHeight, null);
                }
            }
        }

        if (GameLogic.checkWin(Map.humanSymbol)) {
            g.setColor(new Color(241, 9, 0));
            Graphics2D g2D = (Graphics2D)g;
            BasicStroke pen = new BasicStroke(40);
            g2D.setStroke(pen);
            int[] winCoordinates = GameLogic.gettingWinIndexes(Map.aiSymbol, cellWidth, cellHeight);
            g.drawLine(winCoordinates[0], winCoordinates[1], winCoordinates[2], winCoordinates[3]);
            sendMessage(g, "Human wins!", 7*width/20, height/2);
        }
        else if (GameLogic.checkWin(Map.aiSymbol)) {
            g.setColor(new Color(241, 9, 0));
            Graphics2D g2D = (Graphics2D)g;
            BasicStroke pen = new BasicStroke(40);
            g2D.setStroke(pen);
            int[] winCoordinates = GameLogic.gettingWinIndexes(Map.aiSymbol, cellWidth, cellHeight);
            g.drawLine(winCoordinates[0], winCoordinates[1], winCoordinates[2], winCoordinates[3]);
            sendMessage(g,"AI wins!",15*width/40, height/2);
        }
        else if (GameLogic.isMapFull()) {
            sendMessage(g,"Drawn game!", 3*width/10, height/2);
        }
    }


}
