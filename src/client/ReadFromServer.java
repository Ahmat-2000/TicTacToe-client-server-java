
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadFromServer implements Runnable{
    private DataInputStream dataIn;

    ReadFromServer(InputStream in){
        dataIn = new DataInputStream(in);
    }
    @Override
    public void run() {
       
    }

    public int readPlayerId() throws IOException{
        return dataIn.readInt();
    }
    public boolean readGameOver() throws IOException{
        return dataIn.readBoolean();
    }
    public int readAdverseMove() throws IOException{
        return dataIn.readInt();
    }
    public boolean readFirstPlay() throws IOException{
        return dataIn.readBoolean();
    }
    public void waitForStartMessage(){
        try {
           String startMsg = dataIn.readUTF();
           System.out.println("Message from server : \n\t"+startMsg);
        } catch (IOException e) {
            System.out.println("IOException from waitForStartMessage ");
        }
    }

}