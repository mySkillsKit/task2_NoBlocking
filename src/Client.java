import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client implements Runnable {

    @Override
    public void run() {

        System.out.println("start client");

        try {
            InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1",
                    21300);
            final SocketChannel socketChannel = SocketChannel.open();


            try (Scanner scanner = new Scanner(System.in)) {

                socketChannel.connect(socketAddress);
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
                String msg;

                while (true) {
                    System.out.println("Enter any text / If you want to close the program enter 'end' ");
                    msg = scanner.nextLine();
                    if ("end".equals(msg)) break;
                    socketChannel.write(
                            ByteBuffer.wrap(
                                    msg.getBytes(StandardCharsets.UTF_8)));
                    int bytesCount = socketChannel.read(inputBuffer);
                    System.out.println(new String(inputBuffer.array(), 0, bytesCount,
                            StandardCharsets.UTF_8).trim());
                    inputBuffer.clear();
                }

            } catch (IOException exc) {
                exc.printStackTrace();
            } finally {
                socketChannel.close();
            }

        } catch (IOException exc) {
            exc.printStackTrace();
        }

    }


}

