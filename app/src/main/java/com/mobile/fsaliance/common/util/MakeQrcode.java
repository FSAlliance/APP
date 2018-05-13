package com.mobile.fsaliance.common.util;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;


/**
  * @date 创建时间：2018/1/19 15:31
  * @auther tanyadong
  * @Description 生成二维码
*/
public class MakeQrcode {

	/**
	 * @author: tanyadong
	 * @Title: 生成二维码
	 * @Description: generateQRCode
	 * @date: 2018/1/19 15:30
	 */
	public static Bitmap generateQRCode(String content, int width, int height) {
		try {
			HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			// 设置编码方式utf-8
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			//设置二维码的纠错级别为h
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			BitMatrix matrix = new MultiFormatWriter().encode(content,
					BarcodeFormat.QR_CODE, width, height, hints);
			return bitMatrix2Bitmap(matrix);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @author: tanyadong
	 * @Title: 转成bitmap
	 * @Description: bitMatrix2Bitmap
	 * @date: 2018/1/19 15:30
	 */
	private static Bitmap bitMatrix2Bitmap(BitMatrix matrix) {
		matrix = updateBit(matrix, 5);
		int w = matrix.getWidth();
		int h = matrix.getHeight();
		int[] rawData = new int[w * h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int color = Color.WHITE;
				if (matrix.get(i, j)) {
					// 有内容的部分，颜色设置为黑色，当然这里可以自己修改成喜欢的颜色
					color = Color.BLACK;
				}
//                if (i < rec[0] || j < rec[1] || i > (rec[0] + rec[2]) || j > (rec[1] + rec[3])){
//                    color = Color.RED;
//                }
				rawData[i + (j * w)] = color;
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		bitmap.setPixels(rawData, 0, w, 0, 0, w, h);
		return bitmap;
	}


	/**
	 * @author: tanyadong
	 * @Title: 去除自带白边，自定义白边
	 * @Description: updateBit
	 * @date: 2018/1/19 15:28
	 */
	private static BitMatrix updateBit(BitMatrix matrix, int margin){
		int tempM = margin*2;
		int[] rec = matrix.getEnclosingRectangle();   //获取二维码图案的属性
		int resWidth = rec[2] + tempM;
		int resHeight = rec[3] + tempM;
		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
		resMatrix.clear();
		for(int i= margin; i < resWidth- margin; i++){   //循环，将二维码图案绘制到新的bitMatrix中
			for(int j=margin; j < resHeight-margin; j++){
				if(matrix.get(i-margin + rec[0], j-margin + rec[1])){
					resMatrix.set(i,j);
				}
			}
		}
		return resMatrix;
	}

	/**
	 * @author: tanyadong
	 * @Title: savePicpath
	 * @Description: bitmap 保存在本地路径中
	 * @date: 2018/1/19 15:27
	 */
	public static String savePicpath(Bitmap bitmap, String path) {
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(path);
			if (fileOutputStream != null) {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;

	}
}
