package com.modxroidlabs.notificationexample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    NotificationManager mNotificationManager;
    int totalMessages=0;
    int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_CHANNEL_ID = 4565;
    //Notification Channel
    String NOTIFICATION_CHANNEL_NAME = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void showFirstNotification()
    {
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this);
        nBuilder.setContentTitle("Notification");
        nBuilder.setContentText("You have received a new Notification");
        nBuilder.setTicker("New Message");
        nBuilder.setAutoCancel(true);
        nBuilder.setSmallIcon(R.drawable.ic_action_notification);
        nBuilder.setNumber(++totalMessages);

        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(pendingIntent);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, nBuilder.build());
    }

    private void showSecondNotificationInboxStyle()
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setContentTitle("New Message");
        mBuilder.setContentText("You've received new message.");
        mBuilder.setTicker("New Message Alert!");
        mBuilder.setAutoCancel(true);
        mBuilder.setSmallIcon(R.drawable.ic_action_notification);
        mBuilder.setNumber(++totalMessages);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        String[] notificationArray = new String[6];
        notificationArray[0] = "First Line";
        notificationArray[1] = "Second Line";
        notificationArray[2] = "Third Line";
        notificationArray[3] = "Fourth Line";
        notificationArray[4] = "Fifth Line";
        notificationArray[5] = "Sixth Line";

        inboxStyle.setBigContentTitle("Notification Details.");

        for (String aNotificationArray : notificationArray)
        {
            inboxStyle.addLine(aNotificationArray);
        }
        mBuilder.setStyle(inboxStyle);

        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }

    //For Android O only test
    private void showAndroidOOnlyNotification()
    {
        CharSequence name = getString(R.string.app_name);
        String description = "Hello Hello";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel("channelId", "channelName", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(description);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Notification.Builder notificationBuilder;

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND); // Fix for https://code.google.com/p/android/issues/detail?id=53313

        notificationBuilder= new Notification.Builder(this)
                .setContentTitle(getString(R.string.app_name));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_NAME);
        }

        notificationBuilder.setSmallIcon(R.drawable.ic_stat_name);
        notificationBuilder.setContentText("Notification Title");

        Notification notification = notificationBuilder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
        /*
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_NAME)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .setSound(null)
                .setChannelId(NOTIFICATION_CHANNEL_NAME)
                .setContent("")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(R.drawable.ic_action_notification)
                .setAutoCancel(false);
        */
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnFirst:
                showFirstNotification();
                break;

            case R.id.btnSecond:
                showSecondNotificationInboxStyle();
                break;

            case R.id.btnThird:
                showAndroidOOnlyNotification();
                break;

            default:
                Toast.makeText(this, "No Selection", Toast.LENGTH_SHORT).show();
        }
    }
}
