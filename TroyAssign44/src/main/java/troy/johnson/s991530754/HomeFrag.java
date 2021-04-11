package troy.johnson.s991530754;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class HomeFrag extends Fragment {


    public HomeFrag() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        TextView date = (TextView) root.findViewById(R.id.troyDT);
        TextView tvTime = (TextView) root.findViewById(R.id.troyTime);
        String currentDateTimeString = java.text.DateFormat.getDateInstance().format(new Date());
        date.setText(currentDateTimeString);
        java.util.Date noteTS = Calendar.getInstance().getTime();
        String time = getString(R.string.time_format); // 12:00
        tvTime.setText(DateFormat.format(time, noteTS));


        return root;
    }
}

