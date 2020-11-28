package ca.mcgill.ecse321.projectgroup13;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import cz.msebera.android.httpclient.Header;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class Checkout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


    }
    private String isLoggedIn(){
        for (File file: this.getFilesDir().listFiles()) {
            if (file.getName().contains("token")){
                return file.getName().substring(5);
            }
        }
        return "";
    }
    public void submit(View v) {

        //Getting views from layout
        final EditText cardNumber = (EditText) findViewById(R.id.EditTextCardNumber);
        final EditText expiryDate = (EditText) findViewById(R.id.EditTextExpiryDate);
        final EditText cvv = (EditText) findViewById(R.id.EditTextCVV);
        final EditText nameOnCard = (EditText) findViewById(R.id.EditTextNameOnCard);
        final CheckBox checkbox = (CheckBox) findViewById(R.id.CheckBoxDelivery);

        String[] orderID = new String[1];

        //CART PUT

        //Creating Cart Request Params
        RequestParams cartParams = new RequestParams();

        //Creating Order Request Params
        RequestParams orderParams = new RequestParams();

        //Populating Order Params
        orderParams.put("cardNumber", cardNumber.getText().toString());
        orderParams.put("expirationDate", expiryDate.getText().toString());
        orderParams.put("cvv", cvv.getText().toString());
        orderParams.put("nameOnCard", nameOnCard.getText().toString());

        //Get passed in ArtID
        String username = this.isLoggedIn();
        Integer artID = getIntent().getIntExtra("artworkID", 0);
        String artPrice = getIntent().getStringExtra("artPrice");
        cartParams.put("artworkID",  artID);
        //this doesn't work
        if(nameOnCard.getText()==null||cardNumber.getText()==null||expiryDate.getText()==null||expiryDate.getText()==null||cvv.getText()==null||nameOnCard.getText().toString().isEmpty() || cardNumber.getText().toString().isEmpty() || expiryDate.getText().toString().isEmpty() || cvv.getText().toString().isEmpty()){
            //put up label instructing to fill in the required fields
            String error = "fill in the required fields";
            Toast t = Toast.makeText(this, error, Toast.LENGTH_SHORT);
            t.show();
            return;

        }

        //Creating a cart for the user
        HttpUtils.put("/user/" + username + "/edit+/cart", cartParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }
        });

        //Creating a new order
        HttpUtils.post("/user/" + username + "/new/" + "order", orderParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }
        });

        //Get most recent order ID
        HttpUtils.get("/user/" + username + "/new/" + "orders/" + "most-recent", cartParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    orderID[0] = response.getJSONObject(0).getString("orderId");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }
        });
        
        //Get most recent order ID
        HttpUtils.get("/user/" + username + "/new/" + "orders/" + "most-recent", cartParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    orderID[0] = response.getJSONObject(0).getString("orderId");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }
        });

        //Putting information from editTexts into params

        finish();

    }
}