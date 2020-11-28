package ca.mcgill.ecse321.projectgroup13;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cz.msebera.android.httpclient.Header;
/*
         page displaying artwork details to user subsequent to click on buy
*/
public class ArtworkDetail extends AppCompatActivity {
    private String error = null;
    private Context context;
    private int artId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        //create intent in order to pass on to the checkout page
        artId = intent.getIntExtra("artworkID", 0);
        System.out.println("The artid issss: " + artId);

        //in order to know the specified artwork and which detail page to display
        RequestParams params = new RequestParams();
        String artworkUrl = "artwork/byId/" + artId;
        HttpUtils.get(artworkUrl, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //if successful must set necessary contents, otherwise if not handled will cause failure
                try {
                    String title = response.getString("title");
                    TextView tvTitle = (TextView) findViewById(R.id.title);
                    tvTitle.setText(title);

                    String image = response.getString("imageUrl");
                    ImageView img = (ImageView) findViewById(R.id.image);

                    ImageLoader imageLoader = ImageLoader.getInstance();

                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                            .cacheOnDisc(true).resetViewBeforeLoading(true).build();

                    imageLoader.displayImage(image, img, options);

                    //for the price of the artwork to display, retrieve it from the backend response
                    String price = response.getString("worth");
                    TextView tvPrice = (TextView) findViewById(R.id.price);
                    tvPrice.setText("$" + price);

                    //for the artists of the artwork to display, retrieve it from the backend response
                    JSONArray artists = response.getJSONArray("artist");
                    TextView tvArtists = (TextView) findViewById(R.id.artists);
                    String by = "By ";
                    for(int i = 0; i <= artists.length()-1; i++){
                        by += artists.getJSONObject(i).getString("username") + ", ";
                    }
                    by = by.substring(0, by.length() - 2);
                    tvArtists.setText(by);

                    String description = response.getString("description");
                    TextView tvDescription = (TextView) findViewById(R.id.artDescription);
                    if (description == "null") {
                        tvDescription.setText("");
                    } else {
                        tvDescription.setText(description);
                    }
                    //for each of the artwork parameters to display, retrieve it from the backend response
                    String creationDate = response.getString("creationDate");
                    TextView tvCreationDate = (TextView) findViewById(R.id.creationDate);
                    tvCreationDate.setText(creationDate);

                    String dimensions = response.getString("dimensions");
                    TextView tvDimensions = (TextView) findViewById(R.id.dimensions);
                    tvDimensions.setText(dimensions);

                    String medium = response.getString("medium");
                    TextView tvMedium = (TextView) findViewById(R.id.medium);
                    tvMedium.setText(medium);

                    String collection = response.getString("collection");
                    TextView tvCollection = (TextView) findViewById(R.id.collection);
                    tvCollection.setText(collection);

                    //if sold change the availability as necessary
                    Boolean available = response.getBoolean("artworkSold");
                    TextView tvAvailable = (TextView) findViewById(R.id.available);
                    if (available == true) {
                        tvAvailable.setText("No");
                    }else{
                        tvAvailable.setText("Yes");
                    }


                    Boolean onPremise = response.getBoolean("isOnPremise");
                    TextView tvOnPremise = (TextView) findViewById(R.id.onPremise);
                    if (onPremise == true) {
                        tvOnPremise.setText("Yes");
                    }else{
                        tvOnPremise.setText("No");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                    error += "!!!!";
                } catch (JSONException e) {
                    error += e.getMessage();
                    error += "!!!";
                }
                refreshErrorMessage();
            }
        });

    }
    /*
    In case of failure, set a visible error message for ease of user
     */
    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    /*
       method to handle the buy action click of the user, will show him more details on the painting
       @param View v: this is the view to which to show to the user
    */
    public void buy(View v){
        String username = isLoggedIn();
        if(username == ""){
            Intent login = new Intent(ArtworkDetail.this, Login.class);
            startActivity(login);
        }else{
            Intent checkout = new Intent(ArtworkDetail.this, Checkout.class);
            checkout.putExtra("artworkID", artId);
            startActivity(checkout);
        }
    }
    /*
      This method is a helper method used to get the username of the user which is currently logged in
      @return String username of logged in user
      */
    private String isLoggedIn(){
        for (File file: this.getFilesDir().listFiles()) {
            if (file.getName().contains("token")){
                return file.getName().substring(5);
            }
        }
        return "";
    }
}