package ma.enset.bloking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SimpleBlockingClient {


    public static void main(String[] args) {

            try {
                Socket socket = new Socket("localhost",2001);
                InputStream is=socket.getInputStream();
                OutputStream os=socket.getOutputStream();
                Scanner scanner=new Scanner(System.in);
                System.out.println("Entrer un nombre : ");
                int requete=scanner.nextInt();
                os.write(requete);
                int reponse=is.read();
                System.out.println("la reponse est : ");
                System.out.println(reponse);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }

}