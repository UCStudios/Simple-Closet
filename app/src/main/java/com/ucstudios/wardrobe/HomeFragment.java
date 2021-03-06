package com.ucstudios.wardrobe;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Selection;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  HomeFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    java.util.Calendar calendar;
    CalendarView calendarView;
    CalendarAddEvent mCalendarAddEvent;
    DatabaseHelper mDatabaseHelper;


    public HomeFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }



    public ArrayList<Integer> GetSelectedDate(EventDay eventDay){
        ArrayList<Integer> selecteddate = new ArrayList<>();
        Calendar clickedDayCalendar = eventDay.getCalendar();
        Integer Day;
        Integer Month;
        Integer Year;
        Day = clickedDayCalendar.get(Calendar.DAY_OF_MONTH);
        Month = clickedDayCalendar.get(Calendar.MONTH);
        Year = clickedDayCalendar.get(Calendar.YEAR);

        selecteddate.add(Day);
        selecteddate.add(Month+1);
        selecteddate.add(Year);

        return selecteddate;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mDatabaseHelper = new DatabaseHelper(getContext());





        calendarView = view.findViewById(R.id.calendarView);
        populateEvents();
        try {
            calendarView.setDate(Calendar.getInstance());
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }






        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Cursor data = mDatabaseHelper.getData2();
                boolean isempty = false;
                final ArrayList<String> listData = new ArrayList<>();
                while (data.moveToNext()) {
                    listData.add(data.getString(0));
                    isempty = true;
                }

                if(!isempty){

                    new AlertDialog.Builder(getContext())
                            .setTitle("Error!")
                            .setMessage("You have no outfits")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
                else{








                ArrayList<Integer> clickedate = GetSelectedDate(eventDay);
                String SelectedOutfit = null;
                boolean Eventonthisday = false;
                int intutile =0;

                for(int i=0;i<populateControlEvents().size();i++){

                    if(eventDay.getCalendar().get(Calendar.DAY_OF_MONTH)==populateControlEvents().get(i).getCalendar().get(Calendar.DAY_OF_MONTH)&&eventDay.getCalendar().get(Calendar.MONTH)==populateControlEvents().get(i).getCalendar().get(Calendar.MONTH)&&eventDay.getCalendar().get(Calendar.YEAR)==populateControlEvents().get(i).getCalendar().get(Calendar.YEAR)){
                        Eventonthisday=true;
                        intutile = i;

                    }
                }

                if(!Eventonthisday){


                mCalendarAddEvent = new CalendarAddEvent(getContext(),GetSelectedDate(eventDay),false);
                mCalendarAddEvent.show();
                mCalendarAddEvent.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        populateEvents();
                    }
                });

            }
                else{
                    Cursor Data = mDatabaseHelper.getEvents();
                    int i=0;
                    while(Data.moveToNext()) {
                        if(i==intutile){
                        SelectedOutfit = Data.getString(1);}
                        i++;
                    }
                    
                    EventVisualDialog dialog = new EventVisualDialog(getContext(),SelectedOutfit,clickedate);
                    dialog.show();
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            populateEvents();
                        }
                    });
                }


            }}




        });

        calendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {

            }
        });
        calendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {

            }
        });



        return view;

    }

    public  void populateEvents(){
        deleteEmptyOutfit();
        List<EventDay> eventDays = new ArrayList<>();
        List<Integer> events = new ArrayList<>();
        Cursor Data = mDatabaseHelper.getEvents();
        while(Data.moveToNext()){
            events.add(Data.getInt(0));
        }
        List<Integer>Years=new ArrayList<>();
        List<Integer>Months=new ArrayList<>();
        List<Integer>Days=new ArrayList<>();

        for(int i=0;i<events.size();i++){
            int lenght=String.valueOf(events.get(i)).length();
            int event = events.get(i);
            if(lenght==7){

                        Days.add(Integer.parseInt(Integer.toString(event).substring(0, 1)));
                        Months.add(Integer.parseInt(Integer.toString(event).substring(1, 3)));
                        Years.add(Integer.parseInt(Integer.toString(event).substring(3, 7)));



            }
            else if(lenght==8){
                Days.add(Integer.parseInt(Integer.toString(event).substring(0, 2)));
                Months.add(Integer.parseInt(Integer.toString(event).substring(2, 4)));
                Years.add(Integer.parseInt(Integer.toString(event).substring(4, 8)));

            }

            Calendar calendar = Calendar.getInstance();
                calendar.set(Years.get(i),Months.get(i)-1,Days.get(i));

                eventDays.add(new EventDay(calendar, R.drawable.ic_outfit));
        }





        calendarView.setEvents(eventDays);


    }


    public void deleteEmptyOutfit(){
        Cursor data = mDatabaseHelper.getData2();
        final ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
        }
        Cursor categories = mDatabaseHelper.getData();
        final ArrayList<String> categories2 = new ArrayList<>();
        while (categories.moveToNext()){
            categories2.add(categories.getString(0));
        }

        for(int u=0;u<listData.size();u++){
            int controllomatto=0;
            for(int i=0;i<categories2.size();i++){
                Cursor c = mDatabaseHelper.GetItemOutfit(categories2.get(i),listData.get(u));

                while(c.moveToNext()){
                    if(c.getString(0)!=null){
                        controllomatto++;
                    }
                }


            }
            if(controllomatto==0){
                mDatabaseHelper.delete3("outfit_table",listData.get(u));
                mDatabaseHelper.deleteeventswhenoutfitdeleted(listData.get(u));
                Toast.makeText(getContext(),"Outfit "+listData.get(u)+" automatically deleted",Toast.LENGTH_SHORT).show();


            }
        }
    }

    private List<EventDay> populateControlEvents(){
        List<EventDay> eventDays = new ArrayList<>();
        List<Integer> events = new ArrayList<>();
        Cursor Data = mDatabaseHelper.getEvents();
        while(Data.moveToNext()){
            events.add(Data.getInt(0));
        }
        List<Integer>Years=new ArrayList<>();
        List<Integer>Months=new ArrayList<>();
        List<Integer>Days=new ArrayList<>();

        for(int i=0;i<events.size();i++){
            int lenght=String.valueOf(events.get(i)).length();
            int event = events.get(i);
            if(lenght==7){

                Days.add(Integer.parseInt(Integer.toString(event).substring(0, 1)));





                Months.add(Integer.parseInt(Integer.toString(event).substring(1, 3)));



                Years.add(Integer.parseInt(Integer.toString(event).substring(3, 7)));



            }
            else if(lenght==8){
                Days.add(Integer.parseInt(Integer.toString(event).substring(0, 2)));





                Months.add(Integer.parseInt(Integer.toString(event).substring(2, 4)));



                Years.add(Integer.parseInt(Integer.toString(event).substring(4, 8)));

            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(Years.get(i),Months.get(i)-1,Days.get(i));

            eventDays.add(new EventDay(calendar, R.drawable.ic_outfit));
        }





    return eventDays;


    }

    }




