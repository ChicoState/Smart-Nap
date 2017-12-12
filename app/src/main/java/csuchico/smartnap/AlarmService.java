package csuchico.smartnap;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class AlarmService extends IntentService {

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public void onHandleIntent(Intent intent) {

        // Any code to setup the service must be here
        // before we call completeWakefulIntent(intent)
        startDialogActivity(intent);

        // completeWakefulIntent() releases the CPU wake lock set by the receiver
        // This ensures that onHandleIntent() runs its code above while holding the CPU awake
        Log.i("AlarmService", "AlarmService calling completeWakefulIntent on AlarmReceiver");
        AlarmReceiver.completeWakefulIntent(intent);
    }

    /**
     * private void startDialogActivity()
     *
     *  @desc:  Launches the activity for the alarm dialog to allow users to silence the alarm.
     *
     */
    private void startDialogActivity(Intent intent) {
        Intent dialogIntent = new Intent(this, AlarmDialog.class);
        dialogIntent.putExtras(intent.getExtras()); // pass extras along
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }
}
