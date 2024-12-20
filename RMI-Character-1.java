// [Mã câu hỏi (qCode): 0zhGpb02].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý chuỗi.
// Giao diện từ xa:
// public interface CharacterService extends Remote {
// public String requestCharacter(String studentCode, String qCode) throws RemoteException;
// public void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException;
// }
// Trong đó:
// •	Interface CharacterService được viết trong package RMI.
// •	Đối tượng cài đặt giao diện từ xa CharacterService được đăng ký với RegistryServer với tên là: RMICharacterService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với chuỗi được nhận từ RMI Server:
// a. Triệu gọi phương thức requestCharacter để nhận chuỗi ngẫu nhiên từ server với định dạng: "Chuỗi đầu vào".
// b. Thực hiện đếm tần số xuất hiện của mỗi ký tự trong chuỗi đầu vào và tạo ra chuỗi kết quả theo định dạng <Ký tự><Số lần xuất hiện>, sắp xếp theo thứ tự xuất hiện của các ký tự trong chuỗi.
// Ví dụ: Chuỗi đầu vào "AAABBC" -> Kết quả: "A3B2C1".
// c. Triệu gọi phương thức submitCharacter để gửi chuỗi kết quả trở lại server.
// d. Kết thúc chương trình client.

// CharacterService
// requestCharacter - submitCharacter
// RMICharacterService

package RMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class RMICharacter1 {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        String studentCode = "B15DCCN001";
        String qCode = "7Eu425Vn";
        int port = 1099;

        Registry registry = LocalRegistry.getRegistry("203.162.10.109", port);
        CharacterService service = (CharacterService) registry.lookup("RMICharacterService");

        String data = service.requestCharacter(studentCode, qCode);

        Map<Character, Integer> map = new LinkedHashMap<>();
        for(Character i : data.toCharArray()) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        StringBuilder string_builder = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            string_builder.append(entry.getKey()).append(entry.getValue());
        }
        
        service.submitCharacter(studentCode, qCode, string_builder.toString());
    }
}