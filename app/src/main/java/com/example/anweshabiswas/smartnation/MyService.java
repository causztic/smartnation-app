package com.example.anweshabiswas.smartnation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.pubsub.RedisPubSubConnection;
import com.lambdaworks.redis.pubsub.RedisPubSubListener;

import java.util.Map;

public class MyService extends Service {
    RedisPubSubConnection<String, String> connection;
    RedisClient client1;
    SharedPreferences preference;
    SharedPreferences.Editor editor;
    String channel;
    Map<String,?> keys;
    RetrieveFeedTask obj=new MyService.RetrieveFeedTask();
    boolean flag;
    public MyService()
    {

    }
    public void onCreate()
    {
        super.onCreate();
        flag=false;
        preference=getSharedPreferences("prefs", MODE_PRIVATE);
        editor=preference.edit();
        Log.w("TAG", "ScreenListenerService---OnCreate ");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        new MyService.RetrieveFeedTask().execute(getApplicationContext().getString(R.string.redis));


    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        preference=getSharedPreferences("prefs", MODE_PRIVATE);
        editor=preference.edit();
        flag=false;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

       new MyService.RetrieveFeedTask().execute(getApplicationContext().getString(R.string.redis));
        return START_NOT_STICKY;

    }

    public void showNotification(String m)
    {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, StudyMainActivity.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("Notification check")
                .setSmallIcon(R.drawable.notif_icon)
                .setContentTitle("CCTCV Update")
                .setContentText("Vacancy available in "+channel+" study")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
        connection.unsubscribe(channel);
        flag=false;

        client1.shutdown();
        stopSelf();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, RedisClient>
    {

        private Exception exception;
        protected RedisClient doInBackground(String... urls) {

            final RedisClient client = RedisClient.create(urls[0]);
            connection = client.connectPubSub();
            keys = preference.getAll();
            for (final Map.Entry<String, ?> entry : keys.entrySet()) {
                Log.i("sharedpref", entry.getKey().toString());
                if ((entry != null) && entry.getValue().toString().equalsIgnoreCase("true")) {
                    channel = entry.getKey();
                    flag=true;
                    connection.addListener(new RedisPubSubListener<String, String>() {
                        @Override
                        public void message(String channel, String message) {
                            Log.i("message received", message);
                            if (message.equalsIgnoreCase("1")) {
                                editor.putBoolean(channel, false);
                                editor.commit();
                                Log.i("channelfalse", channel + false + "");
                                showNotification(channel);

                            }

                        }

                        @Override
                        public void message(String pattern, String channel, String message) {

                        }

                        @Override
                        public void subscribed(String channel, long count) {
                            Log.i(channel, "subscribed");

                        }

                        @Override
                        public void psubscribed(String pattern, long count) {

                        }

                        @Override
                        public void unsubscribed(String channel, long count)
                        {
                            Log.i("unsubbed",channel);

                        }

                        @Override
                        public void punsubscribed(String pattern, long count)
                        {
                            Log.i("punsubbed",channel);
                        }
                    });



                }

            }
            return client;
        }


        protected void onPostExecute(final RedisClient client)
        {


                    if(flag==true)
                     connection.subscribe(channel);
                    else
                        connection.unsubscribe(channel);

                }



        }
    }

