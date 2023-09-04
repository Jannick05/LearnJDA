package dk.jannick.learnjda;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ConsoleGUI extends JFrame {
    public static void ConsoleGUI() {
        Console console = new Console();
        console.init();
        Point center = new Point(0, 0);
        console.getFrame().setLocation(center);
    }
}
class Console {
    final JFrame frame = new JFrame("ZentrixBOT Console");
    public Console() {
        JTextArea textArea = new JTextArea(24, 80);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.LIGHT_GRAY);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                textArea.append(String.valueOf((char) b));
            }
        }));
        frame.add(textArea);
    }
    public void init() {
        frame.pack();
        frame.setLayout(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public JFrame getFrame() {
        return frame;
    }
}
