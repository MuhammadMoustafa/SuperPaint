package com.oop.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.oop.save.FormatJSON;
import com.oop.save.FormatXML;

/**
 * Provides the GUI and encapsulates the DrawPanel It creates 3 buttons undo,
 * redo and clear. It creates 2 combobox colors and shapes. It creates 1
 * checkbox filled to select whether shape is filled or not. Has two private
 * inner classes One for handling button events Another for handling both combo
 * box events and checkbox events
 */
public class DrawFrame extends JFrame {
	public static DrawFrame drawFrame;
	private JLabel stausLabel; // label display mouse coordinates
	private DrawPanel panel; // draw panel for the shapes

	private JButton undo; // button to undo last drawn shape
	private JButton redo; // button to redo an undo
	private JButton clear; // button to clear panel
	private JButton save;
	private JButton load;
	private JButton sort;
	private JSpinner spinner;

	private JComboBox colors; // combobox with color options
	private JComboBox formats;

	// array of strings containing color options for JComboBox colors
	private String colorOptions[] = { "Black", "Blue", "Cyan", "Dark Gray", "Gray", "Green", "Light Gray", "Magenta",
			"Orange", "Pink", "Red", "White", "Yellow" };

	// aray of Color objects contating Color constants
	private Color colorArray[] = { Color.BLACK, Color.BLUE, Color.CYAN, Color.darkGray, Color.GRAY, Color.GREEN,
			Color.lightGray, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW };

	private JComboBox shapes; // combobox with shape options

	// array of strings containing shape options for JComboBox shapes
	private String shapeOptions[] = { "Line", "Rectangle", "Oval", "Red Rectangle", "Red Oval" };
	private String formatOptions[] = { "XML", "JSON" };
	private JCheckBox filled; // checkbox to select whether shape is filled or
								// not

	private JPanel widgetJPanel; // holds the widgets: buttons, comboboxes and
									// checkbox
	private JPanel widgetPadder; // encapsulates widgetJPanel and adds padding
									// around the edges

	/**
	 * This constructor sets the name of the JFrame. It also creates a DrawPanel
	 * object that extends JPanel for drawing the shapes and contains a
	 * statuslabel for mouse position. Initializes widgets for buttons,
	 * comboboxes and checkbox It also adds event handlers for the widgets
	 */
	private DrawFrame() {
		super("paint application TEAM7"); // sets the name of DrawFrame

		JLabel statusLabel = new JLabel(""); // create JLabel object to pass
												// into DrawPanel

		panel = new DrawPanel(statusLabel); // create draw panel and pass in
											// JLabel

		// create buttons
		undo = new JButton("Undo");
		redo = new JButton("Redo");
		clear = new JButton("Clear");
		save = new JButton("Save");
		load = new JButton("Load");
		sort = new JButton("sort");
		

		
		// from 1 to 10, in 1 steps start value 5  
		SpinnerNumberModel model1 = new SpinnerNumberModel(5, 1, 10, 1);  
		spinner = new JSpinner(model1);
		
		// create comboboxes
		colors = new JComboBox(colorOptions);
		shapes = new JComboBox(shapeOptions);
		formats = new JComboBox(formatOptions);

		// create checkbox
		filled = new JCheckBox("Filled");

		// JPanel object, widgetJPanel, with grid layout for widgets
		widgetJPanel = new JPanel();
		widgetJPanel.setLayout(new GridLayout(1,2, 1, 1)); // sets padding
																// widgets in
																// gridlayout

		// JPanel object, widgetPadder, with flowlayout to encapsulate and pad
		// the widgetJPanel
		widgetPadder = new JPanel();
		widgetPadder.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 5)); // sets
																			// padding
																			// around
																			// the
																			// edges

		// add widgets to widgetJPanel
		widgetJPanel.add(undo);
		widgetJPanel.add(redo);
		widgetJPanel.add(clear);
		widgetJPanel.add(colors);
		widgetJPanel.add(shapes);
		widgetJPanel.add(filled);
		widgetJPanel.add(sort);
		widgetJPanel.add(save);
		widgetJPanel.add(load);
		widgetJPanel.add(formats);
		widgetJPanel.add(spinner);

		widgetJPanel.setPreferredSize(new Dimension(1100, 30));
		// add widgetJPanel to widgetPadder
		widgetPadder.add(widgetJPanel);
		
		// add widgetPadder and panel to JFrame
		add(widgetPadder, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);

		// create new ButtonHandler for button event handling
		ButtonHandler buttonHandler = new ButtonHandler();
		undo.addActionListener(buttonHandler);
		redo.addActionListener(buttonHandler);
		clear.addActionListener(buttonHandler);
		save.addActionListener(buttonHandler);
		load.addActionListener(buttonHandler);
		sort.addActionListener(buttonHandler);

		// create handlers for combobox and checkbox
		ItemListenerHandler handler = new ItemListenerHandler();
		colors.addItemListener(handler);
		shapes.addItemListener(handler);
		filled.addItemListener(handler);
		formats.addItemListener(handler);

	    spinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				panel.spinnerChanged((int)spinner.getValue());
				
			}
	    });
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(1200, 900);
		setVisible(true);
		//setResizable(true);

	} // end DrawFrame constructor

	public static DrawFrame getInstance() {

		if (drawFrame == null) {
			return new DrawFrame();
		} else {
			return drawFrame;
		}
	}

	/**
	 * private inner class for button event handling
	 */
	private class ButtonHandler implements ActionListener {
		private String chooseFile() {
			JFileChooser fileChooser = new JFileChooser();

			int retval = fileChooser.showSaveDialog(panel);
			System.out.println(retval);
			if (retval == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				if (file == null) {
					panel.statusLabel.setText("file equal null");
					System.out.println("you are stuck here");
					return "0";
				}
				if (panel.getCurrentContext().getStrategy() instanceof FormatXML) {
					if (file.getName().toLowerCase().endsWith(".xml")) {
						System.out.println(file.getAbsolutePath());
						String filePath = file.getAbsolutePath();
						return filePath.substring(0, filePath.lastIndexOf('.'));
					}
				} else if (panel.getCurrentContext().getStrategy() instanceof FormatJSON) {
					if (file.getName().toLowerCase().endsWith(".json")) {
						System.out.println(file.getAbsolutePath());
						String filePath = file.getAbsolutePath();
						return filePath.substring(0, filePath.lastIndexOf('.'));
					}
				}
			}
			return "0";
		}

		// handles button events
		public void actionPerformed(ActionEvent event) {
			if (event.getActionCommand().equals("Undo")) {
				panel.clearLastShape();
			}

			else if (event.getActionCommand().equals("sort")) {
				panel.sortShapes();
			} else if (event.getActionCommand().equals("Redo")) {
				panel.redoLastShape();
			} else if (event.getActionCommand().equals("Clear")) {
				panel.clearDrawing();
			} else if (event.getActionCommand().equals("Save")) {
				String s = chooseFile();
				if (s.equals("0")) {
					panel.statusLabel.setText("Not a File");
				} else {
					panel.saveFile(s);
				}

				// panel.statusLabel.setText("Save");
			} else if (event.getActionCommand().equals("Load")) {
				String s = chooseFile();
				if (s.equals("0")) {
					panel.statusLabel.setText("Not a File");
				} else {
					panel.loadFile(s);
				}
			}

		} // end method actionPerformed
	} // end private inner class ButtonHandler

	/**
	 * private inner class for checkbox and combobox event handling
	 */
	private class ItemListenerHandler implements ItemListener {
		public void itemStateChanged(ItemEvent event) {
			// process filled checkbox events
			if (event.getSource() == filled) {
				boolean checkFill = filled.isSelected() ? true : false; //
				panel.setCurrentShapeFilled(checkFill);
			}

			// determine whether combo box selected
			if (event.getStateChange() == ItemEvent.SELECTED) {
				// if event source is combo box colors pass in colorArray at
				// index selected.
				if (event.getSource() == colors) {
					panel.setCurrentShapeColor(colorArray[colors.getSelectedIndex()]);
				}

				// else if event source is combo box shapes pass in index
				// selected
				else if (event.getSource() == shapes) {
					panel.setCurrentShapeType(shapes.getSelectedIndex());
				}

				else if (event.getSource() == formats) {
					panel.setCurrentContext(formats.getSelectedIndex());

					if (formats.getSelectedIndex() == 0) {
						panel.statusLabel.setText("XML");
					} else if (formats.getSelectedIndex() == 1) {
						panel.statusLabel.setText("JSON");
					}

				}
			}

		} // end method itemStateChanged
	}

} // end class DrawFrame