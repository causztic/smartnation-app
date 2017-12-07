package com.example.anweshabiswas.smartnation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.pubsub.RedisPubSubConnection;
import com.lambdaworks.redis.pubsub.RedisPubSubListener;

public class MyService extends Service {
    RedisPubSubConnection<String, String> connection;
    RedisClient client1;
    public MyService()
    {

    }
    public void onCreate()
    {
        super.onCreate();
        Log.w("TAG", "ScreenListenerService---OnCreate ");
        client1=RedisClient.create();
        connection = client1.connectPubSub();

    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        String channel=intent.getStringExtra("type");
        Log.i("service",channel);


        connection.addListener(new RedisPubSubListener<String, String>() {
            @Override
            public void message(String channel, String message)
            {
                Log.i("message received",message);
                if(message.equalsIgnoreCase("1"))
                {
                    showNotification(channel);
                }

            }

            @Override
            public void message(String pattern, String channel, String message)
            {
                Log.i("message received1",message);


            }

            @Override
            public void subscribed(String channel, long count)
            {
                Log.i(channel,"subscribed");

            }

            @Override
            public void psubscribed(String pattern, long count) {

            }

            @Override
            public void unsubscribed(String channel, long count) {

            }

            @Override
            public void punsubscribed(String pattern, long count) {

            } });
        connection.subscribe(channel);

        return START_NOT_STICKY;


    }

    public void showNotification(String m)
    {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, BlockActivity.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("Notification check")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("Notification title")
                .setContentText(m)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

        client1.shutdown();
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
