package com.linsh.protocol.impl.image;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.linsh.protocol.config.ImageConfig;
import com.linsh.protocol.image.ImageLoader;
import com.linsh.protocol.image.ImageManager;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/21
 *    desc   : 放到沙发上的范德萨发
 * </pre>
 */
public class GlideImageManager implements ImageManager {

    private final RequestOptions defaultOptions;

    public GlideImageManager(ImageConfig config) {
        RequestOptions options = new RequestOptions();
        if (config.placeholder() > 0)
            options = options.placeholder(config.placeholder());
        if (config.placeholder() > 0)
            options = options.error(config.error());
        this.defaultOptions = options;
    }

    @Override
    public void load(int res, ImageView view) {
        Glide.with(view)
                .load(res)
                .apply(defaultOptions)
                .into(view);
    }

    @Override
    public void load(File file, ImageView view) {
        Glide.with(view)
                .load(file)
                .apply(defaultOptions)
                .into(view);
    }

    @Override
    public void load(String url, ImageView view) {
        Glide.with(view)
                .load(url)
                .apply(defaultOptions)
                .into(view);
    }

    @Override
    public void load(String url, ImageView view, int placeholder) {
        Glide.with(view)
                .load(url)
                .apply(defaultOptions.clone().placeholder(placeholder))
                .into(view);
    }

    @Override
    public void load(String url, ImageView view, int placeholder, int error) {
        Glide.with(view)
                .load(url)
                .apply(new RequestOptions().placeholder(placeholder).error(error))
                .into(view);
    }

    @Override
    public ImageLoader with(String url) {
        return new ImageLoaderImpl(url);
    }

    @Override
    public ImageLoader with(int resId) {
        return new ImageLoaderImpl(resId);
    }

    @Override
    public ImageLoader with(File file) {
        return new ImageLoaderImpl(file);
    }
}
