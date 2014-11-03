package hello.dcsms.bungkus.Utils;

import java.io.File;

import android.content.Context;

public class Oneshot {
	public static void doshit(Context c){
		File wall = new File(Constran.CACHE_DIR+"/wallpaper");
		File preview = new File(Constran.CACHE_DIR+"/preview");
		if (!wall.exists())
			Asset.copy(c, Constran.CACHE_DIR+"/wallpaper", "files/wallpaper");
		if(!preview.exists())
			Asset.copy(c, Constran.CACHE_DIR+"/preview", "files/preview");
	}

}
