package vn.enclave.iramovies.ui.fragments.Reminder;

import java.util.List;

import vn.enclave.iramovies.local.storage.entity.Reminder;

/**
 *
 * Created by lorence on 13/11/2017.
 */

interface IReminderView {

    void onSuccess(Reminder reminder);

    void onFailure(String message);

    void onReminderSuccess(List<Reminder> groupReminders);
}
