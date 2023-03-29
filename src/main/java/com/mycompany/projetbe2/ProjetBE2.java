/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.projetbe2;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.*;
//import java.awt.events.*;
//import java.awt.FlowLayout;
//import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Arrays;
import java.util.Random ;

/**
 *
 * @author mohammedachrafelkhamlichi
 */

// On programme au début le jeu de vie sans utiliser les threads. Elle marche à 100%.
// Veuillez enlever le "/*" et mettre en commentaire la 2ème version pour essayer cette 1ère version 

public class ProjetBE2 extends JFrame {

    public int taille = 80;
    public boolean caseGrille[][] ;
    public JPanel cases[][] ;
    
    public ProjetBE2(){
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
        
        Random rnd = new Random();
        
        caseGrille = new boolean[taille][taille] ;
        cases = new JPanel[taille][taille] ;
        //setSize(500,500);
        setLayout(new GridLayout(taille,taille,1,1));
        
        for (int i = 0 ; i < taille ; i++){
            for (int j = 0 ; j < taille ; j++){
                caseGrille[i][j] = rnd.nextInt(100) < 30  ;
                //System.out.println(caseGrille[i][j]);
                cases[i][j] = new JPanel() ;
                if (caseGrille[i][j])
                    cases[i][j].setBackground(Color.pink) ;
                else
                    cases[i][j].setBackground(Color.gray) ;
                
                add(cases[i][j]);
                //cases[i][j] = Btemp;
                //add(Btemp);
                
                //add(cases[i][j]);
                    
            }
        }
        Timer mytimer = new Timer(100, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                boolean[][] grilletemp = new boolean[taille][taille];
                for (int i = 0 ; i < taille ; i++){
                    for (int j = 0 ; j < taille ; j++){
                        int count = compterVoisins(i,j);
                        if (caseGrille[i][j]){
                            if (count<2)
                                grilletemp[i][j] = false ;
                            else if (count ==3 || count == 2)
                                grilletemp[i][j] = true ;
                            else if (count> 3)
                                grilletemp[i][j] = false ;
                            else
                                grilletemp[i][j] = true ;
                        }
                        else{
                            if (count == 3)
                                grilletemp[i][j] = true ;
                            else
                                grilletemp[i][j] = false ;
                        }
                    }
                }
                caseGrille = grilletemp ;
                for (int i = 0 ; i < taille ; i++){
                    for (int j = 0 ; j < taille ; j++){
                        if(caseGrille[i][j]){
                            cases[i][j].setBackground(Color.pink);
                        }else{
                            cases[i][j].setBackground(Color.gray);
                        }
                            
                    }
                }
            }
        });
        pack();
        mytimer.start();

    }
    public int compterVoisins(int x , int y){
        int count = 0;
        for (int i = x-1 ; i <= x+1 ; i++){
            for (int j = y-1 ; j <= y+1 ; j++){
                try{
                    if (caseGrille[i][j])
                        count++ ;
                }catch(Exception e){}
            }
        }
        if (caseGrille[x][y])
            count--;
        return count ;
    }
    
    public static void main(String[] args) {
        ProjetBE2 magrille = new ProjetBE2();
    }
}


// Voici la version avec les threads. 

/*
public class ProjetBE2 extends JFrame {
    static int taille = 100;
    static boolean caseGrille[][] ;
    static JPanel cases[][] ;
    static boolean[][] grilletemp  = new boolean[taille][taille]; 
    
    public ProjetBE2(){
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Random rnd = new Random();
        
        caseGrille = new boolean[taille][taille] ;
        cases = new JPanel[taille][taille] ;
        //setSize(500,500);
        setLayout(new GridLayout(taille,taille,1,1));
        
        for (int i = 0 ; i < taille ; i++){
            for (int j = 0 ; j < taille ; j++){
                caseGrille[i][j] = rnd.nextInt(100) < 30  ;
                //System.out.println(caseGrille[i][j]);
                cases[i][j] = new JPanel() ;
                if (caseGrille[i][j])
                    cases[i][j].setBackground(Color.pink) ;
                else
                    cases[i][j].setBackground(Color.gray) ;
                
                add(cases[i][j]);
                //cases[i][j] = Btemp;
                //add(Btemp);
                
                //add(cases[i][j]);
                    
            }
        }
        pack();
        
    }

    static void Etatgrille(int dx , int dy , int fx , int fy){
        //static boolean[][] grilletemp = new boolean[taille][taille];
        for (int i = dx ; i < fx ; i++){
            for (int j = dy ; j < fy ; j++){
                int count = compterVoisins(i,j);
                if (caseGrille[i][j]){
                    if (count<2)
                        grilletemp[i][j] = false ;
                    else if (count ==3 || count == 2)
                        grilletemp[i][j] = true ;
                    else if (count> 3)
                        grilletemp[i][j] = false ;
                    else
                        grilletemp[i][j] = true ;
                }
                else{
                    if (count == 3)
                        grilletemp[i][j] = true ;
                    else
                        grilletemp[i][j] = false ;
                }
            }
        }
        
        //return grilletemp ;
    }
    static int compterVoisins(int x , int y){
        int count = 0;
        for (int i = x-1 ; i <= x+1 ; i++){
            for (int j = y-1 ; j <= y+1 ; j++){
                try{
                    if (caseGrille[i][j])
                        count++ ;
                }catch(Exception e){}
            }
        }
        if (caseGrille[x][y])
            count--;
        return count ;
    }

    static class myThreader implements Runnable { 
        static int myNum ;
        //public boolean[][] grilleLocale ;
        static int departX ;
        static int departY ;
        static int FinX ;
        static int FinY ;
        //public static Object lock = new Object();
        
        public myThreader(int Num){
            myNum = Num ;
            if (myNum == 0 ){
                departX = 0 ;
                departY = 0 ;
                FinX = taille/2;
                FinY = taille/2 ;
            }
            else if (myNum == 1 ){
                departX = 0 ;
                departY = taille/2 ;
                FinX = taille/2;
                FinY = taille ;
            }
            else if (myNum == 2 ){
                departX = taille/2 ;
                departY = 0 ;
                FinX = taille;
                FinY = taille/2 ;
            }
            else{
                departX = taille/2 ;
                departY = taille/2 ;
                FinX = taille;
                FinY = taille ;
            }
                
        }
        public void run() {
            ProjetBE2.Etatgrille( departX ,departY , FinX , FinY );
 
        } // fin run
        
    }
    public static void main(String[] args) {
        ProjetBE2 magrille = new ProjetBE2();
        //long startTime = System.currentTimeMillis();
        myThreader[] calculators = new myThreader[4];
        Thread[] threads = new Thread[4];
        System.out.println("C’est parti");
        for (int i = 0; i < 4; i++) {            
            calculators[i] = new myThreader(i);           
            threads[i] = new Thread(calculators[i]);
            threads[i].start();
        }
        
        Timer mytimer = new Timer(100, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                try {
                    for (int i = 0; i < 4; i++) {
                        threads[i].join(); // ZZ : join sur ”threads”
                        //System.out.println("C’est parti le start");
                    }
                    
                    caseGrille = grilletemp ;
                    for (int i = 0 ; i < taille ; i++){
                        for (int j = 0 ; j < taille ; j++){
                            if(caseGrille[i][j]){
                                cases[i][j].setBackground(Color.pink);
                            }else{
                                cases[i][j].setBackground(Color.gray);
                            }
                        }
                    }
                    //System.out.println(caseGrille.toString());
                    //System.out.println("blablabla");
                }
                catch (InterruptedException ie){
                    //System.out.println("haaaaaaaaaaaaaa");
                }// Ne devrait pas arriver car on n’appelle pas interrupt()
                }
          
        });
        mytimer.start();
        
    }
}
*/
