package ca.mcgill.ecse321.projectgroup13;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import cz.msebera.android.httpclient.Header;

public class Login extends AppCompatActivity {
    public static String error = "";
    private String username = "";
    private String password = "";
    EditText usernameInput;
    EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameInput = findViewById(R.id.EditTextUsername);
        passwordInput = findViewById(R.id.EditTextPassword);
        deleteAuthToken();
    }

    public void login(View v) {
        Login self = this;
        error = "";
        username = usernameInput.getText().toString();
        password = passwordInput.getText().toString();
        RequestParams params = new RequestParams();

        //send login request
        HttpUtils.get("/user/" + username + "/login?password=" + password, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //save current authentication data
                boolean success = createAuthToken();

                if (success){
                    //TODO turn login into logout in menu
                    Toast t = Toast.makeText(self, "Successful login!", Toast.LENGTH_SHORT);
                    t.show();
                    finish(); //return to main page since we successfully logged in
                }
                else {
                    Toast t = Toast.makeText(self, "an error occurred. Please try logging in again", Toast.LENGTH_SHORT);
                    t.show();
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (username.trim().isEmpty() || password.trim().isEmpty()) {   //tell user what went wrong
                    error += "Username and password cannot be empty.";
                }
                else {
                    error += "Username or password is incorrect";
                }
                Toast t = Toast.makeText(self, error, Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }
    private boolean createAuthToken(){
        String fileName = "token" + username;
        String textToWrite = username;
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(fileName , Context.MODE_PRIVATE);
            outputStream.write(textToWrite.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /***
     * logout user by deleting authentication token
     */
    private void deleteAuthToken(){
        for (File file: this.getFilesDir().listFiles()) {
            if (file.getName().contains("token")){
                file.delete();
                return;
            }
        }
    }

    /***
     * check whether user is logged in or not by searching for token file
     * @return  username if logged in, "" otherwise
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
