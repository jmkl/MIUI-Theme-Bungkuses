package hello.dcsms.bungkus.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Xml;

public class XMLHelper {
	public static void create(Context c,ThemeData themedata){
		File f = new File(Constran.BACKUP_DIR);
		File f2 = new File(Constran.CACHE_DIR);
		if(!f.exists())
			f.mkdirs();
		if(!f2.exists())
			f2.mkdirs();
		try {
		    FileOutputStream fos = new  FileOutputStream(Constran.DESC);
		 //  FileOutputStream fileos= c.openFileOutput(xmlFile, Context.MODE_PRIVATE);
		    XmlSerializer xmlSerializer = Xml.newSerializer();              
		    StringWriter writer = new StringWriter();
		    xmlSerializer.setOutput(writer);
		    xmlSerializer.startDocument("UTF-8", true);
		    
		    xmlSerializer.startTag(null, "MIUI-Theme");
		    
		    xmlSerializer.startTag(null, "title");
		    xmlSerializer.text(themedata.TH_NAMA);
		    xmlSerializer.endTag(null, "title");  
		    
		    xmlSerializer.startTag(null, "designer");
		    xmlSerializer.text(themedata.TH_DESIGNER);
		    xmlSerializer.endTag(null, "designer");  
		    
		    xmlSerializer.startTag(null, "author");
		    xmlSerializer.text(themedata.TH_AUTHOR);
		    xmlSerializer.endTag(null, "author");  
		    
		    xmlSerializer.startTag(null, "version");
		    xmlSerializer.text(themedata.TH_VERSI);
		    xmlSerializer.endTag(null, "version");  
		    
		    xmlSerializer.startTag(null, "uiVersion");
		    xmlSerializer.text(themedata.TH_UIVERSI);
		    xmlSerializer.endTag(null, "uiVersion");  
		    
		    xmlSerializer.startTag(null, "description");
		    xmlSerializer.text("this theme was backup with MIUI Theme Bungkuser");
		    xmlSerializer.endTag(null, "description");  
		    
		    xmlSerializer.endTag(null, "MIUI-Theme");
		    
		    xmlSerializer.endDocument();
		    xmlSerializer.flush();
		    String dataWrite = writer.toString();
		    fos.write(dataWrite.getBytes());
		    fos.close();
		}
		catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		catch (IllegalStateException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	}

}
