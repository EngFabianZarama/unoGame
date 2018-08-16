package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends ArrayList<Object> {
	private String color;
	private int number;
	private ImageView cardImage;
	
	public Card(){}
	
	public Card(String color, int number,ImageView cardImage){
		this.color = color;
		this.number = number;
		this.cardImage = cardImage;
	}
	
	public String getColor(){
		return color;
	}
	
	public int getNumber(){
		return number;
	}
	
	public ImageView getCardImage(){
		return cardImage;
	}
	
	public String setWildColor(String a){
		return color=a;
	}
	
	public int setNumber(int a){
		return number=a;
	}
	/*
	public void createCards() throws IOException{
		ArrayList<Card> unoDeck = new ArrayList<Card>();
		unoDeck.add(new Card("","", new ImageView(new Image(getClass().getResourceAsStream("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/1_blue.jpg"), 150, 150,true, true))));
		
		
		unoDeck.add(new Card("blue","1",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/1_blue.jpg"))));
		unoDeck.add(new Card("blue","2",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/2_blue.jpg"))));
		unoDeck.add(new Card("blue","3",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/3_blue.jpg"))));
		unoDeck.add(new Card("blue","4",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/4_blue.jpg"))));
		unoDeck.add(new Card("blue","5",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/5_blue.jpg"))));
		unoDeck.add(new Card("blue","6",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/6_blue.jpg"))));
		unoDeck.add(new Card("blue","7",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/7_blue.jpg"))));

		unoDeck.add(new Card("green","1",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/1_green.jpg"))));
		unoDeck.add(new Card("green","2",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/2_green.jpg"))));
		unoDeck.add(new Card("green","3",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/3_green.jpg"))));
		unoDeck.add(new Card("green","4",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/4_green.jpg"))));
		unoDeck.add(new Card("green","5",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/5_green.jpg"))));
		unoDeck.add(new Card("green","6",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/6_green.jpg"))));
		unoDeck.add(new Card("green","7",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/7_green.jpg"))));

		unoDeck.add(new Card("yellow","1",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/1_yellow.jpg"))));
		unoDeck.add(new Card("yellow","2",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/2_yellow.jpg"))));
		unoDeck.add(new Card("yellow","3",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/3_yellow.jpg"))));
		unoDeck.add(new Card("yellow","4",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/4_yellow.jpg"))));
		unoDeck.add(new Card("yellow","5",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/5_yellow.jpg"))));
		unoDeck.add(new Card("yellow","6",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/6_yellow.jpg"))));
		unoDeck.add(new Card("yellow","7",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/7_yellow.jpg"))));

		unoDeck.add(new Card("red","1",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/1_red.jpg"))));
		unoDeck.add(new Card("red","2",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/2_red.jpg"))));
		unoDeck.add(new Card("red","3",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/3_red.jpg"))));
		unoDeck.add(new Card("red","4",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/4_red.jpg"))));
		unoDeck.add(new Card("red","5",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/5_red.jpg"))));
		unoDeck.add(new Card("red","6",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/6_red.jpg"))));
		unoDeck.add(new Card("red","7",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/7_red.jpg"))));

		unoDeck.add(new Card("blue","1",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/1_plus_blue.jpg"))));
		unoDeck.add(new Card("red","2",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/1_plus_red.jpg"))));
		
		unoDeck.add(new Card("green","1",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/2_plus_green.jpg"))));
		unoDeck.add(new Card("yellow","1",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/2_plus_yellow.jpg"))));
		
		unoDeck.add(new Card("wild","1",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/wild.jpg"))));
		unoDeck.add(new Card("wild","2",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/wild.jpg"))));
		unoDeck.add(new Card("wild","3",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/wild.jpg"))));
		unoDeck.add(new Card("wild","4",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/wild.jpg"))));
		
		unoDeck.add(new Card("back","1",ImageIO.read(new File("/Users/admin/Documents/workspace/UnoGame/src/UNO_cards/back.jpg"))));



	}
	*/
}
