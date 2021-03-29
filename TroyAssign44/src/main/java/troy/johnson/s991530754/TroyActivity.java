/**
 * Troy Johnson s991530754
 *  This is Assignment 04
 *
 */


package troy.johnson.s991530754;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

public class TroyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.nMandId);
        builder.setIcon(R.drawable.back);
        builder.setCancelable(false);
        builder.setMessage(R.string.question);
        builder.setPositiveButton(R.string.answer, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });
        builder.setNegativeButton(R.string.answer2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}