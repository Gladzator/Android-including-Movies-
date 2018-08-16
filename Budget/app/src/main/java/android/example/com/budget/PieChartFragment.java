package android.example.com.budget;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;


public class PieChartFragment extends Fragment {


    MonthlyincmHelper mMonthlyincmHelper;
    String year,month,day;
    BarChart barChart;
    String days,months,years;
    int home=0,daily=0,transport=0,entertainment=0,personal=0,financial=0;

    TextView moneyspenttxt,moneyavailabletxt;

    public PieChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_pie_chart, container, false);


        moneyspenttxt=(TextView)v.findViewById(R.id.moneyspent);
        moneyavailabletxt=(TextView)v.findViewById(R.id.moneyavailable);

        //barchart
         barChart=(BarChart)v.findViewById(R.id.chart);

        year = getArguments().getString("year");
        month = getArguments().getString("month");
        day = getArguments().getString("day");

        mMonthlyincmHelper = new MonthlyincmHelper(getContext());

        Cursor detailsData = mMonthlyincmHelper.getDetailsData();
        detailsData.moveToFirst();
        do {
            days = detailsData.getString(1);
            months = detailsData.getString(2);
            years = detailsData.getString(3);
            if ((months.equals(month))) {
                if(years.equals(year)) {

                    home = Integer.parseInt(detailsData.getString(4)) + home;
                    daily = Integer.parseInt(detailsData.getString(5)) + daily;
                    transport = Integer.parseInt(detailsData.getString(6)) + transport;
                    entertainment = Integer.parseInt(detailsData.getString(7)) + entertainment;
                    personal = Integer.parseInt(detailsData.getString(8)) + personal;
                    financial = Integer.parseInt(detailsData.getString(9)) + financial;
                }else{
                    continue;
                }
            }
        } while (detailsData.moveToNext());
        addData();

        Cursor data=mMonthlyincmHelper.getData();
        data.moveToPosition(0);
        String income ;
        income = data.getString(1);
        
        int moneyspent=home+daily+transport+entertainment+personal+financial;
        int moneyavailable=Integer.parseInt(income)-moneyspent;

        moneyspenttxt.setText("Money Spent:"+Integer.toString(moneyspent));
        moneyavailabletxt.setText("Money Available:"+Integer.toString(moneyavailable));
        // Inflate the layout for this fragment
        return v;
    }



    private void addData() {
        ArrayList<BarEntry> details=new ArrayList<>();
      
    
        int detail[]={home,daily,transport,entertainment,personal,financial};
        String[] name={"home","daily","transport","entertainment","personal","financial"};
        for(int i=0;i<detail.length;i++){
            details.add(new BarEntry(detail[i],i));
        }
           ArrayList<String> names=new ArrayList<>();
           names.add("home");
           names.add("daily");
           names.add("transport");
           names.add("entertainment");
           names.add("personal");
           names.add("financial");

           BarDataSet barDataSet=new BarDataSet(details,"Monthly Spendings");
        //add colors
        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);


        barDataSet.setColors(colors);



        BarData incmData=new BarData(names,barDataSet);
        barDataSet.setBarSpacePercent(50f);

        barChart.setData(incmData);
        barChart.setVisibleXRange(3,3);
        barChart.setEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);

    }













    private void toastmessage(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}
