// [Mã câu hỏi (qCode): 0MgyTBn1].  Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client tương tác với server kịch bản dưới đây:
// a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”
// b.	Nhận thông điệp từ server theo định dạng “requestId; data” 
// -	requestId là một chuỗi ngẫu nhiên duy nhất
// -	data là chuỗi dữ liệu đầu vào cần xử lý
// Ex: “requestId;Qnc8d5x78aldSGWWmaAAjyg3”
// c.	Tìm kiếm ký tự xuất hiện nhiều nhất trong chuỗi và gửi lên server theo định dạng “requestId;ký tự xuất hiện nhiều nhất: các vị trí xuất hiện ký tự đó” 
// ví dụ: “requestId;8:4,9,”
// d.	Đóng socket và kết thúc chương trình

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

public class UDPString1 {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("203.162.10.109");
        int port = 2208;

        String studentCode = "B15DCCN001";
        String qCode = "0MgyTBn1";
        String message = ";" + studentCode + ";" + qCode;

        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        socket.send(packet);

        byte[] buffer = new byte[1024];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        socket.receive(response);

        String receiveData = new String(response.getData().trim());
        String[] parts = receiveData.split(";");
        String requestId = parts[0];
        String dataReceived = parts[1];

        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < dataReceived.length(); i++) {
            char c = dataReceived.charAt(i);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        int max = 0;
        List<Character> list = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                list.clear();
                list.add(entry.getKey());
            } else if (entry.getValue() == max) {
                list.add(entry.getKey());
            }
        }

        StringBuilder result = new StringBuilder();
        result.append(requestId).append(";");
        for (char c : list) {
            result.append(c).append(":");
            for (int i = 0; i < dataReceived.length(); i++) {
                if (dataReceived.charAt(i) == c) {
                    result.append(i).append(",");
                }
            }
        }

        byte[] resultData = result.toString().getBytes();
        DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length, address, port);
        socket.send(resultPacket);

        socket.close();
    }
}