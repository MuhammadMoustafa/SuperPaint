package com.oop.shapes;

import java.awt.Color;

public abstract class AbstractFactory {

	public AbstractFactory() {
		// TODO Auto-generated constructor stub
	}
	public abstract MyShape getShape(ShapeEnum shapeType, int x1, int y1, int x2, int y2, Color color, boolean fill);
	public abstract MyShape getShape(ShapeEnum shapeType, int x1, int y1, int x2, int y2, Color color);
	public abstract MyShape getShape(String shapeType);
}
