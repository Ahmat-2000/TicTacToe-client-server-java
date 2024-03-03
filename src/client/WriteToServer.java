
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class WriteToServer implements Runnable{
    private DataOutputStream dataOut;

    WriteToServer(OutputStream out){
        dataOut = new DataOutputStream(out);
    }
    @Override
    public void run() {
       
    }
    public void sendButton(int x){
        try {
            dataOut.writeInt(x);
            dataOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
