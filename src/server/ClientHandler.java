package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * La classe ClientHandler gère la communication avec un client dans le serveur.
 * Chaque instance de ClientHandler est associée à un joueur.
 */
public class ClientHandler extends Thread {
    /**
     * Constante représentant le code envoyé au client lorsqu'il n'est pas son tour
     */
    private static final int NOT_YOUR_TURN_CODE = 301;

    /**
     * Identifiant du joueur associé à ce gestionnaire de client
     */
    private int playerID;
    /**
     * Flux d'entrée pour la réception des données du client
     */
    private DataInputStream dataIn;
    /**
     * Flux de sortie pour l'envoi des données au client
     */
    private DataOutputStream dataOut;
    /**
     * Plateau de jeu associé à ce gestionnaire de client
     */
    private Plateau plateau;
    /**
     * Gestionnaire de client associé à l'adversaire
     */
    private ClientHandler enemy;
    private Socket clientSocket;

    /**
     * Constructeur de la classe ClientHandler.
     * @param playerID L'identifiant du joueur associé à ce gestionnaire de client.
     * @param client Le socket du joueur
     */
    public ClientHandler(int playerID, Socket client) {
        this.playerID = playerID;
        clientSocket = client;
        try {
            dataOut = new DataOutputStream(client.getOutputStream());
            dataIn = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            try {
                clientSocket.close();
            } catch (Exception a) {
                System.out.println("Erreur lors de la fermeture du socket client dans le constructeur de ClientHandler");
            }
            System.out.println("Erreur lors de la création des flux d'entrée/sortie dans le constructeur de ClientHandler");
        }
    }

    /**
     * Méthode pour définir le plateau de jeu associé à ce gestionnaire de client.
     * @param p Le plateau de jeu.
     */
    public void setPlateu(Plateau p) {
        plateau = p;
    }

    /**
     * Méthode exécutée lors du démarrage du thread.
     * Elle gère la communication avec le client pendant le jeu.
     */
    @Override
    public void run() {
        try {
            int winner = plateau.getWinner();
            while (winner == -1) {
                try {
                    int pos = dataIn.readInt();
                    if (plateau.getCurrentPlayer() == playerID) {
                        if (plateau.validMove(pos)) {
                            plateau.play(pos);
                            winner = plateau.getWinner();
                            plateau.changePlayer();
                            notifyEnemy(pos);
                        }
                    } else {
                        sendNotYourTurnMessage();
                    }
                } catch (Exception e) {
                    // Gérer l'EOFException de manière élégante
                    System.out.println("Fin du flux. Le client est déconnecté");
                    break; // Sortir de la boucle
                }
            }
            sendGameEndMessages(winner);
        } catch (Exception e) {
            System.out.println("Une exception dans Run() ");
        } finally {
            System.out.println("Fin de la partie \n");
            closeStreams();
        }
    }

    /**
     * Méthode pour envoyer un message de début de jeu au client.
     */
    public void sendStartMessage() {
        try {
            dataOut.writeUTF("Le jeu a commencé ...");
            dataOut.flush();
        } catch (IOException e) {
            System.out.println("Une exception dans sendStartMessage ");
            closeStreams();
        }
    }

    /**
     * Méthode pour définir l'adversaire de ce joueur.
     * @param enemy Le gestionnaire de client associé à l'adversaire.
     */
    public void setEnemy(ClientHandler enemy) {
        this.enemy = enemy;
    }

    /**
     * Méthode pour obtenir le flux de sortie vers le client.
     * @return Le flux de sortie vers le client.
     */
    public DataOutputStream getDataOut() {
        return dataOut;
    }

    /**
     * Méthode pour envoyer l'identifiant du joueur au client.
     */
    public void sendPlayerId() {
        try {
            dataOut.writeInt(playerID);
            dataOut.flush();
        } catch (Exception e) {
            System.out.println("Une exception dans sendPlayerId ");
            closeStreams();
        }
    }

    /**
     * Méthode pour envoyer le premier mouvement autorisé au client.
     * @param enable true si le premier mouvement est autorisé, sinon false.
     */
    public void sendFirstPlay(boolean enable) {
        try {
            dataOut.writeBoolean(enable);
            dataOut.flush();
        } catch (IOException e) {
            System.out.println("Une exception dans sendFirstPlay ");
            closeStreams();
        }
    }

    /**
     * Méthode privée pour notifier l'adversaire du joueur.
     * @param pos La position du dernier coup joué.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de l'envoi du message à l'adversaire.
     */
    private void notifyEnemy(int pos) throws IOException {
        enemy.getDataOut().writeBoolean(false);
        enemy.getDataOut().flush();
        enemy.getDataOut().writeInt(pos);
        enemy.getDataOut().flush();
    }

    /**
     * Méthode privée pour envoyer un message indiquant au client que ce n'est pas son tour.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de l'envoi du message au client.
     */
    private void sendNotYourTurnMessage() throws IOException {
        dataOut.writeInt(NOT_YOUR_TURN_CODE);
        dataOut.flush();
    }

    /**
     * Méthode privée pour envoyer les messages de fin de jeu aux joueurs.
     * @param winner L'identifiant du joueur gagnant.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de l'envoi des messages aux joueurs.
     */
    private void sendGameEndMessages(int winner) {
        
        try {
            dataOut.writeBoolean(true);
            dataOut.flush();
            enemy.getDataOut().writeBoolean(true);
            dataOut.flush();
            dataOut.writeInt(winner);
            dataOut.flush();
            enemy.getDataOut().writeInt(winner);
        dataOut.flush();
        } catch (IOException e) {
           System.out.println("Execption dans la fonction sendGameEndMessages de ClientHandler\n");
           System.out.println(e.getCause());
        }
        
    }

    /**
     * Méthode privée pour fermer les flux de communication avec le client.
     */
    private void closeStreams() {
        try {
            dataIn.close();
            dataOut.close();
            clientSocket.close();
            enemy.interrupt();
            enemy.dataIn.close();
            enemy.dataOut.close();
            enemy.clientSocket.close();
        } catch (Exception e) {
            System.out.println("Une exception lors de la fermeture des socket et I/O stream ");
            System.out.println("Cause : "+e.getCause());
        }
    }

}
