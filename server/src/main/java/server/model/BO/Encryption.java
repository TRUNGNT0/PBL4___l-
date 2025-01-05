package server.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Encryption {
	KeyGenerator keyGenerator;
	KeyPairGenerator keyPairGen;
	
	public Encryption() {
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128); // Độ dài khóa: 128, 192, hoặc 256 bit
			
			keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(2048); // Độ dài khóa RSA (2048 bit)
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public SecretKey createAESKey() {
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey;
	}
	
	
	public void sendAESKeyToClient(SecretKey secretKey, DataInputStream dis, DataOutputStream dos) {
	    try {
	        // Bước 1: Nhận PublicKey từ Client
	        String publicKeyBase64 = dis.readUTF();
	        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
	        System.out.println("PublicKey received from client.");

	        // Bước 2: Lấy byte từ SecretKey
	        byte[] secretKeyBytes = secretKey.getEncoded();
 
	        // Bước 3: Mã hóa SecretKey bằng PublicKey
	        Cipher cipher = Cipher.getInstance("RSA");
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        byte[] encryptedKeyBytes = cipher.doFinal(secretKeyBytes);

	        // Bước 4: Chuyển mảng byte đã mã hóa thành Base64
	        String encryptedKeyBase64 = Base64.getEncoder().encodeToString(encryptedKeyBytes);

	        // Bước 5: Gửi chuỗi Base64 qua DataOutputStream
	        dos.writeUTF(encryptedKeyBase64);
	        dos.flush();
	        System.out.println("Encrypted SecretKey sent to client.");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
