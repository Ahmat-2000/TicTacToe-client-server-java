package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * La classe WriteToServer gère l'écriture des données vers le serveur.
 * Elle implémente l'interface Runnable pour pouvoir être exécutée dans un thread.
 */
public class WriteToServer implements Runnable {
    // Flux de sortie pour l'écriture des données vers le serveur
    private DataOutputStream dataOut;

    /**
     * Constructeur de la classe WriteToServer.
     * @param out Le flux de sortie vers le serveur.
     */
    WriteToServer(OutputStream out) {
        dataOut = new DataOutputStream(out);
    }

    /**
     * Méthode exécutée lors du démarrage du thread.
     * Ici, elle est vide car la logique d'écriture est gérée dans d'autres méthodes.
     */
    @Override
    public void run() {
        // La logique d'écriture est gérée dans d'autres méthodes
    }

    /**
     * Méthode pour envoyer le numéro de la case sélectionnée vers le serveur.
     * @param x Le numéro de la case sélectionnée.
     */
    public void sendButton(int x) {
        try {
            dataOut.writeInt(x);
            dataOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
