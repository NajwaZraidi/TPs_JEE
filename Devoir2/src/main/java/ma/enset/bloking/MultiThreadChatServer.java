package ma.enset.bloking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultiThreadChatServer extends Thread {
    private List<ConversationChat> conversationChatList = new ArrayList<>();
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
            while (true) {
                Socket socket = serverSocket.accept();
                //Démmarage de threads
                ++clientCount;
                ConversationChat conversationChat = new ConversationChat(socket, clientCount);
                conversationChatList.add(conversationChat);
                conversationChat.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    class ConversationChat extends Thread {
        //Gestion des entres et des sorties
        private Socket socket;
        private int Id_client;

        public ConversationChat(Socket socket, int Id_client) {
            this.socket = socket;
            this.Id_client = Id_client;
        }

        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                String ip = socket.getRemoteSocketAddress().toString();
                System.out.println("Le nouveau client N° " + Id_client + "\n IP => " + ip);
                pw.println("Soyez lz bienvenue : \n Votre Numero est : " + Id_client);
                String request;
                String message;
                while ((request = br.readLine())!=null) {
                    List<Integer> clientsTo=new ArrayList<>();
                    System.out.println("Request => IP = " + ip + "\nRequest =>" + request);
                    if(request.contains("=>")) {
                        String[] items = request.split("=>");
                        String clients= items[0];
                        message =items[1];
                        if(clients.contains(",")){
                            String[] clientsIDs=clients.split(",");
                          for (String id:clientsIDs){
                              clientsTo.add(Integer.parseInt(id));
                          }
                        }
                        else{
                            clientsTo.add(Integer.parseInt(clients));
                        }
                    }
                    else {
                        clientsTo = conversationChatList.stream().map(c -> c.Id_client).collect(Collectors.toList());
                        message=request;
                    }
                    broadCastMessage(message,this,clientsTo);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }
    public void broadCastMessage(String message,ConversationChat from,List<Integer> clients){
        for (ConversationChat conversationChat:conversationChatList){
            if(conversationChat!=from && clients.contains(conversationChat.Id_client)) {
                try {

                    Socket socket = conversationChat.socket;
                    OutputStream outputStream = socket.getOutputStream();
                    PrintWriter printWriter = new PrintWriter(outputStream, true);
                    printWriter.println(message);


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
