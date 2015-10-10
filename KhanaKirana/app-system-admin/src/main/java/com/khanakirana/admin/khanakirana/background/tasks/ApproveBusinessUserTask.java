package com.khanakirana.admin.khanakirana.background.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.khanakirana.admin.khanakirana.R;
import com.khanakirana.admin.khanakirana.activities.KKAddMeasurementUnitActivity;
import com.khanakirana.admin.khanakirana.adapters.KKRecyclerViewMainScreenAdapter;
import com.khanakirana.backend.sysadminApi.SysadminApi;
import com.khanakirana.backend.sysadminApi.model.BusinessAccount;
import com.khanakirana.backend.sysadminApi.model.MeasurementUnit;
import com.khanakirana.common.ServerInteractionReturnStatus;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class ApproveBusinessUserTask extends AsyncTask<Void, Void, Integer> {

    private final Activity context;
    private final KKRecyclerViewMainScreenAdapter.ViewHolder viewHolder;
    private final SysadminApi sysadminApi;
    private final Boolean approve;

    private Logger logger = Logger.getLogger(ApproveBusinessUserTask.class.getName());


    public ApproveBusinessUserTask(Activity context,
                                   KKRecyclerViewMainScreenAdapter.ViewHolder viewHolder,
                                   SysadminApi sysadminApi,
                                   Boolean approve) {
        this.context = context;
        this.viewHolder = viewHolder;
        this.sysadminApi = sysadminApi;
        this.approve = approve;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            BusinessAccount account = sysadminApi.approveBusiness(viewHolder.getActionable().getId(), approve).execute();
            return ServerInteractionReturnStatus.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerInteractionReturnStatus.FATAL_ERROR;
    }
    protected void onPostExecute (Integer result) {

        switch (result) {
            case ServerInteractionReturnStatus.SUCCESS:
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        viewHolder.setActionableStatus(Boolean.TRUE);
                    }
                });
                break;
            default:
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        viewHolder.setActionableStatus(Boolean.FALSE);
                    }
                });
                Toast.makeText(context, R.string.business_user_approval_failed, Toast.LENGTH_LONG);
        }
    }
}
