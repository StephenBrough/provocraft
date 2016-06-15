package com.blotcoo.provocraftcodechallenge.home;

import android.support.v4.app.Fragment;

/**
 * This fragment is wild and free! A fragment without
 * the oppressive rule of configuration change! It will save
 * us all in our save state!
 *
 * Seriously, this holds our presenter and other state so it doesn't get crushed
 * when you turn the phone. And other configuration changes. Makes it easier to
 * keep complex objects around instead of using the save instance state Bundle
 */
public class FragmentSansFrontieres extends Fragment {
    public static String TAG = "FragmentSansFrontieres";
    public HomeContract.Presenter presenter;

    public FragmentSansFrontieres() {
        setRetainInstance(true);
    }
}
