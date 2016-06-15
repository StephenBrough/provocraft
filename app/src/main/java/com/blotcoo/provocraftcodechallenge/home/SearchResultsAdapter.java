package com.blotcoo.provocraftcodechallenge.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blotcoo.provocraftcodechallenge.App;
import com.blotcoo.provocraftcodechallenge.R;
import com.blotcoo.provocraftcodechallenge.util.network.models.Channel;
import com.blotcoo.provocraftcodechallenge.util.network.models.Query;

import java.util.Arrays;
import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {
    private static final String TAG = "SearchResultsAdapter";

    public Query results;

    List<String> cloudy;
    List<String> sunny;
    List<String> rainy;
    List<String> snowy;

    interface OnResultClicked { void clicked(Channel channel);}
    OnResultClicked clickListener;

    public SearchResultsAdapter(OnResultClicked clicked) {
        this.clickListener = clicked;
        cloudy = Arrays.asList(App.getContext().getResources().getStringArray(R.array.cloudy));
        sunny  = Arrays.asList(App.getContext().getResources().getStringArray(R.array.sunny));
        rainy  = Arrays.asList(App.getContext().getResources().getStringArray(R.array.rainy));
        snowy  = Arrays.asList(App.getContext().getResources().getStringArray(R.array.snowy));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewHolder vh = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_search_result_item, parent, false));
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.clicked(results.results.channel.get(vh.getAdapterPosition()));
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Channel c = results.results.channel.get(position);
        holder.location.setText(c.location.city + ", " + c.location.region);
        holder.country.setText(c.location.country);
        holder.temperature.setText(c.item.condition.temp + "\u00B0" + c.units.temperature);
        holder.conditionsText.setText(c.item.condition.text);

        if (cloudy.contains(c.item.condition.code))
            holder.conditionsIcon.setImageResource(R.drawable.ic_cloudy);
        else if (sunny.contains(c.item.condition.code))
            holder.conditionsIcon.setImageResource(R.drawable.ic_sunny);
        else if (rainy.contains(c.item.condition.code))
            holder.conditionsIcon.setImageResource(R.drawable.ic_rainy);
        else if (snowy.contains(c.item.condition.code))
            holder.conditionsIcon.setImageResource(R.drawable.ic_snowy);
    }

    @Override
    public int getItemCount() {
        if (null == results)
            return 0;
        return results.count;
    }

    /**
     * Inner viewholder class
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView location;
        TextView country;
        TextView conditionsText; // Eg: "Sunny", "Cloudy"
        TextView temperature;
        ImageView conditionsIcon;
        public ViewHolder(View itemView) {
            super(itemView);
            location = (TextView) itemView.findViewById(R.id.location);
            country = (TextView) itemView.findViewById(R.id.country);
            conditionsText = (TextView) itemView.findViewById(R.id.conditionText);
            temperature = (TextView) itemView.findViewById(R.id.temperature);
            conditionsIcon = (ImageView) itemView.findViewById(R.id.conditionsIcon);
        }
    }
}
