package com.huge.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;

public class WordUtil {
	private static String DOC_PATH = "D:\\workspace\\trunk_hr\\src\\main\\webapp\\home\\pact\\PT.doc";

	public static HWPFDocument getDoc(String path) throws FileNotFoundException, IOException{
		File file = new File(path);
		HWPFDocument doc = new HWPFDocument(new BufferedInputStream(new FileInputStream(file)));
		return doc;
	}
	
	public static void writeDocByTemplate(Map<String,String> map,String templatePath,String destPath) throws FileNotFoundException, IOException{
		HWPFDocument doc = getDoc(templatePath);
		Range range = doc.getRange();
		Iterator<Entry<String,String>> ite = map.entrySet().iterator();
		Entry<String,String> entry = null;
		String key = null,value = null;
		while(ite.hasNext()){
			entry = ite.next();
			key = entry.getKey();
			value = entry.getValue();
			range.replaceText(key, value);
		}
		OutputStream os = new FileOutputStream(destPath);
		doc.write(os);
		if(os!=null){
			os.close();
		}
	}
	
	public static String readDoc(String path) throws Exception {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		WordExtractor extractor = new WordExtractor(fis);
		String docText = extractor.getText();
		return docText;
	}

	public static void main(String[] args) throws Exception {
		String myDoc = readDoc(DOC_PATH);
		System.out.println(myDoc);
		Map<String,String> map = new HashMap<String,String>();
		map.put("${company}", "北京瑞志龙腾科技有限公司");
		writeDocByTemplate(map,DOC_PATH,"d:\\doc\\newDoc1.doc");
	}
}
