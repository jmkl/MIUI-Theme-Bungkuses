package hello.dcsms.bungkus.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsAdapter extends FragmentPagerAdapter {
	private List<Fragment> mFragment;

	public TabsAdapter(FragmentManager fragmentManager, List<Fragment> mfrag) {
		super(fragmentManager);
		mFragment = mfrag;

	}


	@Override
	public Fragment getItem(int pos) {
		// TODO Auto-generated method stub
		return mFragment.get(pos);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFragment.size();
	}

}
