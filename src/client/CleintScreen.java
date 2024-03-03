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

/**
 * La classe CleintScreen représente l'interface graphique du client pour le jeu Tic Tac Toe.
 * Elle hérite de JFrame.
 */
public class CleintScreen extends JFrame {
    // Définition des constantes pour l'adresse IP et le port
    public static final String IP = "localhost";
    public static final int PORT = 5050;
    // Déclaration des variables membres
    private Socket socket;
    private ReadFromServer rfsRunnable;
    private WriteToServer wtsRunnable;
    private int width, height, playerID;
    private JPanel plateauView;
    private JTextField header;
    private CustomJButton[] listeOfButton;
    private boolean yourTurn, gameOver;

    /**
     * Constructeur de la classe CleintScreen.
     * @param width La largeur de la fenêtre.
     * @param height La hauteur de la fenêtre.
     */
    public CleintScreen(int width, int height) {
        this.width = width;
        this.height = height;
        plateauView = new JPanel(new GridLayout(3, 3));
        header = this.setHeader();
    }

    /**
     * Initialise l'en-tête du jeu.
     * @return Le champ de texte de l'en-tête.
     */
    private JTextField setHeader() {
        JTextField h = new JTextField("En attente du second joueur :)");
        h.setHorizontalAlignment(SwingConstants.CENTER);
        h.setEditable(false);
        h.setForeground(new Color(4, 64, 27));
        h.setBackground(new Color(233, 240, 234));
        h.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        return h;
    }

    /**
     * Méthode pour mettre à jour le tour de jeu.
     */
    public void updateTurn() {
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!gameOver) {
                        gameOver = rfsRunnable.readGameOver();
                        int i = rfsRunnable.readAdverseMove();
                        if (!gameOver) {
                            System.out.println("L'ennemi a cliqué sur le bouton #" + i);
                            listeOfButton[i].setText(playerID == 1 ? "X" : "0");
                            yourTurn = true;
                            toggleButton(yourTurn);
                            setTurn(yourTurn);
                        } else {
                            System.out.println("Le gagnant est " + i);
                            yourTurn = false;
                            toggleButton(yourTurn);
                            setWinner(i);
                            //TODO
                            socket.close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        a.start();
    }

    /**
     * Active/désactive les boutons en fonction du tour.
     * @param turn Le tour du joueur.
     */
    private void toggleButton(boolean turn) {
        for (int i = 0; i < 9; i++) {
            if (listeOfButton[i].getText() == "") {
                listeOfButton[i].setEnabled(turn);
            }
        }
    }

    /**
     * Définit le tour de jeu.
     * @param turn Le tour du joueur.
     */
    private void setTurn(boolean turn) {
        if (turn) {
            header.setText("Votre tour ");
        } else {
            header.setText("Attendez votre tour");
        }
    }

    /**
     * Définit le gagnant du jeu.
     * @param winner Le joueur gagnant.
     */
    private void setWinner(int winner) {
        if (winner == 0) {
            header.setText("😤 Match nul 😤");
        } else if (winner == playerID) {
            header.setText("🥳 Vous avez gagné 🥳");
        } else {
            header.setText("🤧 Vous avez perdu 🤧");
        }
    }

    /**
     * Initialise les boutons du plateau de jeu.
     */
    private void setUpButton() {
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

    /**
     * Connecte le client au serveur.
     * @throws Exception Si une exception se produit lors de la connexion.
     */
    private void connectToServer() throws Exception {
        socket = new Socket(IP, PORT);

        rfsRunnable = new ReadFromServer(socket.getInputStream());
        wtsRunnable = new WriteToServer(socket.getOutputStream());
        System.out.println("\n Connexion au serveur \n");
        playerID = rfsRunnable.readPlayerId();
        System.out.println("Joueur #" + playerID);
        this.setGUI();
        yourTurn = rfsRunnable.readFirstPlay();
        rfsRunnable.waitForStartMessage();
        updateTurn();
    }

    /**
     * Définit l'interface graphique utilisateur.
     */
    public void setGUI() {
        this.setSize(this.width, this.height);
        this.setTitle("Joueur #" + playerID + (playerID == 1 ? " O " : " X "));
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().add(plateauView, BorderLayout.CENTER);
        this.getContentPane().add(header, BorderLayout.NORTH);
        this.setVisible(true);
    }

    /**
     * Méthode principale pour l'exécution du programme.
     * @param args Les arguments de la ligne de commande.
     * @throws Exception Si une exception se produit pendant l'exécution.
     */
    public static void main(String[] args) throws Exception {
        int WIDTH = 600;
        int HEIGHT = 600;
        CleintScreen window = new CleintScreen(WIDTH, HEIGHT);
        window.connectToServer();
        window.setUpButton();
    }
}
