package com.test.lucene;

import java.io.File;
import java.io.FileFilter;

public class TextFileFilter implements FileFilter {

	@Override
	public boolean accept(File pathName){
		return pathName.getName().toLowerCase().endsWith(".txt");
	}
	
	
}
