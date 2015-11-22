package com.avasthi.roadcompanion.background.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.khanakirana.admin.khanakirana.R;
import com.avasthi.roadcompanion.adapters.KKRecyclerViewMainScreenAdapter;
import com.avasthi.roadcompanion.common.ServerInteractionReturnStatus;

import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class ApproveBusinessUserTask extends AsyncTask<Void, Void, Integer> {

    private final Activity context;
    private final KKRecyclerViewMainScreenAdapter.ViewHolder viewHolder;
    private final Boolean approve;

    private Logger logger = Logger.getLogger(ApproveBusinessUserTask.class.getName());


    public ApproveBusinessUserTask(Activity context,
                                   KKRecyclerViewMainScreenAdapter.ViewHolder viewHolder,
                                   Boolean approve) {
        this.context = context;
        this.viewHolder = viewHolder;
        this.approve = approve;
    }

    @Override
    protected Integer doInBackground(Void... params) {

/*        try {
            BusinessAccount account = EndpointManager.getEndpoints(context).approveBusiness(viewHolder.getActionable().getId(), approve).execute();
            return ServerInteractionReturnStatus.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
        }*/
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
