package com.oop.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.oop.shapes.ShapeEnum;
import com.oop.shapes.ShapeFactory;
import com.oop.shapes.ShapeListDecorator;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import com.oop.save.Context;
import com.oop.save.FormatJSON;
import com.oop.save.FormatXML;
import com.oop.save.Strategy;
import com.oop.shapes.AbstractFactory;
import com.oop.shapes.FactoryEnum;
import com.oop.shapes.FactoryProducer;
import com.oop.shapes.MyBoundedShape;
import com.oop.shapes.MyShape;
import com.oop.drawapis.RedOvalShape;
import com.oop.drawapis.RedRectangeShape;

/**
 * This class handles mouse events and uses them to draw shapes. It contains a
 * dynamic stack myShapes which is the shapes drawn on the panel. It contains a
 * dynamic stack clearedShape which is the shapes cleared from the panel. It has
 * many variables for the current shape [type, variable to store shape object,
 * color, fill]. It contains a JLabel called statusLabel for the mouse
 * coordinates It has mutator methods for currentShapeType, currentShapeColor
 * and currentShapeFilled. It has methods for undoing, redoing and clearing
 * shapes. It has a private inner class MouseHandler which extends MouseAdapter
 * and handles mouse and mouse motion events used for drawing the current shape.
 */

public class DrawPanel extends JPanel {
	private ShapeListDecorator myShapes; // dynamic stack of shapes
	private List<MyShape> clearedShapes; // dynamic stack of cleared shapes from
											// undo

	// current Shape variables
	private int currentShapeType; // 0 for line, 1 for rect, 2 for oval
	private MyShape currentShapeObject; // stores the current shape object
	private Color currentShapeColor; // current shape color
	private boolean currentShapeFilled; // determine whether shape is filled or
										// not

	private FormatXML xmlStrategy;
	private FormatJSON jsonStreategy;
	private Context context;

	JLabel statusLabel; // status label for mouse coordinates

	/**
	 * This constructor initializes the dynamic stack for myShapes and
	 * clearedShapes. It sets the current shape variables to default values. It
	 * initalizes statusLabel from JLabel passed in. Sets up the panel and adds
	 * event handling for mouse events.
	 */
	public DrawPanel(JLabel statusLabel) {

		myShapes = new ShapeListDecorator(); // initialize myShapes dynamic
												// stack
		clearedShapes = new ArrayList<MyShape>(); // initialize clearedShapes
													// dynamic stack

		// Initialize current Shape variables
		currentShapeType = 0;
		currentShapeObject = null;
		currentShapeColor = Color.BLACK;
		currentShapeFilled = false;

		this.statusLabel = statusLabel; // Initialize statusLabel

		setLayout(new BorderLayout()); // sets layout to border layout; default
										// is flow layout
		setBackground(Color.WHITE); // sets background color of panel to white
		add(statusLabel, BorderLayout.SOUTH); // adds a statuslabel to the south
												// border

		xmlStrategy = new FormatXML();
		jsonStreategy=new FormatJSON();
		context = new Context(xmlStrategy);
		xmlStrategy = new FormatXML();		
		xmlStrategy.addObserver(myShapes);
		jsonStreategy.addObserver(myShapes);
		context = new Context(this.xmlStrategy);


		// event handling for mouse and mouse motion events
		MouseHandler handler = new MouseHandler();
		addMouseListener(handler);
		addMouseMotionListener(handler);
	}

	/**
	 * Calls the draw method for the existing shapes.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// draw the shapes
		List<MyShape> shapeArray = myShapes.myShapes;
		for (int counter = 0; counter < shapeArray.size(); counter++)
			shapeArray.get(counter).draw(g);

		// draws the current Shape Object if it is not null
		if (currentShapeObject != null)
			currentShapeObject.draw(g);
	}

	// Mutator methods for currentShapeType, currentShapeColor and
	// currentShapeFilled

	/**
	 * Sets the currentShapeType to type (0 for line, 1 for rect, 2 for oval)
	 * passed in.
	 */
	public void setCurrentShapeType(int type) {
		currentShapeType = type;
	}

	/**
	 * Sets the currentShapeColor to the Color object passed in. The Color
	 * object contains the color for the current shape.
	 */
	public void setCurrentShapeColor(Color color) {
		currentShapeColor = color;
	}

	/**
	 * Sets the boolean currentShapeFilled to boolean filled passed in. If
	 * filled=true, current shape is filled. If filled=false, current shape is
	 * not filled.
	 */
	public void setCurrentShapeFilled(boolean filled) {
		currentShapeFilled = filled;
	}

	/**
	 * Clear the last shape drawn and calls repaint() to redraw the panel if
	 * clearedShapes is not empty
	 */
	public void clearLastShape() {
		if (!myShapes.myShapes.isEmpty()) {
			if (myShapes.myShapes.size() != 0) {
			}
			clearedShapes.add(myShapes.myShapes.remove(myShapes.myShapes.size() - 1));
			repaint();
		}
	}

	/**
	 * Redo the last shape cleared if clearedShapes is not empty It calls
	 * repaint() to redraw the panel.
	 */
	public void redoLastShape() {
		if (!clearedShapes.isEmpty()) {
			myShapes.myShapes.add(clearedShapes.remove(clearedShapes.size() - 1));
			repaint();
		}
	}

	/**
	 * Remove all shapes in current drawing. Also makes clearedShapes empty
	 * since you cannot redo after clear. It called repaint() to redraw the
	 * panel.
	 */
	public void clearDrawing() {
		myShapes.myShapes.clear();
		clearedShapes.clear();
		repaint();
	}

	/**
	 * Private inner class that implements MouseAdapter and does event handling
	 * for mouse events.
	 */
	private class MouseHandler extends MouseAdapter {
		/**
		 * When mouse is pressed draw a shape object based on type, color and
		 * filled. X1,Y1 & X2,Y2 coordinate for the drawn shape are both set to
		 * the same X & Y mouse position.
		 */
		public void mousePressed(MouseEvent event) {
			
			FactoryProducer factory = new FactoryProducer();
			AbstractFactory shapeFactory = factory.getFactory(FactoryEnum.SHAPE);
			
			switch (currentShapeType) {
			case 0:

				currentShapeObject = shapeFactory.getShape(ShapeEnum.LINE, event.getX(), event.getY(), event.getX(),
						event.getY(), currentShapeColor);
				break;
			case 1:
				currentShapeObject = shapeFactory.getShape(ShapeEnum.RECTANGLE, event.getX(), event.getY(),
						event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
				break;
			case 2:
				currentShapeObject = shapeFactory.getShape(ShapeEnum.OVAL, event.getX(), event.getY(), event.getX(),
						event.getY(), currentShapeColor, currentShapeFilled);
				break;
			case 3:

				currentShapeObject = shapeFactory.getShape(ShapeEnum.RECTANGLE, event.getX(), event.getY(),
						event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
				currentShapeObject.setColor(Color.RED);
				((MyBoundedShape) currentShapeObject).setDrawApi(new RedRectangeShape());

				break;
			case 4:
				currentShapeObject = shapeFactory.getShape(ShapeEnum.OVAL, event.getX(), event.getY(), event.getX(),
						event.getY(), currentShapeColor, currentShapeFilled);
				currentShapeObject.setColor(Color.RED);
				((MyBoundedShape) currentShapeObject).setDrawApi(new RedOvalShape());
				break;

			}// end switch case
		} // end method mousePressed

		/**
		 * When mouse is released set currentShapeObject's x2 & y2 to mouse pos.
		 * Then addFront currentShapeObject onto the myShapes dynamic Stack and
		 * set currentShapeObject to null [clearing current shape object since
		 * it has been drawn]. Lastly, it clears all shape objects in
		 * clearedShapes [because you cannot redo after a new drawing] and calls
		 * repaint() to redraw panel.
		 */
		public void mouseReleased(MouseEvent event) {
			// sets currentShapeObject x2 & Y2
			currentShapeObject.setX2(event.getX());
			currentShapeObject.setY2(event.getY());

			myShapes.myShapes.add(currentShapeObject); // addFront
														// currentShapeObject
			// onto myShapes

			currentShapeObject = null; // sets currentShapeObject to null
			clearedShapes.clear(); // clears clearedShapes
			repaint();

		} // end method mouseReleased

		/**
		 * This method gets the mouse pos when it is moving and sets it to
		 * statusLabel.
		 */
		public void mouseMoved(MouseEvent event) {
			statusLabel.setText(String.format("Mouse Coordinates X: %d Y: %d", event.getX(), event.getY()));
		} // end method mouseMoved

		/**
		 * This method gets the mouse position when it is dragging and sets x2 &
		 * y2 of current shape to the mouse pos It also gets the mouse position
		 * when it is dragging and sets it to statusLabel Then it calls
		 * repaint() to redraw the panel
		 */
		public void mouseDragged(MouseEvent event) {
			// sets currentShapeObject x2 & Y2
			currentShapeObject.setX2(event.getX());
			currentShapeObject.setY2(event.getY());

			// sets statusLabel to current mouse position
			statusLabel.setText(String.format("Mouse Coordinates X: %d Y: %d", event.getX(), event.getY()));

			repaint();

		} // end method mouseDragged

	}// end MouseHandler

	public void saveFile(String fileName) {

		context.executeSaveStrategy(fileName, myShapes.myShapes);
	}

	public void loadFile(String fileName) {
		context.executeLoadStrategy(fileName);
		repaint();
	}

	// 0 For XML 1 For JSON
	public void setCurrentContext(int selectedIndex) {
		if (selectedIndex == 0) {
			this.context.setStrategy(this.xmlStrategy);
		} else if (selectedIndex == 1) {
			this.context.setStrategy(this.jsonStreategy);
		}
	}

	public Context getCurrentContext() {
		return this.context;
	}

} // end class DrawPanel