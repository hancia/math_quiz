import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main_menu extends JFrame implements ActionListener {
    private JButton forone,fortwo;
    private JLabel label;
    public Main_menu(){
        super("Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setPreferredSize(new Dimension(300,100));
        setLocation(600,300);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridy=0;
        c.gridwidth=2;
        c.weightx=1;
        c.weighty=1;
        c.fill=GridBagConstraints.BOTH;
        label = new JLabel("Wybierz tryb gry:");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label,c);

        c.gridy++;
        c.gridwidth=1;
        c.fill=GridBagConstraints.NONE;
        forone = new JButton("Jeden Gracz");
        forone.addActionListener(this);
        add(forone,c);

        fortwo = new JButton("Dwóch graczy");
        fortwo.addActionListener(this);
        add(fortwo,c);
        pack();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source==forone){
            Menu menu = new Menu();
            setVisible(false);
        }
        if(source==fortwo){
            Menu2 menu = new Menu2();
            setVisible(false);
        }
    }
}
