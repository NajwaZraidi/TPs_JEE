package ma.enset.nobloking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class SingleThreadServer {
    public static void main(String[] args) throws IOException {
        Selector selector=Selector.open();
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
// Configurer au mode non bloqant
        serverSocketChannel.configureBlocking(false);
        //0.0.0.0 connexion de n'importe quel @ip
        serverSocketChannel.bind(new InetSocketAddress("0.0.0.0",2001));
        //selector notifier le serverSocketChannel
       /* int validOps=serverSocketChannel.validOps();
        serverSocketChannel.register(selector, validOps);*/
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            //Single thread
            int chanelCount=selector.select();
            //chanelCount==0 => aucun demande
            if(chanelCount==0) continue;
            //liste des demandes
            Set<SelectionKey> selectionKeys =selector.selectedKeys();
            Iterator<SelectionKey> iterator=selectionKeys.iterator();
            //tritement des demandes
            while (iterator.hasNext()){
                SelectionKey selectionKey= iterator.next();
                if(selectionKey.isAcceptable()){
                    handleAccept(selectionKey,selector);
                }
                else if (selectionKey.isReadable()){
                    handleReadWrite(selectionKey,selector);
                }
                iterator.remove();
            }

        }
    }

    private static void handleAccept(SelectionKey selectionKey, Selector selector) throws IOException {
    System.out.println(Thread.currentThread().getName());
    ServerSocketChannel serverSocketChannel= (ServerSocketChannel) selectionKey.channel();
    SocketChannel socketChannel=serverSocketChannel.accept();
    socketChannel.configureBlocking(false);
    socketChannel.register(selector,SelectionKey.OP_READ);
        System.out.println(String.format("Nouvelle connexion du %s",socketChannel.getRemoteAddress()));

    }
    private static void handleReadWrite(SelectionKey selectionKey, Selector selector) throws IOException {
        System.out.println(Thread.currentThread().getName());
        SocketChannel socketChannel= (SocketChannel) selectionKey.channel();
    //lire
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        int datasize=socketChannel.read(byteBuffer);
        if(datasize==-1){
            System.out.println(String.format("Le client %s se déconnecter",socketChannel.getRemoteAddress()));
        }
        String requete=new String(byteBuffer.array()).trim();
        System.out.println(String.format("Nouvelle requete %s du %s ",requete,socketChannel.getRemoteAddress()));
        String response=new StringBuffer(requete).reverse().toString().toLowerCase()+"\n";
        ByteBuffer byteBufferResponse=ByteBuffer.allocate(1024);
        byteBufferResponse.put(response.getBytes());
        byteBufferResponse.flip();
        //ENvoie de reponse au client
        socketChannel.write(byteBufferResponse);

    }


}
