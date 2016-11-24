package volumealgorithm;

import Entites.Bottle;
import Entites.Slider;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Painter {
    
    public static void drawBottle(Graphics g, DisplayManager DM, Bottle bottle)
    {
        ImageIcon imageIcon = bottle.imageIcon;
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(bottle.scaledWidth, bottle.scaledHeight,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        imageIcon.paintIcon(DM, g, bottle.x, bottle.y);
    }
    
    public static void drawSlider(Graphics2D g, Slider slider)
    {
        g.setStroke(new BasicStroke(slider.thickness));
        g.setColor(new Color(slider.r, slider.g, slider.b));
        g.drawLine(slider.startx, slider.starty, slider.endx, slider.endy);
    }
            
    
}
