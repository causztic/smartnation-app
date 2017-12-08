package com.example.anweshabiswas.smartnation;

/**
 * Created by Anwesha Biswas on 6/12/2017.
 */

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisStringsConnection;
import com.lambdaworks.redis.pubsub.RedisPubSubConnection;
import com.lambdaworks.redis.pubsub.RedisPubSubListener;

import java.net.URL;


public class RedisConnect {


    RedisPubSubConnection<String, String> connection;
    RedisStringsConnection<String, String> connection1;
    String key;
    TextView occ;


    public RedisConnect(TextView o, String k)
    {
        key=k;
        occ=o;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        new RetrieveFeedTask().execute("redis://redistogo:eeb60232febb9e34c9d61b54948de9b1@grouper.redistogo.com:11914");

    }



    class RetrieveFeedTask extends AsyncTask<String, Void, RedisClient> {

        private Exception exception;
        protected RedisClient doInBackground(String... urls) {

               RedisClient client=RedisClient.create(urls[0]);
               return client;

        }

        protected void onPostExecute(RedisClient client) {

            connection1=client.connect();
            Log.i("AnweshaRedis","connection made");
            Log.i("key value",key);
            String value;
            if(!key.equalsIgnoreCase("55:quiet"))
             value=connection1.get("stat:"+key+":latest");
            else
                value=connection1.get(key);
            occ.setText(key+":"+value+" people ");
//            Log.i("Anweshamsg",value);

            client.shutdown();


        }
    }

}
