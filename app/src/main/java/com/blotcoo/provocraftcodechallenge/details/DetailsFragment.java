package com.blotcoo.provocraftcodechallenge.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blotcoo.provocraftcodechallenge.App;
import com.blotcoo.provocraftcodechallenge.R;
import com.blotcoo.provocraftcodechallenge.home.FragmentSansFrontieres;
import com.blotcoo.provocraftcodechallenge.util.network.models.Channel;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends Fragment {

    public Channel c;

    // Lists of our string resources
    List<String> cloudy;
    List<String> sunny;
    List<String> rainy;
    List<String> snowy;

    // Bind all the views
    @BindView(R.id.temperature)
    TextView temperature;
    @BindView(R.id.conditionText)
    TextView conditionsText;
    @BindView(R.id.conditionsIcon)
    ImageView conditionsIcon;
    @BindView(R.id.forecast)
    LinearLayout forecast;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.country)
    TextView country;
    @BindView(R.id.chill)
    TextView chill;
    @BindView(R.id.direction)
    TextView direction;
    @BindView(R.id.speed)
    TextView speed;
    @BindView(R.id.humidity)
    TextView humidity;
    @BindView(R.id.pressure)
    TextView pressure;
    @BindView(R.id.rising)
    TextView rising;
    @BindView(R.id.visibility)
    TextView visibility;
    @BindView(R.id.sunrise)
    TextView sunrise;
    @BindView(R.id.sunset)
    TextView sunset;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.details_fragment, container, false);
        ButterKnife.bind(this, v);

        c = ((FragmentSansFrontieres)getActivity().getSupportFragmentManager().findFragmentByTag(FragmentSansFrontieres.TAG)).c;

        cloudy = Arrays.asList(App.getContext().getResources().getStringArray(R.array.cloudy));
        sunny = Arrays.asList(App.getContext().getResources().getStringArray(R.array.sunny));
        rainy = Arrays.asList(App.getContext().getResources().getStringArray(R.array.rainy));
        snowy = Arrays.asList(App.getContext().getResources().getStringArray(R.array.snowy));

        location.setText(c.location.city + ", " + c.location.region);
        country.setText(c.location.country);
        temperature.setText(c.item.condition.temp + "\u00B0" + c.units.temperature);
        conditionsText.setText(c.item.condition.text);

        chill.setText(c.wind.chill);
        direction.setText(c.wind.direction);
        speed.setText(c.wind.speed);
        humidity.setText(c.atmosphere.humidity);
        pressure.setText(c.atmosphere.pressure);
        rising.setText(c.atmosphere.rising);
        visibility.setText(c.atmosphere.visibility);
        sunrise.setText(c.astronomy.sunrise);
        sunset.setText(c.astronomy.sunset);
        conditionsIcon.setImageResource(getConditionIcon(c.item.condition.code));

        // Setup forecast
        for (int i = 0; i < 7; i++) {
            View day = LayoutInflater.from(getActivity()).inflate(R.layout.forecast, forecast, false);
            ((TextView) day.findViewById(R.id.date)).setText(c.item.forecast.get(i).day);
            ((ImageView) day.findViewById(R.id.icon)).setImageResource(getConditionIcon(c.item.forecast.get(i).code));
            ((TextView) day.findViewById(R.id.description)).setText(c.item.forecast.get(i).text);
            ((TextView) day.findViewById(R.id.high)).setText(c.item.forecast.get(i).high + "\u00B0" + c.units.temperature);
            ((TextView) day.findViewById(R.id.low)).setText(c.item.forecast.get(i).low + "\u00B0" + c.units.temperature);

            forecast.addView(day);
        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Hide keyboard
        View focus = getActivity().getCurrentFocus();
        if (focus != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
    }

    private int getConditionIcon(String code) {
        if (cloudy.contains(c.item.condition.code))
            return R.drawable.ic_cloudy;
        else if (sunny.contains(c.item.condition.code))
            return R.drawable.ic_sunny;
        else if (rainy.contains(c.item.condition.code))
            return R.drawable.ic_rainy;
        else if (snowy.contains(c.item.condition.code))
            return R.drawable.ic_snowy;
        else return R.drawable.ic_sunny;
    }
}
