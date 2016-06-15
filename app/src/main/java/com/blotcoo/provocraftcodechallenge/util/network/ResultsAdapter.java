package com.blotcoo.provocraftcodechallenge.util.network;

import com.blotcoo.provocraftcodechallenge.util.network.models.Channel;
import com.blotcoo.provocraftcodechallenge.util.network.models.Results;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ResultsAdapter implements JsonDeserializer<Results> {
    private static final String TAG = "ResultsAdapter";

    @Override
    public Results deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json != null && !json.isJsonNull()) {
            JsonObject jObject = json.getAsJsonObject();
            Results results = new Results();
            if (jObject.get("channel") != null && !jObject.get("channel").isJsonNull()) {
                // Check if channel is a json object
                if (jObject.get("channel").isJsonObject()) {
                    results.channel.add((Channel) context.deserialize(jObject.get("channel").getAsJsonObject(), Channel.class));//jObject.get("channel").as)
                } else {
                    // This is a list, add all the things!
                    for (JsonElement e : jObject.get("channel").getAsJsonArray()) {
                        results.channel.add((Channel) context.deserialize(e, Channel.class));
                    }
                }
            }
            return results;
        } else {
            return null;
        }
    }
}