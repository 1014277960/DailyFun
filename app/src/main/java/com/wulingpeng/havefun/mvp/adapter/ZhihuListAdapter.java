package com.wulingpeng.havefun.mvp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wulingpeng.havefun.R;
import com.wulingpeng.havefun.mvp.model.Zhihu;
import com.wulingpeng.havefun.ui.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wulinpeng on 16/8/12.
 */
public class ZhihuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;

    private LayoutInflater inflater;

    private List<Zhihu> items;

    private List<Zhihu> header;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        public void onItemClick(String url);
    }


    public ZhihuListAdapter(Context context, List<Zhihu> items, List<Zhihu> header) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.items = items;
        this.header = header;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Zhihu.TYPE_HEADER) {
            View view = inflater.inflate(R.layout.zhihu_header, parent, false);
            return new TopViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.zhihu_item, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            onBindHeader(holder);
        } else {
            onBindItems(holder, position);
        }
    }

    private void onBindHeader(RecyclerView.ViewHolder holder) {
        ViewPager viewPager = ((TopViewHolder) holder).pager;
        viewPager.setAdapter(new ViewPagerAdapter());
        // 不设置总会出现空白页，因为header总是5个，所以设置4个，
        // 这里还好不多，但是如果很多怎么办？
        // 都缓存就可能OOM
        viewPager.setOffscreenPageLimit(4);
    }

    private void onBindItems(RecyclerView.ViewHolder holder, int position) {
        Glide.with(context).load(items.get(position - 1).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(((ItemViewHolder) holder).imageView);
        ((ItemViewHolder) holder).textView.setText(items.get(position - 1).getTitle());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Zhihu.TYPE_HEADER;
        } else {
            return Zhihu.TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        // 加上header
        return items.size() + 1;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        public ImageView imageView;

        @BindView(R.id.title)
        public TextView textView;

        public ItemViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(items.get(getLayoutPosition() - 1).getUrl());
                    }
                }
            });
        }
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pager)
        public ViewPager pager;

        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return header.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView view = new ImageView(context);
            final ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(lp);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setClickable(true);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(header.get(position).getUrl());
                    }
                }
            });
            Glide.with(context).load(header.get(position).getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(view);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(container.getChildAt(position));
        }
    }
}
