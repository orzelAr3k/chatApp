package guis.custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

/**
 * The CustomDotIcon class is used to represent notification of new message from
 * user.
 * 
 * @author Arkadiusz Orzel
 */
public class CustomDotIcon implements Icon {
    private int size;
    private Color color;

    /**
     * The CustomDotIcon constructor creates a custom icon that is used to represent
     * the notification.
     * 
     *
     * @param size  Set the size of the icon
     * @param color Set the color of the dot
     *
     */
    public CustomDotIcon(int size, Color color) {
        this.size = size;
        this.color = color;
    }

    /**
     * The paintIcon function is called by the JLabel to paint the icon.
     * 
     *
     * @param c Determine the component that is being painted
     * @param g Draw the icon
     * @param x Set the x coordinate of the icon's top left corner
     * @param y Set the y coordinate of the oval
     *
     */
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    /**
     * The getIconWidth function returns the width of the icon.
     * 
     * @return The width of the icon
     *
     */
    @Override
    public int getIconWidth() {
        return size;
    }

    /**
     * The getIconHeight function returns the height of the icon.
     * 
     * @return The height of the icon
     *
     */
    @Override
    public int getIconHeight() {
        return size;
    }
}