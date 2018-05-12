package com.oop.save;

import java.io.File;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFileChooser;

import com.oop.shapes.MyShape;

public class FormatXML implements Strategy {

	public FormatXML() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save(String fileName,List<MyShape> shapes) {

	}

	@Override
	public List<MyShape> load(String fileName) {
		return null;
		// TODO Auto-generated method stub
		
	}

}
