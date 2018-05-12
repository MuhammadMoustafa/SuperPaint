package com.oop.shapes;

public class FactoryProducer {

	public FactoryProducer() {
		// TODO Auto-generated constructor stub
	}
	
	public AbstractFactory getFactory(FactoryEnum factory) {
		if(factory.equals(FactoryEnum.SHAPE)) {
			return new ShapeFactory();
		}
		return null;
	}

}
