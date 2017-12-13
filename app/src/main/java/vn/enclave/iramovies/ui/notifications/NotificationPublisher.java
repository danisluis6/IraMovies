package vn.enclave.iramovies.ui.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import vn.enclave.iramovies.ui.activities.base.Home.HomeView;

/**
 *
 * Created by Lorence on 12/12/17.
 *
 * @Run: https://gist.github.com/BrandonSmith/6679223
 * => Thanks
 */

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);

        Intent activityIntent = new Intent(context, HomeView.class);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activityIntent.putExtra(NOTIFICATION_ID, id);
        context.startActivity(activityIntent);
    }

}