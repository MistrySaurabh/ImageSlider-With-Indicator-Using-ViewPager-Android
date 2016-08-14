package com.app.autometicimageslideshow;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

/**
 * Created by Saurabh on 14-08-2016.
 */
public class ImageSlideAdapter extends PagerAdapter {
    Context context;
    List<ImageItemModel> itemModelList;
    HomeFragment homeFragment;

    public ImageSlideAdapter(Context context, List<ImageItemModel> itemModelList, HomeFragment homeFragment) {
        this.context = context;
        this.itemModelList = itemModelList;
        this.homeFragment = homeFragment;

    }


    @Override
    public int getCount() {
        return itemModelList.size();
    }


    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.vp_image, container, false);
        ImageView mImageView = (ImageView) view.findViewById(R.id.image_display);
        final ProgressBar mProgressBar= (ProgressBar) view.findViewById(R.id.image_progressbar);
        Glide.with(context).load(itemModelList.get(position).getImageUrl()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                mProgressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(mImageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,itemModelList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
