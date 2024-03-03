package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * La classe GameServer représente le serveur de jeu pour Tic Tac Toe.
 */
public class GameServer {
    private ClientHandler player1, player2;
    private ServerSocket serverSocket;
    private ArrayList<Socket> listOfSocket;
    public static int PORT = 5050;

    /**
     * Constructeur de la classe GameServer.
     */
    public GameServer() {
        System.out.println("===== GAME SERVER STARTED =====");
        try {
            serverSocket = new ServerSocket(PORT);
            listOfSocket = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            this.closeEveryThing();
            System.out.println("IOException from GameServer Constructor");
        }
    }

    /**
     * Méthode pour accepter les connexions des joueurs.
     */
    public void startServer() {
        try {
            System.out.println("SERVER RUNNING AT " + serverSocket.getLocalSocketAddress());
            Socket clientSocket = null;
            while (true) {
                int rand = (int) (Math.random() * 2) + 1;
                Plateau p = new Plateau(1, 2, rand);
                System.out.println("Waitting for players...");
                clientSocket = serverSocket.accept();
                System.out.println("Player 1 has connected...");
                listOfSocket.add(clientSocket);
                player1 = new ClientHandler(1, clientSocket);
                player1.setPlateu(p); 
                player1.start();
                player1.sendPlayerId();

                clientSocket = serverSocket.accept();
                System.out.println("Player 2 has connected ...");
                System.out.println("New Partie ...");
                listOfSocket.add(clientSocket);
                player2 = new ClientHandler(2, clientSocket);
                player1.setEnemy(player2);
                player2.setEnemy(player1);

                player2.setPlateu(p);
                player2.start();
                player2.sendPlayerId();

                player1.sendFirstPlay(rand == 1);
                player2.sendFirstPlay(rand == 2);

                player1.sendStartMessage();
                player2.sendStartMessage();
            }
            // System.out.println("No longer accepting connections");
        } catch (IOException e) {
            closeEveryThing();
            System.out.println("IOException from acceptConnections");
        }
    }

    /**
     * Méthode pour fermer le serveur.
     * @param s Le socket du serveur à fermer.
     */
    private void closeEveryThing() {
        try {
            serverSocket.close();  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode principale pour démarrer le serveur.
     * @param args Arguments de la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.startServer();
    }
}
