// [Mã câu hỏi (qCode): 7Eu425Vn].  [Loại bỏ ký tự đặc biệt và ký tự trùng giữ nguyên thứ tự xuất hiện]
// Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208 . Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản dưới đây:
// a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode”. Ví dụ: ";B15DCCN001;B34D51E0"
// b.	Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;str1;str2".
// •	requestId là chuỗi ngẫu nhiên duy nhất
// •	str1,str2 lần lượt là chuỗi thứ nhất và chuỗi thứ hai
// c.	Loại bỏ các ký tự trong chuỗi thứ nhất mà xuất hiện trong chuỗi thứ hai, giữ nguyên thứ tự xuất hiện. Gửi thông điệp là một chuỗi lên server theo định dạng "requestId;strOutput", trong đó chuỗi strOutput là chuỗi đã được xử lý ở trên.
// d.	Đóng socket và kết thúc chương trình.

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

public class UDPString2 {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("203.162.10.109");
        int port = 2208;

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
        String str1 = parts[1];
        String str2 = parts[2];

        String res = requestId + ";";
        for (int i = 0; i < str1.length(); i++) {
            if (!str2.contains(String.valueOf(str1.charAt(i)))){
                res += str1.charAt(i);
            }
        }

        byte[] resultData = res.toString().getBytes();
        DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length, address, port);
    }
}