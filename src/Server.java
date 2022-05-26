import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server implements Runnable {

    @Override
    public void run() {

        System.out.println("start server");

        try {
            final ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress("localhost", 21300));

            while (true) {
                try (SocketChannel socketChannel = serverChannel.accept()) {

                    final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

                    while (socketChannel.isConnected()) {
                        int bytesCount = socketChannel.read(inputBuffer);
                        if (bytesCount == -1) break;
                        String msg = new String(inputBuffer.array(), 0, bytesCount,
                                StandardCharsets.UTF_8);
                        inputBuffer.clear();
                        System.out.println("Received a message from a client: " + msg);

                        msg = msg.replace(" ", "");
                        socketChannel.write(ByteBuffer.wrap(("SERVER: Text without spaces -  " +
                                msg).getBytes(StandardCharsets.UTF_8)));
                    }

                } catch (IOException exc) {
                    System.out.println(exc.getMessage());
                }
            }

        } catch (IOException exc) {
            exc.printStackTrace();
        }

    }


}
