package com.oop.save;

import java.util.List;


import com.oop.shapes.MyShape;

public interface Strategy {
	
	public void save(String fileName, List<MyShape> shapes);
	public List<MyShape> load(String fileName);
}
