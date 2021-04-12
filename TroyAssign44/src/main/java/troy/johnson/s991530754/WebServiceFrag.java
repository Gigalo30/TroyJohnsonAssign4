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
            content = weather.execute(getString(R.string.url1) + zName + getString(R.string.url2)).get();

            JSONObject jsonObject = new JSONObject(content);
            String mainTemperature = jsonObject.getString(getString(R.string.main));
            String Coord = jsonObject.getString(getString(R.string.coord));
            String Name = jsonObject.getString(getString(R.string.sys));

            String temperature = "";
            String humidity = "";
            String longitude = "";
            String latitude = "";
            String name = "";
            String zip = "";

            JSONObject mainPart = new JSONObject(mainTemperature);
            temperature = mainPart.getString(getString(R.string.temp));
            double temp =Double.valueOf(temperature).doubleValue();
            Double tempi=temp*5/9;
            humidity = mainPart.getString(getString(R.string.hum));

            JSONObject mainCoord = new JSONObject(Coord);
            longitude = mainCoord.getString(getString(R.string.longitude));
            latitude = mainCoord.getString(getString(R.string.latitude));

            JSONObject mainName = new JSONObject(Name);
            name = mainName.getString(getString(R.string.count));

            String resultText = getString(R.string.resultTemp) + tempi + getString(R.string.resultHum) + humidity + getString(R.string.resultLong) + longitude + getString(R.string.resultLat) + latitude + getString(R.string.resultNM) + name + getString(R.string.resultZP) + zName;
            tAddress.setText(resultText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




