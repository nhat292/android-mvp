package com.techco.igotrip.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.techco.common.AppConstants;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.ApiEndPoint;
import com.techco.igotrip.ui.custom.ProgressWheel;
import com.techco.igotrip.ui.custom.photo_view.PhotoView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nhat on 1/22/18.
 */

public class ImageViewPagerAdapter extends PagerAdapter {


    private List<String> items;
    private Context context;
    private LayoutInflater inflater;

    public ImageViewPagerAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        final ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.image_pager_item, container, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        String item = items.get(position);
        String image;
        if (item.startsWith(AppConstants.HTTP_PREFIX)) {
            image = item;
        } else {
            image = ApiEndPoint.BASE_URL + item;
        }
        Glide.with(context)
                .load(image)
                .into(holder.imageView);
        holder.imageView.getmAttacher().setPhotoClickListener(() -> {

        });
        container.addView(view);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.progressLoading)
        ProgressWheel progressLoading;
        @BindView(R.id.imageView)
        PhotoView imageView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
