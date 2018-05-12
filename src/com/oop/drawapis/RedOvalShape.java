package com.oop.drawapis;

import java.awt.Color;
import java.awt.Graphics;

import com.oop.shapes.MyOval;
import com.oop.shapes.MyShape;


public class RedOvalShape implements DrawApi{

	@Override
	public void draw(Graphics g, MyShape shape) {
		
		MyOval myShape = (MyOval) shape;

        g.setColor( Color.RED ); //sets the color
        if (myShape.getFill()) //determines whether fill is true or false
            g.fillOval( myShape.getUpperLeftX(), myShape.getUpperLeftY(), myShape.getWidth(), myShape.getHeight() ); //draws a filled oval
        else
            g.drawOval( myShape.getUpperLeftX(), myShape.getUpperLeftY(), myShape.getWidth(), myShape.getHeight() ); //draws a regular oval
	
	}

}
