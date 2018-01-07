package com.techco.igotrip.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.techco.common.AppConstants;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.ApiEndPoint;
import com.techco.igotrip.data.network.model.object.ArticleImage;

import java.util.List;

/**
 * Created by Nhat on 4/23/17.
 */

public class ImagePagerAdapter extends PagerAdapter {
    private List<ArticleImage> listItems;
    private Context context;
    private LayoutInflater layoutInflater;

    public ImagePagerAdapter(Context context, List<ArticleImage> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.image_swipe_item, container, false);
        ImageView image = view.findViewById(R.id.image_item);
        if (listItems.get(position).getImage().startsWith(AppConstants.HTTP_PREFIX)) {
            Glide.with(context)
                    .load(listItems.get(position).getImage())
                    .into(image);
        } else {
            Glide.with(context)
                    .load(ApiEndPoint.BASE_URL + listItems.get(position).getImage())
                    .into(image);
        }
        image.setOnClickListener(v ->
                new ImageViewer.Builder<>(context, listItems)
                        .setFormatter(item -> {
                            if (item.getImage().startsWith(AppConstants.HTTP_PREFIX)) {
                                return item.getImage();
                            } else {
                                return ApiEndPoint.BASE_URL + item.getImage();
                            }
                        })
                        .setStartPosition(position)
                        .allowZooming(true)
                        .allowSwipeToDismiss(true)
                        .show()
        );
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
