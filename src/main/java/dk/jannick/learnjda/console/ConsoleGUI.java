package dk.jannick.learnjda.console;

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

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Customize the scrollbar appearance
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());

        frame.add(scrollPane);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(new Dimension(630, 630));
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
