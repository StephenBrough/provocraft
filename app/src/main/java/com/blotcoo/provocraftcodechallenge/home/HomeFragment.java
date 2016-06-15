package com.blotcoo.provocraftcodechallenge.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.blotcoo.provocraftcodechallenge.R;
import com.blotcoo.provocraftcodechallenge.util.network.models.Channel;
import com.blotcoo.provocraftcodechallenge.util.network.models.WeatherApiBaseModel;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class HomeFragment extends Fragment implements SearchResultsAdapter.OnResultClicked {
    private static final String TAG = "HomeFragment";

    @BindView(R.id.search)
    EditText search;

    @BindView(R.id.searchResults)
    RecyclerView searchResults;

    @BindView(R.id.content)
    FrameLayout content;

    Subscription searchObservable;
    HomeContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, v);

        FragmentSansFrontieres frag = (FragmentSansFrontieres) getActivity().getSupportFragmentManager().findFragmentByTag(FragmentSansFrontieres.TAG);
        this.presenter = frag.presenter;

        searchResults.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchResults.setAdapter(new SearchResultsAdapter(this));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Setup an observable that will fire off after 500ms of no user input
        searchObservable = RxTextView.textChangeEvents(search)
                .skip(1) // Event fires off immediately, so skip that unnecessary hit
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TextViewTextChangeEvent>() {
                    @Override
                    public void call(TextViewTextChangeEvent textViewTextChangeEvent) {
                        presenter.searchWeatherForecast(textViewTextChangeEvent.view().getText().toString(),
                                new Callback<WeatherApiBaseModel>() {
                                    @Override public void onResponse(Call<WeatherApiBaseModel> call, Response<WeatherApiBaseModel> response) {
                                        if (response.isSuccessful()) {
                                            Log.d(TAG, "onResponse: RESPONSE SUCCESSFUL");
                                            // Update the adapter
                                            Log.d(TAG, "onResponse: body: " + response.body().toString());
                                                ((SearchResultsAdapter) searchResults.getAdapter()).results = response.body().query;
                                                searchResults.getAdapter().notifyDataSetChanged();
                                                content.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    @Override public void onFailure(Call<WeatherApiBaseModel> call, Throwable t) {
                                        Toast.makeText(getActivity(), "There was a problem hitting the server, check your network connection and try again!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        // Release strong reference to views tied to this subscriber
        if (!searchObservable.isUnsubscribed())
            searchObservable.unsubscribe();
    }

    @Override
    public void clicked(Channel channel) {
        ((HomeContract.View) getActivity()).showForecastDetails(channel);
    }
}
