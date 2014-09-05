package common;

import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import panels.QRServerMain;

public class IconDB {
	private static final HashMap<String,ImageIcon> iconMap = new HashMap<String,ImageIcon>();
	public static final ImageIcon unknown = new ImageIcon(System.class.getResource("/icons/comm/question.png"), "unknown");
	
	static
	{
	 initIcon("/icons/ext/archive.png",".7z",".deb",".gz",".pkg",".rar",".rpm",".zip",".tar");
     initIcon("/icons/ext/ai.gif",".ai");
     initIcon("/icons/ext/aiff.gif",".aiff",".aif",".aifc");
     initIcon("/icons/ext/ace.gif",".ace");
     initIcon("/icons/ext/adp.gif",".adp");
     initIcon("/icons/ext/akf.gif",".akf");
     initIcon("/icons/ext/as.gif",".as");
     initIcon("/icons/ext/avi.gif",".avi");
     initIcon("/icons/ext/bat.gif",".bat");
     initIcon("/icons/ext/bas.gif",".bas");
     initIcon("/icons/ext/bfc.gif",".bfc");
     initIcon("/icons/ext/brn.gif",".brn");
     initIcon("/icons/ext/bmp.gif",".bmp");
     initIcon("/icons/ext/c.png",".c");
     initIcon("/icons/ext/cab.png",".cab");
     initIcon("/icons/ext/cat.gif",".cat");
     initIcon("/icons/ext/cdf.gif",".cdf");
     initIcon("/icons/ext/cdx.gif",".cdx");
     initIcon("/icons/ext/cfm.gif",".cfm");
     initIcon("/icons/ext/cha.gif",".cha");
     initIcon("/icons/ext/chk.gif",".chk");
     initIcon("/icons/ext/chm.gif",".chm");
     initIcon("/icons/ext/cil.gif",".cil");
     initIcon("/icons/ext/class.gif",".class");
     initIcon("/icons/ext/clr.gif",".clr");
     initIcon("/icons/ext/cls.gif",".cls");
     initIcon("/icons/ext/cmd.gif",".cmd");
     initIcon("/icons/ext/cpp.gif",".cpp");
     initIcon("/icons/ext/com.png",".com");
     initIcon("/icons/ext/css.gif",".css");
     initIcon("/icons/ext/dll.gif",".dll",".ocx");
     initIcon("/icons/ext/doc.gif",".doc",".docx");
     initIcon("/icons/ext/dv.gif",".dv");
     initIcon("/icons/ext/dsr.gif",".dsr");
     initIcon("/icons/ext/exe.gif",".exe");
     initIcon("/icons/ext/frm.gif",".frm");
     initIcon("/icons/ext/h.gif",".h");
     initIcon("/icons/ext/hpp.gif",".hpp");
     initIcon("/icons/ext/html.gif",".html");
     initIcon("/icons/ext/img.gif",".jpg",".gif",".png");
     initIcon("/icons/ext/iso.png",".iso");
     initIcon("/icons/ext/java.gif",".java");
     initIcon("/icons/ext/js.gif",".js");
     initIcon("/icons/ext/mad.gif",".mad");
     initIcon("/icons/ext/mov.gif",".mov",".qt");
     initIcon("/icons/ext/mp4.png",".mp4");
     initIcon("/icons/ext/msg.gif",".msg");
     initIcon("/icons/ext/ods.gif",".ods");
     initIcon("/icons/ext/odt.gif",".odt");
     initIcon("/icons/ext/pdf.gif",".pdf");
     initIcon("/icons/ext/php.gif",".php");
     initIcon("/icons/ext/ppt.gif",".ppt");
     initIcon("/icons/ext/pptx.png",".pptx");
     initIcon("/icons/ext/rm.gif",".rm");
     initIcon("/icons/ext/rtf.gif",".rtf");
     initIcon("/icons/ext/sys.png",".sys");
     initIcon("/icons/ext/tex.png",".tex");
     initIcon("/icons/ext/txt.gif",".txt",".log",".aspx");
     initIcon("/icons/ext/vbg.gif",".vbg");
     initIcon("/icons/ext/wav.gif",".wav");
     initIcon("/icons/ext/xls.gif",".xls");
     initIcon("/icons/ext/xml.gif",".xml");
     
     initIcon("/icons/comm/circle_green.png","green");
     initIcon("/icons/comm/circle_gray.png","gray");
     initIcon("/icons/comm/circle_red.png","red");
     }
	
	private static void initIcon(String path, String... exts)
	{
		ImageIcon icon = new ImageIcon(QRServerMain.class.getResource(path));
		for (String ext : exts)
		{
			Icon tmp = iconMap.get(ext);
			if (null != tmp)
				System.out.println("Warning icon extention" + ext + " is remapped");
			iconMap.put(ext, icon);
		}
	}
	
	public static ImageIcon getIcon(String ext)
	{
		ImageIcon i = iconMap.get(ext);
		return (null != i) ? i : unknown;
	}
}
