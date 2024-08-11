package utility;

import javax.swing.JFrame;

public class Utility {
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 300;

    public static void setWindowSize(JFrame frame) {
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null); // Center the window
    }

    /*public static void setWindowSize(JDialog dialog) {
        dialog.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        dialog.setLocationRelativeTo(null); // Center the window
    }*/
}
