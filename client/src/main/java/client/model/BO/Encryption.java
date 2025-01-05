package client.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
	KeyPairGenerator keyPairGen;
	
    public Encryption() {
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(2048); // Độ dài khóa RSA (2048 bit)
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    }
    
    public SecretKey receiveSecretKeyFromServer(DataInputStream dis, DataOutputStream dos) {
        try {
            // Bước 1: Tạo cặp khóa RSA và gửi PublicKey cho Server
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // Gửi PublicKey dưới dạng Base64 cho Server
            String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            dos.writeUTF(publicKeyBase64);
            dos.flush();

            // Bước 2: Nhận chuỗi Base64 của SecretKey đã mã hóa từ Server
            String encryptedKeyBase64 = dis.readUTF();
//            System.out.println("Encrypted SecretKey received: " + encryptedKeyBase64);

            // Bước 3: Giải mã SecretKey bằng PrivateKey
            byte[] encryptedKeyBytes = Base64.getDecoder().decode(encryptedKeyBase64);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] secretKeyBytes = cipher.doFinal(encryptedKeyBytes);

            // Bước 4: Tái tạo SecretKey từ byte
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, 0, secretKeyBytes.length, "AES");
//            System.out.println("Reconstructed SecretKey: " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            return secretKey;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
}
