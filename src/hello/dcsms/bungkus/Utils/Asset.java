package hello.dcsms.bungkus.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class Asset {
	public static void copy(Context context, String folder, String assetfolder) {

		File defICONDIR = new File(folder);
		if (!defICONDIR.exists()) {
			defICONDIR.mkdirs();
		}

		AssetManager assetManager = context.getAssets();
		String[] files = null;
		try {
			files = assetManager.list(assetfolder);
		} catch (IOException e) {
			Log.e("tag", e.getMessage());
		}

		for (String filename : files) {
			File f = new File(folder + "/" + filename);
			if (!f.exists()) {
				InputStream in = null;
				OutputStream out = null;
				try {
					in = assetManager.open(assetfolder + "/" + filename);
					out = new FileOutputStream(new File(folder, filename));

					byte[] buf = new byte[1024];
					int len;
					try {
						while ((len = in.read(buf, 0, buf.length)) != -1) {
							out.write(buf, 0, len);
						}
					} finally {
						in.close();
						out.close();
					}
				} catch (Exception e) {
					Log.e("tag", e.getMessage());
				}
			}
		}

	}

}
