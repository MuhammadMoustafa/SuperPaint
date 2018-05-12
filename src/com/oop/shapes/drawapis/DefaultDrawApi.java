package com.oop.shapes.drawapis;

import java.awt.Color;
import java.awt.Graphics;

import com.oop.shapes.MyBoundedShape;
import com.oop.shapes.MyOval;
import com.oop.shapes.MyRectangle;

public class DefaultDrawApi implements DrawApi {

	@Override
	public void draw(Graphics g, MyBoundedShape shape) {
		if (shape instanceof MyOval) {
			 g.setColor( shape.getColor() ); //sets the color
		        if (shape.getFill()) //determines whether fill is true or false
		            g.fillOval( shape.getUpperLeftX(), shape.getUpperLeftY(), shape.getWidth(), shape.getHeight() ); //draws a filled oval
		        else
		            g.drawOval( shape.getUpperLeftX(), shape.getUpperLeftY(), shape.getWidth(), shape.getHeight() ); //draws a regular oval
			
			
		}else if (shape instanceof MyRectangle) {
			 g.setColor( shape.getColor() ); //sets the color
		        if (shape.getFill()) //determines whether fill is true or false
		            g.fillRect( shape.getUpperLeftX(), shape.getUpperLeftY(), shape.getWidth(), shape.getHeight() ); //draws a filled oval
		        else
		            g.drawRect( shape.getUpperLeftX(), shape.getUpperLeftY(), shape.getWidth(), shape.getHeight() ); //draws a regular oval
			
			
		}
	}

	}

