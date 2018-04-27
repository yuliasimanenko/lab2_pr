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

                while(true){
                    System.out.println("Waiting for request...");

                    socket.receive(packet);

                    System.out.println("Received packet of size: " + packet.getLength());

                    System.out.println(b[0]+" number of command");
                    //создание нового потока для каждого пользователяб вызов
                    QueueClirnt qc = new QueueClirnt(b,socket,dataOutStream,oStream,packet);
                    qc.start();
                }
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


    public static void ifFunction (byte b[],DatagramSocket socket,ByteArrayOutputStream dataOutStream,
                            ObjectOutputStream oStream,DatagramPacket packet){
        try{if (b[0] == 1) {
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
        }finally {
            socket.close();
        }

    }


}
class QueueClirnt extends Thread{
    public byte t[];
    public DatagramSocket socketT;
    public ByteArrayOutputStream dataOutStreamT;
    public ObjectOutputStream oStreamT;
    public DatagramPacket packetT;

    public QueueClirnt(byte b[],DatagramSocket socket,ByteArrayOutputStream dataOutStream,
                       ObjectOutputStream oStream,DatagramPacket packet){
        byte t[]= b;
        DatagramSocket socketT=socket;
        ByteArrayOutputStream dataOutStreamT=dataOutStream;
        ObjectOutputStream oStreamT =oStream;
        DatagramPacket packetT= packet;

    }

    public void run(){
        //вызывается вторая часть логики по обработке уже конкретных команд
        NetworkServer.ifFunction(t,socketT,dataOutStreamT,oStreamT,packetT);
    }
}



