package android.example.com.tv;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.example.com.movies.PopularMovieDetails;
import android.example.com.movies.R;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

public class TvDetailsActivity extends AppCompatActivity {

    ImageView movieImage;
    //BlurImageView blurImageView;
    String key;
    TextView movieTitle,date,rating,overview;

    PopularMovieDetails details;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        //blurImageView=(BlurImageView)findViewById(R.id.backgroundImage);
        movieImage=(ImageView)findViewById(R.id.movieInfoImage);
        movieTitle=(TextView)findViewById(R.id.movietitle);

        date=(TextView)findViewById(R.id.date);
        rating=(TextView)findViewById(R.id.rating);
        overview=(TextView)findViewById(R.id.overview);

        details= (PopularMovieDetails) getIntent().getExtras().getSerializable("TV_DETAILS");

        if (isConnected(TvDetailsActivity.this)) {
            new CheckConnectionStatus().execute("http://api.themoviedb.org/3/tv/"+String.valueOf(details.getId())+"/videos?api_key=f7df3696efcc5684f0e5e7b7da66c88f");
        }else {
            Toast.makeText(this,"No internet found!",Toast.LENGTH_SHORT).show();
        }


            movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Trailer();
            }
        });

           if(details!=null){
            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + details.getPoster_path()).into(movieImage);
            //Picasso.get().load("https://image.tmdb.org/t/p/w500/" + details.getPoster_path()).into(blurImageView);
            //blurImageView.setBlur(2);
            movieTitle.setText(details.getOriginal_title());
            date.setText(details.getRelease_date());
            rating.setText(String.valueOf(details.getVote_average()));
            overview.setText(details.getOverview());

        }
    }
    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnected());

    }

    //get key
    class CheckConnectionStatus extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            //    textView.setText("");
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

            JSONObject jsonObject= null;
            try {
                jsonObject = new JSONObject(s);
                //Map<Integer,String> nameOfmovie=new HashMap<>();
                JSONArray jsonArray=jsonObject.getJSONArray("results");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    //nameOfmovie.put(object.getInt("id"),object.getString("original_title"));
                    PopularMovieDetails movieDetails=new PopularMovieDetails();
                    if(object.getString("type").contains("trailer")||object.getString("type").contains("Trailer")) {
                        movieDetails.setTrailerKey(object.getString("key"));
                        key = movieDetails.getTrailerKey();
                        break;
                    }else{
                        movieDetails.setTrailerKey(object.getString("key"));
                        key = movieDetails.getTrailerKey();
                    }
                }

                //  textView.setText(nameOfmovie.get(269149));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
    public void Trailer(){
        Intent appIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+key));
        Intent webIntent=new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v="+key));
        appIntent.putExtra("force_fullscreen",true);
        try{
            startActivity(appIntent);
        }catch(ActivityNotFoundException ex){
            startActivity(webIntent);
        }

    }


}
