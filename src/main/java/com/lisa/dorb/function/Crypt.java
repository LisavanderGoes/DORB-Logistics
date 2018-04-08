package com.lisa.dorb.function;

import javax.crypto.Cipher;
import java.security.*;

public class Crypt {
    static String message;
    static byte [] encrypted;
    static byte[] decrypted;

    public static void main(String [] args) throws Exception {
        // generate public and private keys
        KeyPair keyPair = buildKeyPair();
        PublicKey pubKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // encrypt the message
        encrypted = encrypt(privateKey, message);
        System.out.println(new String(encrypted));  // <<encrypted message>>

        // decrypt the message
        decrypted = decrypt(pubKey, encrypted);
        System.out.println(new String(decrypted));     // This is a secret message
    }

    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("Crypt");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.genKeyPair();
    }

    public static byte[] encrypt(PrivateKey privateKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance("Crypt");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(message.getBytes());
    }

    public static byte[] decrypt(PublicKey publicKey, byte [] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("Crypt");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(encrypted);
    }

    public static byte[] getDecrypted() {
        return decrypted;
    }

    public static void setEncrypted(String msg) {
        message = msg;
    }
}
