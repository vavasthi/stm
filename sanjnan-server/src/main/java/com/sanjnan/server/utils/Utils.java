/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.utils;

/**
 * Created by subrat on 15/3/16.
 */
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

    static Logger log = Logger.getLogger(Properties.class);
    private static java.util.Properties serverConf;

    public static String readValueOf(String attribute) throws IOException {

        if(serverConf == null){
            InputStream inputStream = Properties.class.getResourceAsStream("/application.properties");
            if (inputStream == null) {
                throw new FileNotFoundException("property file not found in the classpath, \nMost probably -DSERVER_ENVIRONMENT variable is not set, please start server by passing proper SERVER_ENVIRONMENT vraible");
            }
            serverConf = new java.util.Properties();
            serverConf.load(inputStream);
        }
        return serverConf.getProperty(attribute).trim();
    }

}