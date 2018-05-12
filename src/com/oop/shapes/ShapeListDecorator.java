package com.oop.shapes;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.oop.save.FormatJSON;
import com.oop.save.FormatXML;

public class ShapeListDecorator implements Observer {

	public List<MyShape> myShapes;

	public ShapeListDecorator() {
		super();
		myShapes = new ArrayList<MyShape>();
	}

	@Override
	public void update(Observable o, Object obj) {
		if (o instanceof FormatJSON || o instanceof FormatXML) {
			if (obj instanceof List<?>) {
				myShapes = (List<MyShape>) obj;
			}

		}

	}

}
