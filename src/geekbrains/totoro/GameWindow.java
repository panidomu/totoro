package geekbrains.totoro;

import javax.imageio.ImageIO;
import javax.swing.*;  //добавилась эта строка после того, как ниже дописали extends JFrame
import java.awt.*;
import java.io.IOException;

public class GameWindow extends JFrame {  //extends JFrame дописываем руками и жмем Enter -> выше добавилась строка mport javax.swing.*;
    private static GameWindow game_window;
    private static long last_frame_time;
    private static Image totoro_bg;
    private static Image game_over;
    private static Image totoro_sticker;
   private static float  left = -124;
   private static float top = 100;
   private static float totoro_v =150;



    public static void main(String[] args) throws IOException {
    totoro_bg = ImageIO.read(GameWindow.class.getResourceAsStream("totoro_bg.jpg"));
    game_over =ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
    totoro_sticker=ImageIO.read(GameWindow.class.getResourceAsStream("totoro_sticker.png"));

        game_window = new GameWindow();
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_window.setLocation(200, 100);
        game_window.setSize(1000, 600);
        game_window.setResizable(false);
        last_frame_time = System.nanoTime();
        GameField game_field = new GameField();
        game_window.add(game_field);
        game_window.setVisible(true);
    }

    private static void onRepaint(Graphics g) {
        long current_time = System.nanoTime();
        float delta_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;
       //top = top + totoro_v * delta_time;
       left = left + totoro_v * delta_time;
     g.drawImage(totoro_bg, 0, 0, null);
    g.drawImage(totoro_sticker, (int) left, (int) top, null);
       //if (top > game_window.getHeight())
      if (left > game_window.getWidth ())
        g.drawImage(game_over, 0, 0, null);

    }
    private static class GameField extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint ();
        }
    }
}