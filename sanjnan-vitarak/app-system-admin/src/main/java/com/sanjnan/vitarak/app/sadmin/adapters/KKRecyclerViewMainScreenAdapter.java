package com.sanjnan.vitarak.app.sadmin.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khanakirana.admin.khanakirana.R;
import com.khanakirana.backend.sysadminApi.model.Actionable;

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
        public ViewHolder(final Activity context, View itemView) {

            super(itemView);
            this.context = context;
        }
        public void setActionable(Actionable actionable) {
            this.actionable = actionable;
        }
        public Actionable getActionable() {
            return actionable;
        }
        public void setActionableStatus(Boolean status) {
            actionable.setDone(status);
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
        ((TextView)holder.itemView.findViewById(R.id.kk_sysadmin_main_count)).setText(String.valueOf(actionables[position].getCount()));
        ((TextView)holder.itemView.findViewById(R.id.kk_sysadmin_main_action)).setText(context.getResources().getStringArray(R.array.server_shared_strings)[actionables[position].getTitle()]);
        ((TextView)holder.itemView.findViewById(R.id.kk_sysadmin_main_details)).setText(context.getResources().getStringArray(R.array.server_shared_strings)[actionables[position].getDescription()]);
    }

    @Override
    public int getItemCount() {
        return actionables.length;
    }

}

/*
            ImageButton button_ok = (ImageButton)(itemView.findViewById(R.id.actionable_button_yes));
            button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pb.setVisibility(View.VISIBLE);
                    new ApproveBusinessUserTask(context, ViewHolder.this, Boolean.TRUE).execute();


                }
            });
            ImageButton button_cancel = (ImageButton)(itemView.findViewById(R.id.actionable_button_no));
            button_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pb.setVisibility(View.VISIBLE);
                    new ApproveBusinessUserTask(context, ViewHolder.this, Boolean.FALSE).execute();

                }
            });
            setButtonVisibility();
        private void setButtonVisibility() {
            ImageButton button_ok = (ImageButton)(itemView.findViewById(R.id.actionable_button_yes));
            if (actionable != null && actionable.getDone()) {
                button_ok.setVisibility(View.INVISIBLE);
            }
            ImageButton button_cancel = (ImageButton)(itemView.findViewById(R.id.actionable_button_no));
            if (actionable != null && actionable.getDone()) {
                button_cancel.setVisibility(View.INVISIBLE);
            }
        }
            pb = (ProgressBar)(itemView.findViewById(R.id.progressBar));
            pb.setVisibility(View.INVISIBLE);
                        private ProgressBar pb;
            pb.setVisibility(View.INVISIBLE);


 */