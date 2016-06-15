package com.blotcoo.provocraftcodechallenge.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
        } else {
            View current = getCurrentFocus();
            if (current != null) {
                current.clearFocus();
            }
        }
    }

    @Override
    public void showForecastDetails(Channel channel) {
        ((FragmentSansFrontieres)getSupportFragmentManager().findFragmentByTag(FragmentSansFrontieres.TAG)).c = channel;

        DetailsFragment frag = new DetailsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, frag)
                .addToBackStack(null)
                .commit();
    }
}
