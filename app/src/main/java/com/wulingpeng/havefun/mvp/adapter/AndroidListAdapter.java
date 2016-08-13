package com.wulingpeng.havefun.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wulingpeng.havefun.R;
import com.wulingpeng.havefun.mvp.model.Android;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wulinpeng on 16/8/13.
 */
public class AndroidListAdapter extends RecyclerView.Adapter<AndroidListAdapter.ViewHolder> {

    private Context context;

    private List<Android> data;

    private LayoutInflater inflater;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        public void onItemClick(String url);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AndroidListAdapter(Context context, List<Android> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.android_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = data.get(position).getDesc();
        String from = data.get(position).getWho();

        holder.textView.setText("Title:" + title + "\nFrom:" + (from == null ? "unknow" : from));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(data.get(getLayoutPosition()).getUrl());
                    }
                }
            });
        }
    }
}
