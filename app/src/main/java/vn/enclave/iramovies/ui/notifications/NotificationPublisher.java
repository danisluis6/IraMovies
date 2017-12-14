package vn.enclave.iramovies.ui.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Lorence on 12/12/17.
 *
 */

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    private static UpdateReminder mUpdateReminder;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);
        if(mUpdateReminder != null)
        {
            mUpdateReminder.onUpdate(id);
        }
    }

    public interface UpdateReminder {
        void onUpdate(int id);
    }

    public static void setUpdateReminder(UpdateReminder updateReminder) {
        NotificationPublisher.mUpdateReminder = updateReminder;
    }
}