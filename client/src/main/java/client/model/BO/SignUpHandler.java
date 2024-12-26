package client.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SignUpHandler {

    public boolean signUp(String username, String password, DataInputStream dis, DataOutputStream dos) throws IOException {
    	dos.writeUTF("SIGNUP");  // Gửi yêu cầu "SIGNUP" đến server
        dos.writeUTF(username);  // Gửi username
        dos.writeUTF(password);  // Gửi password
        dos.flush();

        // Nhận phản hồi từ server
        boolean signUpSuccess = dis.readBoolean();
        String serverMessage =dis.readUTF();
        System.out.println(serverMessage);

        return signUpSuccess;
    }
}
