package pro.watchnews.watchnewspro.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.time.Month;
import java.time.Year;

import pro.watchnews.watchnewspro.fragments.MonthlyPackage;
import pro.watchnews.watchnewspro.fragments.SixMonthPackage;
import pro.watchnews.watchnewspro.fragments.YearlyPackage;

public class SubscriptionPagerAdapter extends FragmentStatePagerAdapter {

    int NUM_OF_TABS;
    Context mContext;

    public SubscriptionPagerAdapter(@NonNull FragmentManager fm, int NUM_OF_TABS, Context mContext) {
        super(fm);
        this.NUM_OF_TABS = NUM_OF_TABS;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                MonthlyPackage monthlyPackage = new MonthlyPackage();
                return monthlyPackage;
            case 1:
                SixMonthPackage sixMonthPackage = new SixMonthPackage();
                return  sixMonthPackage;
            case 2:
                YearlyPackage yearlyPackage = new YearlyPackage();
                return yearlyPackage;
            default:
                return null ;
        }
    }

    @Override
    public int getCount() {
        return NUM_OF_TABS;
    }
}
