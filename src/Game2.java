import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Random;

public class Game2 extends JFrame implements ActionListener {
    private String player_name1,player_name2;
    private int level, good_result,questions1,questions2,points1,points2;
    private JButton start, next;
    private JLabel operation,current;
    private JTextField result;
    private Random generator;
    private JLabel point_bar1,point_bar2;
    private PointsPanel Panel;
    private Menu2 main_menu;
    private boolean flag;
    public Game2(String name1, String name2, int lev,Menu2 mainm) {
        super("Quiz");
        player_name1 = name1;
        player_name2=name2;
        level = lev;
        main_menu=mainm;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setPreferredSize(new Dimension(300, 400));
        setLocation(600, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        c.weighty = 1;
        c.weightx = 1;
        c.gridwidth=2;
        start = new JButton("Start");
        start.addActionListener(this);
        start.setHorizontalAlignment(JButton.CENTER);
        add(start, c);

        c.gridy++;
        current = new JLabel("");
        current.setHorizontalAlignment(JLabel.CENTER);
        add(current,c);

        c.gridy++;
        operation = new JLabel("");
        operation.setHorizontalAlignment(JLabel.CENTER);
        add(operation, c);

        c.gridy++;
        //c.fill=GridBagConstraints.HORIZONTAL;
        result = new JTextField("");
        result.setPreferredSize(new Dimension(100, 20));
        //result.setSize(100,20);
        result.addActionListener(this);
        add(result, c);

        c.gridy++;
        c.fill=GridBagConstraints.NONE;
        next = new JButton("Dalej");
        next.addActionListener(this);
        next.setHorizontalAlignment(JButton.CENTER);
        add(next, c);

        c.gridy++;
        point_bar1 = new JLabel(player_name1+ " 0/0/0");
        point_bar1.setHorizontalAlignment(JLabel.CENTER);
        add(point_bar1,c);

        c.gridy++;
        point_bar2 = new JLabel(player_name2+ " 0/0/0");
        point_bar2.setHorizontalAlignment(JLabel.CENTER);
        add(point_bar2,c);


        c.gridy++;
        c.fill=GridBagConstraints.HORIZONTAL;
        Panel = new PointsPanel();
        add(Panel,c);

        pack();

        generator = new Random();
        questions1=0;
        questions2=0;
        points1=0;
        points2=0;

        flag=false;
    }

    private void show_operation(int lev, int num1, int num2) {
        String level = "";
        switch (lev) {
            case 0: {
                level = "+";
                good_result = num1 + num2;
                break;
            }
            case 1: {
                level = "-";
                good_result = num1 - num2;
                break;
            }
            case 2: {
                level = "*";
                good_result = num1 * num2;
                break;
            }
            case 3: {
                level = "/";
                good_result = num1 / num2;
                break;
            }
        }
        operation.setText(Integer.toString(num1) + level + Integer.toString(num2));
    }

    private void get_operation() {
        int op;
        if(level>0)
            op = generator.nextInt(level+1);
        else op=0;
        int num1=0,num2=0;
        switch(op){
            case 0:{
                num1=generator.nextInt(20);
                num2=generator.nextInt(20);
                break;
            }
            case 1:{
                num1=generator.nextInt(20);
                num2=generator.nextInt(20);
                break;
            }
            case 2:{
                num1=generator.nextInt(20);
                num2=generator.nextInt(20);
                break;
            }
            case 3:{
                num1=generator.nextInt(400);
                num2=generator.nextInt(20);
                break;
            }
        }
        show_operation(op,num1,num2);
    }

    public void BackToMenu(){
        this.dispose();
        main_menu.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == start) {
            questions1=25;
            questions2=25;
            points1=0;
            get_operation();
            point_bar1.setText(player_name1+" "+Integer.toString(points1)+"/0/25");

            points2=0;
            get_operation();
            point_bar2.setText(player_name2+" "+Integer.toString(points2)+"/0/25");
            start.setEnabled(false);
            current.setText(player_name1);
        }
        if(source == next){
            if(flag){
                current.setText(player_name1);
            }
            else{
                current.setText(player_name2);
            }
            int points,questions;
            String player_name;
            JLabel point_bar;
            if(flag){
                player_name=player_name2;
                points=points2;
                point_bar=point_bar2;
                questions=questions2;
            }
            else{
                player_name=player_name1;
                points=points1;
                point_bar=point_bar1;
                questions=questions1;
            }
            String temp = result.getText();
            if(!(Objects.equals(temp,null)||Objects.equals(temp,new String("")))){
                int answer = Integer.parseInt(temp);
                if(answer==good_result) {
                    points++;
                    Panel.change_color(true);
                    Panel.setVisible(true);
                }
                else {
                    Panel.change_color(false);
                    Panel.setVisible(true);
                    if(points>0) points--;
                }
            } else {
                Panel.change_color(false);
                Panel.setVisible(true);
            }
            if(questions>0) {
                point_bar.setText(player_name+" "+Integer.toString(points)+"/"+Integer.toString(26-questions)+"/25");
                get_operation();
                questions--;
            }
            else {
                if(points1>points2) JOptionPane.showMessageDialog(super.rootPane, "Wygrywa gracz: "+player_name1);
                else
                    JOptionPane.showMessageDialog(super.rootPane, "Wygrywa gracz: "+player_name2);
                BackToMenu();
            }
            if(flag){
                player_name2=player_name;
                points2=points;
                point_bar2=point_bar;
                questions2=questions;
            }
            else{
                player_name1=player_name;
                points1=points;
                point_bar1=point_bar;
                questions1=questions;
            }
            flag=!(flag);
        }
    }

}
