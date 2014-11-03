package hello.dcsms.bungkus.adapter;

import hello.dcsms.bungkus.R;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ThemeAdapter extends BaseAdapter {
	private List<ThemeData> themedata;
	private static LayoutInflater inflater = null;

	public ThemeAdapter(Activity activity, List<ThemeData> td) {
		// TODO Auto-generated constructor stub
		themedata = td;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return themedata.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return themedata.get(pos);
	}

	@Override
	public long getItemId(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup arg2) {
		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.themedetail, null);
		}
		ThemeData td = themedata.get(pos);
		TextView tvnama, tvsize, tvdate;
		tvnama = (TextView) vi.findViewById(R.id.namatheme);
		tvsize = (TextView) vi.findViewById(R.id.sizetheme);
		tvdate = (TextView) vi.findViewById(R.id.datetheme);
		tvnama.setText(td.NAMA);
		tvsize.setText(td.SIZE);
		tvdate.setText(td.DATES);
		return vi;
	}

}
