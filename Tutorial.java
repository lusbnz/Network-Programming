// TCP
packet TCP;

// ====== ByteStream =====
public class TCPByteStream = {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2206);

        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        String code = "";
        os.write(code.getBytes());
        os.flush();

        byte[] data = new byte[1024];
        is.read(data);

        String str = new String(data);
        os.write(str.getBytes());
        os.flush();

        is.close();
        os.close();
        socket.close();
    }
}

// ===== CharacterStream =====
public class TCPCharacterStream = {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2208);

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String code = "";
        bw.write(code);
        bw.newLine();
        bw.flush();

        String str = br.readLine();
        bw.write(str);
        bw.newLine();
        bw.flush();

        br.close();
        bw.close();
        socket.close();
    }
}

// ===== DataStream =====
public class TCPDataStream = {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2207);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        String code = "";
        dos.writeUTF(code);
        dos.flush();

        String str = dis.readUTF();
        dos.writeUTF(str);

        dis.close();
        dos.close();
        socket.close();
    }
}

// ===== ObjectStream =====
public class TCPObjectStream {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("203.162.10.109", 2209);

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        String code = "";
        oos.writeUTF(code);
        oos.flush();

        Object object = (Object) ois.readObject();

        String key = object.getkey()
        object.setKey(key);

        oos.writeObject(object);

        ois.close();
        oos.close();
        socket.close();
    }
}

// UDP
packet UDP;

public class UDPString {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("203.162.10.109");
        int port = 2208;

        String code = "";
        byte[] data = code.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        socket.send(packet);

        byte[] buffer = new byte[1024];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        socket.receive(response);

        String receiveData = new String(response.getData().trim());

        byte[] resultData = receiveData.toString().getBytes();
        DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length, address, port);
        socket.send(resultPacket);
    }
}

// RMI
// folder com.rmi -> tạo file interface tên giống đề cho -> dán code interface vào trong file đó
packet RMI;

public class RMIData {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        String code = "";
        int port = 1099;

        Registry registry = LocalRegistry..getRegistry("203.162.10.109", port);
        DataService service = (DataService) registry.lookup("RMIDataService");

        int n = service.requestData(code);

        service.submitData(n);
    }
}

// WS
// Import WS: 
// Tool -> Java Platforms -> JDK 1.8
// Chỉnh file pom.xml 21 -> 1.8
// Chuột phải project -> New -> Web Service Client -> WSDL URL 
// -> Dán http://<ip>:port/JNPWS/CharacterService?wsdl (Đề cho)

// CharacterService.wsdl
// CharacterService, requestString, submitCharacterString đề cho

packet WS;

public class WSCharacter {
    public static void main(String[] args) {
        String studentCode = "";
        String qCode = "";
        
        CharacterService_Service service = new CharacterService_Service();
        CharacterService port = service.getCharacterServicePort();
        
        String str = port.requestString(studentCode, qCode);
        
        String res = str;
        port.submitCharacterString(studentCode, qCode, res);
    }
}