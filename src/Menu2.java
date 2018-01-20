import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Objects;

public class Menu2 extends JFrame implements ActionListener{
    private JButton start;
    private JLabel name_label,level_label,timer_label;
    private JTextField name1,name2;
    private JComboBox level;
    private String player_name1,player_name2;
    private Game game;
    private int lev;
    public Menu2(){
        super("Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setPreferredSize(new Dimension(300,300));
        setLocation(600,300);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridy=0;
        c.weightx=1;
        c.weighty=1;
        c.gridwidth=2;
        name_label = new JLabel("Podaj imię");
        name_label.setHorizontalAlignment(JLabel.CENTER);
        add(name_label,c);

        c.gridy++;
        c.gridwidth=1;
        name1 = new JTextField("");
        name1.setPreferredSize(new Dimension(100,20));
        name1.addActionListener(this);
        add(name1,c);

        name2 = new JTextField("");
        name2.setPreferredSize(new Dimension(100,20));
        name2.addActionListener(this);
        add(name2,c);

        c.gridy++;
        c.gridwidth=2;
        level_label = new JLabel("Wybierz poziom");
        level_label.setHorizontalAlignment(JTextField.CENTER);
        add(level_label,c);

        String[] lev_ = new String[4];
        lev_[0]="+";
        lev_[1]="+ i -";
        lev_[2]=" +, - i *";
        lev_[3]="+, -, *, /";
        level = new JComboBox(lev_);
        level.setSelectedIndex(0);
        level.addActionListener(this);
        c.gridy++;
        add(level,c);

        c.gridy++;
        start = new JButton("Start");
        start.addActionListener(this);
        c.gridy++;
        start.setHorizontalAlignment(JButton.CENTER);
        add(start,c);

        pack();

        lev=0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source==name1){
            player_name1 = name1.getText();
        }
        if(source==name2){
            player_name2=name2.getText();
        }
        if(source==level) {
            lev = level.getSelectedIndex();
        }
        if(source==start){
            player_name1 = name1.getText();
            player_name2 = name2.getText();
            if((Objects.equals(player_name1,new String(""))||Objects.equals(player_name2,null))||(Objects.equals(player_name2,new String(""))||Objects.equals(player_name2,null))) {
                JOptionPane.showMessageDialog(super.rootPane, "Podaj imię!");
            }
            else{
                Game2 game2 = new Game2(player_name1,player_name2,lev,this);
            }

        }
    }
}
