package hello.dcsms.bungkus.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ZipTask extends AsyncTask<Void, Void, Boolean> {
	List<String> themedata;
	String zipfile;
	ProgressDialog pd;
	Context context;

	private static void copyFileUsingFileChannels(File source, File dest)
			throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}

	public ZipTask(Context c, List<String> td, String zip) {
		themedata = td;
		zipfile = zip;
		context = c;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pd = new ProgressDialog(context);
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		pd.setMessage("Wait a sec...");
		pd.show();
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		pd.dismiss();
		Toast.makeText(
				context,
				result ? "Backup suckceed!!blah"
						: "Ouch, something went wrong.. Try Again",
				Toast.LENGTH_SHORT).show();

	}

	@Override
	protected Boolean doInBackground(Void... params) {
		boolean boo = false, wallbool = false, bootanibool = false;
		File lockwallp = new File(Constran.THEME_DIR + "/lock_wallpaper");
		File fonts = new File(Constran.THEME_DIR + "/fonts");
		boolean backupfont = false;
		ExecCMD exe = new ExecCMD(context);
		ArrayList data = new ArrayList();

		for (int i = 0; i < themedata.size(); i++) {
			File f = new File(themedata.get(i));
			if (f.isFile()) {
				if (f.getName().endsWith("ttf") || f.getName().endsWith("otf"))
					backupfont = true;
				else if (f.getName().equals("wallpaper")) {
					wallbool = true;
					exe.go("mkdir /sdcard/MIUI/Bungkus/.cache/wallpaper",
							"cp "
									+ Constran.WALLPAPER
									+ " /sdcard/MIUI/Bungkus/.cache/wallpaper/default_wallpaper.jpg");
				} else if (f.getName().equals("bootanimation.zip")) {
					exe.go("mkdir /sdcard/MIUI/Bungkus/.cache/boots",
							"cp "
									+ Constran.BOOTANI
									+ " /sdcard/MIUI/Bungkus/.cache/boots/bootanimation.zip");
					bootanibool = true;
				} else
					data.add(new File(themedata.get(i)));
			}

		}
		try {
			ZipFile zip = new ZipFile(zipfile);
			ZipParameters parameters = new ZipParameters();
			// Set the compression level.
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			if (data.size() > 0)
				zip.addFiles(data, parameters);
			zip.addFile(new File(Constran.DESC), parameters);

			zip.addFolder(Constran.CACHE_DIR + "/preview", parameters);
			if (bootanibool)
				zip.addFolder(Constran.CACHE_DIR + "/boots", parameters);
			if (wallbool)
				zip.addFile(new File(Constran.CACHE_DIR
						+ "/wallpaper/default_wallpaper.jpg"), parameters);

			if (backupfont) {
				File[] fnts = fonts.listFiles();
				for (File file : fnts) {
					if (!GetList.isSymlink(file))
						try {
							File folder = new File(Constran.CACHE_DIR
									+ "/fonts");
							if (!folder.exists())
								folder.mkdirs();
							else
								folder.delete();

							if (!folder.exists())
								folder.mkdirs();
							copyFileUsingFileChannels(file,
									new File(Constran.CACHE_DIR + "/fonts/"
											+ file.getName()));
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
				zip.addFolder(Constran.CACHE_DIR + "/fonts", parameters);

			}

			if (lockwallp.exists()) {
				File lockfile = new File(Constran.CACHE_DIR
						+ "/default_lock_wallpaper.jpg");
				try {
					copyFileUsingFileChannels(lockwallp, lockfile);
				} catch (IOException e) {
					e.printStackTrace();
				}

				parameters.setRootFolderInZip("wallpaper/");
				zip.addFile(lockfile, parameters);
			}
			boo =true;
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			boo= false;
		}

		return boo;
	}
}
