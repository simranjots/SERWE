package com.serwe.serwe.Home;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.serwe.serwe.HttpDataHandler;
import com.serwe.serwe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    static final int REQUEST_LOCATION = 1;
    TextView txtlocation;
    LocationManager locationManager;
    MyAdapter adapter;
    ArrayList<Menu> arrmenu = new ArrayList<>();
    String userName, userPic, userEmail;
    SharedPreferences pref;
    private DrawerLayout mdrawer;
    private ActionBarDrawerToggle mtoggle;
    private Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_detail);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.username);
        TextView nav_Email = (TextView) hView.findViewById(R.id.userEmail);
        ImageView temp = (ImageView) findViewById(R.id.userpic);
        txtlocation = (TextView) findViewById(R.id.txtlocation);
        SearchView srch = (SearchView) findViewById(R.id.srch1);
        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);
        Button btn3 = (Button) findViewById(R.id.btn3);
        ListView List = (ListView) findViewById(R.id.List);
        mtoolbar = (Toolbar) findViewById(R.id.nav_action);
        mdrawer = (DrawerLayout) findViewById(R.id.drawerlayout);
        mtoggle = new ActionBarDrawerToggle(this, mdrawer, R.string.open, R.string.close);


        setSupportActionBar(mtoolbar);
        mdrawer.addDrawerListener(mtoggle);
        mtoggle.syncState();


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.action_bar);

        userName = pref.getString("KEY_NAME", "");
        userPic = pref.getString("KEY_PIC", "");
        userEmail = pref.getString("KEY_Email", "");
        Bitmap pic = StringToBitMap(userPic);
        //temp.setImageBitmap(pic);
        nav_user.setText(userName);
        nav_Email.setText(userEmail);


        arrmenu.add(new Menu("Mexican cuisine"));
        arrmenu.add(new Menu("Italian cuisine"));
        arrmenu.add(new Menu("Indian cuisine"));
        arrmenu.add(new Menu("Chinese cuisine"));
        arrmenu.add(new Menu("Indonesian cuisine"));
        arrmenu.add(new Menu("Spanish cuisine"));
        arrmenu.add(new Menu("French cuisine"));
        arrmenu.add(new Menu("Thai cuisine"));

        adapter = new MyAdapter(arrmenu, getApplicationContext());
        List.setAdapter(adapter);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                {
                    Intent intent = new Intent(getApplicationContext(), WebActivity.class);

                    switch (position) {
                        case 0:
                            intent.putExtra("url", "http://www.dominos.co.in");
                            startActivity(intent);
                            break;

                        case 1:
                            intent.putExtra("url", "https://online.kfc.co.in" +
                                    "");
                            startActivity(intent);
                            break;
                        case 2:
                            intent.putExtra("url", "https://www.mcdonalds.com");
                            startActivity(intent);
                            break;
                        case 3:
                            intent.putExtra("url", "http://www.pizzahut.co.in");
                            startActivity(intent);
                            break;
                        case 4:
                            intent.putExtra("url", "http://www.starbucks.co.in");
                            startActivity(intent);
                            break;
                        case 5:
                            intent.putExtra("url", "http://www.burgerking.co.in");
                            startActivity(intent);
                            break;

                        default:
                            break;
                    }
                }
            }
        });


    }

    public Bitmap StringToBitMap(String image) {
        try {
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (mtoggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void logout() {

        finish();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                new GetAddress().execute(String.format("%.4f,%.4f", latti, longi));
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }

    private class GetAddress extends AsyncTask<String, Void, String> {

        ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                double lat = Double.parseDouble(strings[0].split(",")[0]);
                double lng = Double.parseDouble(strings[0].split(",")[1]);
                String response;
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?latlng=%.4f,%.4f&sensor=false", lat, lng);
                response = http.GetHTTPData(url);
                return response;
            } catch (Exception ex) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                String address = ((JSONArray) jsonObject.get("results")).getJSONObject(0).get("formatted_address").toString();
                txtlocation.setText(address);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

}


