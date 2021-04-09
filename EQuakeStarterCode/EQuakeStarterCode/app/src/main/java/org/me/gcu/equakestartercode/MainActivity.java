package org.me.gcu.equakestartercode;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
// Mark Feeney - MaticulationNumber : s1921828

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private TextView rawDataDisplay;
    private Button startButton;
    private ImageButton returnButton;
    private String result = "";
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private ArrayList<String> items;
    private ListView earthquakeListView;
    private Button startListViewButton;
    private Button startApplicationButton;
    private Button startDatePickerButton;
    private Button quitApplicationButton;
    private Button calculateValuesButton;
    private Button quitAppMainMenuButton;
    private TextView displayDateTxtBox;

    private TextView mostNorthTxtBox;
    private TextView mostEastTxtBox;
    private TextView mostSouthTxtBox;
    private TextView mostWestTxtBox;
    private TextView mostMagTxtBox;
    private TextView mostDeepTxtBox;
    private TextView mostShallTxtBox;
    private DatePickerDialog.OnDateSetListener eDateSetListener;


    private String widgetString = "";

    public void showtbDialog(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(s + ",Are you sure you want to exit?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                Toast.makeText(getApplicationContext(), " You Pressed Yes", Toast.LENGTH_SHORT).show();
                MainActivity.this.finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
                Toast.makeText(getApplicationContext(), " You Pressed No", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.intro_screen);
        Log.e("MyTag","in onCreate");
        startApplicationButton = (Button)findViewById(R.id.startappbtn);
        quitApplicationButton = (Button)findViewById(R.id.quitappbtn);
        quitApplicationButton.setOnClickListener(this);
        Log.e("MyTag","after startButton");
    }

    public void onClick(View aview) {
        if (aview == startButton) {
            Log.e("MyTag", "in onClick");
            startProgress(1);
            Log.e("MyTag", "after startProgress");
        }

        if (aview == startApplicationButton) {
            setContentView(R.layout.main_menu);
           // displayDateTxtBox = (TextView) findViewById((R.id.dateText));
            startDatePickerButton = (Button) findViewById((R.id.datepickerbutton));
            startDatePickerButton.setOnClickListener(this);
            startListViewButton = (Button) findViewById(R.id.listViewBtn);
            startListViewButton.setOnClickListener(this);
            quitAppMainMenuButton = (Button) findViewById(R.id.quitAppBtn);
            quitAppMainMenuButton.setOnClickListener(this);
        }

        if(aview == startDatePickerButton)
        {
            setContentView(R.layout.date_menu);
             displayDateTxtBox = (TextView) findViewById((R.id.dateText));
             returnButton = (ImageButton) findViewById(R.id.returnBtn);
             returnButton.setOnClickListener(this);
             calculateValuesButton = (Button) findViewById(R.id.calculateBtn);
             calculateValuesButton.setOnClickListener(this);
             mostNorthTxtBox = (TextView) findViewById(R.id.northTxtFill);
            mostEastTxtBox = (TextView) findViewById(R.id.eastTxtFill);
            mostSouthTxtBox = (TextView) findViewById(R.id.southTxtFill);
            mostWestTxtBox = (TextView) findViewById(R.id.westTxtFill);
            mostMagTxtBox = (TextView) findViewById(R.id.magTxtFill);
            mostDeepTxtBox = (TextView) findViewById(R.id.deepTxtFill);
            mostShallTxtBox = (TextView) findViewById(R.id.shallTxtFill);
        }

        if(aview == returnButton)
        {
            setContentView(R.layout.main_menu);
            // displayDateTxtBox = (TextView) findViewById((R.id.dateText));
            startDatePickerButton = (Button) findViewById((R.id.datepickerbutton));
            startDatePickerButton.setOnClickListener(this);
            startListViewButton = (Button) findViewById(R.id.listViewBtn);
            startListViewButton.setOnClickListener(this);
            quitAppMainMenuButton = (Button) findViewById(R.id.quitAppBtn);
            quitAppMainMenuButton.setOnClickListener(this);
        }

        if(aview == quitAppMainMenuButton)
        {
            Log.d("MyTag", "Trying to quit");
            MainActivity.this.finish();
        }

        if(aview == quitApplicationButton)
        {
            MainActivity.this.finish();
        }

        if(aview == calculateValuesButton)
        {
            Log.d("MyTag", "CalcValues");
            startProgress(2);
        }



        if (aview == displayDateTxtBox) {
            Log.d("MyTag ", "TxtBox Hitting");
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpDialog = new DatePickerDialog(MainActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    eDateSetListener,
                    year, month, day);
            dpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dpDialog.show();
        }

        eDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                Log.d("DateTag", "onDateSet : date " + dayOfMonth + "/" + month + "/" + year);
            String date = month + "/" +dayOfMonth + "/" + year;
            displayDateTxtBox.setText(date);
            CalculateValuesOfEarthquakes();
            }
        };



        if(aview == startListViewButton)
        {
            setContentView(R.layout.activity_main);
            ArrayList<org.me.gcu.equakestartercode.WidgetClass> alist = null;
            ArrayList<org.me.gcu.equakestartercode.WidgetClass> titlelist = null;
            rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
            returnButton = (ImageButton)findViewById(R.id.returnBtn);
            returnButton.setOnClickListener(this);
            startButton = (Button)findViewById(R.id.startButton);
            startButton.setOnClickListener(this);
            earthquakeListView = (ListView)findViewById(R.id.list);
            Log.e("MyTag","after startButton");
               titlelist = parseData(widgetString);
               alist = parseData(widgetString);
               if (alist != null)
              {
                  Log.e("MyTag","List not null");
                  for (Object o : alist)
                  {
                      Log.e("MyTag",o.toString());
                      items.add(o.toString());
                   }
               }
               else
                {
                    Log.e("MyTag","List is null");
               }
            Log.d("MyTag", "start app button pushed");
        }



        if(aview == quitApplicationButton)
        {
            Log.d("MyTag", "Quit app button pushed");
        }
    }

    public void startProgress(int theActualMode)
    {
        new Thread(new Task(urlSource, theActualMode)).start();
    }


    private class Task implements Runnable
    {
        private String url;
        private int actualMode;

        public Task(String aurl, int mode)
        {
            url = aurl;
            actualMode = mode;

        }
        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";

            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                Log.e("MyTag","after ready");

                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    Log.e("MyTag",inputLine);

                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception in run");
            }

            MainActivity.this.runOnUiThread(new Runnable()
            {
                @RequiresApi(api = Build.VERSION_CODES.O)
                public void run() {


                    Log.d("MyTag", "TheActualMode Value is " + actualMode);


                    ArrayList<org.me.gcu.equakestartercode.WidgetClass> tempList = parseData(result); //Widget count = list count
                    Log.d("UI thread", "I am the UI thread");

                    List Data = new ArrayList<String>();

                    if(actualMode == 1) {

                        ListView newListView = (ListView)findViewById(R.id.list);

                        CustomArrayAdapter adapter = new CustomArrayAdapter(MainActivity.this, tempList);

                        if (adapter == null) {
                            Log.e("MyTag", "Adapter error");
                        }
                        newListView.setAdapter(adapter);

                        for (int i = 0; i < tempList.size(); i++) {
                            Log.e("DEBUG TAG", tempList.get(i).getTitle());
                        }
                        ListSorter.setListViewHeightBasedOnChildren((newListView));
                    }
                    else if (actualMode == 2)
                    {
                        String locStartStr = "Location: ";
                        String locEndStr = " ; Lat/long";

                        String dateStartStr = "date/time:";



                        String depthStartStr = "Depth: ";
                        String depthEndStr = " km";
                       // String magStartStr = "M ";
                       // String magEndStr = magStartStr+4;

                        float mostNorthEQ = 0f;
                        float mostEastEQ = 0f;
                        float mostSouthEQ = 100f;
                        float mostWestEQ = 0f;
                        float highestMag = 0f;
                        float highestDepth = 0f;
                        float lowestDepth = 100f;

                        String mostNorthTxt = "";
                        String mostSouthTxt = "100";
                        String mostEastTxt = "";
                        String mostWestTxt = "";
                        String highestMagTxt = "";


                        String currentDate = displayDateTxtBox.getText().toString();



                        String parsedCurrentDate = currentDate.substring(currentDate.length()-4);

                        int actualcurrentDate = Integer.parseInt(parsedCurrentDate);


                        for (int i = 0; i < tempList.size(); i++)
                        {

                            int locationStart = tempList.get(i).getDescription().indexOf(locStartStr)+locStartStr.length();
                            int locationEnd = tempList.get(i).getDescription().indexOf(locEndStr);

                            int depthStart = tempList.get(i).getDescription().indexOf(depthStartStr)+depthStartStr.length();
                            int depthEnd = tempList.get(i).getDescription().indexOf(depthEndStr);

                            int dateStart = tempList.get(i).getDescription().indexOf(dateStartStr)+dateStartStr.length();
                            int dateEnd = tempList.get(i).getDescription().indexOf(dateStartStr)+27;

                         //   int magnitudeStart = tempList.get(i).getDescription().indexOf(magStartStr)+magStartStr.length();
                         //   int magnitudeEnd = tempList.get(i).getDescription().indexOf(magEndStr);

                            String lat = tempList.get(i).getCoordLat();
                            String loong = tempList.get(i).getCoordLong();
                            String location = tempList.get(i).getDescription().substring(locationStart, locationEnd);
                            String depth = tempList.get(i).getDescription().substring(depthStart, depthEnd);
                            String magnitude = tempList.get(i).getTitle().substring(25, 29);
                            String actualEQDate = tempList.get(i).getDescription().substring(dateStart, dateEnd);
                            String yearString = tempList.get(i).getDescription().substring(dateStart + 13, dateStart + 17);


                            int actualYear = Integer.parseInt(yearString);




                            Log.d("ParamTag", "The current Date is " + actualcurrentDate + " and parsed month is " + actualYear);
                            Log.d("ParamTag", "The Date Is " + currentDate + " and the Earthquake date is " + actualEQDate);

                            float latNum = Float.parseFloat(lat);
                            float longNum = Float.parseFloat(loong);
                            float MagNum = Float.parseFloat(magnitude);
                            float depthNum = Float.parseFloat(depth);


                            if(depthNum > highestDepth && actualcurrentDate == actualYear)
                            {
                                highestDepth = depthNum;
                                String deepString = ("Location : " + location + " Depth was : " + highestDepth);
                                mostDeepTxtBox.setText(deepString);
                            }

                            if(depthNum < lowestDepth && actualcurrentDate == actualYear)
                            {
                                lowestDepth = depthNum;
                                String shallString = ("Location : " + location + " Depth was : " + highestDepth);
                                mostShallTxtBox.setText(shallString);
                            }



                            if(MagNum > highestMag && actualcurrentDate == actualYear)
                            {
                                highestMag = MagNum;
                                highestMagTxt = ("Location : " + location + " Highest Magnitude was " + highestMag);
                                mostMagTxtBox.setText(highestMagTxt);
                            }

                            if(latNum > mostNorthEQ && actualcurrentDate == actualYear)
                            {
                                mostNorthEQ = latNum;
                                mostNorthTxt = ("Location : " + location);
                                mostNorthTxtBox.setText(mostNorthTxt);
                            }
                            if(latNum < mostSouthEQ && actualcurrentDate == actualYear)
                            {
                                mostSouthEQ = latNum;
                                mostSouthTxt = ("Location : " + location);
                                mostSouthTxtBox.setText(mostSouthTxt);
                            }
                            if(longNum > mostEastEQ && actualcurrentDate == actualYear)
                            {
                                mostEastEQ = longNum;
                                mostEastTxt = ("Location : " + location);
                                mostEastTxtBox.setText(mostEastTxt);
                            }
                            if(longNum < mostWestEQ && actualcurrentDate == actualYear)
                            {
                                mostWestEQ = longNum;
                                mostWestTxt = ("Location : " + location);
                                mostWestTxtBox.setText(mostWestTxt);
                            }


                            Data.add("LocationHELLOTHISISMARK : " + location + " " + tempList.get(i).getDescription().substring(tempList.get(i).getDescription().lastIndexOf(';') + 1));
                            Log.d("tempTag", "Lat is : " + latNum + " Long is : " + loong);

                            Log.d("magTag", "Magnitude is : " + magnitude);
                        }

                       // Log.d("ParamTag", "The Date Is " + currentDate + " and the Earthquake date is " + );
                        Log.d("ParamTag", "NorthResults" + mostNorthTxt);
                        Log.d("ParamTag", "SouthResults" + mostSouthTxt);
                        Log.d("ParamTag", "EastResults" +mostEastTxt);
                        Log.d("ParamTag", "WestResults" +mostWestTxt);
                        Log.d("ParamTag", "MagnitudeResults" + highestMagTxt);
                        Log.d("ModeTag", "Mode is 2");
                    //    Log.d("ParamTag", mostNo)
                    }
                    //rawDataDisplay.setText(result);
                }
            });
        }
    }

    private ArrayList<org.me.gcu.equakestartercode.WidgetClass> parseData(String dataToParse)//url
    {
        org.me.gcu.equakestartercode.WidgetClass widget = null;
        ArrayList <org.me.gcu.equakestartercode.WidgetClass> alist = null;
        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new StringReader( dataToParse ) );
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                // Found a start tag
                if(eventType == XmlPullParser.START_TAG)
                {

                    switch  (xpp.getName().toLowerCase())
                    {
                        case "item":
                            if(alist == null) {
                                alist = new ArrayList<org.me.gcu.equakestartercode.WidgetClass>();
                            }
                            break;
                        case "title":
                            //Log.e("MyTag","Item Start Tag found");
                            widget = new org.me.gcu.equakestartercode.WidgetClass();
                            String aTitle = xpp.nextText();
                            widget.setTitle(aTitle);
                            break;
                        case "description":
                            // Now just get the associated text
                            String aDescription = xpp.nextText();
                            // Do something with text
                            //Log.e("MyTag","Bolt is " + aDescription);
                            widget.setDescription(aDescription);
                            break;
                        case "link":
                            // Now just get the associated text
                            String aLink = xpp.nextText();
                            // Do something with text
                            //Log.e("MyTag","Nut is " + aLink);
                            widget.setLink(aLink);
                            break;
                        case "pubdate":
                            // Now just get the associated text
                            String aPubDate = xpp.nextText();
                            // Do something with text
                            //Log.e("MyTag","pubdate is " + aPubDate);
                            widget.setPublishDate(aPubDate);
                            break;
                        case "categorytype":
                            // Now just get the associated text
                            String aCategoryType = xpp.nextText();
                            // Do something with text
                            //Log.e("MyTag","cat is is " + catType);
                            widget.setCategoryType(aCategoryType);
                            break;
                        case "lat":
                            // Now just get the associated text
                            String aCoordLat = xpp.nextText();
                            // Do something with text
                            //Log.e("MyTag","Washer is " + aPubDate);
                            widget.setCoordLat(aCoordLat);
                            break;
                        case "long":
                            // Now just get the associated text
                            String aCoordLong = xpp.nextText();
                            // Do something with text
                            //Log.e("MyTag","Washer is " + aPubDate);
                            widget.setCoordLong(aCoordLong);
                            break;
                    }
                }
                if(eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        Log.e("MyTag","Earthquake is " + widget.toString());
                        alist.add(widget);
                    }
                    else
                    if (xpp.getName().equalsIgnoreCase("channel"))
                    {
                        int size;
                        size = alist.size();
                        Log.e("MyTag","channel size is " + size);
                    }
                }
                // Get the next event
                eventType = xpp.next();

            } // End of while

        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }
        Log.e("MyTag","End document");
        return alist;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        // Do something if an item is clicked
    }


    public void CalculateValuesOfEarthquakes()
    {
        Log.d("MyTag", "CalculateValuesOfEarthquakes");
    }


}