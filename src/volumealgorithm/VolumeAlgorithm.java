package volumealgorithm;

public class VolumeAlgorithm {
    
    DisplayManager displayManager = new DisplayManager();

    public VolumeAlgorithm()
    {
        displayManager.initDisplay();
    }
    
    public static void main(String[] args) 
    {
        new VolumeAlgorithm();
    }
    
}
