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

/**
 * This class configures the listView in the home page. Think of it like a manager. It automatically updates the listView based on the
 * content of the array "artworks"
 */
public class ArtworkListAdapter extends ArrayAdapter<Artwork> {
    private Context context;
    private int resource;


    public ArtworkListAdapter(Context context, int resource, ArrayList<Artwork> artworks){
        super(context,resource,artworks);
        this.context=context;
        this.resource=resource;
    }

    /**
     * This is a override of the default getView() of arrayAdapter class. It loads the view according to the item in position.
     * @param position of the item in listView
     * @param convertView view in question
     * @param parent parent group
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        setUpImageLoader();
        Artwork artwork=getItem(position);
        // If no external view is used, inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);

        }
        // find the views to update
        TextView tvTitle = (TextView) convertView.findViewById(R.id.artTitle);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.artPrice);
        ImageView img = (ImageView) convertView.findViewById(R.id.image);
        // update the views
        tvTitle.setText(artwork.getTitle());
        tvPrice.setText("Price: $"+Integer.toString(artwork.getPrice()));

        //here, I am using an external library to help with loading images. The code below is adapted from a related blog
        // From https://github.com/nostra13/Android-Universal-Image-Loader
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
    /*configure image loader before loading images. For more information, see https://github.com/nostra13/Android-Universal-Image-Loader*/
    private void setUpImageLoader(){

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


    }
}
