package hello.dcsms.bungkus.Utils;

import com.stericson.RootTools.RootTools;

public class ROOT {

	public static boolean isroot() {
		if (RootTools.isRootAvailable()) {
			return RootTools.isAccessGiven();
		} else
			return false;

	}
}