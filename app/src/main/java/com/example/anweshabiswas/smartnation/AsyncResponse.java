package com.example.anweshabiswas.smartnation;

import com.lambdaworks.redis.RedisStringsConnection;
import com.lambdaworks.redis.pubsub.RedisPubSubConnection;

/**
 * Created by Anwesha Biswas on 6/12/2017.
 */


public interface AsyncResponse {
    void processFinish( RedisPubSubConnection<String, String> output);
}
