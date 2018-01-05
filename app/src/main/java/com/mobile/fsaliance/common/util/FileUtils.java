package com.mobile.fsaliance.common.util;

import java.io.File;


public class FileUtils {
	
	// 文件是否存在
	public static boolean isFileExists(String sPath) {
		if (sPath == null || sPath.equals("")) {
			return false;
		}

		File f = new File(sPath);
		if (f.exists() && f.isFile()) {
			return true;
		}
		return false;
	}


}
