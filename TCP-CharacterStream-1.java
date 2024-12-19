// [Mã câu hỏi (qCode): RWJvxHaS].  [Loại bỏ các ký tự trong chuỗi thứ nhất mà xuất hiện trong chuỗi thứ hai]
// Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản sau:
// a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;DE0C2BF0"
// b. Nhận lần lượt hai chuỗi ngẫu nhiên từ server
// c. Loại bỏ các ký tự trong chuỗi thứ nhất mà xuất hiện trong chuỗi thứ hai, yêu cầu giữ nguyên thứ tự xuất hiện của ký tự. Gửi chuỗi đã được xử lý lên server.
// d. Đóng kết nối và kết thúc chương trình

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPCharacterStream1 {
	public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2208);

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String studentCode = "B16DCCN999;RWJvxHaS";
        writer.write(studentCode);
        writer.newLine();
        writer.flush();

        String str1 = reader.readLine();
        String str2 = reader.readLine();

        String result = "";
        for (int i = 0; i < str1.length(); i++) {
            char c = str1.charAt(i);
            if (str2.indexOf(c) == -1) {
                result += c;
            }
        }

        writer.write(result);
        writer.newLine();
        writer.flush();

        reader.close();
        writer.close();
        socket.close();
    }
}