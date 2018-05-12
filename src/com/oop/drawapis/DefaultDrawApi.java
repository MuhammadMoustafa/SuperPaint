package com.oop.drawapis;

import java.awt.Color;
import java.awt.Graphics;

import com.oop.shapes.MyBoundedShape;
import com.oop.shapes.MyLine;
import com.oop.shapes.MyOval;
import com.oop.shapes.MyRectangle;
import com.oop.shapes.MyShape;

public class DefaultDrawApi implements DrawApi {

	@Override
	public void draw(Graphics g, MyShape shape) {
		if (shape instanceof MyOval) {
			g.setColor(shape.getColor()); // sets the color
			if (((MyOval) shape).getFill()) // determines whether fill is true
											// or false
				g.fillOval(((MyOval) shape).getUpperLeftX(), ((MyOval) shape).getUpperLeftY(),
						((MyOval) shape).getWidth(), ((MyOval) shape).getHeight()); // draws
																					// a
																					// filled
																					// oval
			else
				g.drawOval(((MyOval) shape).getUpperLeftX(), ((MyOval) shape).getUpperLeftY(),
						((MyOval) shape).getWidth(), ((MyOval) shape).getHeight()); // draws
																					// a
																					// regular
																					// oval

		} else if (shape instanceof MyRectangle) {
			g.setColor(shape.getColor()); // sets the color
			if (((MyRectangle) shape).getFill()) // determines whether fill is
													// true or false
				g.fillRect(((MyRectangle) shape).getUpperLeftX(), ((MyRectangle) shape).getUpperLeftY(),
						((MyRectangle) shape).getWidth(), ((MyRectangle) shape).getHeight()); // draws
																								// a
																								// filled
																								// oval
			else
				g.drawRect(((MyRectangle) shape).getUpperLeftX(), ((MyRectangle) shape).getUpperLeftY(),
						((MyRectangle) shape).getWidth(), ((MyRectangle) shape).getHeight()); // draws
																								// a
																								// regular
																								// oval

		} else if (shape instanceof MyLine) {
			g.setColor(shape.getColor()); // sets the color
			g.drawLine(shape.getX1(), shape.getY1(), shape.getX2(), shape.getY2()); // draws
																					// the
																					// line
		}
	}

}
