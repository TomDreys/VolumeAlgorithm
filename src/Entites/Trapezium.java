package Entites;

public class Trapezium {
    
    public int lengthA, lenghtB, height;
    public double theta, pixelVolume;
    
    public Trapezium(int LengthA, int LengthB, int height)
    {
        this.lengthA = LengthA;
        this.lenghtB = LengthB;
        this.height = Math.abs(height);
        this.theta = Math.toDegrees(Math.atan(height/(Math.abs(lenghtB-lengthA)+1)/2)); //get base angle of trapezium
    }
    
    public float getPixelVolume(String shape)
    {
        float totalHeight = (float) (Math.tan(theta)*(lenghtB/2));
        float totalVolume, smallVolume;
        
        //get volume if bottle is cylinder
        if (shape == "Cylinder")
        {
            totalVolume = (float) ((Math.PI)*(lenghtB/2)*(totalHeight/3));
            smallVolume = (float) (Math.PI)*(lengthA/2)*((totalHeight-height)/3);
            return totalVolume - smallVolume;
        }
        
        //get volume if bottle is cuboid
        else if (shape == "Cuboid")
        {
            totalVolume = ((lenghtB*lenghtB)*totalHeight)/3;
            smallVolume = ((lengthA*lengthA)*(totalHeight-height))/3;
            return totalVolume-smallVolume;
        }
        
        //if its neither return 0
        else 
        {
            return 0;
        }
       
    }
    
}
