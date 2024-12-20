// [Mã câu hỏi (qCode): ayt2sCVY].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
// a.      	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;DC73CA2E”
// b.      	Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;a1,a2,...,a50”
// -        	requestId là chuỗi ngẫu nhiên duy nhất
// -        	a1 -> a50 là 50 số nguyên ngẫu nhiên
// c.      	Thực hiện tìm giá trị lớn nhất và giá trị nhỏ nhất thông điệp trong a1 -> a50 và gửi thông điệp lên lên server theo định dạng “requestId;max,min”
// d.      	Đóng socket và kết thúc chương trình

package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UDPData1 {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("203.162.10.109");
        int port = 2207;

        String studentCode = "B15DCCN001";
        String qCode = "7Eu425Vn";
        String message = ";" + studentCode + ";" + qCode;

        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(message, message.length, address, port);
        socket.send(packet);

        byte[] buffer = new byte[1024];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        socket.receive(response);

        String receiveData = new String(response.getData().trim());
        String[] parts = receiveData.split(";");
        String requestId = parts[0];
        String[] numbers = parts[1];

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < numbers.lengh(); i++) {
            int value = Integer.parseInt(numbers[i]);
            if (value > max) {
                max = value;
            }
             if (value < min) {
                min = value;
            }
        }

        String res = requestId + ";" + max + "," + min;

        byte[] resultData = res.toString().getBytes();
        DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length, address, port); 
    }
}