package client.test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class SymmetricEncryption {
    public static void main(String[] args) {
        try {
            // Bước 1: Tạo khóa bí mật AES (Secret Key)
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128); // Độ dài khóa: 128, 192, hoặc 256 bit
            SecretKey secretKey = keyGenerator.generateKey();

            // In khóa bí mật ra để tham khảo
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            System.out.println("Secret Key (Base64): " + encodedKey);

            // Dữ liệu cần mã hóa
            String plainText = "This is a secret message";

            // Bước 2: Mã hóa dữ liệu
            Cipher encryptCipher = Cipher.getInstance("AES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = encryptCipher.doFinal(plainText.getBytes());
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            System.out.println("Encrypted Text: " + encryptedText);

            // Bước 3: Giải mã dữ liệu
            Cipher decryptCipher = Cipher.getInstance("AES");
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = decryptCipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(decryptedBytes);
            System.out.println("Decrypted Text: " + decryptedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

