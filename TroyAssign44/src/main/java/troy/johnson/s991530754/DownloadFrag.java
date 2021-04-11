/**
 * Troy Johnson s991530754
 *  This is Assignment 04
 *
 */


package troy.johnson.s991530754;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class DownloadFrag extends Fragment {
    private ArrayList<NatureItem> mNatureItem;
    private NatureAdapter mAdapter;
    URL ImageUrl = null;
    InputStream is = null;
    Bitmap bmImg = null;
    ImageView imageView = null;
    ProgressDialog p;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_download, container, false);
        initList();

        Button button = root.findViewById(R.id.troyBtnDwn);
        imageView = root.findViewById(R.id.troyDwnImg);

        Spinner spinnerNature = root.findViewById(R.id.troySpinner);

        mAdapter = new NatureAdapter(this.getActivity(), mNatureItem);
        spinnerNature.setAdapter(mAdapter);



        spinnerNature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NatureItem clickedItem = (NatureItem) parent.getItemAtPosition(position);
                String clickedItemName = clickedItem.getNatureItem();
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        clicked(clickedItemName);
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return root;
    }


    private void initList() {

        mNatureItem = new ArrayList<>();
        mNatureItem.add(new NatureItem(getString(R.string.n_item1), R.drawable.flower));
        mNatureItem.add(new NatureItem(getString(R.string.n_item2), R.drawable.nature));
        mNatureItem.add(new NatureItem(getString(R.string.n_item3), R.drawable.sky));
    }

    public void clicked(String clickedItemName) {
        CharSequence text = getString(R.string.selected);
        AsyncTaskExample asyncTask = new AsyncTaskExample();
        if (clickedItemName == getString(R.string.n_item1)) {
            asyncTask.execute(getString(R.string.imgDwn1));
        }
        else if (clickedItemName == getString(R.string.n_item2)) {
            asyncTask.execute(getString(R.string.imgDwn2));
        }
        else if (clickedItemName == getString(R.string.n_item3)) {
            asyncTask.execute(getString(R.string.imgDwn3));
        }
        else
        {
            Toast.makeText(getActivity(), clickedItemName + text, Toast.LENGTH_SHORT).show();
        }
    }

    public class AsyncTaskExample extends AsyncTask<String, String, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(getActivity());
            p.setMessage(getString(R.string.dwnSetmessage));
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {

                ImageUrl = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) ImageUrl
                        .openConnection();
                conn.setDoInput(true);
                conn.connect();
                is = conn.getInputStream();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bmImg = BitmapFactory.decodeStream(is, null, options);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return bmImg;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (imageView != null) {
                p.hide();
                imageView.setImageBitmap(bitmap);
            } else {
                p.show();
            }
        }

    }
}
