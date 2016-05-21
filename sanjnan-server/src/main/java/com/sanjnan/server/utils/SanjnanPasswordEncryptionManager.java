/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.utils;

import com.sanjnan.server.pojos.constants.H2OConstants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

/**
 * Created by vinay on 2/8/16.
 */
public class SanjnanPasswordEncryptionManager {

  public static SanjnanPasswordEncryptionManager INSTANCE = new SanjnanPasswordEncryptionManager();
  /**
   * The input is encryped using AES256 algorithm. A variant of @ref username is used for password while a variant of
   * tenant is used as salt. After encryption, ivBuffer is prepended alongwith the length of ivBuffer and then whole
   * string is base64 encoded. It is assumed that ivBuffer length will never be greater than 255 because it is stored
   * within a single byte.
   * +------+----------+--------------------+
   * |ivLen |ivBuffer  |encoded input string|
   * +------+----------+--------------------+
   */
  private static int pswdIterations = 65536  ;
  private static int keySize = 256;
  private final String keyFactoryName = "PBKDF2WithHmacSHA1";
  private final String cipherInstanceName = "AES/CBC/PKCS5Padding";
  private final String aesEncryption = "AES";
  private Logger logger = Logger.getLogger(SanjnanPasswordEncryptionManager.class);

  private SanjnanPasswordEncryptionManager() {

  }
  /**
   * The input password is encrypted and base64 encoded and returned. Tenant and username is used to create keys for
   * each user.
   * @param username username of the user
   * @param password password that needs to be encrypted
   * @return
   * @throws UnsupportedEncodingException
   * @throws NoSuchAlgorithmException
   * @throws NoSuchPaddingException
   * @throws InvalidKeySpecException
   * @throws InvalidKeyException
   * @throws InvalidParameterSpecException
   * @throws BadPaddingException
   * @throws IllegalBlockSizeException
   */
  public synchronized String encrypt(String username, String password)
      throws UnsupportedEncodingException,
      NoSuchAlgorithmException,
      NoSuchPaddingException,
      InvalidKeySpecException,
      InvalidKeyException,
      InvalidParameterSpecException,
      BadPaddingException,
      IllegalBlockSizeException {

    return encrypt(H2OConstants.H2O_DEFAULT_TENANT, username, password);
  }


  /**
   * The input password is encrypted and base64 encoded and returned. Tenant and username is used to create keys for
   * each user.
   * @param tenant tenant for the user
   * @param username username of the user
   * @param password password that needs to be encrypted
   * @return
   * @throws UnsupportedEncodingException
   * @throws NoSuchAlgorithmException
   * @throws NoSuchPaddingException
   * @throws InvalidKeySpecException
   * @throws InvalidKeyException
   * @throws InvalidParameterSpecException
   * @throws BadPaddingException
   * @throws IllegalBlockSizeException
   */
  public synchronized String encrypt(String tenant, String username, String password)
      throws UnsupportedEncodingException,
      NoSuchAlgorithmException,
      NoSuchPaddingException,
      InvalidKeySpecException,
      InvalidKeyException,
      InvalidParameterSpecException,
      BadPaddingException,
      IllegalBlockSizeException {

    Cipher cipher = Cipher.getInstance(cipherInstanceName);
    cipher.init(Cipher.ENCRYPT_MODE, getSecret(tenant, username));
    AlgorithmParameters params = cipher.getParameters();
    byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
    byte[] encryptedTextBytes = cipher.doFinal(password.getBytes("UTF-8"));
    int outputLength = 1 + ivBytes.length + encryptedTextBytes.length;
    byte[] outputBytes = new byte[outputLength];
    int count = 0;
    outputBytes[count] = Integer.valueOf(ivBytes.length).byteValue();
    ++count;
    for (byte b : ivBytes) {
      outputBytes[count] = b;
      ++count;
    }
    for (byte b : encryptedTextBytes) {
      outputBytes[count] = b;
      ++count;
    }
    return new Base64().encodeAsString(outputBytes);
  }
  public synchronized boolean matches(String username,
                                      String encryptedPassword,
                                      String password)
      throws NoSuchPaddingException,
      UnsupportedEncodingException,
      IllegalBlockSizeException,
      BadPaddingException,
      NoSuchAlgorithmException,
      InvalidParameterSpecException,
      InvalidKeyException,
      InvalidKeySpecException, InvalidAlgorithmParameterException {

    return matches(H2OConstants.H2O_DEFAULT_TENANT, username, encryptedPassword, password);
  }

  private String decrypt(String username, String encryptedText)
      throws NoSuchPaddingException,
      NoSuchAlgorithmException,
      UnsupportedEncodingException,
      InvalidKeySpecException,
      InvalidAlgorithmParameterException,
      InvalidKeyException,
      BadPaddingException,
      IllegalBlockSizeException {

    return decrypt(H2OConstants.H2O_DEFAULT_TENANT, username, encryptedText);
  }

  public synchronized boolean matches(String tenant,
                                      String username,
                                      String encryptedPassword,
                                      String password)
      throws NoSuchPaddingException,
      UnsupportedEncodingException,
      IllegalBlockSizeException,
      BadPaddingException,
      NoSuchAlgorithmException,
      InvalidParameterSpecException,
      InvalidKeyException,
      InvalidKeySpecException, InvalidAlgorithmParameterException {

    try {

      String decryptedPassword = decrypt(tenant, username, encryptedPassword);
      return decryptedPassword.equals(password);
    }
    catch(Exception ex) {
      String msg = String.format("Authentication failed. Password mismatch: %s", username);
      logger.log(Level.INFO, msg);
      throw new BadCredentialsException(msg);
    }
  }

  private String decrypt(String tenant, String username, String encryptedText)
      throws NoSuchPaddingException,
      NoSuchAlgorithmException,
      UnsupportedEncodingException,
      InvalidKeySpecException,
      InvalidAlgorithmParameterException,
      InvalidKeyException,
      BadPaddingException,
      IllegalBlockSizeException {

    byte[] inputBytes = Base64.decodeBase64(encryptedText);
    int count = 0;
    int ivLength = inputBytes[count];
    ++count;
    byte[] ivBytes = new byte[ivLength];
    for (int i = 0; i < ivLength; ++i) {
      ivBytes[i] = inputBytes[count];
      ++count;
    }
    byte[] encryptedBytes = new byte[inputBytes.length - 1 - ivLength];
    for (int i = 0; i < encryptedBytes.length; ++i) {
      encryptedBytes[i] = inputBytes[count];
      ++count;
    }
    Cipher cipher = Cipher.getInstance(cipherInstanceName);
    cipher.init(Cipher.DECRYPT_MODE, getSecret(tenant, username), new IvParameterSpec(ivBytes));
    byte[] decryptedTextBytes = cipher.doFinal(encryptedBytes);
    return new String(decryptedTextBytes);
  }

  private char[] generatePassword(String username) {

    return DigestUtils.md5Hex(new String("|_|" + username + "|_|")).toCharArray();
  }

  private byte[] generateSalt(String tenant) {

    return DigestUtils.md5Hex(new String("|_|" + tenant + "|_|")).getBytes();
  }

  private SecretKeySpec getSecret(String tenant, String username) throws
      UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {

    char[] passwordCharacters = generatePassword(username);
    byte[] saltBytes = generateSalt(tenant);

    SecretKeyFactory factory = SecretKeyFactory.getInstance(keyFactoryName);
    PBEKeySpec pbeKeySpec = new PBEKeySpec(passwordCharacters, saltBytes, pswdIterations, keySize);
    SecretKey secretKey = factory.generateSecret(pbeKeySpec);
    SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), aesEncryption);
    return secretKeySpec;
  }

  public boolean matchBCrypt(String username, String encryptedPassword, String password) {

    String pw_hash = BCrypt.hashpw(password, encryptedPassword.substring(0, 29));
    return pw_hash.equals(encryptedPassword);
  }
}
