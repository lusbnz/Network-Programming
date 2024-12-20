// [Mã câu hỏi (qCode): xFHndckg].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu.
// Giao diện từ xa:
// public interface DataService extends Remote {
// public Object requestData(String studentCode, String qCode) throws RemoteException;
// public void submitData(String studentCode, String qCode, Object data) throws RemoteException;
// }
// Trong đó:
// •	Interface DataService được viết trong package RMI.
// •	Đối tượng cài đặt giao diện từ xa DataService được đăng ký với RegistryServer với tên là: RMIDataService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhận được từ RMI Server:
// a. Triệu gọi phương thức requestData để nhận một số nguyên dương N từ server, đại diện cho giới hạn trên của khoảng cần kiểm tra.
// b. Thực hiện tìm tất cả các số nguyên tố trong khoảng từ 1 đến N. Ví dụ: Với N = 10, kết quả là danh sách các số nguyên tố “2, 3, 5, 7”.
// c. Triệu gọi phương thức submitData để gửi List< Integer> danh sách các số nguyên tố đã tìm được trở lại server.
// d. Kết thúc chương trình client.

package RMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.ArrayList;
import java.util.List;

public class RMIData1 {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        String studentCode = "B21DCCN269";
        String qCode = "j1AhbOxw";
        int port = 1099;

        Registry registry = LocalRegistry.getRegistry("203.162.10.109", port);
        DataService service = (DataService) registry.lookup("RMIDataService");

        int N = service.requestData(studentCode, qCode);

        List<Integer> list = new ArrayList<>();
        for (int i = 2; i <= N; i++) {
            if (isPrime(i)) {
                list.add(i);
            }
        }

        service.submitData(studentCode, qCode, list);
    }
    
    private static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
