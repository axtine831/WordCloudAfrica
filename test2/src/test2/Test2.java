package test2;

/*
 Copyright 2010 Daniel Bernier

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import java.util.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import wordcram.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
public class Test2 extends PApplet {

	WordCram wordcram;
	Word[] names;
	Color green =  new Color(0,255,0);
	Color purple = new Color(255,0,255);
	public void setup() {

		// destination.image.getGraphics():
		// P2D -> sun.awt.image.ToolkitImage, JAVA2D -> java.awt.image.BufferedImage.

		// parent.getGraphics():
		// P2D -> sun.java2d.SunGraphics2D, JAVA2D -> same thing.

		// P2D can't draw to destination.image.getGraphics(). Interesting.

		size(1700, 1400); // (int)random(300, 800)); //1200, 675); //1600, 900);
		smooth();
		colorMode(HSB);
		loadNames();
		initWordCram();
		//frameRate(1);
	}

	private PFont randomFont() {
		String[] fonts = PFont.list();
		String noGoodFontNames = "Dingbats|Standard Symbols L";
		String blockFontNames = "OpenSymbol|Mallige Bold|Mallige Normal|Lohit Bengali|Lohit Punjabi|Webdings";
		Set<String> noGoodFonts = new HashSet<String>(Arrays.asList((noGoodFontNames+"|"+blockFontNames).split("|")));
		String fontName;
		do {
			fontName = fonts[(int)random(fonts.length)];
		} while (fontName == null || noGoodFonts.contains(fontName));
		System.out.println(fontName);
		return createFont(fontName, 1);
		//return createFont("Molengo", 1);
	}

	//PGraphics pg;
	private void initWordCram() {
		background(255);

		//pg = createGraphics(800, 600, JAVA2D);
		//pg.beginDraw();
		//try{
		//Area outline;
		//File img= new File("/Users/newuser/Downloads/motorcycle.jpg");
		//BufferedImage in = ImageIO.read(img);
		//BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_RGB);
		//outline=getOutline(Color.BLACK,newImage);
		Shape circle = new Ellipse2D.Float(100.0f, 20.0f, 400.0f, 400.0f);
		//ShapeBasedPlacer shapeBasedPlacer= new ShapeBasedPlacer(circle);
		ShapeBasedPlacer shapeBasedPlacer = ShapeBasedPlacer.fromFile("/Users/newuser/Downloads/2africa4.png",Color.BLACK);
		wordcram = new WordCram(this)
//					.withCustomCanvas(pg)
					//.fromTextFile(textFilePath())
		           .fromWords(names)
	               .withFont(createFont("/Users/newuser/Downloads/MINYN___.TTF", 1))
                //	.fromWords(alphabet())
//					.upperCase()
//					.excludeNumbers()
					//.withFonts(randomFont())
         			.withColorer(Colorers.twoHuesRandomSats(this))
					//.withColorer(Colorers.complement(this, random(255), 200, 220))
	            //    .withColors(color(255,0,0), color(0), color(0,0,255)) 
	                
					.withAngler(Anglers.mostlyHoriz())
					.withPlacer(shapeBasedPlacer)
	              // .withPlacer(Placers.horizLine())
                 //	.withPlacer(Placers.centerClump())
					//.withSizer(Sizers.byWeight(12, 60))
                    .sizedByWeight(12, 60)
					//.withWordPadding(1)
               
//					.minShapeSize(0)
//					.withMaxAttemptsForPlacement(10)
					.maxNumberOfWordsToDraw(1000)
					.withNudger(shapeBasedPlacer)
//					.withNudger(new PlottingWordNudger(this, new SpiralWordNudger()))
//					.withNudger(new RandomWordNudger())

					;
		//}
		/*catch (IOException e) {
            e.printStackTrace();
      }
      System.out.println("Success");*/
	}

	private void finishUp() {
		//pg.endDraw();
		//image(pg, 0, 0);

		//println(wordcram.getSkippedWords());

		println("Done");
		save("wordcram.png");
		noLoop();
	}
	void loadNames() { 
		  String[] nameData = loadStrings("/Users/newuser/Downloads/names3.txt");
		  names = new Word[nameData.length];
		  for (int i = 0; i < names.length; i++) {
			 // System.out.println(nameData[i]);
		    names[i] = parseName(nameData[i]);
		    System.out.println(names[i]);
		  }
		  Arrays.sort(names,new Arrange());
		  for (int i = 0; i < names.length; i++) {
				 // System.out.println(nameData[i]);
			    System.out.println(names[i].word);
			  }
		}
	Word parseName(String data) {
		  String[] parts = split(data, '\t');
		  
		  String name = parts[0];
		  float frequency = Float.valueOf(parts[1]);
		  boolean isUs = "f".equals(parts[2]);
		  boolean isKe="k".equals(parts[2]);
		  Word word = new Word(name, frequency);
		  
		  if (isUs) {
		    word.setColor(color(3,163,255)); // orange
		  }
		  else if(isKe)
		  {
			  word.setColor(color(255,200,200));
		  }
		  else {
		    word.setColor(color(100,197,76)); // spring green
		  }
		  
		  return word;
		}

	public void draw() {
		//fill(55);
		//rect(0, 0, width, height);

		boolean allAtOnce = false;
		if (allAtOnce) {
			wordcram.drawAll();
			finishUp();
		}
		else {
			//int wordsPerFrame = 1;
			int z=0;
			while (wordcram.hasMore() /*&& wordsPerFrame-- > 0*/) {
				
				//System.out.println("iamhere");
				wordcram.drawNext();
			}

			if (!wordcram.hasMore()) {
				finishUp();
			}
		}
	}
	

	public void mouseMoved() {
		/*
		Word word = wordcram.getWordAt(mouseX, mouseY);
		if (word != null) {
			System.out.println(round(mouseX) + "," + round(mouseY) + " -> " + word.word);
		}
		*/
	}

	public void mouseClicked() {
		//initWordCram();
		//loop();
	}

	public void keyPressed() {
		if (keyCode == ' ') {
			saveFrame("wordcram-##.png");
		}
	}

	private String textFilePath() {
		return "/Users/newuser/Downloads/Untitled.txt";
	}

	private Word[] alphabet() {
		Word[] w = new Word[26];
		for (int i = 0; i < w.length; i++) {
			w[i] = new Word(new String(new char[]{(char)(i+65)}), 26-i);
		}
		return w;
	}
	
}