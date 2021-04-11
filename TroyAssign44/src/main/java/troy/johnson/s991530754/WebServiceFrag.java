package troy.johnson.s991530754;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceFrag extends Fragment  {
    public static String BaseUrl = "http://api.openweathermap.org/";
    public static String AppId = "cde4629101a96793e89e3fde42e35739";
    public static String lat = "35";
    public static String lon = "139";

    public WebServiceFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_web_service, container, false);
        EditText zipCode = (EditText)root.findViewById(R.id.troyED);
        TextView tAddress = root.findViewById(R.id.troyTV2);
        Button button = (Button) root.findViewById(R.id.troyBtn);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence text = getString(R.string.Toast_text);
                CharSequence text1 = getString(R.string.Tast_cancel);
                if(zipCode.getText().toString().isEmpty() || zipCode.getText().toString().length() < 5){
                    AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
                    dialog.setIcon(R.drawable.alert);
                    //dialog.setError("The Field cannot be empty or cannot be more than 5 digits");
                    zipCode.setError(getString(R.string.setError));
                    dialog.setTitle(R.string.setTitle);
                    dialog.setPositiveButton(R.string.setB,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Toast.makeText(getActivity(),text,Toast.LENGTH_LONG).show();
                                }
                            });
                    dialog.setNegativeButton(R.string.dialog_negative,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(),text1,Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog alertDialog=dialog.create();
                    alertDialog.show();
                }

                else {
                    getCurrentData(tAddress, zipCode);
                }
            }
            });
        return root;
    }


          public void getCurrentData(TextView tAddress, TextView zipCode) {
             zipCode.getText().toString();
              Retrofit retrofit = new Retrofit.Builder()
                      .baseUrl(BaseUrl)
                      .addConverterFactory(GsonConverterFactory.create())
                      .build();
              WeatherService service = retrofit.create(WeatherService.class);

              Call<WeatherResponse> call = service.getCurrentWeatherData(lat, lon,  AppId);
              call.enqueue(new Callback<WeatherResponse>() {

                    @Override
                    public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                        if (response.code() == 200){
                            WeatherResponse weatherResponse = response.body();
                            assert weatherResponse != null;

                            String stringBuilder = getString(R.string.country) +
                                    weatherResponse.sys.country +
                                    "\n" +
                                    getString(R.string.c_name) +
                                    weatherResponse.name +
                                    "\n" +
                                    getString(R.string.c_temp) +
                                    weatherResponse.main.temp +
                                    "\n" +
                                    getString(R.string.c_tempMin) +
                                    weatherResponse.main.temp_min +
                                    "\n" +
                                    getString(R.string.c_tempMax) +
                                    weatherResponse.main.temp_max +
                                    "\n" +
                                    getString(R.string.c_hum) +
                                    weatherResponse.main.humidity +
                                    "\n" +
                                    getString(R.string.c_lat) +
                                    weatherResponse.coord.lon +
                                    "\n" +
                                    getString(R.string.c_long) +
                                    weatherResponse.coord.lat +
                                      "\n" +
                                    getString(R.string.c_zip) +
                                    zipCode.getText().toString();

                            tAddress.setText(stringBuilder);
                        }
                        }


                    @Override
                    public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                        tAddress.setText(t.getMessage());
                    }
                });
            }
}



