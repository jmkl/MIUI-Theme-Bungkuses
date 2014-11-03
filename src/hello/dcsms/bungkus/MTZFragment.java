package hello.dcsms.bungkus;

import hello.dcsms.bungkus.adapter.ThemeAdapter;
import hello.dcsms.bungkus.adapter.ThemeData;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MTZFragment extends Fragment implements OnItemClickListener {
	ListView lv;
	ThemeAdapter adapter;
	List<ThemeData> td;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		LinearLayout v = (LinearLayout) inflater.inflate(R.layout.list, null);
		lv = (ListView) v.findViewById(R.id.listtheme);
		adapter = new ThemeAdapter(getActivity(), getthemedata());
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
		return v;
	}

	NumberFormat mDecimalFormat = new DecimalFormat("##0.0");
	NumberFormat mIntegerFormat = NumberFormat.getIntegerInstance();
	String mB = "B";
	String mKB = "KB";
	String mMB = "MB";
	String mS = "s";

	private String formatsize(long bytes, boolean speed) {
		if (bytes > 10485760) { // 1024 * 1024 * 10
			return (speed ? "" : "(") + mIntegerFormat.format(bytes / 1048576)
					+ (speed ? mMB + "/" + mS : mMB + ")");
		} else if (bytes > 1048576) { // 1024 * 1024
			return (speed ? "" : "(")
					+ mDecimalFormat.format(((float) bytes) / 1048576f)
					+ (speed ? mMB + "/" + mS : mMB + ")");
		} else if (bytes > 10240) { // 1024 * 10
			return (speed ? "" : "(") + mIntegerFormat.format(bytes / 1024)
					+ (speed ? mKB + "/" + mS : mKB + ")");
		} else { // 1024
			return (speed ? "" : "(")
					+ mDecimalFormat.format(((float) bytes) / 1024f)
					+ (speed ? mKB + "/" + mS : mKB + ")");
		}
	}

	private List<ThemeData> getthemedata() {
		td = new ArrayList<ThemeData>();
		File f = new File(Environment.getExternalStorageDirectory()
				+ "/MIUI/Bungkus");
		if (!f.exists())
			f.mkdirs();

		File[] themes = f.listFiles();
		for (File file : themes) {
			if (file.getName().endsWith("mtz")) {

				ThemeData t = new ThemeData();
				t.NAMA = file.getName();
				t.PATH = file.getPath();

				t.SIZE = formatsize(file.length(),false);
				Date d = new Date(file.lastModified());
				t.DATES = d.toString();

				td.add(t);
			}

		}
		return td;
	}

	@Override
	public void onItemClick(AdapterView<?> paramAdapterView, View v, int pos,
			long paramLong) {
		if(td!=null&&td.size()>-1){
			String path = td.get(pos).PATH;
			Intent intent = new Intent("android.intent.action.MAIN");
			intent.setComponent(ComponentName
					.unflattenFromString("com.android.thememanager/com.android.thememanager.ApplyThemeForScreenshot"));
			Bundle b = new Bundle();
			b.putString("theme_file_path", path);
			b.putString("api_called_from", "MIUI Theme Bungkusers");
			intent.putExtras(b);
			startActivity(intent);
		}

	}
}
