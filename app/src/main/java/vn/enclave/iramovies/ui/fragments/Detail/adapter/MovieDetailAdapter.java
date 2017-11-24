package vn.enclave.iramovies.ui.fragments.Detail.adapter;

import android.content.Context;
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
import vn.enclave.iramovies.services.IraMovieInfoAPIs;
import vn.enclave.iramovies.ui.activities.base.BaseView;
import vn.enclave.iramovies.ui.fragments.Movie.bean.CastCrew;
import vn.enclave.iramovies.utilities.OverrideFonts;

/**
 *
 * Created by lorence on 24/11/2017.
 */

public class MovieDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CastCrew> mCastCrews;
    private BaseView mBaseView;

    public MovieDetailAdapter(Context context, BaseView baseView, List<CastCrew> castCrewList) {
        this.mContext = context;
        this.mBaseView = baseView;
        this.mCastCrews = castCrewList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.item_castcrew, parent, false);
        return new MovieDetailAdapter.CastCrewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CastCrewViewHolder) holder).tvName.setText(mCastCrews.get(position).getName());
        final String path = IraMovieInfoAPIs.Images.Small + mCastCrews.get(position).getPathImage();
        Glide.with(mContext)
                .load(path)
                .placeholder(R.drawable.load)
                .into(((CastCrewViewHolder) holder).imvPhoto);

    }

    @Override
    public int getItemCount() {
        return mCastCrews.size();
    }

    class CastCrewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;

        @BindView(R.id.imvPhoto)
        ImageView imvPhoto;

        CastCrewViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            initAttributes();
        }

        void initAttributes() {
            tvName.setTypeface(OverrideFonts.getTypeFace(mBaseView, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.BOLD));
        }
    }
}
