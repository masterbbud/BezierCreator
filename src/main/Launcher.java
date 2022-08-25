package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Launcher extends PApplet {

	public PImage image;
	
	public ArrayList<Bez> beziers = new ArrayList<Bez>();
	public PVector startPos;
	public PVector currentFinal; // drag 0
	public PVector current1;
	public PVector current2;
	public float circleSize = 10;
	public int currentDrag = -1;
	
	public boolean showLine = true;
	
	public static void main(String[] args) {
		System.out.println("-------------\nINPUT NAME\n-------------\n");
		PApplet.main("main.Launcher");
	}

	public void settings() {
		size(1440, 800,P2D);
	}

	public void setup() {
		image = loadImage("4-4.png");
		image.resize(0, 600);
	}

	public void draw() {
		background(220);
		image(image,100,100);
		stroke(255,0,0,140);
		strokeWeight(2);
		
		if (currentFinal != null) {
			noFill();
			beginShape();
			vertex(startPos.x,startPos.y);
			for (Bez b : beziers) {
				bezierVertex(b.x1,b.y1,b.x2,b.y2,b.xf,b.yf);
			}
			bezierVertex(current1.x,current1.y,current2.x,current2.y,currentFinal.x,currentFinal.y);
			endShape();
			stroke(0);
			fill(255,0,0,140);
			ellipse(currentFinal.x,currentFinal.y,circleSize,circleSize);
			line(currentFinal.x,currentFinal.y,current2.x,current2.y);
			PVector lastPos;
			if (beziers.size() > 0) {
				lastPos = new PVector(beziers.get(beziers.size()-1).xf, beziers.get(beziers.size()-1).yf);
			}
			else {
				lastPos = startPos;
			}
			line(lastPos.x,lastPos.y,current1.x,current1.y);
			fill(0,255,0,140);
			ellipse(current1.x,current1.y,circleSize,circleSize);
			ellipse(current2.x,current2.y,circleSize,circleSize);
		}
		
		if (showLine && beziers.size() > 0) {
			stroke(255,100);
			float distance = 100;
			line(beziers.get(beziers.size()-1).xf - distance * (beziers.get(beziers.size()-1).xf - beziers.get(beziers.size()-1).x2), beziers.get(beziers.size()-1).yf - distance * (beziers.get(beziers.size()-1).yf - beziers.get(beziers.size()-1).y2), beziers.get(beziers.size()-1).xf + distance * (beziers.get(beziers.size()-1).xf - beziers.get(beziers.size()-1).x2), beziers.get(beziers.size()-1).yf + distance * (beziers.get(beziers.size()-1).yf - beziers.get(beziers.size()-1).y2));
		}
		
	}

	public void keyPressed() {
		if (keyCode == ENTER) {
			beziers.add(new Bez(current1.x,current1.y,current2.x,current2.y,currentFinal.x,currentFinal.y));
			current1 = currentFinal.copy();
			current2 = currentFinal.copy();
			//startPos = currentFinal.copy();
		}
		if (keyCode == BACKSPACE) {
			
			beziers.remove(beziers.size()-1);
			currentFinal = new PVector(beziers.get(beziers.size()-1).xf,beziers.get(beziers.size()-1).yf);
			current1 = currentFinal.copy();
			current2 = currentFinal.copy();
		}
		if (key == 'p') {
			System.out.println(currentFinal.x+"   "+currentFinal.y);
			float x = 493;
			float y = 415;
			float s = 38;
			System.out.println("p.vertex(x2+(float)("+String.valueOf(map(x,s,startPos.x))+")*s,y+(float)("+String.valueOf(map(y,s,startPos.y))+")*s);");
			for (Bez b : beziers) {
				System.out.println("p.bezierVertex(x2+(float)("+String.valueOf(map(x,s,b.x1))+")*s,y+(float)("+String.valueOf(map(y,s,b.y1))+")*s,x2+(float)("+String.valueOf(map(x,s,b.x2))+")*s,y+(float)("+String.valueOf(map(y,s,b.y2))+")*s,x2+(float)("+String.valueOf(map(x,s,b.xf))+")*s,y+(float)("+String.valueOf(map(y,s,b.yf))+")*s);");
			}
			System.out.println("p.vertex(x2+(float)("+String.valueOf(map(x,s,startPos.x))+")*s,y+(float)("+String.valueOf(map(y,s,startPos.y))+")*s);");
		}
	}
	public float map(float z, float s, float n) {
		//gets the number of Ss away from Z N is.
		return (n - z) / s;
	}
	public void mousePressed() {
		if (currentFinal == null) {
			currentFinal = new PVector(mouseX,mouseY);
			startPos = currentFinal.copy();
			current1 = currentFinal.copy();
			current2 = currentFinal.copy();
		}
		else if (Math.sqrt(Math.pow(mouseX-currentFinal.x, 2)+Math.pow(mouseY-currentFinal.y, 2)) < circleSize) {
			currentDrag = 0;
		}
		
		else if (Math.sqrt(Math.pow(mouseX-current2.x, 2)+Math.pow(mouseY-current2.y, 2)) < circleSize) {
			currentDrag = 2;
		}
		else if (Math.sqrt(Math.pow(mouseX-current1.x, 2)+Math.pow(mouseY-current1.y, 2)) < circleSize) {
			currentDrag = 1;
		}
	}
	public void mouseDragged() {
		System.out.println(currentDrag);
		if (currentDrag == 0) {
			currentFinal.x = mouseX;
			currentFinal.y = mouseY;
		}
		else if (currentDrag == 1) {
			current1.x = mouseX;
			current1.y = mouseY;
		}
		else if (currentDrag == 2) {
			current2.x = mouseX;
			current2.y = mouseY;
		}
	}
}