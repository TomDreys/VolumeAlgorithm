package Entites;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import volumealgorithm.DisplayManager;
import volumealgorithm.Loader;

public class Bottle extends JPanel{
    
    private Loader loader = new Loader();
    
    public String message = "Right-Click to set max level";
    public String shape = "Cylinder";
    
    public BufferedImage image;
    public ImageIcon imageIcon;
    
    private float relativeHeight;
    private float imageHeight, imageWidth;
    private float scale;
    public int scaledHeight, scaledWidth;
    public int x, y;
    public int maxLevel, minLevel, currentLevel;
    public int maxVolume;
    public float mlPerPixel, selectedVolume, pixelVolume;
    
    private boolean hasMaxLevel = false, hasMinLevel = false;
    
    private List<Trapezium> trapList = new ArrayList<>();
    
    public Bottle(String path)
    {        
        this.image = loader.LoadBufferedImage(path);
        this.imageIcon = new ImageIcon(getClass().getClassLoader().getResource(path));
        
        this.relativeHeight = DisplayManager.getHEIGHT() - 100;
        
        this.imageHeight = image.getHeight();
        this.imageWidth = image.getWidth();
        
        this.scale = relativeHeight/imageHeight;
        this.scaledWidth = (int) (imageWidth*scale);
        this.scaledHeight = (int) relativeHeight;
        
        this.x = (DisplayManager.getWIDTH()-scaledWidth)/2;
        this.y = 50;
    }
    
    //update the bottles values
    public void updateBottle(String volume, boolean changed)
    {
        //get the max volume
        maxVolume = Integer.parseInt(volume);
        
        //if the max/min sliders have changed, update the MLPP
        if (changed == true)
        {
            mlPerPixel = splitBottle(2, changed);
        }
        
        //if the current slider has changed, update the selected volume
        else if (changed == false)
        {
            selectedVolume = splitBottle(2, changed);
        }
    }
    
    //volume algorithm
    public float splitBottle(int xLimit, boolean getMLPP)
    {
        int lowerBound = 0;
        int upperBound = 0;
        
        int topFirstX = 0, topLastX = 0, topY = 0;
        int botFirstX = 0, botLastX = 0, botY = 0;
        
        boolean firstP, firstL = true;
        
        trapList.clear();
        
        //set lower bound to max level if getting MLPP
        if(getMLPP == true)
        {
            lowerBound = (int) ((maxLevel-50)/scale);
            upperBound = (int) ((minLevel-50)/scale);
        }
        //set lower bound to slider level if getting selected volume
        else if (getMLPP == false)
        {
            lowerBound = (int) ((currentLevel-50)/scale);
            upperBound = (int) ((minLevel-50)/scale);
        }
        
        //go through each line of pixels in the image
        for (int py = lowerBound; py <= upperBound; py++)
        {
            firstP = true;
            
            //go through each pixel on the current line 
            for (int px = 0; px < imageWidth; px++)
            {
                //get the colour for the current pixel
                Color c = new Color(image.getRGB(px, py), true);

                //test if pixel has alpha value
                if(c.getAlpha() != 0)
                {
                    //test to see if its the first line of the current trapezium being made
                    if (firstL == true)
                    {
                        //test to see if its the first pixel on the current line
                        if (firstP == true)
                        {
                            //set values for the first pixel on the first line of trapezium
                            topFirstX = px;
                            botFirstX = px;
                            topY = py;
                            firstP = false;
                        }
                        
                        //if its not the first pixel
                        else
                        {
                            //set values for current pixel of first line of the trapezium
                            topLastX = px;
                            botLastX = px;
                        }
                    }
                    
                    //if not the first line on current trapezium
                    else
                    {
                        //test to see if its the first pixel on the current line
                        if (firstP == true)
                        {
                            //set values for the first pixel on the current line of trapezium
                            botFirstX = px;
                            botY = py;
                            firstP = false;
                        }
                        
                        //if its not the first pixel
                        else
                        {
                            //set values for the current pixel on the current line of trapezium
                            botLastX = px;
                        }
                    }
                }
            }
            
            //finish trapezium off if on last line
            if (py == upperBound)
            {
                Trapezium trap = new Trapezium(topLastX-topFirstX, botLastX-botFirstX, botY-topY);
                trapList.add(trap);
            }
            
            //finish trapezium if difference in x is larger that the set limit
            //else if so that two duplicate trapeziums aren't made
            else if (Math.abs(topFirstX - botFirstX) > xLimit && firstL == false)
            {
                Trapezium trap = new Trapezium(topLastX-topFirstX, botLastX-botFirstX, botY-topY);
                trapList.add(trap);
                firstL = true;
                firstP = true;
            }
            
            //if a new trapezium hasn't been made then it is not on the first line
            else
            {
                firstL = false;
            }
            
        }
        
        //reset pixel volume so that it does not add with multiple selections 
        pixelVolume = 0;
            
        //go through each trapezium for the bottle
        for (Trapezium trap: trapList)
        {
            //get the volume of the trapezium and add it to the total volume
            pixelVolume += trap.getPixelVolume(shape);
        }
        
        //get MLPP only if limits have been set by user
        if (getMLPP == true && hasMaxLevel == true && hasMinLevel == true)
        {
            return maxVolume/pixelVolume; 
        }
        
        //get selected volume only if limits have been set by user
        else if (getMLPP == false && hasMaxLevel == true && hasMinLevel == true)
        {
            return  pixelVolume * mlPerPixel;
        }
            
        return 0;
    }
    
    //getters and setters
    
    public void setMax(int y)
    {
        maxLevel = y;
        hasMaxLevel = true;
    }
    
    public void setMin(int y)
    {
        minLevel = y;
        hasMinLevel = true;
    }
    
    public void setCurrent(int y)
    {
        currentLevel = y;
    }
    
    public void setImage(String url)
    {
        this.image = loader.LoadBufferedImage("res/"+url);
        this.imageIcon = new ImageIcon(getClass().getClassLoader().getResource("res/"+url));
        this.imageHeight = image.getHeight();
        this.imageWidth = image.getWidth();
        
        this.scale = relativeHeight/imageHeight;
        this.scaledWidth = (int) (imageWidth*scale);
        this.scaledHeight = (int) relativeHeight;
        
        this.x = (DisplayManager.getWIDTH()-scaledWidth)/2;
        this.y = 50;
    }

    public int getScaledWidth() {
        return scaledWidth;
    }

    public int getScaledHeight() {
        return scaledHeight;
    }
    
    
    
}
