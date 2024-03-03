package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ClientHandler extends Thread{
    private int playerID;
    private DataInputStream dataIn;    
    private DataOutputStream dataOut;
    private Plateau plateau;
    private ClientHandler enemy;
    public ClientHandler(int playerID, InputStream in, OutputStream out) {
        this.playerID = playerID;
        this.dataOut = new DataOutputStream(out) ;
        this.dataIn = new DataInputStream(in);
        System.out.println("ClientHandler for Player #"+playerID+" is created");
    }
    public void setPlateu(Plateau p){
        plateau = p;
    }
    @Override
    public void run() {
        try {
            int winner = plateau.getWinner();
            while (winner == -1) { // tant que le jeu n'est pas fini
                int pos = dataIn.readInt();
                if(plateau.getCurrentPlayer() == playerID){ // c'est pour la sécurité
                    if(plateau.validMove(pos)){
                        plateau.play(pos);
                        winner = plateau.getWinner();
                        plateau.changePlayer();
                        enemy.dataOut.writeBoolean(false); // le jeu est terminé
                        enemy.getDataOut().flush();
                        enemy.getDataOut().writeInt(pos);
                        enemy.getDataOut().flush();

                    }
                }else{
                    dataOut.writeInt(301); // 301 pour dire que c'est pas votre tour
                    dataOut.flush();
                }
            }
            dataOut.writeBoolean(true); // le jeu est terminé
            dataOut.flush();
            enemy.dataOut.writeBoolean(true); // le jeu est terminé
            dataOut.flush();
            dataOut.writeInt(winner); // on envie le gagnant
            dataOut.flush();
            enemy.dataOut.writeInt(winner); // on envie le gagnant
            dataOut.flush();

            // dataIn.close();
            // dataOut.close();
            // enemy.dataIn.close();
            // enemy.dataOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendStartMessage(){
        try {
           dataOut.writeUTF("Le jeu a commencé ...");
           dataOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException from sendForStartMessage ");
        }
    }

    public void setEnemy(ClientHandler enemy){
        this.enemy = enemy;
    }
    public DataOutputStream getDataOut(){
        return dataOut;
    }
    public void sendPlayerId(){
        try {
           dataOut.writeInt(playerID);
           dataOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException from sendForStartMessage ");
        }
    }
    public void sendFirstPlay(boolean enable){
        try {
           dataOut.writeBoolean(enable);
           dataOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException from sendForStartMessage ");
        }
    }
}