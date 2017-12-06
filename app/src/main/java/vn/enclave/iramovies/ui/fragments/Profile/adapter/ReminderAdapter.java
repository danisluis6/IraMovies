package vn.enclave.iramovies.ui.fragments.Profile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.entity.Reminder;
import vn.enclave.iramovies.utilities.OverrideFonts;

/**
 *
 * Created by lorence on 06/12/2017.
 */

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private Context mContext;
    private List<Reminder> mGroupReminders;

    public ReminderAdapter(Context context, List<Reminder> groupReminders) {
        this.mContext = context;
        this.mGroupReminders = groupReminders;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reminder_small, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Reminder reminder = mGroupReminders.get(position);
        viewHolder.tvTitle.setText(reminder.getTitle());
        viewHolder.tvReminder.setText(reminder.getReminderDate());
    }

    @Override
    public int getItemCount() {
        if (mGroupReminders.size() > 2) {
            return 2;
        }
        return mGroupReminders.size();
    }

    public void setReminders(List<Reminder> reminders) {
        this.mGroupReminders = reminders;
        notifyDataSetChanged();
    }

    public void addReminder(Reminder reminder) {
        this.mGroupReminders.add(reminder);
        notifyDataSetChanged();
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

    public List<Reminder> getList() {
        return mGroupReminders;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvReminder)
        TextView tvReminder;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            initAttributes();
        }

        void initAttributes() {
            tvTitle.setTypeface(OverrideFonts.getTypeFace(mContext, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.BOLD));
            tvReminder.setTypeface(OverrideFonts.getTypeFace(mContext, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
        }
    }
}
