package com.avasthi.roadcompanion.background.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.avasthi.roadcompanion.KKAndroidConstants;
import com.khanakirana.admin.khanakirana.R;
import com.avasthi.roadcompanion.activities.KKAddProductInMasterListActivity;
import com.avasthi.roadcompanion.utils.EndpointManager;
import com.khanakirana.backend.sysadminApi.model.MasterItem;
import com.avasthi.roadcompanion.common.ServerInteractionReturnStatus;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Formatter;
import java.util.UUID;
import java.util.logging.Logger;


/**
 * Created by vavasthi on 14/9/15.
 */
public class AddMasterItemTask extends AsyncTask<Void, Void, Integer> {

    private final KKAddProductInMasterListActivity context;
    private final String title;
    private final String description;
    private final String upc;
    private final String imageType;
    private final String measurementCategory;
    private final byte[] content;

    public static final String IMAGE_PART_NAME = "imageToBeUploaded";
    public static final String UNKNOWN_UPC = "UNKNOWN-UPC-CODE";
    private Logger logger = Logger.getLogger(AddMasterItemTask.class.getName());


    public AddMasterItemTask(KKAddProductInMasterListActivity context,
                             String title,
                             String description,
                             String upc,
                             String imageType,
                             String measurementCategory,
                             byte[] content) {
        this.context = context;
        this.title = title;
        this.description = description;
        if (upc == null || upc.trim().equals("")) {
            this.upc = UNKNOWN_UPC;
        }
        else {
            this.upc = upc;
        }
        this.imageType = imageType;
        this.measurementCategory = measurementCategory;
        this.content = content;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try{
            String uploadURL = EndpointManager.getEndpoints(context).getUploadURL().execute().getUrl();
            OkHttpClient httpClient = new OkHttpClient();
            String filename = UUID.randomUUID().toString() + ".png";
            RequestBody body = new MultipartBuilder(IMAGE_PART_NAME)
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart(KKAndroidConstants.BLOB_STORE_KEY_FILE, filename, RequestBody.create(MediaType.parse("image/png"), content))
                    .build();
            Request request = new Request.Builder().url(uploadURL).post(body).build();
            Response response = httpClient.newCall(request).execute();
            logger.info("Upload request/response is " + request.toString() + "\n" + response.toString());
            String cloudKey = response.header(KKAndroidConstants.BLOB_CLOUD_KEY);
            MasterItem mi = EndpointManager.getEndpoints(context).addItemInMasterList(title, description, upc, imageType, cloudKey, measurementCategory).execute();
            if (!response.isSuccessful()) {

                return ServerInteractionReturnStatus.FATAL_ERROR;
            }
            return ServerInteractionReturnStatus.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerInteractionReturnStatus.FATAL_ERROR;
    }
    protected void onPostExecute (Integer result) {

        switch (result) {
            case ServerInteractionReturnStatus.SUCCESS:
                context.dismisProgressIndicator();
                break;
            default:
                Formatter formatter = new Formatter();
                String msg = formatter.format(context.getString(R.string.kk_master_item_addition_failed), title).toString();
                Toast.makeText(context, msg, Toast.LENGTH_LONG);
        }
    }
}
