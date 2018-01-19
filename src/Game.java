import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Game extends JFrame implements ActionListener {
    private String player_name,bestname;
    private int level, good_result,questions,points,bestscore,z,startclick;
    private JButton start, next,highscores, clear_highscores;
    private JLabel operation;
    private JTextField result;
    private Random generator;
    private JLabel point_bar,time;
    private PointsPanel Panel;
    private Score[] scores;
    private Menu main_menu;
    private Component parentComponent;
    private boolean run,count_time;
    public Game(String name, int lev,Menu mainm,boolean time_t) throws FileNotFoundException {
        super("Quiz");
        player_name = name;
        level = lev;
        main_menu=mainm;
        count_time=time_t;

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
        add(start, c);


        c.gridy++;
        operation = new JLabel("");
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
        add(next, c);

        c.gridy++;
        point_bar = new JLabel("0/0/0");
        add(point_bar,c);


        time = new JLabel("0");
        time.setVisible(count_time);
        c.gridy++;
        add(time,c);

        c.gridy++;
        highscores = new JButton("Highscores");
        highscores.addActionListener(this);
        add(highscores,c);

        c.gridy++;
        clear_highscores = new JButton("Wyczyść najlepsze");
        clear_highscores.addActionListener(this);
        add(clear_highscores,c);

        c.gridy++;
        c.fill=GridBagConstraints.HORIZONTAL;
        Panel = new PointsPanel();
        add(Panel,c);

        pack();

        generator = new Random();
        questions=0;
        points=0;
        parentComponent=super.rootPane;


        run=true;
        z=0;
        startclick=0;

        scores = new Score[4];
        for(int i=0; i<4; i++) scores[i]=new Score("None",0);
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

    private void get_best(){
        String best="";
        String best_split[]=null;
        Scanner odczyt=null;
        try {
            odczyt = new Scanner(new File("highscore.txt"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        do {
            best = odczyt.nextLine();
            best_split=best.split(" ");
        }while(!Objects.equals(best_split[1],Integer.toString(level))&&odczyt.hasNextLine());
        bestname=best_split[3];
        bestscore=Integer.parseInt(best_split[5]);
        odczyt.close();
    }
    private void get_scores(){
        Scanner read=null;
        String best="";
        String best_split[]=null;
        int temp=0;
        try {
            read = new Scanner(new File("highscore.txt"));
            while(read.hasNextLine()) {
                best = read.nextLine();
                best_split = best.split(" ");
                scores[temp].name = best_split[3];
                scores[temp].score = Integer.parseInt(best_split[5]);
                temp++;
            }

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        read.close();
    }

    private void write_best(){
        scores[level].name=player_name;
        scores[level].score=points;
        PrintWriter zapis=null;
        try {
            zapis = new PrintWriter(new BufferedWriter(new FileWriter("highscore.txt", true)));

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0; i<4; i++) {
            zapis.println("Level: "+i+" Imię: "+scores[i].name+" Punkty: "+scores[i].score);
        }
        zapis.close();
    }

    private void clear_best(){
        PrintWriter zapis=null;
        try {
            zapis = new PrintWriter("highscore.txt");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            }
        for(int i=0; i<4; i++) {
            zapis.println("Level: "+i+" Imię: None Punkty: "+0);
        }
        zapis.close();
    }
    private void clear_file(){
        PrintWriter zapis=null;
        try {
            zapis = new PrintWriter("highscore.txt");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        zapis.close();
    }
    public void BackToMenu(){
        this.dispose();
        main_menu.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == start) {
            if(count_time) {
                Instant t1 = Instant.now();
                new Thread(new Runnable() {
                    public void run() {
                        run = true;
                        z = 0;
                        Thread thisThread = java.lang.Thread.currentThread();
                        while (run) {
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    Instant t2 = Instant.now();
                                    time.setText(Integer.toString((int) Duration.between(t1, t2).toSeconds()));
                                }
                            });
                            try {
                                java.lang.Thread.sleep(1000);
                            } catch (Exception e) {
                            }
                            z++;
                            if (z == 60) {
                                JOptionPane.showMessageDialog(parentComponent, "Koniec czasu :/");
                                run = false;
                                BackToMenu();
                                thisThread.interrupt();
                            }
                            if (thisThread.isInterrupted()) return;
                        }
                    }
                }).start();
            }

            questions=25;
            points=0;
            get_operation();
            point_bar.setText(Integer.toString(points)+"/"+Integer.toString(26-questions)+"/25");
            start.setEnabled(false);
        }
        if(source == next){
            String temp = result.getText();
            if(!(Objects.equals(temp,null)||Objects.equals(temp,new String("")))){
                int answer = Integer.parseInt(temp);
                if(answer==good_result) {
                    points++;
                    Panel.change_color(true);
                    Panel.setVisible(true);
                    JOptionPane.showMessageDialog(super.rootPane, "Dobra odpowiedź");
                }
                else {
                    Panel.change_color(false);
                    Panel.setVisible(true);
                    if(points>0) points--;
                    JOptionPane.showMessageDialog(super.rootPane, "Zła odpowiedź");
                }
            } else {
                Panel.change_color(false);
                Panel.setVisible(true);
                JOptionPane.showMessageDialog(super.rootPane, "Brak odpowiedzi");
            }
            if(questions>0) {
                point_bar.setText(Integer.toString(points)+"/"+Integer.toString(26-questions)+"/25");
                get_operation();
                questions--;
            }
            else {
                JOptionPane.showMessageDialog(super.rootPane, "Koniec gry, liczba zdobytych punktów: " + Integer.toString(points));
                get_best();
                if ((points > bestscore)||(Objects.equals(bestname,new String("None")))) {
                    JOptionPane.showMessageDialog(super.rootPane, "Nowy najlepszy wynik! " + Integer.toString(points));
                    get_scores();
                    clear_file();
                    write_best();
                }
                BackToMenu();
            }
        }
        if(source==highscores){
            get_best();
            System.out.println(bestname);
            if(!Objects.equals(bestname,new String("None")))
                JOptionPane.showMessageDialog(super.rootPane, "Najlepszy wynik dla tego poziomu: Gracz: "+bestname+" Punkty: "+bestscore);
            else
                JOptionPane.showMessageDialog(super.rootPane, "Nie ma najlepszego wyniku dla tego poziomu");
        }
        if(source==clear_highscores){
            clear_best();
        }
    }

}
