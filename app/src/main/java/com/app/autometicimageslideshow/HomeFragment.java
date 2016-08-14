package com.app.autometicimageslideshow;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    public static final String ARG_ITEM_ID = "home_fragment";
    private static final long ANIM_VIEWPAGER_DELAY = 5000;
    private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;
    private ViewPager mViewPager;
    TextView imgNameTxt;
    PageIndicator mIndicator;
    List<ImageItemModel> itemModelList;
    boolean stopSliding = false;
    String message;

    private Runnable animateViewPager;
    private Handler handler;
    List<ImageItemModel> imageItemModelList = new ArrayList<ImageItemModel>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);

        sendRequest();

        mIndicator.setOnPageChangeListener(new PageChangeListener());
        mViewPager.setOnPageChangeListener(new PageChangeListener());
        mViewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction()) {

                    case MotionEvent.ACTION_CANCEL:
                        break;

                    case MotionEvent.ACTION_UP:
                        // calls when touch release on ViewPager
                        if (imageItemModelList != null && imageItemModelList.size() != 0) {
                            stopSliding = false;
                            runnable(imageItemModelList.size());
                            handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY_USER_VIEW);
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        // calls when ViewPager touch
                        if (handler != null && stopSliding == false) {
                            stopSliding = true;
                            handler.removeCallbacks(animateViewPager);
                        }
                        break;
                }
                return false;
            }
        });


        return view;
    }

    public void runnable(final int size) {
        handler = new Handler();
        animateViewPager = new Runnable() {
            public void run() {
                if (!stopSliding) {
                    if (mViewPager.getCurrentItem() == size - 1) {
                        mViewPager.setCurrentItem(0);
                    } else {
                        mViewPager.setCurrentItem(
                                mViewPager.getCurrentItem() + 1, true);
                    }
                    handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
                }
            }
        };
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

   /* @Override
    public void onResume() {
        if (imageItemModelList == null) {
            sendRequest();
        } else {
            mViewPager.setAdapter(new ImageSlideAdapter(getActivity(), imageItemModelList, HomeFragment.this));
            mIndicator.setViewPager(mViewPager);
            runnable(imageItemModelList.size());
            handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
        }
        super.onResume();
    }


    @Override
    public void onPause() {
        if (handler != null) {
            //Remove callback
            handler.removeCallbacks(animateViewPager);
        }
        super.onPause();
    }*/

    private void sendRequest() {
        ConnectivityChecker connectivityChecker = new ConnectivityChecker(getActivity());
        if (connectivityChecker.isConnectedToInternet()) {
            ApiInterface.ApiClient.getApiInterface().GetAllPictures().enqueue(new Callback<ImageItemModel>() {
                @Override
                public void onResponse(Call<ImageItemModel> call, Response<ImageItemModel> response) {
                    try {
                        Toast.makeText(getActivity(), ""+response.message()+"\n"+response.code()+"\n"+response.isSuccessful(), Toast.LENGTH_SHORT).show();

                          imageItemModelList = response.body().getResults();
                        mViewPager.setAdapter(new ImageSlideAdapter(getActivity(), imageItemModelList, HomeFragment.this));
                        mIndicator.setViewPager(mViewPager);
              //          imgNameTxt.setText("" + ((ImageItemModel) imageItemModelList.get(mViewPager.getCurrentItem())).getTitle());
                        runnable(imageItemModelList.size());
                        handler.postDelayed(animateViewPager,ANIM_VIEWPAGER_DELAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ImageItemModel> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
/*
            if (imageItemModelList != null) {
                imgNameTxt.setText("" + ((ImageItemModel) imageItemModelList.get(mViewPager.getCurrentItem())).getTitle());
            }
*/
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
