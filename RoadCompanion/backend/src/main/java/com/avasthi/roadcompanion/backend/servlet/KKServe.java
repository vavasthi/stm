package com.avasthi.roadcompanion.backend.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

/**
 * Created by vavasthi on 24/9/15.
 */

public class KKServe extends HttpServlet {

    private final static Logger logger = Logger.getLogger(KKServe.class.getName());

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
        logger.info("Serving BlobKey = " + blobKey);
        blobstoreService.serve(blobKey, res);
    }
}