package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchLocationActivity extends AppCompatActivity {
    ImageButton select;
    ImageView back;
    EditText search;

    TextView location;
    LinearLayout result;
    TextView location_nonlist;
    ListView locationList;

    String location_item = null;
    String city, gu, dong, code;
    String returnAddress = null;
    int returnCode = 0;

    ArrayList<String> lc_list;
    ArrayList<Integer> locationCode;
    ArrayAdapter<String> adpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.set_search_location));

        back = (ImageView) findViewById(R.id.search_back);
        search = (EditText) findViewById(R.id.search_text);
        select = (ImageButton) findViewById(R.id.btn_location_select);

        location = (TextView) findViewById(R.id.get_location);
        result = (LinearLayout) findViewById(R.id.search_result);
        location_nonlist = (TextView) findViewById(R.id.location_nonlist);
        locationList = (ListView) findViewById(R.id.location_list);

        location_nonlist.setVisibility(View.GONE);
        result.setVisibility(View.GONE);

        Intent intent = getIntent();
        String searchLocation = intent.getStringExtra("searchLocation");
        search.setText(searchLocation);
        location_item = search.getText().toString();

        lc_list = new ArrayList<String>();
        locationCode = new ArrayList<Integer>();

        adpater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lc_list);
        setLocationList(location_item);

        if (lc_list.isEmpty()) {
            location_nonlist.setVisibility(View.VISIBLE);
            locationList.setVisibility(View.GONE);
            result.setVisibility(View.GONE);
            location.setText(location_item);
        } else {
            locationList.setAdapter(adpater);
            location_nonlist.setVisibility(View.GONE);
            locationList.setVisibility(View.VISIBLE);
            result.setVisibility(View.VISIBLE);
            location.setText(location_item);

            locationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    String data = (String) adapterView.getItemAtPosition(position);
                    String[] address = data.split(" ");
                    returnAddress = address[2];
                    returnCode = locationCode.get(position);
                    select.setImageResource(R.drawable.searchlocation_btn_select);
                }
            });
        }

        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String change = search.getText().toString();
                    select.setImageResource(R.drawable.searchlocation_btn_select_none);
                    if (change != null) {
                        setLocationList(change);
                        if (lc_list.isEmpty()) {
                            location_nonlist.setVisibility(View.VISIBLE);
                            locationList.setVisibility(View.GONE);
                            result.setVisibility(View.GONE);
                            location.setText(location_item);
                        } else {
                            locationList.setAdapter(adpater);
                            location_nonlist.setVisibility(View.GONE);
                            locationList.setVisibility(View.VISIBLE);
                            result.setVisibility(View.VISIBLE);
                            location.setText(change);

                            locationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                    String data = (String) adapterView.getItemAtPosition(position);
                                    String[] address = data.split(" ");
                                    returnAddress = address[2];
                                    returnCode = locationCode.get(position);
                                    select.setImageResource(R.drawable.searchlocation_btn_select);
                                }
                            });
                        }

                        return true;
                    }
                }
                return false;
            }
        });

        //기본 세팅
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(returnAddress != null){
                    Intent intent = new Intent();
                    intent.putExtra("dong", returnAddress);
                    intent.putExtra("code", returnCode);
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    public void setLocationList(String searchLocation) {
        PostTask searchLocationTask = new PostTask();
        lc_list.clear();
        locationCode.clear();
        try {
            String response = searchLocationTask.execute("home/location/" + searchLocation, searchLocation).get();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                code = jsonArray.getJSONArray(i).get(0).toString();
                city = jsonArray.getJSONArray(i).get(1).toString();
                gu = jsonArray.getJSONArray(i).get(2).toString();
                dong = jsonArray.getJSONArray(i).get(3).toString();

                lc_list.add(city + " " + gu + " " + dong);
                locationCode.add(Integer.parseInt(code));
            }
        } catch(JSONException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
