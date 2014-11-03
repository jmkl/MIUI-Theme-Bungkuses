package hello.dcsms.bungkus.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetList {
	/**
	 * Ambil data
	 * */
	public static ListThemeData Theme() {
		ListThemeData tdata = new ListThemeData();
		List<String> list = new ArrayList<String>();
		List<String> listnama = new ArrayList<String>();
		File f = new File(Constran.THEME_DIR);
		File[] files = f.listFiles();
		if(new File(Constran.WALLPAPER).exists()){
			listnama.add("wallpaper");
			list.add(Constran.WALLPAPER);
		}
		if(new File(Constran.BOOTANI).exists()){
			listnama.add("bootanimation");
			list.add(Constran.BOOTANI);
		}
		for (File file : files) {			
			if (file.isDirectory() && file.getName().equals("fonts")) {
				File[] fontfiles = file.listFiles();
				for (File file2 : fontfiles) {
					if (!isSymlink(file2)) {
						list.add(file2.getAbsolutePath());
						listnama.add(file2.getName());
					}
				}
			}
			if (file.isFile() && !file.getName().equals("description.xml")
					&& !file.getName().equals("config.config")&&!file.getName().equals("lock_wallpaper")) {
				list.add(file.getAbsolutePath());
				listnama.add(file.getName());
			}
		}
		tdata.TDIR = list;
		tdata.TNAME = listnama;
		return tdata;
	}

	public static boolean isSymlink(File file) {
		File canon;
		boolean islink = false;
		try {
			if (file.getParent() == null) {
				canon = file;
			} else {
				File canonDir;
				canonDir = file.getParentFile().getCanonicalFile();
				canon = new File(canonDir, file.getName());
			}
			islink = !canon.getCanonicalFile().equals(canon.getAbsoluteFile());

		} catch (IOException e) {
			e.printStackTrace();

		}
		return islink;

	}
}
