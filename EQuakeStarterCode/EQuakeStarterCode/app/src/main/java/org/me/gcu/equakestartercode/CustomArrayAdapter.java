package org.me.gcu.equakestartercode;
// Mark Feeney - MaticulationNumber : s1921828

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class CustomArrayAdapter extends ArrayAdapter<WidgetClass>
    {
        private final Context thisContext;
        private final ArrayList<WidgetClass> items;
        public CustomArrayAdapter(@NonNull Context context, @LayoutRes ArrayList<WidgetClass> list)
        {
            super(context, 0, list);
            thisContext = context;
            items = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            View listItem = convertView;
            if(listItem == null)
            {
                listItem = LayoutInflater.from(thisContext).inflate(R.layout.activity_listview, parent, false);
            }

            TextView buttonTxt = (TextView)listItem.findViewById(R.id.label);

            String testText = "Test Text";

            WidgetClass item = items.get(position);

           // textView.setText(item.getDescription() + " " + item.getCoordLong());
            buttonTxt.setText(item.getTitle());

            buttonTxt.setOnClickListener((View v) ->
            {
                Log.d("LogLat", "hoihoih");
            });

            String locStartStr = "Location: ";
            String locEndStr = " ; Lat/long";

            int locationStart = item.getDescription().indexOf(locStartStr)+locStartStr.length();
            int locationEnd = item.getDescription().indexOf(locEndStr);

            String dateStartStr = "date/time:";

            int dateStart = item.getDescription().indexOf(dateStartStr)+dateStartStr.length();
            int dateEnd = item.getDescription().indexOf(dateStartStr)+27;

            int timeStart = item.getDescription().indexOf(dateStartStr)+28;
            int timeEnd = item.getDescription().indexOf(dateStartStr)+36;


          //  String actualDate =


            String testString = item.getTitle();
            String actualTitle = testString.substring(0, testString.indexOf(':'));

            String testDescription = item.getDescription();


            String actualLocation = item.getDescription().substring(locationStart, locationEnd);
            String actualDate = item.getDescription().substring(dateStart, dateEnd);
            String actualTime = item.getDescription().substring(timeStart, timeEnd);

            String magnitudeNumber = (testString.substring(25, 29));


            String buttonInformation = ("Data is from " + actualTitle + " and earthquake has a magnitude of " + magnitudeNumber);
            buttonTxt.setText(buttonInformation); // Sets the TEXT INFORMATION ON THE RELATIVE LIST SECTION
            float magnitude = Float.parseFloat(magnitudeNumber);



            float magNorm = magnitude / 10.0f;
            int colorNum = 0;

            if (magnitude  <=  1.0)
            {
                colorNum = Color.parseColor("#7FFF00");

            }
            else if (magnitude <= 2.0)
            {
                colorNum = Color.parseColor("#00FF00");
            }
            else
            {
                colorNum = Color.parseColor("#8B0000");

            }


            Log.e("debug","magNorm: "+String.valueOf(magNorm)+"; colorNum: "+String.valueOf(colorNum));

            buttonTxt.setBackgroundColor(colorNum);

            buttonTxt.setOnClickListener((View v) ->
            {
                Log.d("LogLat", "hoihoih");
                AlertDialog.Builder builder = new AlertDialog.Builder(thisContext);
                builder.setMessage("Location : " + actualLocation + "\n" +
                                    "Date : " + actualDate + "\n" +
                                    "Time : " + actualTime);
                builder.setCancelable(false);
                builder.setPositiveButton("return", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getContext(), " You Pressed Yes", Toast.LENGTH_SHORT).show();
                        // MainActivity.this.finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            });

            return buttonTxt;
        }

    }
