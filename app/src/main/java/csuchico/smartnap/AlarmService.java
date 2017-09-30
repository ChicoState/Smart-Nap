package csuchico.smartnap;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class AlarmService extends IntentService {

    private NotificationManager alarmNotificationManager;
    private AlertDialog alarmDialog;

    public AlarmService() {
        super("AlarmService");
    }

    static final int ALARM_SILENCE_REQUEST = 1;
    @Override
    public void onHandleIntent(Intent intent) {

        // Any code to setup the service must be here
        // before we call completeWakefulIntent(intent)
        startDialogActivity();

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
    private void startDialogActivity() {
        Intent dialogIntent = new Intent(this, AlarmDialog.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }

    private void sendNotification() {
        Log.d("AlarmService", "Preparing to send notification..");
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Alarm.class), 0);

        NotificationCompat.Builder alarmNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle("Alarm").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Silenced!"))
                .setContentText("Silenced!").setAutoCancel(true);


        alarmNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alarmNotificationBuilder.build());
        Log.d("AlarmService", "Notification sent.");
    }
    //private void sendNotification(String msg) {
    private void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                // data is processed here
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("AlarmService", "Preparing to send notification..");
                alarmNotificationManager = (NotificationManager) this
                        .getSystemService(Context.NOTIFICATION_SERVICE);

                PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                        new Intent(this, Alarm.class), 0);

                NotificationCompat.Builder alarmNotificationBuilder = new NotificationCompat.Builder(
                        this).setContentTitle("Alarm").setSmallIcon(R.mipmap.ic_launcher)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Silenced!"))
                        .setContentText("Silenced!").setAutoCancel(true);


                alarmNotificationBuilder.setContentIntent(contentIntent);
                alarmNotificationManager.notify(1, alarmNotificationBuilder.build());
                Log.d("AlarmService", "Notification sent.");
            }
        }
    }
}
