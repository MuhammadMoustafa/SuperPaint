package com.oop.drawapis;

import java.awt.Color;
import java.awt.Graphics;

import com.oop.shapes.MyRectangle;
import com.oop.shapes.MyShape;

public class RedRectangeShape implements DrawApi {

	@Override
	public void draw(Graphics g, MyShape shape) {

		MyRectangle myShape = (MyRectangle) shape;

		g.setColor(Color.RED); // sets the color
		if (myShape.getFill()) // determines whether fill is true or false
			g.fillRect(myShape.getUpperLeftX(), myShape.getUpperLeftY(), myShape.getWidth(), myShape.getHeight()); // draws
		// a
		// filled
		// oval
		else
			g.drawRect(myShape.getUpperLeftX(), myShape.getUpperLeftY(), myShape.getWidth(), myShape.getHeight()); // draws
		// a
		// regula // oval

	}
}
