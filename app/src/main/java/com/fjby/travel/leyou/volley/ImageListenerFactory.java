package com.fjby.travel.leyou.volley;

import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.fjby.travel.leyou.utils.LogUtil;

public class ImageListenerFactory {

    public static ImageListener getImageListener(final ImageView view, final int defaultImageResId, final int errorImageResId) {
        return new ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (errorImageResId != 0) {
                    view.setImageResource(errorImageResId);
                }
                LogUtil.v("ImageListener---------------error:   " + error);
            }

            @Override
            public void onResponse(ImageContainer response, boolean isImmediate) {
                LogUtil.v("ImageListener---------------response:   " + response);
                if (response.getBitmap() != null) {
                    LogUtil.v("ImageListener---------------response1111:   " + response);
                    if (view.getTag().toString() == response.getRequestUrl()) {
                        view.setImageBitmap(response.getBitmap());
                    } else {
                        LogUtil.i("图片错位");
                    }
                } else if (defaultImageResId != 0) {
                    view.setImageResource(defaultImageResId);
                    LogUtil.v("ImageListener---------------response22222222222:   " + response);
                }
            }
        };
    }
}