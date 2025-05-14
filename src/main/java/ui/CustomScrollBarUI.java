package ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

class CustomScrollBarUI extends BasicScrollBarUI {

    private static final Color TRACK_COLOR = new Color(75, 85, 110);
    private static final Color THUMB_COLOR = new Color(250, 161, 117);
    private static final Color THUMB_HIGHLIGHT_COLOR = new Color(247, 108, 38);
    private static final int SCROLLBAR_WIDTH = 5;

    /**
     * Metoda pentru configurarea culorilor scrollbarului
     */
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = THUMB_COLOR;
        this.trackColor = TRACK_COLOR;
    }

    /**
     * Metoda pentru colorarea butoanelor
     */
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(thumbColor);
        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);

        g2.dispose();
    }

    /**
     * Metoda pentru colorarea barii
     */
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(trackColor);
        g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);

        g2.dispose();
    }

    /**
     * Metoda pentru a crea butonul de decrease
     * @param orientation the orientation
     * @return
     */
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createCustomButton();
    }

    /**
     * Metoda pentru a crea butonul de increase
     * @param orientation the orientation
     * @return
     */
    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createCustomButton();
    }

    /**
     * Metoda care creaza butonul custom
     * @return
     */
    private JButton createCustomButton() {
        JButton button = new JButton();
        button.setBackground(new Color(250, 161, 117));
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    /**
     * Metoda care ia marimea minima
     * @return
     */
    @Override
    protected Dimension getMinimumThumbSize() {
        return new Dimension(SCROLLBAR_WIDTH, SCROLLBAR_WIDTH);
    }
}

