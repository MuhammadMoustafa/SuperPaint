package com.oop.shapes;

import java.awt.Color;

public class ShapeFactory {
	// use getShape method to get object of type shape
	public MyShape getShape(String shapeType) {
		if (shapeType == null) {
			return null;
		}
		if (shapeType.equals(ShapeEnum.OVAL)) {
			return new MyOval();
		} else if (shapeType.equals(ShapeEnum.RECTANGLE)) {
			return new MyRectangle();
		} else if (shapeType.equals(ShapeEnum.LINE)) {
			return new MyLine();
		}
		return null;
	}
	
	// use getShape method to get object of type shape
	public MyShape getShape(ShapeEnum shapeType, int x1, int y1, int x2, int y2, Color color, boolean fill) {
		if (shapeType == null) {
			return null;
		}
		if (shapeType.equals(ShapeEnum.OVAL)) {
			return new MyOval(x1, y1, x2, y2, color, fill);
		} else if (shapeType.equals(ShapeEnum.RECTANGLE)) {
			return new MyRectangle(x1, y1, x2, y2, color, fill);
		}
		return null;
	}
	
	// use getShape method to get object of type shape
	public MyShape getShape(ShapeEnum shapeType, int x1, int y1, int x2, int y2, Color color) {
		if (shapeType == null) {
			return null;
		}
		if (shapeType.equals(ShapeEnum.LINE)) {
			return new MyLine(x1, y1, x2, y2, color);
		}
		return null;
	}
}