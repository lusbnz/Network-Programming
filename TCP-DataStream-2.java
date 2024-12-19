// [Mã câu hỏi (qCode): 5WsCpEmj].  Mật mã caesar, còn gọi là mật mã dịch chuyển, để giải mã thì mỗi ký tự nhận được sẽ được thay thế bằng một ký tự cách nó một đoạn s. Ví dụ: với s = 3 thì ký tự “A” sẽ được thay thế bằng ký tự “D”.
// Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2207 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng chương trình client tương tác với server trên, sử dụng các luồng byte (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
// a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;D68C93F7"
// b.	Nhận lần lượt chuỗi đã bị mã hóa caesar và giá trị dịch chuyển s nguyên
// c.	Thực hiện giải mã ra thông điệp ban đầu và gửi lên Server
// d.	Đóng kết nối và kết thúc chương trình.

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
        Socket socket = new Socket("203.162.10.109", 2207);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        String studentCode = "B16DCCN999;5WsCpEmj";
        dos.writeUTF(studentCode);
        dos.flush();

        String data = dis.readUTF();
        int s = dis.readInt();

        String result = "";
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                result += (char) ((c - base - s + 26) % 26 + base);
            } else {
                result += c;
            }
        }

        dos.writeUTF(result);

        dis.close();
        dos.close();
        socket.close();
    }
}
