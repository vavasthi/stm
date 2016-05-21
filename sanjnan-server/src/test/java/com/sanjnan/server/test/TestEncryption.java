/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.test;

import com.sanjnan.server.utils.SanjnanPasswordEncryptionManager;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.testng.annotations.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

import static org.testng.Assert.assertTrue;

/**
 * Created by vinay on 2/9/16.
 */
public class TestEncryption {

  Logger logger = Logger.getLogger(TestEncryption.class);

  @Test(threadPoolSize = 25, invocationCount = 50, successPercentage = 100)
  public void testEncryption() throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException,
      BadPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException {

    String pw_hash = BCrypt.hashpw("hubble01", "$2a$10$ThmQVoMkO.GviMmy8ce4P.");
    System.out.println(pw_hash);
    String tenant = generateRandomText();
    String username = generateRandomText();
    String password = generateRandomText();
    String encryptedPassword = SanjnanPasswordEncryptionManager.INSTANCE.encrypt(tenant, username, password);
    logger.info(String.format("Tenant = %s, Username = %s, Password = %s, Encrypted = %s",
        tenant,
        username,
        password,
        encryptedPassword));
    assertTrue(SanjnanPasswordEncryptionManager.INSTANCE.matches(tenant, username, encryptedPassword, password));
  }

  private String generateRandomText() {
    Random r = new Random((new Date()).getTime());
    int size = r.nextInt(32) + 8;
    byte[] buffer = new byte[size];
    r.nextBytes(buffer);
    return new String(Base64.getEncoder().encode(buffer));
  }
}
