package volumealgorithm;

import Entites.Bottle;
import Entites.Slider;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static volumealgorithm.Painter.*;

public class DisplayManager extends JPanel implements MouseMotionListener, MouseListener,KeyListener{
    
    public static final int WIDTH = 700, HEIGHT = 900;
    
    //initialize display elements
    public JFrame frame = new JFrame();
    public Bottle bottle1 = new Bottle("res/Coke.png");
    public Slider slider1 = new Slider(0,0,255);
    public Slider slider2 = new Slider(255,0,0);
    public Slider slider3 = new Slider(0, 255, 0);
    public JTextField textbox1 = new JTextField("500", 8);
    public JLabel label1 = new JLabel();
    DefaultComboBoxModel model = new DefaultComboBoxModel();
    JComboBox<String> comboBox = new JComboBox<>();
    
    public int mouseY;

    public void initDisplay()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        
        model.addElement("j20.png");
        model.addElement("Coke.png");
        model.addElement("smirnoff.png");
        model.addElement("bottleimage.png");
        comboBox.setModel(model);
        
        this.add(comboBox);
        
        textbox1.setAlignmentX(LEFT_ALIGNMENT);
        textbox1.setAlignmentY(BOTTOM_ALIGNMENT);
        this.add(textbox1);
        
        label1.setVerticalAlignment(JLabel.TOP);
        label1.setHorizontalAlignment(JLabel.CENTER);
        this.add(label1);
        
        frame.add(this);
        frame.setVisible(true);
        
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        textbox1.addKeyListener(this);
        comboBox.addKeyListener(this);
        this.setFocusable(true);
        
        updateDisplay();
    }
    
    public void updateDisplay()
    {
        textbox1.setEditable(true);
        bottle1.setImage(comboBox.getSelectedItem().toString());
        label1.setText("PV: "+bottle1.pixelVolume+" MLV: "+bottle1.selectedVolume);
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        //draw graphic elements onto display
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        drawBottle(g2, this, bottle1);
        drawSlider(g2, slider1);
        drawSlider(g2, slider2);
        drawSlider(g2, slider3);
    }
    
    public void mouseMoved(MouseEvent e)
    {
        mouseY = e.getY();
    }
    
    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        //if leftMB clicked
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            bottle1.setCurrent(mouseY);
            slider1.updateSlider(mouseY);
            bottle1.updateBottle(textbox1.getText(), false);
        }
        
        updateDisplay();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        //if leftMB pressed
        if (key == KeyEvent.VK_A)
        {
            bottle1.setCurrent(mouseY);
            slider1.updateSlider(mouseY);
            bottle1.updateBottle(textbox1.getText(), false);
        }
        //if U key clicked
        if (key == KeyEvent.VK_U)
        {
            bottle1.setMax(mouseY);
            slider2.updateSlider(mouseY);
            bottle1.updateBottle(textbox1.getText(), true);
        }
        //if L key pressed
        if (key == KeyEvent.VK_L)
        {
            bottle1.setMin(mouseY);
            slider3.updateSlider(mouseY);
            bottle1.updateBottle(textbox1.getText(), true);
        }
        
        if (key == KeyEvent.VK_ENTER)
        {
            textbox1.setEditable(false);
            this.requestFocus();
        }
        
        updateDisplay();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    
    }
        

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

}
