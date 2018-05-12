package com.oop.shapes.drawapis;

import java.awt.Color;
import java.awt.Graphics;

import com.oop.shapes.MyBoundedShape;

public class RedOvalShape implements DrawApi{

	@Override
	public void draw(Graphics g, MyBoundedShape shape) {

        g.setColor( Color.RED ); //sets the color
        if (shape.getFill()) //determines whether fill is true or false
            g.fillOval( shape.getUpperLeftX(), shape.getUpperLeftY(), shape.getWidth(), shape.getHeight() ); //draws a filled oval
        else
            g.drawOval( shape.getUpperLeftX(), shape.getUpperLeftY(), shape.getWidth(), shape.getHeight() ); //draws a regular oval
	
	}

}
