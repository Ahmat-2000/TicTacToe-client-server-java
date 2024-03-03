package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * La classe GameServer représente le serveur de jeu pour Tic Tac Toe.
 */
public class GameServer {
    /**
     * player1 et player2 sont deux Threads pour gérer les deux joueurs
     */
    private ClientHandler player1, player2;
    /**
     * C'est le socket du server
     */
    private ServerSocket serverSocket;
    /**
     * Le port sur lequel le server écoute
     */
    public static int PORT = 5050;

    /**
     * Constructeur de la classe GameServer.
     */
    public GameServer() {
        System.out.println("===== GAME SERVER STARTED =====");
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
            this.closeEveryThing();
            System.out.println("IOException dans le Constructeur de GameServer ");
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
                System.out.println("Attente des joueurs");
                clientSocket = serverSocket.accept();
                System.out.println("Le joueur N°1 est connecté");
                player1 = new ClientHandler(1, clientSocket);
                player1.setPlateu(p); 
                player1.start();
                player1.sendPlayerId();

                clientSocket = serverSocket.accept();
                System.out.println("Le joueur N°2 est connecté");
                System.out.println("Nouvelle partie");
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
            System.out.println("IOException dans startServer");
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
