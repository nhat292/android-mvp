package com.techco.igotrip.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.techco.common.AppConstants;
import com.techco.igotrip.BuildConfig;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Article;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nhat on 5/1/17.
 */

public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View contentView;
    @BindView(R.id.imgArticle)
    ImageView imgArticle;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtDistance)
    TextView txtDistance;

    private Map<Marker, Article> markerMap;
    private Context context;

    public MapInfoWindowAdapter(Context context, Map<Marker, Article> markerMap) {
        this.context = context;
        this.markerMap = markerMap;
        contentView = LayoutInflater.from(context).inflate(R.layout.info_window_item, null);
        ButterKnife.bind(this, contentView);
    }

    @Override
    public View getInfoWindow(final Marker marker) {
        Article data = markerMap.get(marker);
        if (data != null) {
            if (!data.getImage().isEmpty()) {
                if (data.getImage().startsWith(AppConstants.HTTP_PREFIX)) {
                    Glide.with(context)
                            .load(data.getImage())
                            .centerCrop()
                            .dontAnimate()
                            .into(imgArticle);
                } else {
                    Glide.with(context)
                            .load(BuildConfig.BASE_URL + data.getImage())
                            .centerCrop()
                            .dontAnimate()
                            .into(imgArticle);
                }
            } else {
            }
            txtTitle.setText(data.getTitle());
            double distance = data.getDistance();
            String distanceStr;
            if (distance >= 1.0) {
                distanceStr = String.format("%.2f", distance) + " km";
            } else {
                distanceStr = (int) (distance * 1000.0) + " m";
            }
            txtDistance.setText(String.format(context.getString(R.string.distance_format), distanceStr));
            return contentView;
        }
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
