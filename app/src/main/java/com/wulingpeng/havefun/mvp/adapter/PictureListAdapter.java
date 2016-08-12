package com.wulingpeng.havefun.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wulingpeng.havefun.R;
import com.wulingpeng.havefun.mvp.model.Douban;
import com.wulingpeng.havefun.mvp.model.Gank;
import com.wulingpeng.havefun.ui.RatioImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmObject;

/**
 * 展示图片的RecyclerView的Adapter，采用瀑布流
 */
public class PictureListAdapter extends RecyclerView.Adapter<PictureListAdapter.ViewHolder> {

    private Context context;

    private LayoutInflater inflater;

    private List<? extends RealmObject> data;

    private OnItemClickListener mClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public PictureListAdapter(Context context, List<? extends RealmObject> data, OnItemClickListener listener) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.mClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.pic_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RealmObject object = data.get(position);
        if (object instanceof Gank) {
            // 如果是Gank类型的图片就通过getWidth和getHeight的到图片的实际长宽
            String url = ((Gank) object).getUrl();
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
            holder.imageView.setOriginalSize(((Gank) object).getWidth(), ((Gank) object).getHeight());
        } else {
            String url = ((Douban) object).getUrl();
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
            holder.imageView.setOriginalSize(((Douban) object).getWidth(), ((Douban) object).getHeight());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        public RatioImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(v, getLayoutPosition());
                }
            });
        }
    }
}
