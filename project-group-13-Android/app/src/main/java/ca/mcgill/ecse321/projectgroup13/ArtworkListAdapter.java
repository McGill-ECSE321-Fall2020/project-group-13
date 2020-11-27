package ca.mcgill.ecse321.projectgroup13;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class ArtworkListAdapter extends ArrayAdapter<Artwork> {
    private Context context;
    private int resource;

    private static class ViewHolder{
        TextView title;
        TextView price;
        ImageView img;

    }
    public ArtworkListAdapter(Context context, int resource, ArrayList<Artwork> artworks){
        super(context,resource,artworks);
        this.context=context;
        this.resource=resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        setUpImageLoader();
        Artwork artwork=getItem(position);
        ViewHolder holder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);

        }
        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.artTitle);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.artPrice);
        ImageView img = (ImageView) convertView.findViewById(R.id.image);
        // Populate the data into the template view using the data object
        tvTitle.setText(artwork.getTitle());
        tvPrice.setText(Integer.toString(artwork.getPrice()));

        ImageLoader imageLoader = ImageLoader.getInstance();
        int artIfLoadFailed = context.getResources().getIdentifier("@drawable/art_if_load_failed",null,context.getPackageName());
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(artIfLoadFailed)
                .showImageOnFail(artIfLoadFailed)
                .showImageOnLoading(artIfLoadFailed).build();

        imageLoader.displayImage(artwork.getImgURL(), img, options);
        // Return the completed view to render on screen
        return convertView;

    }
    private void setUpImageLoader(){
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP

    }
}
