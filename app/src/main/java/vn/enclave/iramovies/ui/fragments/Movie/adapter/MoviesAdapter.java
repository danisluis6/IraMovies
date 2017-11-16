package vn.enclave.iramovies.ui.fragments.Movie.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.OverrideFonts;

/**
 * Created by lorence on 14/11/2017.
 * @Run:
 *
 * @Run: http://www.devexchanges.info/2017/02/android-recyclerview-dynamically-load.html
 * => Done
 *
 * @Run: https://stackoverflow.com/questions/32040798/recyclerview-oncreateviewholder-return-type-incompatibility-with-multiple-custom
 * => Done
 *
 * @Run: https://codentrick.com/load-more-recyclerview-bottom-progressbar/
 * => Done
 *
 *  @Run: http://android-pratap.blogspot.in/2015/01/recyclerview-with-checkbox-example.html
 *  => Fix save the states when scroll view in Recycler View
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<Movie> mGrouMovies;
    private BaseView mBaseView;
    private final int TYPE_MOVIE = 0;
    private final int TYPE_LOAD = 1;

    /** Load more */
    private OnLoadMoreListener mLoadMoreListener;
    private boolean mIsLoading = false;
    private boolean mIsMoreDataAvailable = true;

    /** Set Favorite start */
    private OnChooseFavoriteListener mChooseFavoriteListener;

    public MoviesAdapter(Context context, BaseView baseView, List<Movie> grouMovies) {
        this.mContext = context;
        this.mGrouMovies = grouMovies;
        this.mBaseView = baseView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        if (viewType == TYPE_MOVIE) {
            View view = LayoutInflater.from(this.mContext).inflate(R.layout.item_movie, viewGroup, false);
            return new MovieViewHolder(view);
        } else if (viewType == TYPE_LOAD) {
            View view = LayoutInflater.from(this.mContext).inflate(R.layout.item_load, viewGroup, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position){
        final int mPosition = position;
        if (mPosition >= (getItemCount() - 1) && !mIsLoading && mLoadMoreListener != null) {
            if (mIsMoreDataAvailable) {
                mIsLoading = true;
                mLoadMoreListener.onLoadMore();
            }
        }
        if (getItemViewType(mPosition) == TYPE_MOVIE) {
            if (holder instanceof MovieViewHolder) {
                final Movie movie = mGrouMovies.get(mPosition);
                ((MovieViewHolder) holder).tvTitle.setText(movie.getTitle());
                ((MovieViewHolder) holder).tvReleaseDate.setText(movie.getReleaseDate());
                ((MovieViewHolder) holder).tvRating.setText(movie.getVoteAverage() + Constants.Keyboards.FORWARD_SLASH + "10.0");
                ((MovieViewHolder) holder).tvOverview.setText(movie.getOverview());

                ((MovieViewHolder) holder).imvFavorite.setImageResource((movie.getFavorite() == Constants.Favorites.FAVORITE) ? R.drawable.ic_star_picked : R.drawable.ic_star);
                ((MovieViewHolder) holder).imvFavorite.setTag(mGrouMovies.get(mPosition));

                String poster = IraMoviesInfoAPIs.Images.Thumbnail + movie.getBackdropPath();
                Glide.with(mContext)
                        .load(poster)
                        .placeholder(R.drawable.load)
                        .into(((MovieViewHolder) holder).imvSmallThumbnail);

                ((MovieViewHolder) holder).imvFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        backupStatusOfScrolling(v);
                        navigateToMovieView(movie);
                    }

                    private void backupStatusOfScrolling(View v) {
                        ImageView imvFavorite = (ImageView) v;
                        Movie movie = (Movie) imvFavorite.getTag();

                        movie.setFavorite(Constants.Favorites.FAVORITE);
                        mGrouMovies.get(mPosition).setFavorite(Constants.Favorites.FAVORITE);
                        // Temp
                        imvFavorite.setImageResource((movie.getFavorite() == Constants.Favorites.FAVORITE) ? R.drawable.ic_star_picked : R.drawable.ic_star);
                    }

                    private void navigateToMovieView(Movie movie) {
                        mChooseFavoriteListener.onChoose(movie);
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return mGrouMovies.size();
    }

    public void setMovies(List<Movie> movies) {
        this.mGrouMovies = movies;
        mIsLoading = false;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.equals(mGrouMovies.get(position).getType(), Constants.Objects.LOAD)) {
            return TYPE_LOAD;
        } else {
            return TYPE_MOVIE;
        }
    }


    class MovieViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvReleaseDate)
        TextView tvReleaseDate;

        @BindView(R.id.tvRating)
        TextView tvRating;

        @BindView(R.id.tvOverview)
        TextView tvOverview;

        @BindView(R.id.imvSmallThumbnail)
        ImageView imvSmallThumbnail;

        @BindView(R.id.imvFavorite)
        ImageView imvFavorite;

        MovieViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            initAttributes();
        }

        void initAttributes() {
            tvTitle.setTypeface(OverrideFonts.getTypeFace(mBaseView, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.BOLD));
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        LoadingViewHolder(View view) {
            super(view);
        }
    }

    /** Interface */
    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.mLoadMoreListener = loadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setChooseFavoriteListener (OnChooseFavoriteListener chooseFavoriteListener) {
        this.mChooseFavoriteListener = chooseFavoriteListener;
    }

    public interface OnChooseFavoriteListener {
        void onChoose(Movie movie);
    }

    public void updateStatusLoading(boolean isLoading) {
        this.mIsLoading = isLoading;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        mIsMoreDataAvailable = moreDataAvailable;
    }

    public void add(Movie movie) {
        mGrouMovies.add(movie);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(List<Movie> movies) {
        mGrouMovies.addAll(movies);
        mIsLoading = false;
        notifyDataSetChanged();
    }

    public void remove(int index) {
        if (getItemCount() > 0) {
            mGrouMovies.remove(index);
            mIsLoading = false;
            notifyItemRemoved(index);
            notifyItemRangeChanged(index, getItemCount());
        }
    }

    public void clear() {
        mGrouMovies.clear();
        notifyDataSetChanged();
    }
}
