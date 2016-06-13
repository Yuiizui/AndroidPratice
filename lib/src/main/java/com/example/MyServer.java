package com.example;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import javafx.scene.layout.Background;


public class MyServer extends JFrame implements Runnable{
    private Thread thread;
    private ServerSocket servSock;
    private BufferedReader reader;
    private  Socket clntSock;
    private JLabel message;
    private String s;
    private BackGround a;

    public static void main(String[]args){
        MyServer obj = new MyServer();
    }

    public MyServer(){

        super("Server");
        setLayout(null);
        setSize(500,800);
        setLocation(200,200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.gray);//to set the BackGroung color

        //new Jlbael
        message = new JLabel(s);
        message.setBounds(100,20,400,30);
        add(message);

        //new background
        a = new BackGround();
        a.setBounds(0,0,500,800);
        add(a);

        setVisible(true);

        try {
            // Detect server ip
            InetAddress IP = InetAddress.getLocalHost();
            System.out.println("IP of my system is := "+IP.getHostAddress());
            System.out.println("Waitting to connect......");

            // Create server socket
            servSock = new ServerSocket(2000);

           /* this.connectionToClient = this.servSock.accept();
            this.reader = new BufferedReader(new InputStreamReader(connectionToClient.getInputStream()));*/

            clntSock = servSock.accept();
            InputStream in = clntSock.getInputStream();
            this.reader = new BufferedReader(new InputStreamReader(in));

            System.out.println("Connected!!");//test whether connected or not

            // Create socket thread
            thread = new Thread(this);
            thread.start();
        } catch (java.io.IOException e) {
            System.out.println("Socket啟動有問題 !");
            System.out.println("IOException :" + e.toString());
        } finally{

        }
    }

    @Override
    public void run(){
        // Running for waitting multiple client
        while(true){
            try{
                // After client connected, create client socket connect with client

                s = this.reader.readLine();
                message.setText("The answer fom App is "+s);
                message.setBackground(Color.BLUE);
                System.out.println("[Server Said]" + s);

            }
            catch(Exception e){
                //System.out.println("Error: "+e.getMessage());
            }
        }
    }

    public class BackGround extends JPanel {

        private static final long serialVersionUID = 1L;
        private BufferedImage image;

        public BackGround(){
            setLayout(null);
            try{
                image = ImageIO.read(new File("b.jpg"));//read the ball.png
            }catch(IOException e){
                System.out.println("NOTICE::IOEXCEPTION ERROR!!");
            }

        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image,0,0,500,800,null);//paint ball on the screen


        }
    }
}
