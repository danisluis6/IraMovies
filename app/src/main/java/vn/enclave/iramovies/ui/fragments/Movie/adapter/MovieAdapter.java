package vn.enclave.iramovies.ui.fragments.Movie.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
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
import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.services.IraMovieInfoAPIs;
import vn.enclave.iramovies.ui.activities.base.BaseView;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.OverrideFonts;
import vn.enclave.iramovies.utilities.Utils;

/**
 * Created by lorence on 14/11/2017.
 *
 * @Run:
 * @Run: http://www.devexchanges.info/2017/02/android-recyclerview-dynamically-load.html
 * => Done
 * @Run: https://stackoverflow.com/questions/32040798/recyclerview-oncreateviewholder-return-type-incompatibility-with-multiple-custom
 * => Done
 * @Run: https://codentrick.com/load-more-recyclerview-bottom-progressbar/
 * => Done
 * @Run: http://android-pratap.blogspot.in/2015/01/recyclerview-with-checkbox-example.html
 * => Fix save the states when scroll view in Recycler View
 * @Run: https://stackoverflow.com/questions/28581712/android-recyclerview-change-layout-file-list-to-grid-onoptionitemselected
 * => Done
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_MOVIE = 0;
    private final int TYPE_LOAD = 1;
    private Context mContext;
    private List<Movie> mGrouMovies;
    private BaseView mBaseView;
    /**
     * Load more
     */
    private OnLoadMoreListener mLoadMoreListener;
    private boolean mIsLoading = false;
    private boolean mIsMoreDataAvailable = true;
    private boolean isModeDisplay;

    /**
     * Set Favorite start
     */
    private OnChooseFavoriteListener mChooseFavoriteListener;
    private IMovieAdapter mIMovieAdapter;

    public MovieAdapter(Context context, BaseView baseView, List<Movie> grouMovies, boolean isModeDisplay, IMovieAdapter iMovieAdapter) {
        this.mContext = context;
        this.mGrouMovies = grouMovies;
        this.mBaseView = baseView;
        this.isModeDisplay = isModeDisplay;
        this.mIMovieAdapter = iMovieAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (isModeDisplay) {
            if (viewType == TYPE_MOVIE) {
                View view = LayoutInflater.from(this.mContext).inflate(R.layout.item_movie_list, viewGroup, false);
                return new MovieViewHolder(view);
            } else if (viewType == TYPE_LOAD) {
                View view = LayoutInflater.from(this.mContext).inflate(R.layout.item_load, viewGroup, false);
                return new LoadingViewHolder(view);
            }
        } else {
            if (viewType == TYPE_MOVIE) {
                View view = LayoutInflater.from(this.mContext).inflate(R.layout.item_movie_thumbnail, viewGroup, false);
                return new MovieViewHolder(view);
            } else if (viewType == TYPE_LOAD) {
                View view = LayoutInflater.from(this.mContext).inflate(R.layout.item_load, viewGroup, false);
                return new LoadingViewHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
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
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mIMovieAdapter.openDetailMovie(movie);
                    }
                });
                if (isModeDisplay) {
                    ((MovieViewHolder) holder).tvTitle.setText(movie.getTitle());
                    ((MovieViewHolder) holder).tvReleaseDate.setText(movie.getReleaseDate());
                    ((MovieViewHolder) holder).tvRating.setText(movie.getVoteAverage() + Constants.Keyboards.FORWARD_SLASH + "10.0");
                    ((MovieViewHolder) holder).tvOverview.setText(movie.getOverview());

                    ((MovieViewHolder) holder).imvFavorite.setImageResource((movie.getFavorite() == Constants.Favorites.FAVORITE) ? R.drawable.ic_star_picked : R.drawable.ic_star);
                    ((MovieViewHolder) holder).imvFavorite.setTag(mGrouMovies.get(mPosition));

                    String poster = IraMovieInfoAPIs.Images.Small + movie.getPosterPath();
                    Glide.with(mContext)
                            .load(poster)
                            .placeholder(R.drawable.load)
                            .into(((MovieViewHolder) holder).imvSmallThumbnail);

                    ((MovieViewHolder) holder).imvFavorite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            backupStatusWhenScrolling(v);
                        }

                        private void backupStatusWhenScrolling(View v) {
                            ImageView imvFavorite = (ImageView) v;
                            Movie movie = (Movie) imvFavorite.getTag();
                            if (Utils.isDoubleClick()) {
                                return;
                            }
                            if (movie.getFavorite() == Constants.Favorites.DEFAULT) {
                                updateView(Constants.Favorites.FAVORITE, imvFavorite);
                                navigateToAddNew(movie);
                            } else {
                                updateView(Constants.Favorites.DEFAULT, imvFavorite);
                                navigateToRemove(movie);
                            }
                        }

                        private void navigateToAddNew(Movie movie) {
                            mChooseFavoriteListener.onChoose(movie);
                        }

                        private void navigateToRemove(Movie movie) {
                            mChooseFavoriteListener.onRemove(movie);
                        }

                        private void updateView(int status, ImageView imvFavorite) {
                            movie.setFavorite(status);
                            mGrouMovies.get(mPosition).setFavorite(status);
                            imvFavorite.setImageResource((movie.getFavorite() == Constants.Favorites.FAVORITE) ? R.drawable.ic_star_picked : R.drawable.ic_star);
                        }
                    });
                } else {
                    ((MovieViewHolder) holder).tvTitle.setText(movie.getTitle());
                    String poster = IraMovieInfoAPIs.Images.Thumbnail + movie.getBackdropPath();
                    Glide.with(mContext)
                            .load(poster)
                            .placeholder(R.drawable.load)
                            .into(((MovieViewHolder) holder).imvSmallThumbnail);
                }
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

    public void refreshFavorite(Movie movie) {
        for (int index = 0; index < mGrouMovies.size(); index++) {
            if (mGrouMovies.get(index).getId().equals(movie.getId())) {
                mGrouMovies.get(index).setFavorite(Constants.Favorites.DEFAULT);
                break;
            }
        }
        notifyDataSetChanged();
    }

    /**
     * Interface
     */
    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.mLoadMoreListener = loadMoreListener;
    }

    public void setChooseFavoriteListener(OnChooseFavoriteListener chooseFavoriteListener) {
        this.mChooseFavoriteListener = chooseFavoriteListener;
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

    public void setModeDisplay(boolean isModeDisplay) {
        this.isModeDisplay = isModeDisplay;
    }

    public void refreshStatusFavorite(Movie movie) {
        for (int index = 0; index < mGrouMovies.size(); index++) {
            if (mGrouMovies.get(index).getId().equals(movie.getId())) {
                mGrouMovies.get(index).setFavorite(Constants.Favorites.FAVORITE);
            }
        }
        this.notifyDataSetChanged();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface OnChooseFavoriteListener {
        void onChoose(Movie movie);

        void onRemove(Movie movie);
    }

    public interface IMovieAdapter {
        void openDetailMovie(Movie movie);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @Nullable
        @BindView(R.id.tvReleaseDate)
        TextView tvReleaseDate;

        @Nullable
        @BindView(R.id.tvRating)
        TextView tvRating;

        @Nullable
        @BindView(R.id.tvOverview)
        TextView tvOverview;

        @BindView(R.id.imvSmallThumbnail)
        ImageView imvSmallThumbnail;

        @Nullable
        @BindView(R.id.imvFavorite)
        ImageView imvFavorite;

        MovieViewHolder(View view) {
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
}
