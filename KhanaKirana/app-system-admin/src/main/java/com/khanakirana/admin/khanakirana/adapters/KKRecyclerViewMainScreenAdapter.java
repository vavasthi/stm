package com.khanakirana.admin.khanakirana.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.khanakirana.admin.khanakirana.R;
import com.khanakirana.admin.khanakirana.activities.KhanaKiranaMainAdminActivity;
import com.khanakirana.admin.khanakirana.background.tasks.ApproveBusinessUserTask;
import com.khanakirana.backend.sysadminApi.model.Actionable;
import com.khanakirana.common.KKActionTypes;
import com.khanakirana.common.KKConstants;

/**
 * Created by vavasthi on 8/10/15.
 */
public class KKRecyclerViewMainScreenAdapter extends RecyclerView.Adapter<KKRecyclerViewMainScreenAdapter.ViewHolder> {

    Actionable[] actionables;
    Activity context;
    public KKRecyclerViewMainScreenAdapter(Activity context, Actionable[] actionables) {
        this.context = context;
        this.actionables = actionables;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final Activity context;
        private Actionable actionable;
        private ProgressBar pb;
        public ViewHolder(final Activity context, View itemView) {

            super(itemView);
            this.context = context;
            pb = (ProgressBar)(itemView.findViewById(R.id.progressBar));
            pb.setVisibility(View.INVISIBLE);
            ImageButton button_ok = (ImageButton)(itemView.findViewById(R.id.actionable_button_yes));
            if (actionable != null && actionable.getDone()) {
                button_ok.setVisibility(View.INVISIBLE);
            }
            button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pb.setVisibility(View.VISIBLE);
                    new ApproveBusinessUserTask(context, ViewHolder.this, KhanaKiranaMainAdminActivity.getEndpoints(), Boolean.TRUE).execute();


                }
            });
            ImageButton button_cancel = (ImageButton)(itemView.findViewById(R.id.actionable_button_no));
            if (actionable != null && actionable.getDone()) {
                button_cancel.setVisibility(View.INVISIBLE);
            }
            button_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pb.setVisibility(View.VISIBLE);
                    new ApproveBusinessUserTask(context, ViewHolder.this, KhanaKiranaMainAdminActivity.getEndpoints(), Boolean.FALSE).execute();

                }
            });
        }
        public void setActionable(Actionable actionable) {
            this.actionable = actionable;
        }
        public Actionable getActionable() {
            return actionable;
        }
        public void setActionableStatus(Boolean status) {
            actionable.setDone(status);
            pb.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public KKRecyclerViewMainScreenAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.khana_kirana_sysadmin_main_item, parent, false);
        KKRecyclerViewMainScreenAdapter.ViewHolder vh = new ViewHolder(context, v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.setActionable(actionables[position]);
        ((TextView)holder.itemView.findViewById(R.id.kk_sysadmin_main_title)).setText(context.getResources().getStringArray(R.array.server_shared_strings)[actionables[position].getTitle()]);
        ((TextView)holder.itemView.findViewById(R.id.kk_sysadmin_main_action)).setText(context.getResources().getStringArray(R.array.server_shared_strings)[actionables[position].getActionTitle()]);
        ((TextView)holder.itemView.findViewById(R.id.kk_sysadmin_main_details)).setText(actionables[position].getDetails());
        ((TextView)holder.itemView.findViewById(R.id.kk_sysadmin_main_description)).setText(context.getResources().getStringArray(R.array.server_shared_strings)[actionables[position].getDescription()]);
    }

    @Override
    public int getItemCount() {
        return actionables.length;
    }

}
