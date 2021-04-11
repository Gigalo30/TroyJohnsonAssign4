/**
 * Troy Johnson s991530754
 *  This is Assignment 04
 *
 */

package troy.johnson.s991530754;

import android.widget.TextView;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("data/2.5/weather?")
    Call<WeatherResponse> getCurrentWeatherData(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String app_id);
}

