package ma.enset.bloking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleBlockingServer {
    public static void main(String[] args) {
        ServerSocket serverSocket;

        {
            try {
                serverSocket = new ServerSocket(2001);
                System.out.println("----------------------");
                System.out.println("J\'attend une nouvelle connection");
                Socket socket= serverSocket.accept();
                //la fct accept block et attend la connexion
                InputStream is=socket.getInputStream();
                OutputStream os=socket.getOutputStream();
                System.out.println("J\'attend la requete");
                int data=is.read();
                int reponse=data*23;
                System.out.println("Envoie de réponse");
                os.write(reponse);
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
