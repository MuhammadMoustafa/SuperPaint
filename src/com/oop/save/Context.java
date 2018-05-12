package com.oop.save;

public class Context {

	private Strategy strategy;
	
	public Context(Strategy strategy) {
		this.strategy = strategy;
	}

	public void executeSaveStrategy() {
		strategy.save();
	}
	
	public void executeLoadStrategy() {
		strategy.load();
	}
}
