package com.wowo.wowo.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wowo.wowo.Activity.GroupDetailsActivity;
import com.wowo.wowo.Base.BaseActivity;
import com.wowo.wowo.R;
import com.wowo.wowo.Views.CircleImageView.CircleImageView;

import java.util.List;

public class GroupRcAdapter extends RecyclerView.Adapter<GroupRcAdapter.ViewHolder>{
    private Bundle bundle;
    private Context mContext;
    private List<String> list;

    public GroupRcAdapter(Context mContext,List<String> list) {
        this.list = list;
        this.mContext= mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gv_group, parent, false);
        GroupRcAdapter.ViewHolder viewHolder = new GroupRcAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
            final String name  = list.get(position);
            holder.mTvName.setText(name);
            holder. mLyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("pos", position + "");
                ((BaseActivity)mContext).openActivity(GroupDetailsActivity.class,bundle);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvName;
        CircleImageView mCiHead;
        LinearLayout mLyItem;

        ViewHolder(View itemView) {
            super(itemView);
            mLyItem =  itemView.findViewById(R.id.item_gv_group);
            mTvName = itemView.findViewById(R.id.item_tv_name);
            mCiHead = itemView.findViewById(R.id.item_ci_group);
        }
    }
    }
