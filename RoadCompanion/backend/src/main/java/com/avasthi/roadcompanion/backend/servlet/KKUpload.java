package com.avasthi.roadcompanion.backend.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.avasthi.roadcompanion.common.KKConstants;

/**
 * Created by vavasthi on 24/9/15.
 */

public class KKUpload extends HttpServlet {

    private final static Logger logger = Logger.getLogger(KKUpload.class.getName());

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
        List<BlobKey> blobKeys = blobs.get(KKConstants.BLOB_STORE_KEY_FILE);
        if (blobKeys == null || blobKeys.isEmpty()) {
        } else {
            res.setHeader(KKConstants.BLOB_CLOUD_KEY, blobKeys.get(0).getKeyString());
        }
    }
}