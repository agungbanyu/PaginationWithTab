package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView listview;
    private TextView title;

    private ArrayList<String> data;
    ArrayAdapter<String> sd;


    public int TOTAL_LIST_ITEMS = 1030;
    public int NUM_ITEMS_PAGE   = 100;
    private int selectedPage=0;
    private int noOfBtns;
    private Button[] btns;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView)findViewById(R.id.listView);
        title    = (TextView)findViewById(R.id.title);

        Btnfooter();

        data = new ArrayList<String>();

/**
 * The ArrayList data contains all the list items
 */
        for(int i=0;i<TOTAL_LIST_ITEMS;i++)
        {
            data.add("This is Item "+(i+1));
        }

        loadList(0);

        CheckBtnBackGroud(0);

    }

    private void Btnfooter()
    {
        int val = TOTAL_LIST_ITEMS%NUM_ITEMS_PAGE;
        val = val==0?0:1;
        noOfBtns=TOTAL_LIST_ITEMS/NUM_ITEMS_PAGE+val;

        LinearLayout ll = (LinearLayout)findViewById(R.id.btnLay);

        btns    =new Button[noOfBtns];

        for(int i=0;i<noOfBtns;i++)
        {
            btns[i] =   new Button(this);
            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText(""+(i+1));

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(btns[i], lp);

            final int j = i;
            btns[j].setOnClickListener(new View.OnClickListener() {

                public void onClick(View v)
                {
                    loadList(j);
                    CheckBtnBackGroud(j);
                    changeBtnIndex(j);
                    selectedPage=j;
                }
            });
        }
        hideIndex(false);
    }

    private void hideIndex(boolean isReverse){
        if(noOfBtns>5){
            for(int i=0;i<noOfBtns-1;i++)
            {
                btns[i].setVisibility(View.VISIBLE);
            }

            if(!isReverse){
                for(int i=4;i<noOfBtns-2;i++)
                {
                    btns[i].setVisibility(View.GONE);
                }
                btns[noOfBtns-2].setText("...");
                btns[1].setText("2");
            }
            else {
                for(int i=noOfBtns-5;i>1;i--)
                {
                    btns[i].setVisibility(View.GONE);
                }
                btns[1].setText("...");
                btns[noOfBtns-2].setText(""+(noOfBtns-1));
            }
        }
    }

    private void changeBtnIndex(int index){
        if(index==0){
            hideIndex(false);
        }
        else if (index==noOfBtns-1){
            hideIndex(true);
        }
        else if(noOfBtns>5 && index != 1 && index != noOfBtns-2 && index!=noOfBtns-1){
            if(index<selectedPage){
                if(btns[index-1].getVisibility()==View.GONE || btns[index-1].getText().equals("...")){
                    btns[index-1].setVisibility(View.VISIBLE);
                    btns[index-1].setText(""+(index));

                    if(!btns[noOfBtns-2].getText().equals("...")){
                        btns[noOfBtns-3].setVisibility(View.GONE);
                        btns[noOfBtns-2].setText("...");
                    }
                    else {
                        for(int i=noOfBtns-3;i>3;i--)
                        {
                            if(btns[i].getVisibility()==View.VISIBLE && btns[i+1].getVisibility()==View.GONE){
                                btns[i].setVisibility(View.GONE);
                                break;
                            }
                        }
                    }
                }
            }
            else {
                if(btns[index+1].getVisibility()==View.GONE || btns[index+1].getText().equals("...")){
                    btns[index+1].setVisibility(View.VISIBLE);
                    btns[index+1].setText(""+(index+2));

                    if(!btns[1].getText().equals("...")){
                        btns[2].setVisibility(View.GONE);
                        btns[1].setText("...");
                    }
                    else {
                        for(int i=2;i<noOfBtns-4;i++)
                        {
                            if(btns[i].getVisibility()==View.VISIBLE && btns[i-1].getVisibility()==View.GONE){
                                btns[i].setVisibility(View.GONE);
                                break;
                            }
                        }
                    }
                }

            }
        }
    }

    /**
     * Method for Checking Button Backgrounds
     */
    private void CheckBtnBackGroud(int index)
    {
        title.setText("Page "+(index+1)+" of "+noOfBtns);
        for(int i=0;i<noOfBtns;i++)
        {
            if(i==index)
            {
//                btns[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.box_green));
                btns[i].setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
            else
            {
                btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btns[i].setTextColor(getResources().getColor(android.R.color.black));
            }
        }

    }

    /**
     * Method for loading data in listview
     * @param number
     */
    private void loadList(int number)
    {
        ArrayList<String> sort = new ArrayList<String>();

        int start = number * NUM_ITEMS_PAGE;
        for(int i=start;i<(start)+NUM_ITEMS_PAGE;i++)
        {
            if(i<data.size())
            {
                sort.add(data.get(i));
            }
            else
            {
                break;
            }
        }
        sd = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                sort);
        listview.setAdapter(sd);
    }
}