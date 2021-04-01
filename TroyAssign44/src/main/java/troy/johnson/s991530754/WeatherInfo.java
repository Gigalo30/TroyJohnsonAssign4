package troy.johnson.s991530754;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
