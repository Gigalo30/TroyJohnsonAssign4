package troy.johnson.s991530754;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class WebServiceFrag<WeatherInfo> extends Fragment  {

    class WeatherInfo extends AsyncTask<String, Void, String> {
        public String doInBackground(String... params) {

            try{
              URL url = new URL(params[0]);
              HttpURLConnection connection = (HttpURLConnection) url.openConnection();
              connection.connect();

              InputStream is = connection.getInputStream();
              InputStreamReader reader = new InputStreamReader(is);

              int data = reader.read();
              String apiDetails = "";
              char current;

              while(data != -1){

                  current = (char) data;
                  apiDetails += current;
                  data = reader.read();
                }

              return apiDetails;

            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }


    public WebServiceFrag() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_web_service, container, false);

        WebServiceFrag.WeatherInfo weatherInfo = new WebServiceFrag.WeatherInfo();

        EditText zipCode = (EditText)root.findViewById(R.id.troyED);
        TextView tAddress = root.findViewById(R.id.troyTV2);
        Button button = (Button) root.findViewById(R.id.troyBtn);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
       try {


           if (zipCode.getText().toString().isEmpty()) {
               AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .setTitle("Error Message")
                       .setMessage("Please enter a Zip Code")
                       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       })
                       .setNegativeButton("No", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               //set what should happen when negative button is clicked
                               Toast.makeText(getActivity(),"Nothing Happened",Toast.LENGTH_LONG).show();
                           }
                       })
                       .show();
           }


           else {
               AsyncTask weatherApiDetails = (AsyncTask) weatherInfo.execute("https://samples.openweathermap.org/data/2.5/weather?zip=" + zipCode.getText().toString() + "&appid=cde4629101a96793e89e3fde42e35739").get();
               Log.i("Weather Api Info", String.valueOf(weatherApiDetails));

               JSONObject jsonObject = new JSONObject((Map) weatherApiDetails);

               String weather = jsonObject.getString("weather");
               Log.i("Weather Details", weather);

               JSONArray array = new JSONArray(weather);

               int longitude = 1;
               String latitude = "";
               String humidity = "";
               String name = "";
             //  String temperature = "";
               String zipcode = "";

               for (int i = 0; i < array.length(); i++) {

                   JSONObject arrayObject = array.getJSONObject(i);

                   longitude = arrayObject.getInt("id");
                   latitude = arrayObject.getString("main");
                   humidity = arrayObject.getString("description");
                   name = arrayObject.getString("icon");
                  // temperature = arrayObject.getString("temp_min");
                   zipcode = arrayObject.getString(String.valueOf(zipCode));
               }

               tAddress.append("Longitude :" + longitude + "\n" + "Latitude" + latitude + "\n" + "Humidity" +
                       humidity + "\n" + "Name" + name + "\n" + "Temperature" + /**temperature */ "\n" + "Zip Code"   + zipcode);
           }
       }


       catch(Exception e){
           e.printStackTrace();
       }
            }
        });
        return root;
    }
}



