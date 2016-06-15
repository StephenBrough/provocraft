package com.blotcoo.provocraftcodechallenge.util.network.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephen on 6/14/2016.
 */
public class Item {
    public String title;
    public String lat;
    public String _long;
    public String link;
    public String pubDate;
    public Condition condition;
    public List<Forecast> forecast = new ArrayList<Forecast>();
    public String description;
    public Guid guid;
}
