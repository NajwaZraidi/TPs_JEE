package ma.enset.bloking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer extends Thread {
    int clientCount;
    public static void main(String[] args) {
           //Démarrage de thread
        new MultiThreadChatServer().start();

    }

    @Override
    public void run() {
        System.out.println("Le démarrage de serveur N°2001 ");
        //super.run();
        try {
           ServerSocket serverSocket = new ServerSocket(2001);
           //Conection des clients
           while (true){
               Socket socket=serverSocket.accept();
               //Démmarage de threads
               ++clientCount;
               new Conversation(socket,clientCount).start();
           }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
class Conversation extends Thread{
    //Gestion des entres et des sorties
    private Socket socket;
    private int Id_client;
    public Conversation(Socket socket,int Id_client){
        this.socket=socket;
        this.Id_client=Id_client;
    }
    @Override
    public void run() {
        try {
            InputStream is=socket.getInputStream();
            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader br=new BufferedReader(isr);
            OutputStream os=socket.getOutputStream();
            PrintWriter pw=new PrintWriter(os,true);
            String ip=socket.getRemoteSocketAddress().toString();
            System.out.println("Le nouveau client N° " +Id_client+"\n IP => "+ip);
            pw.println("Soyez lz bienvenue : \n Votre Numero est : "+Id_client);
            while (true){
                String request = br.readLine();
                System.out.println("Request => IP = "+ip+"\nRequest =>"+request);
                String reponse="La taille de la chaine de caractere est  =>"+request.length();
                //System.out.println(reponse);
                pw.println(reponse);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
