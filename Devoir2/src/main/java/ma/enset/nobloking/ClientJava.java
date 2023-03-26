package ma.enset.nobloking;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;


public class ClientJava {

        private static final Charset charset = Charset.forName("UTF-8");
        private static final String REQUEST = "NAJWA ZRAIDI";

        public static void main(String[] args) throws IOException {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("localhost", 2001));
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(REQUEST.getBytes(charset));
            buffer.flip();
            while (buffer.hasRemaining()) {
                socketChannel.write(buffer);
            }
            buffer.clear();
            socketChannel.read(buffer);
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            String response = new String(bytes, charset);
            System.out.println(response);
            //socketChannel.close();
        }


}
