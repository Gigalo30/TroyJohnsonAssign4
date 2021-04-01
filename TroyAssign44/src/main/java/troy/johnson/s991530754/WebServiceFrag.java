package troy.johnson.s991530754;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
                getCurrentData(tAddress);
            }
            });
        return root;
    }


          public void getCurrentData(TextView tAddress) {
              Retrofit retrofit = new Retrofit.Builder()
                      .baseUrl(BaseUrl)
                      .addConverterFactory(GsonConverterFactory.create())
                      .build();
              WeatherService service = retrofit.create(WeatherService.class);
              Call<WeatherResponse> call = service.getCurrentWeatherData(lat, lon, AppId);
              call.enqueue(new Callback<WeatherResponse>() {

                    @Override
                    public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                        if (response.code() == 200) {
                            WeatherResponse weatherResponse = response.body();
                            assert weatherResponse != null;

                            String stringBuilder = "Country: " +
                                    weatherResponse.sys.country +
                                    "\n" +
                                    "Temperature: " +
                                    weatherResponse.main.temp +
                                    "\n" +
                                    "Temperature(Min): " +
                                    weatherResponse.main.temp_min +
                                    "\n" +
                                    "Temperature(Max): " +
                                    weatherResponse.main.temp_max +
                                    "\n" +
                                    "Humidity: " +
                                    weatherResponse.main.humidity +
                                    "\n" +
                                    "Pressure: " +
                                    weatherResponse.main.pressure;

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



