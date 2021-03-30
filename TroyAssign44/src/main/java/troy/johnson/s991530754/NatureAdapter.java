package troy.johnson.s991530754;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NatureAdapter extends ArrayAdapter<NatureItem> {
    public NatureAdapter(Context context, ArrayList<NatureItem> natureList) {
        super(context, 0, natureList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.custom, parent, false
            );
        }

        ImageView imageViewFlag = convertView.findViewById(R.id.troyIM);
        TextView textViewName = convertView.findViewById(R.id.troyTV);

        NatureItem currentItem = getItem(position);
if(currentItem != null) {
    imageViewFlag.setImageResource(currentItem.getFlagImage());
    textViewName.setText(currentItem.getNatureItem());
}

        return convertView;
    }
}
