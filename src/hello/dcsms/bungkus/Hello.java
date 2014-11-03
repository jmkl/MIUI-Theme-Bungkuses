package hello.dcsms.bungkus;

import hello.dcsms.bungkus.Utils.Constran;
import hello.dcsms.bungkus.Utils.GetList;
import hello.dcsms.bungkus.Utils.ListThemeData;
import hello.dcsms.bungkus.Utils.Oneshot;
import hello.dcsms.bungkus.Utils.ROOT;
import hello.dcsms.bungkus.Utils.ThemeData;
import hello.dcsms.bungkus.Utils.XMLHelper;
import hello.dcsms.bungkus.Utils.ZipTask;
import hello.dcsms.bungkus.adapter.TabsAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Hello extends FragmentActivity implements OnClickListener {
	Button exe;
	EditText etnama, etdesigner, etbekaper, etversi, etuiversi;
	Context mcontext;
	SharedPreferences pref;
	ViewPager mPager;
	TabsAdapter tabadapter;
	List<Fragment> listfrag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(0x060d003a);
		setContentView(R.layout.hello);
		mPager = (ViewPager)findViewById(R.id.pager);
		listfrag = new ArrayList<Fragment>();
		listfrag.add(new InfoFragment());
		listfrag.add(new MTZFragment());
		tabadapter = new TabsAdapter(getSupportFragmentManager(), listfrag);
		mPager.setAdapter(tabadapter);	
		mcontext = this;
		if (!ROOT.isroot())
			Toast.makeText(
					mcontext,
					"no root access..\nit means u'll able to backup wallpaper & bootanimation",
					Toast.LENGTH_SHORT).show();
		else {
		}
		pref = getSharedPreferences("theme_info", Context.MODE_PRIVATE);

		Oneshot.doshit(this);
		exe = (Button) findViewById(R.id.exe);
		exe.setOnClickListener(this);

	}

	private String getText(EditText et) {
		return et.getText().toString();
	}

	private void findview(View v) {
		etnama = (EditText) v.findViewById(R.id.nama);
		etbekaper = (EditText) v.findViewById(R.id.author);
		etdesigner = (EditText) v.findViewById(R.id.designer);
		etversi = (EditText) v.findViewById(R.id.versi);
		etuiversi = (EditText) v.findViewById(R.id.uiversi);

		etnama.setText(pref.getString("nama", ""));
		etbekaper.setText(pref.getString("backuper", ""));
		etdesigner.setText(pref.getString("designer", ""));
		etversi.setText(pref.getString("versi", ""));
		etuiversi.setText(pref.getString("uiversi", ""));

	}

	@Override
	public void onClick(final View v) {
		final ListThemeData theme = GetList.Theme();
		final boolean[] boo = new boolean[theme.TDIR.size()];
		Arrays.fill(boo, Boolean.TRUE);
		AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
		dialog.setMultiChoiceItems(
				theme.TNAME.toArray(new String[theme.TNAME.size()]), boo,
				new OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {
						boo[which] = isChecked;

					}
				});
		dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				final List<String> dothis = new ArrayList<String>();
				for (int i = 0; i < theme.TDIR.size(); i++) {
					if (boo[i]) {
						dothis.add(theme.TDIR.get(i));
					}
				}
				AlertDialog.Builder name = new AlertDialog.Builder(v
						.getContext());
				final View view = getLayoutInflater().inflate(R.layout.data,
						null);
				name.setView(view);
				findview(view);
				name.setNeutralButton("Ok",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								doShit(dothis);

							}
						});
				name.show();

			}
		});
		dialog.show();
	}

	protected void doShit(List<String> data) {
		if (!dodescription()) {
			Toast.makeText(this, "Name already exist", Toast.LENGTH_SHORT)
					.show();
			return;
		}

		ThemeData themedata = new ThemeData();
		themedata.TH_NAMA = getText(etnama);
		themedata.TH_AUTHOR = getText(etbekaper);
		themedata.TH_DESIGNER = getText(etdesigner);
		themedata.TH_VERSI = getText(etversi);
		themedata.TH_UIVERSI = getText(etuiversi);
		Editor edit = pref.edit();
		edit.putString("nama", getText(etnama));
		edit.putString("backuper", getText(etbekaper));
		edit.putString("designer", getText(etdesigner));
		edit.putString("versi", getText(etversi));
		edit.putString("uiversi", getText(etuiversi));
		edit.apply();
		XMLHelper.create(mcontext, themedata);
		ZipTask zip = new ZipTask(mcontext, data, Constran.BACKUP_DIR + "/"
				+ getText(etnama) + ".mtz");
		zip.execute();

	}

	private boolean themeexist(String nama) {
		boolean exist = false;
		File files = new File(Constran.BACKUP_DIR);
		File[] themes = files.listFiles();
		for (File file : themes) {
			if (file.getName().equals(nama))
				exist = true;
		}
		return exist;
	}

	private boolean dodescription() {
		if (themeexist(etnama.getText().toString() + ".mtz"))
			return false;
		else
			return true;
	}
}
