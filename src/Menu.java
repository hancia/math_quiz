import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Objects;

public class Menu extends JFrame implements ActionListener{
    private JButton start;
    private JLabel name_label,level_label,timer_label;
    private JTextField name;
    private JComboBox level;
    private String player_name;
    private Game game;
    private int lev;
    private JCheckBox timer;
    private boolean count_time;
    public Menu(){
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
        name_label = new JLabel("Podaj imię");
        add(name_label,c);

        c.gridy++;
        name = new JTextField("");
        name.setPreferredSize(new Dimension(100,20));
        name.addActionListener(this);
        add(name,c);

        c.gridy++;
        level_label = new JLabel("Wybierz poziom");
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
        timer_label=new JLabel("Gra na czas");
        add(timer_label,c);
        c.gridy++;
        timer=new JCheckBox();
        timer.addActionListener(this);
        add(timer,c);

        start = new JButton("Start");
        start.addActionListener(this);
        c.gridy++;
        add(start,c);

        pack();

        lev=0;
        count_time=false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source==name){
            player_name = name.getText();
        }
        if(source==level) {
            lev = level.getSelectedIndex();
        }
        if(source==start){
            player_name = name.getText();
            if(Objects.equals(player_name,new String(""))||Objects.equals(player_name,null)) {
                JOptionPane.showMessageDialog(super.rootPane, "Podaj imię!");
            }
            else{
                try {
                    game = new Game(player_name,lev,this,count_time);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                this.setVisible(false);
            }

            }
            if(source==timer){
                if(timer.isSelected()){
                    count_time=true;
                    System.out.println("no i elo");
                }
                else{
                    count_time=false;
                }
            }
        }
    }
