package client.test;

import javax.crypto.Cipher;
import java.security.*;

public class AsymmetricEncryption {
    public static void main(String[] args) {
        try {
            // Bước 1: Tạo cặp khóa (public key và private key)
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048); // Độ dài khóa RSA (2048 bit)
            KeyPair keyPair = keyPairGen.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            
            System.out.println("Public Key: " + publicKey);
            System.out.println("Private Key: " + privateKey);

            // Dữ liệu cần mã hóa
            String message = "Hello, this is a secret message!";

            // Bước 2: Mã hóa dữ liệu bằng Public Key
            Cipher encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedMessage = encryptCipher.doFinal(message.getBytes());
            System.out.println("Encrypted Message: " + bytesToHex(encryptedMessage));

            // Bước 3: Giải mã dữ liệu bằng Private Key
            Cipher decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedMessage = decryptCipher.doFinal(encryptedMessage);
            System.out.println("Decrypted Message: " + new String(decryptedMessage));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm tiện ích: Chuyển byte[] sang dạng chuỗi Hex để in ra
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
