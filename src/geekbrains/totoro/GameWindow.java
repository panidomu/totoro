package geekbrains.totoro;

import javax.imageio.ImageIO;
import javax.swing.*;  //добавилась эта строка после того, как ниже дописали extends JFrame
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameWindow extends JFrame {  //extends JFrame дописываем руками и жмем Enter -> выше добавилась строка mport javax.swing.*;
    private static GameWindow game_window;
    private static long last_frame_time;
    private static Image totoro_bg;
    private static Image game_over;
    private static Image totoro_sticker;
    private static float left = -124;
    private static float top = 100;
    private static float totoro_v = 150; //сделала скорость перемещения поменьше


    public static void main(String[] args) throws IOException {
        totoro_bg = ImageIO.read(GameWindow.class.getResourceAsStream("totoro_bg.jpg"));
        game_over = ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
        totoro_sticker = ImageIO.read(GameWindow.class.getResourceAsStream("totoro_sticker.png"));

        game_window = new GameWindow();
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_window.setLocation(200, 100); //положение игрового поля на экране
        game_window.setSize(1000, 600); //размер игровог ополя
        game_window.setResizable(false);
        last_frame_time = System.nanoTime();
        GameField game_field = new GameField();
        game_field.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mousePressed(MouseEvent e) {  //метод mousePressed
                                            // будет вызываться нажатием на кнопку мыши
                                            //  super.mousePressed(e);
                                            int x = e.getX(); //получаем координату  x той точки, в которую указали мышью
                                            int y = e.getY(); //получаем координату  y той точки, в которую указали мышью
                                            float right = left + totoro_sticker.getWidth(null);  //координата правой границы
                                            float bottom = top + totoro_sticker.getHeight(null);
                                            boolean is_totoro_sticker = x >= left && x <= right && y >= top && y <=bottom; //is_totoro_sticker = true или false
                                            // в зависимости от того, попали ли мышью в каплю
                                            if(is_totoro_sticker) {
                                                top = -141; //перенесли каплю за границу окна
                                                left = (int) (Math.random() * (game_field.getWidth() - totoro_sticker.getWidth(null))); //перенсли новую каплю по горизонтали в случайное место
                                                // от 0 до (ширина поля - ширина капли).     (int) - приведение типа
                                                totoro_v  = totoro_v  + 20; //увеличение скорости  очередной "пробежки" Totoro
                                        }
                                    }


        )
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
        if (left > game_window.getWidth()) //если положение "хвостика"/левой границы Totoro больше ширины окна - выводим Game over
            g.drawImage(game_over, 0, 0, null);

    }

    private static class GameField extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}