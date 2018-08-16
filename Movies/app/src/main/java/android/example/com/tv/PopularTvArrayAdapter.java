package android.example.com.tv;

import android.content.Context;
import android.content.Intent;
import android.example.com.movies.PopularMovieDetails;
import android.example.com.movies.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class PopularTvArrayAdapter extends RecyclerView.Adapter <PopularTvArrayAdapter.ViewHolder> {

    private List<PopularMovieDetails> tvDetailsList;
    private LayoutInflater mInflater;
    Context mContext;

    public PopularTvArrayAdapter(List<PopularMovieDetails> tvDetailsList, Context context) {
        this.tvDetailsList = tvDetailsList;
        this.mInflater = LayoutInflater.from(context);
        mContext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.popular_movie_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        PopularMovieDetails popularListItem = tvDetailsList.get(position);

        holder.movieName.setText(popularListItem.getOriginal_title());
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + popularListItem.getPoster_path()).into(holder.movieImage);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,TvDetailsActivity.class);
                intent.putExtra("TV_DETAILS",(PopularMovieDetails)tvDetailsList.get(position));

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView movieName;
        public ImageView movieImage;
        //itemclick
        LinearLayout linearLayout;

        CardView cardView;
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);

            movieName = (TextView) itemView.findViewById(R.id.moviename);
            movieImage = (ImageView) itemView.findViewById(R.id.movieimage);
            cardView=(CardView)itemView.findViewById(R.id.card_view);

        }

        @Override
        public void onClick(View view) {

        }
    }

}
