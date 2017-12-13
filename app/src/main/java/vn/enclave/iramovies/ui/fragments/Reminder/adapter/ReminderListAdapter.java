package vn.enclave.iramovies.ui.fragments.Reminder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import vn.enclave.iramovies.local.storage.entity.Reminder;
import vn.enclave.iramovies.services.IraMovieInfoAPIs;
import vn.enclave.iramovies.utilities.OverrideFonts;

/**
 *
 * Created by lorence on 06/12/2017.
 */

public class ReminderListAdapter extends RecyclerView.Adapter<ReminderListAdapter.ViewHolder> {

    private Context mContext;
    private List<Reminder> mGroupReminders;

    private OpenReminderDetail mOpenReminderDetail;

    public ReminderListAdapter(Context context, List<Reminder> groupReminders, OpenReminderDetail openReminderDetail) {
        this.mContext = context;
        this.mGroupReminders = groupReminders;
        this.mOpenReminderDetail = openReminderDetail;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reminder_medium, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Reminder reminder = mGroupReminders.get(position);
        viewHolder.tvSmallTitle.setText(reminder.getTitle());
        viewHolder.tvSmallDate.setText(reminder.getReminderDate());
        String poster = IraMovieInfoAPIs.Images.Small + reminder.getPosterPath();
        Glide.with(mContext)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(viewHolder.imvSmallThumbnail);
        viewHolder.imvIconArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOpenReminderDetail.openReminder(reminder);
            }
        });

    }

    public void updateReminder(Reminder reminder) {
        for (int index = 0; index < mGroupReminders.size(); index++) {
            if (mGroupReminders.get(index).getId().equals(reminder.getId())) {
                mGroupReminders.remove(mGroupReminders.get(index));
            }
        }
        mGroupReminders.add(reminder);
        notifyDataSetChanged();
    }

    public void removeReminder(int id) {
        for (int index = 0; index < mGroupReminders.size(); index++) {
            if (mGroupReminders.get(index).getId().equals(id)) {
                mGroupReminders.remove(mGroupReminders.get(index));
            }
        }
        notifyDataSetChanged();
    }

    public interface OpenReminderDetail {
        void openReminder(Reminder reminder);
    }

    @Override
    public int getItemCount() {
        return mGroupReminders.size();
    }

    public void setReminders(List<Reminder> reminders) {
        this.mGroupReminders = reminders;
        notifyDataSetChanged();
    }

    public ArrayList<Reminder> getList() {
        return (ArrayList<Reminder>) mGroupReminders;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.imvSmallThumbnail)
        ImageView imvSmallThumbnail;

        @BindView(R.id.tvSmallTitle)
        TextView tvSmallTitle;

        @BindView(R.id.tvSmallDate)
        TextView tvSmallDate;

        @BindView(R.id.imvIconArrow)
        ImageView imvIconArrow;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            initAttributes();
        }

        void initAttributes() {
            tvSmallTitle.setTypeface(OverrideFonts.getTypeFace(mContext, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.BOLD));
            tvSmallDate.setTypeface(OverrideFonts.getTypeFace(mContext, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
        }
    }
}
