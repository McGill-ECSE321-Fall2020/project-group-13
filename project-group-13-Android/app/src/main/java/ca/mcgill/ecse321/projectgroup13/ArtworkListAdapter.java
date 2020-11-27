package ca.mcgill.ecse321.projectgroup13;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class ArtworkListAdapter extends ArrayAdapter<Artwork> {
    private Context context;
    private int resource;

    private static class ViewHolder{
        TextView name;
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
        Artwork artwork=getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }
        // Lookup view for data population
        //TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        //TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        // Populate the data into the template view using the data object
        //tvName.setText(user.name);
        //tvHome.setText(user.hometown);
        // Return the completed view to render on screen
        return convertView;

    }
    private void setUpImageLoader(){


    }
}
