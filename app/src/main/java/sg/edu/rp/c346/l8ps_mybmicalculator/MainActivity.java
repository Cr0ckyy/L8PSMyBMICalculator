package sg.edu.rp.c346.l8ps_mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCalculate, btnReset;
    TextView tvDate, tvBMI, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        btnCalculate = findViewById(R.id.calButton);
        btnReset = findViewById(R.id.resetButton);
        tvDate = findViewById(R.id.tvDate);
        tvBMI = findViewById(R.id.tvBMI);
        tvDescription = findViewById(R.id.tvDescription);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final double StringWeight = Double.parseDouble(etWeight.getText().toString().trim());
                final double StringWeightHeight = Double.parseDouble(etHeight.getText().toString().trim());
                DecimalFormat oneDForm = new DecimalFormat("#.##");

                double BMI = StringWeight / (StringWeightHeight * StringWeightHeight);
                tvBMI.setText("Last Calculated BMI: " + Double.valueOf(oneDForm.format(BMI)));

                Calendar curTiming = Calendar.getInstance();
                String dateTime = curTiming.get(Calendar.DAY_OF_MONTH) + "/" +
                        (curTiming.get(Calendar.MONTH) + 1) + "/" +
                        curTiming.get(Calendar.YEAR) + " " +
                        curTiming.get(Calendar.HOUR_OF_DAY) + ":" +
                        curTiming.get(Calendar.MINUTE);
                tvDate.setText("Last Calculated Date: " + dateTime);

                if (BMI < 18.5) {
                    tvDescription.setText("You are underweight");
                } else if (BMI <= 24.9) {
                    tvDescription.setText("Your BMI is normal");
                } else if (BMI <= 29.9) {
                    tvDescription.setText("You are overweight");
                } else {
                    tvDescription.setText("You are obese");
                }

                etHeight.setText("");
                etWeight.setText("");

                save();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWeight.setText("");
                etHeight.setText("");
                tvDate.setText("Last Calculated Date:");
                tvBMI.setText("Last Calculated BMI:");
                tvDescription.setText("");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String StringDate = pref.getString("date", "Last Calculated Date:");
        String StringBMI = pref.getString("bmi", "Last Calculated BMI:");
        String StringDescription = pref.getString("description", "");

        tvDate.setText(StringDate);
        tvBMI.setText(StringBMI);
        tvDescription.setText(StringDescription);

    }




    protected void save() {
        String BMI = tvBMI.getText().toString().trim();
        String Date = tvDate.getText().toString().trim();
        String description = tvDescription.getText().toString().trim();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = pref.edit();

        prefEdit.putString("date", Date);
        prefEdit.putString("bmi", BMI);
        prefEdit.putString("description", description);
        prefEdit.commit();
    }

}