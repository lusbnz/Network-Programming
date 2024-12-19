// [Mã câu hỏi (qCode): MkzcAL43].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). 
// Yêu cầu xây dựng chương trình client thực hiện kết nối tới server trên sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự: 
// a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;C64967DD"
// b. Nhận dữ liệu từ server là một chuỗi gồm các giá trị nguyên được phân tách với nhau bằng  "|"
// Ex: 2|5|9|11
// c. Thực hiện tìm giá trị tổng của các số nguyên trong chuỗi và gửi lên server
// Ex: 27
// d. Đóng kết nối và kết thúc

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPByteStream1 {
	public static void main(String[] args) throws IOException {
    	Socket socket = new Socket("203.162.10.109", 2206);

		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();

		String studentCode = "B16DCCN999;MkzcAL43";
		os.write(studentCode.getBytes());
		os.flush();

		byte[] data = new byte[1024];
		is.read(data);

		String str = new String(data);
		String[] arr = str.split("\\|");
		int sum = 0;
		for (String s : arr) {
			sum += Integer.parseInt(s);
		}

		os.write((sum + "").getBytes());
		os.flush();

		is.close();
		os.close();
		socket.close();
	}
}
