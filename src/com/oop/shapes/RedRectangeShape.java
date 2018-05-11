package com.oop.shapes;

import java.awt.Color;
import java.awt.Graphics;

public class RedRectangeShape implements DrawApi {

	@Override
	public void draw(Graphics g, MyBoundedShape shape) {

		g.setColor(Color.RED); // sets the color
		if (shape.getFill()) // determines whether fill is true or false
			g.fillRect(shape.getUpperLeftX(), shape.getUpperLeftY(), shape.getWidth(), shape.getHeight()); // draws
																											// a
																											// filled
																											// oval
		else
			g.drawRect(shape.getUpperLeftX(), shape.getUpperLeftY(), shape.getWidth(), shape.getHeight()); // draws
																											// a
																											// regula																								// oval

	}
}
