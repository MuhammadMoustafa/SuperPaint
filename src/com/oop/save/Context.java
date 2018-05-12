package com.oop.save;

import java.util.List;

import javax.swing.JComponent;

import com.oop.gui.DrawPanel;
import com.oop.shapes.MyShape;

public class Context {

	private Strategy strategy;
	
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public Strategy getStrategy() {
		return this.strategy;
	}

	public Context(Strategy strategy) {
		this.strategy = strategy;
	}

	public void executeSaveStrategy(String fileName, List<MyShape> shapes) {
		strategy.save(fileName, shapes);
	}
	
	public List<MyShape> executeLoadStrategy(String fileName) {
		return strategy.load(fileName);
	}
}
