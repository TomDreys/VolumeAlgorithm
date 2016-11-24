package Entites;

import volumealgorithm.DisplayManager;


public class Slider {
    
    public int startx,starty,endx,endy,thickness;
    public int r,g,b;
    
    public Slider(int r, int g, int b)
    {
        this.startx = 0;
        this.starty = 400;
        this.endx = DisplayManager.getWIDTH();
        this.endy = 400;
        this.thickness = 3;
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public void updateSlider(int y)
    {
        starty = y;
        endy = y;
    }
    
}
