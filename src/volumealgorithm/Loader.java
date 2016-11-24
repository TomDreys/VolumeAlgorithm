package volumealgorithm;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Loader {
    
    public BufferedImage LoadBufferedImage(String path)
    {   
        BufferedImage image = null;

        try 
        {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(path));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return image;
    }
    
}
