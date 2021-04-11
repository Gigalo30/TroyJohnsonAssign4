/**
 * Troy Johnson s991530754
 *  This is Assignment 04
 *
 */


package troy.johnson.s991530754;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceFrag extends Fragment {

    class Weather extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... zipCode) {
            try {
                URL url = new URL(zipCode[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int data = isr.read();
                String content = "";
                char ch;
                while (data != -1) {
                    ch = (char) data;
                    content = content + ch;
                    data = isr.read();
                }
                return content;
            } catch (IOException e) {
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
        EditText zipCode = (EditText) root.findViewById(R.id.troyED);
        TextView tAddress = root.findViewById(R.id.troyTV2);
        Button button = (Button) root.findViewById(R.id.troyBtn);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence text = getString(R.string.Toast_text);
                CharSequence text1 = getString(R.string.Tast_cancel);
                if (zipCode.getText().toString().isEmpty() || zipCode.getText().toString().length() < 5) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setIcon(R.drawable.alert);
                    //dialog.setError("The Field cannot be empty or cannot be more than 5 digits");
                    zipCode.setError(getString(R.string.setError));
                    dialog.setTitle(R.string.setTitle);
                    dialog.setPositiveButton(R.string.setB,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
                                }
                            });
                    dialog.setNegativeButton(R.string.dialog_negative, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(), text1, Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                } else {
                    getCurrentData(tAddress, zipCode);
                }
            }
        });
        return root;
    }


    public void getCurrentData(TextView tAddress, TextView zipCode) {
        String zName = zipCode.getText().toString();
        String content;
        Weather weather = new Weather();
        try {
            content = weather.execute("https://samples.openweathermap.org/data/2.5/weather?zip=" + zName + ",us&appid=cde4629101a96793e89e3fde42e35739").get();
            Log.i("contentData", content);

            JSONObject jsonObject = new JSONObject(content);
            String mainTemperature = jsonObject.getString("main");
            String Coord = jsonObject.getString("coord");
            String Name = jsonObject.getString("sys");

            String temperature = "";
            String humidity = "";
            String longitude = "";
            String latitude = "";
            String name = "";
            String zip = "";

            JSONObject mainPart = new JSONObject(mainTemperature);
            temperature = mainPart.getString("temp");
            humidity = mainPart.getString("humidity");

            JSONObject mainCoord = new JSONObject(Coord);
            longitude = mainCoord.getString("lon");
            latitude = mainCoord.getString("lat");

            JSONObject mainName = new JSONObject(Name);
            name = mainName.getString("country");

            String resultText = "Temperature: " + temperature + "\nHumidity: " + humidity + "\nLongitude: " + longitude + "\nLatitude: " + latitude + "\nName: " + name + "\nZip: " + zName;
            tAddress.setText(resultText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




