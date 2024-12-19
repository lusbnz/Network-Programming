// [Mã câu hỏi (qCode): AmJldrO1].  Một chương trình server cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng chương trình client tương tác với server bằng các byte stream (DataInputStream/DataOutputStream) để trao đổi thông tin theo trình tự sau:
// a. Gửi một chuỗi chứa mã sinh viên và mã câu hỏi ở định dạng "studentCode;qCode". Ví dụ: "B10DCCN000;A1B2C3D4".
// b. Nhận từ server một mảng chứa n số nguyên, với n được gửi từ máy chủ. Ví dụ: Server gửi mảng [5, 9, 3, 6, 8].
// c. Tính tổng, trung bình cộng, và phương sai của mảng. Gửi kết quả lần lượt lên server dưới dạng số nguyên và float. Ví dụ, gửi lên lần lượt: 31, 6.2, 4.5599995. 
// d. Đóng kết nối và kết thúc chương trình.

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
        Socket socket = new Socket("203.162.10.109", 2207);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        String studentCode = "B16DCCN999;AmJldrO1";
        dos.writeUTF(studentCode);
        dos.flush();

        dis.readInt();
        int n = dis.readInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = dis.readInt();
        }

        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        float avg = (float) sum / n;
        float variance = 0;
        for (int i : arr) {
            variance += (i - avg) * (i - avg);
        }
        variance /= n;

        dos.writeInt(sum);
        dos.writeFloat(avg);
        dos.writeFloat(variance);

        dis.close();
        dos.close();
        socket.close();
    }
}