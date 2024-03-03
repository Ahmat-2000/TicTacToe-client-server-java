package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * La classe ReadFromServer gère la lecture des données provenant du serveur.
 * Elle implémente l'interface Runnable pour pouvoir être exécutée dans un thread.
 */
public class ReadFromServer implements Runnable {
    // Flux d'entrée pour la lecture des données depuis le serveur
    private DataInputStream dataIn;

    /**
     * Constructeur de la classe ReadFromServer.
     * @param in Le flux d'entrée depuis le serveur.
     */
    ReadFromServer(InputStream in) {
        dataIn = new DataInputStream(in);
    }

    /**
     * Méthode exécutée lors du démarrage du thread.
     * Ici, elle est vide car la logique de lecture est gérée dans d'autres méthodes.
     */
    @Override
    public void run() {
        // La logique de lecture est gérée dans d'autres méthodes
    }

    /**
     * Méthode pour lire l'identifiant du joueur depuis le flux d'entrée.
     * @return L'identifiant du joueur.
     * @throws IOException Si une erreur d'entrée/sortie se produit pendant la lecture.
     */
    public int readPlayerId() throws IOException {
        return dataIn.readInt();
    }

    /**
     * Méthode pour lire l'état de fin de jeu depuis le flux d'entrée.
     * @return true si le jeu est terminé, sinon false.
     * @throws IOException Si une erreur d'entrée/sortie se produit pendant la lecture.
     */
    public boolean readGameOver() throws IOException {
        return dataIn.readBoolean();
    }

    /**
     * Méthode pour lire le mouvement adverse depuis le flux d'entrée.
     * @return Le numéro de la case sur laquelle le joueur adverse a joué.
     * @throws IOException Si une erreur d'entrée/sortie se produit pendant la lecture.
     */
    public int readAdverseMove() throws IOException {
        return dataIn.readInt();
    }

    /**
     * Méthode pour lire le premier mouvement depuis le flux d'entrée.
     * @return true si c'est le premier mouvement du joueur, sinon false.
     * @throws IOException Si une erreur d'entrée/sortie se produit pendant la lecture.
     */
    public boolean readFirstPlay() throws IOException {
        return dataIn.readBoolean();
    }

    /**
     * Méthode pour attendre un message de démarrage du serveur.
     */
    public void waitForStartMessage() {
        try {
            String startMsg = dataIn.readUTF();
            System.out.println("Message from server : \n\t" + startMsg);
        } catch (IOException e) {
            System.out.println("IOException from waitForStartMessage ");
        }
    }
}
