package android.example.com.tv;

import android.content.Context;
import android.content.Intent;
import android.example.com.movies.MainActivity;
import android.example.com.movies.PopularMovieDetails;
import android.example.com.movies.R;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AiringTodayTvActivity extends AppCompatActivity{
    //TextView textView;
    PopularTvArrayAdapter adapter;
    RecyclerView popularView;
    GridLayoutManager layoutManager;
    ArrayList<PopularMovieDetails> popularMovieLists;
    MaterialSearchView searchView;

    //navigation drawer
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    //search
    List<String> moviename;
    Spinner spinner;
    //Progress bar
    private ProgressBar progressBar;
    SwipeRefreshLayout mySwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        //Spinner
        spinner = (Spinner) findViewById(R.id.movieTypes);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AiringTodayTvActivity.this,
                R.layout.spinner_item_layout, getResources().getStringArray(R.array.TypeofTvList));
        myAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItem().toString().equals("Top Rated")) {
                    Intent intent = new Intent(AiringTodayTvActivity.this, TopRatedTvActivity.class);
                    ActivityCompat.finishAffinity(AiringTodayTvActivity.this);
                    startActivity(intent);
                }
                if (spinner.getSelectedItem().toString().equals("Popular")) {
                    Intent intent = new Intent(AiringTodayTvActivity.this, PopularTvActivity.class);
                    ActivityCompat.finishAffinity(AiringTodayTvActivity.this);
                    startActivity(intent);
                }
                if (spinner.getSelectedItem().toString().equals("On Air")) {
                    Intent intent = new Intent(AiringTodayTvActivity.this, OnAirTvActivity.class);
                    ActivityCompat.finishAffinity(AiringTodayTvActivity.this);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        mTitle.setText("Airing Today");

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_movie:
                        Intent intent = new Intent(AiringTodayTvActivity.this, MainActivity.class);
                        ActivityCompat.finishAffinity(AiringTodayTvActivity.this);
                        startActivity(intent);
                        break;
                    case R.id.nav_tv:
                        break;
                }

                return false;
            }
        });


        searchView = (MaterialSearchView) findViewById(R.id.search_movies);

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

                //If closed search view return default
                adapter = new PopularTvArrayAdapter(popularMovieLists, AiringTodayTvActivity.this);
                popularView.setAdapter(adapter);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                onsearch(newText);
                return true;
            }
        });


        //progressbar
        progressBar = (ProgressBar) findViewById(R.id.progressbar);


        // textView=findViewById(R.id.text);
        // set up the RecyclerView
        popularView = (RecyclerView) findViewById(R.id.popularmovielist);
        int numberOfColumns = 2;
        layoutManager = new GridLayoutManager(AiringTodayTvActivity.this, numberOfColumns);
        popularView.setHasFixedSize(true);
        popularView.setLayoutManager(layoutManager);
        popularView.setItemAnimator(new DefaultItemAnimator());

        if (isConnected(AiringTodayTvActivity.this)) {
            new CheckConnectionStatus().execute("https://api.themoviedb.org/3/tv/airing_today?api_key=f7df3696efcc5684f0e5e7b7da66c88f");
        }else {
            progressBar.setVisibility(View.GONE);
            ImageView noNetImage=(ImageView)findViewById(R.id.noNetImage);
            noNetImage.setImageResource(R.drawable.ic_error_black_24dp);

            TextView noNetText=(TextView)findViewById(R.id.noNetText);
            noNetText.setText("No Internet Connection");

        }
        mySwipeRefreshLayout=findViewById(R.id.refresh);
        //refresh
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        adapter = new PopularTvArrayAdapter(popularMovieLists,AiringTodayTvActivity.this);
                        popularView.setAdapter(adapter);
                        myUpdate();
                    }

                }
        );
    }
    public void myUpdate() {
        mySwipeRefreshLayout.setRefreshing(false);
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_list_item,menu);
        MenuItem menuItem=menu.findItem(R.id.search);
        searchView.setMenuItem(menuItem);

        return true;
    }

    /**
     * Converting dp to pixel
     */
    //private int dpToPx(int dp) {
    //      Resources r = getResources();
    //      return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    // }

    class CheckConnectionStatus extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            //    textView.setText("");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //getting inputstream from connection i.e. the response from server
                InputStream inputStream = urlConnection.getInputStream();
                //reading the response
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s = bufferedReader.readLine();
                bufferedReader.close();
                return s;
            } catch (IOException e) {
                Log.e("Error: ",e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressBar.setVisibility(View.GONE);

            JSONObject jsonObject= null;
            try {
                jsonObject = new JSONObject(s);

                popularMovieLists=new ArrayList<>();
                moviename=new ArrayList<String>();
                //Map<Integer,String> nameOfmovie=new HashMap<>();
                JSONArray jsonArray=jsonObject.getJSONArray("results");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    //nameOfmovie.put(object.getInt("id"),object.getString("original_title"));
                    PopularMovieDetails movieDetails=new PopularMovieDetails();
                    movieDetails.setOriginal_title(object.getString("original_name"));
                    movieDetails.setOverview(object.getString("overview"));
                    movieDetails.setRelease_date(object.getString("first_air_date"));
                    movieDetails.setVote_average(object.getDouble("vote_average"));
                    movieDetails.setPoster_path(object.getString("poster_path"));
                    movieDetails.setId(object.getInt("id"));
                    popularMovieLists.add(movieDetails);
                    moviename.add(object.getString("original_name"));
                }
                adapter = new PopularTvArrayAdapter(popularMovieLists,AiringTodayTvActivity.this);
                popularView.setAdapter(adapter);
                //  textView.setText(nameOfmovie.get(269149));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnected());

    }
    public void onsearch(String newText){
        if(newText!=null && !newText.isEmpty()) {
            ArrayList<PopularMovieDetails> listfound = new ArrayList<>();
            for (int i = 0; i < moviename.size(); i++) {
                String item = moviename.get(i);
                if (item.contains(newText))
                    listfound.add(popularMovieLists.get(i));

            }
            adapter = new PopularTvArrayAdapter(listfound,AiringTodayTvActivity.this);
            popularView.setAdapter(adapter);
        }else{
            //if search text is null
            //return default
            adapter = new PopularTvArrayAdapter(popularMovieLists,AiringTodayTvActivity.this);
            popularView.setAdapter(adapter);
        }
    }



}
