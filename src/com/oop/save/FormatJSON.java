package com.oop.save;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.stream.Collectors;

import javax.swing.JComponent;

import com.oop.shapes.MyBoundedShape;
import com.oop.shapes.MyShape;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.Color;
import java.io.FileReader;
import java.io.FileWriter;


public class FormatJSON extends Observable implements Strategy {

	public FormatJSON() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save(String fileName, List<MyShape> shapes) {
		
		JSONObject obj = new JSONObject();
		
	    for(int i=0; i<shapes.size(); i++) {
			JSONObject temp = new JSONObject();
	    	temp.put("class", shapes.get(i).getClass().getName().toString());
			temp.put("x1", shapes.get(i).getX1());
	    	temp.put("y1", shapes.get(i).getY1());
	    	temp.put("x2", shapes.get(i).getX2());
	    	temp.put("y2", shapes.get(i).getY2());
	    	temp.put("r", shapes.get(i).getColor().getRed());
	    	temp.put("g", shapes.get(i).getColor().getGreen());
	    	temp.put("b", shapes.get(i).getColor().getBlue());
	    	if(shapes.get(i) instanceof MyBoundedShape) {
	    		MyBoundedShape b = (MyBoundedShape)shapes.get(i);
	    		temp.put("fill", b.getFill());
	    	}
	    	obj.put(i, temp);
	    }
		// try-with-resources statement based on post comment below :)
		try (FileWriter file = new FileWriter(fileName+".json")) {
			file.write(obj.toJSONString());
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MyShape> load(String fileName) {
		List<MyShape> shapes = new ArrayList<MyShape>();
		JSONParser parser = new JSONParser();
        try {
        	fileName = (fileName.replaceAll("\\\\","\\\\\\\\")) + ".json";
        	JSONObject objects = (JSONObject) parser.parse(new FileReader(fileName));
        	for(int i=0; i< objects.size(); i++) {
        		JSONObject shapeJSON = new JSONObject();
        		shapeJSON = (JSONObject) objects.get(String.valueOf(i));
                String className = (String) shapeJSON.get("class");
                
        		MyShape shape = (MyShape) Class.forName(className).newInstance(); 

                Long r = new Long((long)shapeJSON.get("r"));
                Long g = new Long((long)shapeJSON.get("g"));
                Long b = new Long((long)shapeJSON.get("b"));

                Color color = new Color(r.intValue(), g.intValue(), b.intValue());                
                shape.setColor(color);
                
                Long x1 = new Long((long)shapeJSON.get("x1"));
                shape.setX1(x1.intValue());
                
                Long y1 = new Long((long)shapeJSON.get("y1"));
                shape.setY1(y1.intValue());
                
                Long x2 = new Long((long)shapeJSON.get("x2"));
                shape.setX2(x2.intValue());
                
                Long y2 = new Long((long)shapeJSON.get("y2"));
                shape.setY2(y2.intValue());
                
                if(shape instanceof MyBoundedShape) {
                	MyBoundedShape bb = (MyBoundedShape) shape;
                	bb.setFill((boolean)shapeJSON.get("fill"));
                }
                
                
                shapes.add(shape);
        	}


/*
            // loop array
            JSONArray msg = (JSONArray) obj.get("messages");
            Iterator<String> iterator = msg.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
*/
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        this.setChanged();
        this.notifyObservers(shapes);
		return shapes;

    }		

}
