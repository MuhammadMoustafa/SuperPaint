package com.oop.shapes.drawapis;

import java.awt.Graphics;

import com.oop.shapes.MyBoundedShape;

public interface DrawApi {
	void draw(Graphics g ,MyBoundedShape shape);

}
