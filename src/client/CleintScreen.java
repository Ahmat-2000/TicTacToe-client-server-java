
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class CleintScreen extends JFrame{
    public static final String IP = "localhost";
    public static final int PORT = 5050;
    private Socket socket;
    private ReadFromServer rfsRunnable;
    private WriteToServer wtsRunnable;
    private int width, height, playerID;
    private JPanel plateauView;
    private JTextField header;
    private CustomJButton[] listeOfButton ;
    private boolean yourTurn ;
    public CleintScreen(int width , int height){
        this.width = width;
        this.height = height;
        plateauView = new JPanel(new GridLayout(3, 3));
        header = this.setHeader();
    }
    private JTextField setHeader(){
        JTextField h = new JTextField("En attente du sécond joueur :)");
        h.setHorizontalAlignment(SwingConstants.CENTER);
        h.setEditable(false);
        h.setForeground(new Color(4, 64, 27));
        h.setBackground(new Color(233, 240, 234));
        h.setFont(new Font(Font.MONOSPACED,Font.BOLD, 30));
        return h;
    }
    public void updateTurn(){
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        boolean gameOver = rfsRunnable.readGameOver();
                        int i = rfsRunnable.readAdverseMove();
                        if (!gameOver) {
                            System.out.println("L'ennemy a clické sur le bouton #"+i);
                            // O => joueur avec id 1 
                            // X => joueur avec id 2
                            listeOfButton[i].setText(playerID == 1 ? "X" : "0");
                            yourTurn = true;
                            toggleButton(yourTurn);
                            setTurn(yourTurn);
                        }
                        
                        else{
                            System.out.println("The winner is "+i);
                            yourTurn = false;
                            toggleButton(yourTurn);
                            setWinner(i);
                            //socket.close();
                        }
                        //readWinner();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
            
        });
        a.start();
    }

    private void toggleButton(boolean turn){
        for (int i = 0; i < 9; i++) {
            if (listeOfButton[i].getText() == "") {
                listeOfButton[i].setEnabled(turn);
            }
        }
    }
    private void setTurn(boolean turn){
        if (turn) {
            header.setText("Votre tour à jouer :)");
        }else{
            header.setText("Attendez votre tour :)");
        }
    }
    private void setWinner(int winner){
        if (winner == 0) {
            header.setText("😤 Match null 😤");
        }else if(winner == playerID){
            header.setText("🥳 Vous avez gagné 🥳");
        }else{
            header.setText("🤧 Vous avez perdu 🤧");
        }
    }
    private void setUpButton(){
        listeOfButton = new CustomJButton[9];
        ActionListener al = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 9; i++) {
                    if (listeOfButton[i] == (CustomJButton) e.getSource()) {
                        yourTurn = false;
                        setTurn(yourTurn);
                        toggleButton(yourTurn);
                        listeOfButton[i].setText(playerID == 1 ? "O" : "X");
                        wtsRunnable.sendButton(i);
                    }
                }
            }
            
        };
        for (int i = 0; i < 9; i++) {
            listeOfButton[i] = new CustomJButton(i);
            listeOfButton[i].addActionListener(al);
            plateauView.add(listeOfButton[i]);
            plateauView.revalidate();
        }
        this.toggleButton(yourTurn);
        this.setTurn(yourTurn);
    }
    private void connectToServer() throws Exception{
            socket = new Socket(IP,PORT);
            
            rfsRunnable = new ReadFromServer(socket.getInputStream());
            wtsRunnable = new WriteToServer(socket.getOutputStream());
            System.out.println("\n connectToServer \n");
            playerID =  rfsRunnable.readPlayerId();
            System.out.println("Player #"+playerID);
            this.setGUI();
            yourTurn = rfsRunnable.readFirstPlay();
            rfsRunnable.waitForStartMessage();

            Thread readThread = new Thread(rfsRunnable);
            Thread writeThread = new Thread(wtsRunnable);

            readThread.start();
            writeThread.start();
            updateTurn();
    }

    public void setGUI(){
        this.setSize(this.width,this.height);
        this.setTitle("Player #"+playerID+(playerID == 1 ? " O " : " X "));
        // this.setTitle("Tic Tac Toe");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().add(plateauView,BorderLayout.CENTER);
        this.getContentPane().add(header,BorderLayout.NORTH);
        this.setVisible(true);
    }
    public static void main(String[] args) throws Exception {
        int WIDTH = 600;
        int HEIGHT = 600;
        CleintScreen window = new CleintScreen(WIDTH, HEIGHT);
        window.connectToServer();
        window.setUpButton();
    }
}
