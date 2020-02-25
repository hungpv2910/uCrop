package com.yalantis.ucrop.helper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.yalantis.ucrop.R;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;
import com.yalantis.ucrop.view.CropImageView;
import com.yalantis.ucrop.view.widget.AspectRatioTextView;

import java.util.ArrayList;
import java.util.List;

import static com.yalantis.ucrop.UCrop.Options.EXTRA_ASPECT_RATIO_SELECTED_BY_DEFAULT;

public class AspectRatioHelper {
    public static List<ViewGroup> initAspectRatio(Intent intent, LinearLayout viewGroup, int activeControlsWidgetColor) {
        List<ViewGroup> resultViewAspectRatio = new ArrayList<>();
        List<AspectRatio> aspectRatioList = getListAspectRatio(viewGroup.getContext(), intent);

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        for (AspectRatio item : aspectRatioList) {
            ViewGroup aspectRatioView = createAspectRatioView(item, layoutInflater, activeControlsWidgetColor);
            viewGroup.addView(aspectRatioView);
            resultViewAspectRatio.add(aspectRatioView);
        }

        return resultViewAspectRatio;
    }

    public static int getAspectRatioSelected(Intent intent) {
        if (intent != null && intent.hasExtra(EXTRA_ASPECT_RATIO_SELECTED_BY_DEFAULT)) {
            return intent.getIntExtra(EXTRA_ASPECT_RATIO_SELECTED_BY_DEFAULT, 0);
        }
        return 2;
    }


    public static void scrollCenterAspectRatio(HorizontalScrollView scrollView, ViewGroup view) {
        int center = scrollView.getScrollX() + scrollView.getWidth() / 2;

        int viewLeft = view.getLeft();
        int viewWidth = view.getWidth();
        scrollView.smoothScrollBy((viewLeft + viewWidth / 2) - center, 0);
    }

    private static ViewGroup createAspectRatioView(AspectRatio item, LayoutInflater layoutInflater, int activeControlsWidgetColor) {
        FrameLayout wrapperAspectRatio = (FrameLayout) layoutInflater.inflate(R.layout.ucrop_aspect_ratio, null);
        AspectRatioTextView aspectRatioTextView = ((AspectRatioTextView) wrapperAspectRatio.getChildAt(0));
        aspectRatioTextView.setActiveColor(activeControlsWidgetColor);
        aspectRatioTextView.setAspectRatio(item);
        return wrapperAspectRatio;
    }

    private static List<AspectRatio> getListAspectRatio(Context context, Intent intent) {
        ArrayList<AspectRatio> aspectRatioList = intent.getParcelableArrayListExtra(UCrop.Options.EXTRA_ASPECT_RATIO_OPTIONS);

        if (aspectRatioList == null || aspectRatioList.isEmpty()) {
            aspectRatioList = new ArrayList<>();
            aspectRatioList.add(new AspectRatio(null, 1, 1));
            aspectRatioList.add(new AspectRatio(null, 3, 4));
            aspectRatioList.add(new AspectRatio(context.getString(R.string.ucrop_label_original).toUpperCase(),
                    CropImageView.SOURCE_IMAGE_ASPECT_RATIO, CropImageView.SOURCE_IMAGE_ASPECT_RATIO));
            aspectRatioList.add(new AspectRatio(null, 3, 2));
            aspectRatioList.add(new AspectRatio(null, 16, 9));
        }

        return aspectRatioList;
    }

}
