
import javax.swing.*;
import java.awt.*;

public class PointsPanel extends JPanel {
    private boolean flag;
    private Color color;
    public PointsPanel() {

    }

    public void change_color(boolean flag_){
        if(flag_==true){
            color=Color.GREEN;
            repaint();
        }
        else{
            color=Color.RED;
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //g2d = (Graphics2D) g;
        if(color!=null) {
            g.setColor(color);
            g.fillOval(135, 0, 10, 10);
        }
    }
}
