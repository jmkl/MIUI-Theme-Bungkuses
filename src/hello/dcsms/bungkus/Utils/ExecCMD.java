package hello.dcsms.bungkus.Utils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.content.Context;
import android.util.Log;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.Command;

public class ExecCMD {
	private Context c;


	public ExecCMD(Context c) {
		this.c = c;
	}

	public void go(String... params) {
		Command command = new Command(0, params) {

			@Override
			public void commandTerminated(int arg0, String arg1) {

			}

			@Override
			public void commandOutput(int arg0, String arg1) {
				Log.e("ROOT CMD : " + Integer.toString(arg0), arg1);
			}

			@Override
			public void commandCompleted(int arg0, int arg1) {

			}

		};
		try {
			RootTools.getShell(true).add(command);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (RootDeniedException e) {
			e.printStackTrace();
		}
		
	}

}
