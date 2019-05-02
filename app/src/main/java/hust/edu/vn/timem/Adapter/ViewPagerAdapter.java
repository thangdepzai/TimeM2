package hust.edu.vn.timem.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> data = new ArrayList<>();
    private List<String> dataTitle = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return data.get(i);
    }

    @Override
    public int getCount() {
        return data.size();
    }
    public void addFragment(Fragment fragment, String title){
        data.add(fragment);
        dataTitle.add(title);

    }
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position){
//        return dataTitle.get(position);
//    }
}
