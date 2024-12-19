// [Mã câu hỏi (qCode): VdLsbmSC].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client thực hiện kết nối tới server và sử dụng luồng ký tự (BufferedWriter/BufferedReader) để trao đổi thông tin theo kịch bản
// a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;5E263AE1"
// b. Nhận một chuỗi ngẫu nhiên từ server
// c. Tách chuỗi đã nhận thành 2 chuỗi và gửi lần lượt theo thứ tự lên server
//    i. Chuỗi thứ nhất gồm các ký tự và số (loại bỏ các ký tự đặc biệt)
//    ii. Chuỗi thứ hai gồm các ký tự đặc biệt
// d. Đóng kết nối và kết thúc chương trình

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPCharacterStream2 {
	public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2208);

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

       	String studentCode = "B16DCCN999;MkzcAL43";
        writer.write(studentCode);
        writer.newLine();
        writer.flush();

        String data = reader.readLine();
        String str1 = "";
        String str2 = "";

        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                str1 += c;
            } else {
                str2 += c;
            }
        }

        writer.write(str1);
        writer.newLine();
        writer.write(str2);
        writer.newLine();

        reader.close();
        writer.close();
        socket.close();
    }
}