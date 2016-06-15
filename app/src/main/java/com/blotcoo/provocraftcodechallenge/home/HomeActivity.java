package com.blotcoo.provocraftcodechallenge.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.blotcoo.provocraftcodechallenge.R;
import com.blotcoo.provocraftcodechallenge.details.DetailsFragment;
import com.blotcoo.provocraftcodechallenge.util.network.ApiImpl;
import com.blotcoo.provocraftcodechallenge.util.network.models.Channel;

public class HomeActivity extends AppCompatActivity implements HomeContract.View{
    private static final String TAG = "HomeActivity";

    HomeContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        // Check if Fragment Sans Frontieres is already added, if not, get one configured and saved
        if (getSupportFragmentManager().findFragmentByTag(FragmentSansFrontieres.TAG) == null) {
            FragmentSansFrontieres frag = new FragmentSansFrontieres();
            frag.presenter = new HomePresenterImpl(new ApiImpl());
            getSupportFragmentManager().beginTransaction().add(frag, FragmentSansFrontieres.TAG).commit();
            getSupportFragmentManager().executePendingTransactions();
        }

        // If the activity is completely fresh, load up the home fragment
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content, new HomeFragment()).commit();
        }
    }

    @Override
    public void showForecastDetails(Channel channel) {
        // Hide keyboard
        View v = getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

        DetailsFragment frag = new DetailsFragment();
        frag.c = channel;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, frag)
                .addToBackStack(null)
                .commit();
    }
}
