// [Mã câu hỏi (qCode): E4Hrktfr].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server trên sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự: 
// a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;FF49DC02"
// b. Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách nhau bởi ký tự ","
// Ex: 1,3,9,19,33,20
// c. Thực hiện tìm giá trị khoảng cách nhỏ nhất của các phần tử nằm trong chuỗi và hai giá trị lớn nhất tạo nên khoảng cách đó. Gửi lên server chuỗi gồm "khoảng cách nhỏ nhất, số thứ nhất, số thứ hai". Ex: 1,19,20
// d. Đóng kết nối và kết thúc

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPByteStream2 {
	public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2206);

        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        String studentCode = "B16DCCN999;E4Hrktfr";
        os.write(studentCode.getBytes());
        os.flush();

        byte[] data = new byte[1024];
        is.read(data);

        String str = new String(data);
        String[] arr = str.split(",");

        List<Integer> list = new ArrayList<>();
        for (String s : arr) {
            list.add(Integer.parseInt(s));
        }

        Collections.sort(list);

        int minDistance = Integer.MAX_VALUE;
        int first = 0;
        int second = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            int distance = list.get(i + 1) - list.get(i);
            if (distance < minDistance) {
                minDistance = distance;
                first = list.get(i);
                second = list.get(i + 1);
            }
        }

        String result = minDistance + "," + first + "," + second;
        os.write(result.getBytes());

        is.close();
        os.close();
        socket.close();
    }
}