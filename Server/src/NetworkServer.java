import lab1.CollectionManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class NetworkServer {

    private static CollectionManager manager;

    public  NetworkServer(CollectionManager o){
        manager = o;
    }

    final int PORT= 57912;
    public void runServer(){
        while (true){
            //повторяющийся участок кода
            DatagramSocket socket = null;
            ByteArrayOutputStream dataOutStream = null;
            ObjectOutputStream oStream = null;

            try {
                byte b[] = new byte[1];
                SocketAddress a = new InetSocketAddress(PORT);
                socket = new DatagramSocket(a);
                DatagramPacket packet = new DatagramPacket(b, b.length);

                //while
                    System.out.println("Waiting for request...");

                    socket.receive(packet);

                    System.out.println("Received packet of size: " + packet.getLength());

                    System.out.println(b[0]+" number of command");
                    final DatagramSocket se = socket;
                    //создание нового потока для каждого пользователяб вызов

                    Thread c = new Thread(){
                        public void run(){
                            ifFunction(b,se,packet);
                            se.close();
                        }
                    };
                    c.start();
                    c.join();


            }
            catch (Exception e) {
                    System.out.println(e.getMessage());
                }finally {
                    if (socket != null)
                        socket.close();
                    try {
                        if (dataOutStream != null)
                            dataOutStream.close();
                    } catch (Exception inner) {}

                    try {
                        if (oStream != null)
                            oStream.close();
                    }
                    catch (Exception inner) {}
                }

            }
    }


    public static void ifFunction (byte b[],DatagramSocket socket,
                            DatagramPacket packet){
        try{if (b[0] == 1) {
            ByteArrayOutputStream dataOutStream;
            ObjectOutputStream oStream;
            dataOutStream = new ByteArrayOutputStream();
            oStream = new ObjectOutputStream(dataOutStream);
            Object[] array = manager.getSortedUmbrellas();
            oStream.writeObject(array);
            byte[] collectionBytes = dataOutStream.toByteArray();
            System.out.println("Serialied objects, got " + collectionBytes.length + " bytes");

            int size = collectionBytes.length;
            byte[] sizeBytes = ByteBuffer.allocate(4).putInt(size).array();
            DatagramPacket firstSetPacket = new DatagramPacket(sizeBytes, sizeBytes.length, packet.getAddress(), packet.getPort());
            socket.send(firstSetPacket);

            System.out.println("Sent 4 bytes to client.");

            DatagramPacket setPacket = new DatagramPacket(collectionBytes, collectionBytes.length, packet.getAddress(), packet.getPort());
            socket.send(setPacket);

            System.out.println("Sent " + setPacket.getLength() + " bytes");
        }
        }catch(IOException e ){
            e.getMessage();
        }

    }


}
