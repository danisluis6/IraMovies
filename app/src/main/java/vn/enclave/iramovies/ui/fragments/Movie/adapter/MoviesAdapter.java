package vn.enclave.iramovies.ui.fragments.Movie.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.services.IraMoviesInfoAPIs;
import vn.enclave.iramovies.services.response.Movie;
import vn.enclave.iramovies.ui.activities.base.BaseView;
import vn.enclave.iramovies.utilities.OverrideFonts;

/**
 * Created by lorence on 14/11/2017.
 * @Run:
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{

    private Context mContext;
    private List<Movie> mGrouMovies;
    private static BaseView mBaseView;

    public MoviesAdapter(Context context, BaseView baseView, List<Movie> grouMovies) {
        this.mContext = context;
        this.mGrouMovies = grouMovies;
        this.mBaseView = baseView;
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesAdapter.ViewHolder holder, int position){
        final Movie movie = mGrouMovies.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvReleaseDate.setText(movie.getReleaseDate());
        holder.tvRating.setText(movie.getVoteAverage() + "/10.0");
        holder.tvOverview.setText(movie.getOverview());
        String poster = IraMoviesInfoAPIs.Images.Thumbnail + movie.getPosterPath();
        Glide.with(mContext)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(holder.imvSmallThumbnail);
    }

    @Override
    public int getItemCount() {
        return mGrouMovies.size();
    }

    public void setMovies(List<Movie> movies) {
        this.mGrouMovies = movies;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvTitle)
        public TextView tvTitle;

        @BindView(R.id.tvReleaseDate)
        public TextView tvReleaseDate;

        @BindView(R.id.tvRating)
        public TextView tvRating;

        @BindView(R.id.tvOverview)
        public TextView tvOverview;

        @BindView(R.id.imvSmallThumbnail)
        public ImageView imvSmallThumbnail;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            initAttributes();
        }

        void initAttributes() {
            tvTitle.setTypeface(OverrideFonts.getTypeFace(mBaseView, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.BOLD));
        }
    }

}
