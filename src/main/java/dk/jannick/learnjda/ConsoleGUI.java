package dk.jannick.learnjda;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class ConsoleGUI {
    private JFrame frame;
    private JTextPane textPane;
    private StyledDocument document;

    public void openGUI() {
        frame = new JFrame("ZentrixBOT Console");

        textPane = new JTextPane();
        textPane.setBackground(Color.BLACK);
        textPane.setFont(new Font("Tahoma", Font.PLAIN, 16));

        document = textPane.getStyledDocument(); // Initialize the document

        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                appendText(String.valueOf((char) b), Color.LIGHT_GRAY);
            }
        }));

        frame.add(new JScrollPane(textPane));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(new Dimension(610, 610));
        frame.setVisible(true);
    }

    public void log(String message, Color color) {
        appendText(message + "\n", color);
    }

    private void appendText(String text, Color color) {
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setForeground(attributes, color);
        try {
            document.insertString(document.getLength(), text, attributes);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
