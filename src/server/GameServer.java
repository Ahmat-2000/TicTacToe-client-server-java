package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

@SuppressWarnings("unused")

public class GameServer {
    private ClientHandler player1, player2;
    private ServerSocket ss;
    private int numPlayers;
    private int maxPlayers;
    public static int PORT = 5050;
    public GameServer() {
        System.out.println("===== GAME SERVER STARTED =====");
        this.numPlayers = 0;
        this.maxPlayers = 2;

        try {
            this.ss = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
            this.closeEveryThing(ss);
            System.out.println("IOException from GameServer Constructor");
        }
    }

    public void acceptConnections(){
        try {
            System.out.println("SERVER RUNNING AT "+ss.getLocalSocketAddress());
            Plateau p; int rand;
            rand = -1;
            p = null;
            while (numPlayers < maxPlayers) {
                Socket clientSocket = ss.accept();
                numPlayers++;
                if (numPlayers == 1) {
                    rand = (int)(Math.random() * 2) + 1;
                    p = new Plateau(1, 2, rand);
                    player1 = new ClientHandler(numPlayers, clientSocket.getInputStream(),clientSocket.getOutputStream());
                    player1.setPlateu(p);
                    player1.start();
                    player1.sendPlayerId();
                }
                else if (numPlayers == 2){
                    player2 = new ClientHandler(numPlayers, clientSocket.getInputStream(),clientSocket.getOutputStream());
                    player1.setEnemy(player2); player2.setEnemy(player1);
                    
                    player2.setPlateu(p);
                    player2.start();
                    player2.sendPlayerId();
                    player1.sendFirstPlay(rand == 1);
                    player2.sendFirstPlay(rand == 2);
                    player1.sendStartMessage();
                    player2.sendStartMessage();
                    break;
                }

            }
            System.out.println("No longer accepting connections");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException from acceptConnections");
        }
    }
    private void closeEveryThing(ServerSocket s) {
       try {
            if (s !=null) {
                s.close();
            }
       } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}
