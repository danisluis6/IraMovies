package vn.enclave.iramovies.ui.fragments.Favorite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.services.IraMovieInfoAPIs;
import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.ui.activities.base.BaseView;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.OverrideFonts;
import vn.enclave.iramovies.utilities.Utils;

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

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{

    private Context mContext;
    private List<Movie> mOriginalGroupMovies;
    private List<Movie> mFilterMovies;
    private BaseView mBaseView;
    private IFavoriteAdapter mIFavoriteAdapter;

    /** Set Favorite start */
    private OnRemoveFavoriteListener mRemoveFavoriteListener;

    public FavoriteAdapter(Context context, BaseView baseView, List<Movie> grouMovies, IFavoriteAdapter iFavoriteAdapter) {
        mContext = context;
        mOriginalGroupMovies = grouMovies;
        mFilterMovies = mOriginalGroupMovies;
        mBaseView = baseView;
        mIFavoriteAdapter = iFavoriteAdapter;
    }

    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoriteAdapter.ViewHolder holder, int position){
        final int mPosition = position;
        final Movie movie = mFilterMovies.get(mPosition);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIFavoriteAdapter.openDetailMovie(movie);
            }
        });
        holder.tvTitle.setText(movie.getTitle());
        holder.tvReleaseDate.setText(movie.getReleaseDate());
        holder.tvRating.setText(movie.getVoteAverage() + Constants.Keyboards.FORWARD_SLASH + "10.0");
        holder.tvOverview.setText(movie.getOverview());

        holder.imvFavorite.setImageResource((movie.getFavorite() == Constants.Favorites.FAVORITE) ? R.drawable.ic_star_picked : R.drawable.ic_star);
        holder.imvFavorite.setTag(mFilterMovies.get(mPosition));

        String poster = IraMovieInfoAPIs.Images.Thumbnail + movie.getPosterPath();
        Glide.with(mContext)
                .load(poster)
                .placeholder(R.drawable.load)
                .into((holder.imvSmallThumbnail));

        holder.imvFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movie.setFavorite(Constants.Favorites.DEFAULT);
                mRemoveFavoriteListener.onRemove(movie);
            }

        });

    }

    /**
     * https://stackoverflow.com/questions/34429090/getitemcount-return-only-the-original-content-of-recyclerview
     * => Decide that mOriginalGroupMovies or MFilterMovies is returned
     * @return
     */
    @Override
    public int getItemCount() {
        return mFilterMovies.size();
    }

    public void setMovies(List<Movie> movies) {
        mOriginalGroupMovies = movies;
        mFilterMovies = mOriginalGroupMovies;
        this.notifyDataSetChanged();
    }

    public void filter(CharSequence search) {
        if (TextUtils.isEmpty(search)) {
            mFilterMovies = mOriginalGroupMovies;
        } else {
            mFilterMovies = new ArrayList<>();
            search = search.toString().trim().toLowerCase();
            for (Movie movie: mOriginalGroupMovies) {
                String temp = movie.getTitle().trim().toLowerCase();
                if (temp.contains(search)) {
                    mFilterMovies.add(movie);
                }
            }
        };
        this.notifyDataSetChanged();
    }

    public interface IFavoriteAdapter {
        void openDetailMovie(Movie movie);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

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

        ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            initAttributes();
        }

        void initAttributes() {
            tvTitle.setTypeface(OverrideFonts.getTypeFace(mBaseView, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.BOLD));
        }

    }

    public void setRemoveFavoriteListener (OnRemoveFavoriteListener removeFavoriteListener) {
        this.mRemoveFavoriteListener = removeFavoriteListener;
    }

    public interface OnRemoveFavoriteListener {
        void onRemove(Movie movie);
    }

    public void add(Movie movie) {
        mOriginalGroupMovies.add(movie);
        mFilterMovies = mOriginalGroupMovies;
        notifyDataSetChanged();
    }

    public void remove(Movie movie) {
        for (int index = 0; index < mOriginalGroupMovies.size(); index++) {
            if (mOriginalGroupMovies.get(index).getTitle().equals(movie.getTitle())) {
                mOriginalGroupMovies.remove(index);
            }
        }
        mFilterMovies = mOriginalGroupMovies;
        this.notifyDataSetChanged();
    }
}
