package com.oop.drawapis;

import java.awt.Graphics;

import com.oop.shapes.MyBoundedShape;

public interface DrawApi {
	void draw(Graphics g ,MyBoundedShape shape);

}
