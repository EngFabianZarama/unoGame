package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Main extends Application {
	private ArrayList<Card> unoDeck = new ArrayList<Card>();
	private ArrayList<Card> pcHand = new ArrayList<Card>();
	private ArrayList<Card> userHand = new ArrayList<Card>();
	private ArrayList<Card> faceUpDeck = new ArrayList<Card>();
	private int whoStarts = 0;
	private int index = 0;
	private boolean calledUno = false;
	private HBox hBoxUser = new HBox(37);
	private HBox hBoxPc = new HBox(37);
	private GridPane gridPane = new GridPane();
	// private BorderPane pane = new BorderPane();

	@Override
	public void start(Stage primaryStage) {

		BorderPane pane = new BorderPane();
		pane.getStyleClass().add("image-view-wrapper");
		pane.setStyle("-fx-border-color: red");
		
		VBox vBox = new VBox(10);

		ScrollPane spUser = new ScrollPane();
		ScrollPane spPc = new ScrollPane();

		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(5.5);
		gridPane.setVgap(5.5);
		pane.setPrefSize(750, 500);

		// Creation of buttons
		Button btnContinue = new Button("Continue");
		Button btnDraw = new Button("Draw");
		Button btnUno = new Button("UNO!");

		pane.setCenter(vBox);

		vBox.getChildren().clear();

		Text text = new Text("UNO GAME!");
		text.setFont(Font.font("comic sans ms", FontWeight.NORMAL, FontPosture.REGULAR, 62));
		text.setFill(Color.RED);
		gridPane.add(text, 0, 0);

		Text takeACard = new Text("Take one card");
		takeACard.setFont(Font.font("comic sans ms", FontWeight.NORMAL, FontPosture.REGULAR, 32));
		takeACard.setFill(Color.BLUE);
		gridPane.add(takeACard, 0, 1);

		ImageView chooseCard = new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/back.jpg"), 150, 150, true, true));
		gridPane.add(chooseCard, 0, 2);
		GridPane.setHalignment(takeACard, HPos.CENTER);
		GridPane.setHalignment(chooseCard, HPos.CENTER);
		pane.setCenter(gridPane);

		chooseCard.setOnMouseClicked(e -> {
			shuffleCardsToStart();
			gridPane.getChildren().clear();

			Text txtYourCard = new Text("Your card is");
			txtYourCard.setFont(Font.font("comic sans ms", FontWeight.NORMAL, FontPosture.REGULAR, 32));
			txtYourCard.setFill(Color.BLUE);
			gridPane.add(txtYourCard, 0, 0);

			gridPane.add(unoDeck.get(0).getCardImage(), 0, 1);

			Text txtPcCard = new Text("PC card is");
			txtPcCard.setFont(Font.font("comic sans ms", FontWeight.NORMAL, FontPosture.REGULAR, 32));
			txtPcCard.setFill(Color.BLUE);
			gridPane.add(txtPcCard, 8, 0);

			gridPane.add(unoDeck.get(1).getCardImage(), 8, 1);

			whoStarts = whoStarts();
			if (whoStarts == 1) {
				Text txtWinStart = new Text("You start first ");
				txtWinStart.setFont(Font.font("comic sans ms", FontWeight.NORMAL, FontPosture.REGULAR, 32));
				txtWinStart.setFill(Color.BLUE);

				gridPane.add(txtWinStart, 4, 4);
			} else if (whoStarts == -1) {
				Text txtWinStart = new Text("PC start first ");
				txtWinStart.setFont(Font.font("comic sans ms", FontWeight.NORMAL, FontPosture.REGULAR, 32));
				txtWinStart.setFill(Color.BLUE);
				gridPane.add(txtWinStart, 4, 4);

			}

			gridPane.add(btnContinue, 4, 8);
			GridPane.setHalignment(btnContinue, HPos.CENTER);

		});// End of choose card

		btnContinue.setOnAction(e3 -> {
			unoDeck.clear();

			gridPane.getChildren().clear();

			shuffleCards();
			draw5Cards();

			// Show user Images
			for (int i = 0; i < userHand.size(); i++) {
				hBoxUser.getChildren().add(userHand.get(i).getCardImage());
			}

			// Show PC Images
			for (int i = 0; i < pcHand.size(); i++) {
				hBoxPc.getChildren().add(new ImageView(
						new Image(getClass().getResourceAsStream("/UNO_cards/back.jpg"), 150, 150, true, true)));
			}

			// Show face down deck
			gridPane.add(
					new ImageView(
							new Image(getClass().getResourceAsStream("/UNO_cards/back.jpg"), 150, 150, true, true)),
					0, 0);

			// draw 1sr card and show until is not special
			boolean exit = false;
			while (exit == false) {
				faceUpDeck.add(unoDeck.remove(0));
				gridPane.add(faceUpDeck.get(faceUpDeck.size() - 1).getCardImage(), 3, 0);

				if (faceUpDeck.get(faceUpDeck.size() - 1).getNumber() == 8
						|| faceUpDeck.get(faceUpDeck.size() - 1).getNumber() == 9
						|| faceUpDeck.get(faceUpDeck.size() - 1).getNumber() == 10
						|| faceUpDeck.get(faceUpDeck.size() - 1).getColor().equals("wild")) {

					exit = false;
				} else {
					exit = true;
				}
			}

			if (whoStarts == 1) {
				userTurn(faceUpDeck.get(faceUpDeck.size() - 1));
			} else {
				pcTurn(faceUpDeck.get(faceUpDeck.size() - 1));
			}

			btnDraw.setOnAction(e4 -> {
				try {
					if (unoDeck.isEmpty()) {
						System.out.println("btndraw unoDeck is empty, unoDeck=faceUpDeck");
						shuffleCards();
						// unoDeck = new ArrayList<Card>(faceUpDeck);
						// faceUpDeck.clear();
						// faceUpDeck.add(unoDeck.get(unoDeck.size() - 1));
					}
					calledUno = false;
					userHand.add(unoDeck.remove(0));
					hBoxUser.getChildren().add(userHand.get(userHand.size() - 1).getCardImage());

					pcTurn(faceUpDeck.get(faceUpDeck.size() - 1));
				} catch (Exception e) {
					System.out.println("Error in draw button");
					pcTurn(faceUpDeck.get(faceUpDeck.size() - 1));
				}

			});// End btnDrow

			btnUno.setOnAction(e4 -> {
				if (unoDeck.isEmpty()) {
					System.out.println("btnUno unoDeck is empty, unoDeck=faceUpDeck");
					shuffleCards();
					// unoDeck = new ArrayList<Card>(faceUpDeck);
					// faceUpDeck.clear();
					// faceUpDeck.add(unoDeck.get(unoDeck.size() - 1));
				}
				try {
					if (userHand.size() == 2) {
						calledUno = true;
						userTurn(faceUpDeck.get(faceUpDeck.size() - 1));
					} else {
						notUno();
					}
				} catch (Exception e) {
					System.out.println("Error in Uno button");
					pcTurn(faceUpDeck.get(faceUpDeck.size() - 1));
				}

			});// End btnUno

			gridPane.add(btnDraw, 6, 0);
			gridPane.add(btnUno, 8, 0);

			spUser.setContent(hBoxUser);
			spUser.setPrefSize(100, 170);
			spUser.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

			spPc.setContent(hBoxPc);
			spPc.setPrefSize(100, 170);
			spPc.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

			pane.setTop(spPc);
			pane.setBottom(spUser);
			pane.setCenter(gridPane);

			pane.setBottom(spUser);
		});// End of btnContinue

		for (int i = 0; i < unoDeck.size(); i++) {
			hBoxUser.getChildren().add(unoDeck.get(i).getCardImage());
		}

		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void newColorWild() {
		String[] color = { "red", "yellow", "blue", "green" };
		Random random = new Random();
		int index = random.nextInt(color.length);
		faceUpDeck.get(faceUpDeck.size() - 1).setWildColor(color[index]);
		faceUpDeck.get(faceUpDeck.size() - 1).setNumber(10);

		// System.out.println("new color"+color[index]);

		Stage stage = new Stage();
		VBox box = new VBox(5);
		box.setAlignment(Pos.CENTER);
		Label label = new Label("New color: " + color[index]);
		label.setFont(Font.font("comic sans ms", FontWeight.NORMAL, FontPosture.REGULAR, 32));
		label.setTextFill(Color.BLUE);
		Button btnOk = new Button();
		btnOk.setText("Ok");

		btnOk.setOnAction(e -> {
			stage.close();
		});

		box.getChildren().add(label);
		box.getChildren().add(btnOk);
		Scene scene = new Scene(box, 300, 150);
		stage.setScene(scene);
		stage.show();

	}

	public void pcWon() {
		Stage stage = new Stage();
		VBox box = new VBox(5);
		box.setAlignment(Pos.CENTER);
		Label label = new Label("PC Won");
		label.setFont(Font.font("comic sans ms", FontWeight.NORMAL, FontPosture.REGULAR, 32));
		label.setTextFill(Color.GREEN);
		Button btnOk = new Button();
		btnOk.setText("Close Game");

		btnOk.setOnAction(e -> {
			System.exit(0);
		});

		box.getChildren().add(label);
		box.getChildren().add(btnOk);
		Scene scene = new Scene(box, 300, 150);
		stage.setScene(scene);
		stage.show();

	}

	public void userWon() {
		Stage stage = new Stage();
		VBox box = new VBox(5);
		box.setAlignment(Pos.CENTER);
		Label label = new Label("You Won");
		label.setFont(Font.font("comic sans ms", FontWeight.NORMAL, FontPosture.REGULAR, 32));
		label.setTextFill(Color.GREEN);
		Button btnOk = new Button();
		btnOk.setText("Close Game");

		btnOk.setOnAction(e -> {
			System.exit(0);
		});

		box.getChildren().add(label);
		box.getChildren().add(btnOk);
		Scene scene = new Scene(box, 300, 150);
		stage.setScene(scene);
		stage.show();

	}

	public void wildCard() {
		Stage stage = new Stage();
		VBox box = new VBox(5);
		box.setAlignment(Pos.CENTER);
		Label label = new Label("Next color:");
		label.setFont(Font.font("comic sans ms", FontWeight.NORMAL, FontPosture.REGULAR, 32));
		label.setTextFill(Color.BLUE);
		Button btnGreen = new Button();
		Button btnBlue = new Button();
		Button btnRed = new Button();
		Button btnYellow = new Button();

		btnGreen.setText("Green");
		btnBlue.setText("Blue");
		btnRed.setText("Red");
		btnYellow.setText("Yellow");

		btnGreen.setOnAction(e -> {
			faceUpDeck.get(faceUpDeck.size() - 1).setWildColor("green");
			stage.close();
			pcTurn(faceUpDeck.get(faceUpDeck.size() - 1));
		});

		btnBlue.setOnAction(e -> {
			faceUpDeck.get(faceUpDeck.size() - 1).setWildColor("blue");
			stage.close();
			pcTurn(faceUpDeck.get(faceUpDeck.size() - 1));
		});

		btnRed.setOnAction(e -> {
			faceUpDeck.get(faceUpDeck.size() - 1).setWildColor("red");
			stage.close();
			pcTurn(faceUpDeck.get(faceUpDeck.size() - 1));
		});

		btnYellow.setOnAction(e -> {
			faceUpDeck.get(faceUpDeck.size() - 1).setWildColor("yellow");
			stage.close();
			pcTurn(faceUpDeck.get(faceUpDeck.size() - 1));
		});

		box.getChildren().add(label);
		box.getChildren().add(btnGreen);
		box.getChildren().add(btnBlue);
		box.getChildren().add(btnRed);
		box.getChildren().add(btnYellow);
		Scene scene = new Scene(box, 300, 200);
		stage.setScene(scene);
		stage.show();
	}

	public void pcCallsUno() {
		Stage stage = new Stage();
		VBox box = new VBox(5);
		box.setAlignment(Pos.CENTER);
		Label label = new Label("PC says: UNO");
		label.setFont(Font.font("comic sans ms", FontWeight.NORMAL, FontPosture.REGULAR, 32));
		label.setTextFill(Color.RED);
		Button btnOk = new Button();
		btnOk.setText("Ok");

		btnOk.setOnAction(e -> {
			stage.close();
		});

		box.getChildren().add(label);
		box.getChildren().add(btnOk);
		Scene scene = new Scene(box, 300, 150);
		stage.setScene(scene);
		stage.show();

	}

	public void notUno() {
		if (unoDeck.size() < 2) {
			unoDeck = faceUpDeck;
		}

		if (calledUno == false) {
			Stage stage = new Stage();
			VBox box = new VBox(5);
			box.setAlignment(Pos.CENTER);
			Label label1 = new Label("Not UNO");
			label1.setFont(Font.font("comic sans ms", FontWeight.NORMAL, FontPosture.REGULAR, 32));
			label1.setTextFill(Color.GREEN);

			Label label2 = new Label("U take 2 cards");
			label2.setFont(Font.font("comic sans ms", FontWeight.NORMAL, FontPosture.REGULAR, 32));
			label2.setTextFill(Color.GREEN);
			Button btnOk = new Button();
			btnOk.setText("Okay");

			userHand.add(unoDeck.remove(0));
			hBoxUser.getChildren().add(userHand.get(userHand.size() - 1).getCardImage());
			userHand.add(unoDeck.remove(0));
			hBoxUser.getChildren().add(userHand.get(userHand.size() - 1).getCardImage());
			// pcTurn(faceUpDeck.get(faceUpDeck.size() - 1));

			btnOk.setOnAction(e -> {
				calledUno = false;
				stage.close();

			});

			box.getChildren().add(label1);
			box.getChildren().add(label2);
			box.getChildren().add(btnOk);
			Scene scene = new Scene(box, 300, 150);
			stage.setScene(scene);
			stage.show();
		}
	}

	public void notCalledUno() {

		if (unoDeck.isEmpty()) {
			System.out.println("notCalledUno unodeck is empty()");
			shuffleCards();
			// unoDeck = new ArrayList<Card>(faceUpDeck);
			// faceUpDeck.clear();
			// faceUpDeck.add(unoDeck.get(unoDeck.size() - 1));
		}
		if (userHand.size() == 1 && calledUno == false) {
			Stage stage = new Stage();
			VBox box = new VBox(5);
			box.setAlignment(Pos.CENTER);
			Label label1 = new Label("Not called UNO");
			label1.setFont(Font.font("comic sans ms", FontWeight.NORMAL, FontPosture.REGULAR, 32));
			label1.setTextFill(Color.GREEN);

			Label label2 = new Label("U take 2 cards");
			label2.setFont(Font.font("comic sans ms", FontWeight.NORMAL, FontPosture.REGULAR, 32));
			label2.setTextFill(Color.GREEN);
			Button btnOk = new Button();
			btnOk.setText("Okay");

			userHand.add(unoDeck.remove(0));
			hBoxUser.getChildren().add(userHand.get(userHand.size() - 1).getCardImage());
			userHand.add(unoDeck.remove(0));
			hBoxUser.getChildren().add(userHand.get(userHand.size() - 1).getCardImage());
			

			btnOk.setOnAction(e -> {
				calledUno = false;
				stage.close();
				//pcTurn(faceUpDeck.get(faceUpDeck.size() - 1));
			});

			box.getChildren().add(label1);
			box.getChildren().add(label2);
			box.getChildren().add(btnOk);
			Scene scene = new Scene(box, 300, 150);
			stage.setScene(scene);
			stage.show();

		}

	}

	public void userTurn(Card faceUpCard) {
		if (unoDeck.isEmpty()) {
			System.out.println("userTurn unoDeck is empty, unoDeck=faceUpDeck");
			//shuffleCards();
			 //unoDeck = new ArrayList<Card>(faceUpDeck);
			 unoDeck.addAll(faceUpDeck);
			 //faceUpDeck.clear();
			 //faceUpDeck.add(unoDeck.get(unoDeck.size() - 1));
		}

		notCalledUno();

		for (index = 0; index < userHand.size(); index++) {
			int t = index;
			userHand.get(t).getCardImage().setOnMouseClicked(ew -> {
//System.out.println(t + ") " + userHand.get(t).getColor() + ", " + userHand.get(t).getNumber());
//System.out.println((faceUpCard.getColor()) + ", "+ faceUpCard.getNumber());
//System.out.println(pcHand.size() + "\n");
//System.out.println(unoDeck.size());

				if ((userHand.get(t).getColor().equals(faceUpCard.getColor())
						|| userHand.get(t).getNumber() == faceUpCard.getNumber())
						&& ((userHand.get(t).getNumber() != 8) && (userHand.get(t).getNumber() != 9)
								&& (userHand.get(t).getNumber() != 10))) {
					faceUpDeck.add(userHand.remove(t));
					hBoxUser.getChildren().remove(t);
					// add card to faceUpDeck
					gridPane.add(faceUpDeck.get(faceUpDeck.size() - 1).getCardImage(), 3, 0);

					if (userHand.size() == 0) {
						userWon();
					}
					pcTurn(faceUpDeck.get(faceUpDeck.size() - 1));

					// plus 1 card to pc
				} else if ((faceUpDeck.get(faceUpDeck.size() - 1).getColor().equals(userHand.get(t).getColor())
						&& userHand.get(t).getNumber() == 8)) {
					// || (faceUpDeck.get(faceUpDeck.size() -
					// 1).getColor().equals(userHand.get(t).getColor())
					// && userHand.get(t).getNumber() == 8)) {
					pcHand.add(unoDeck.remove(0));

					
					// Show PC Images
					
					hBoxPc.getChildren().clear();
					for (int i = 0; i < pcHand.size(); i++) {
						hBoxPc.getChildren()
								.add(new ImageView(new Image(getClass().getResourceAsStream("/UNO_cards/back.jpg"), 150,
										150, true, true)));
					}
					 
					faceUpDeck.add(userHand.remove(t));
					gridPane.add(faceUpDeck.get(faceUpDeck.size() - 1).getCardImage(), 3, 0);

					if (userHand.size() == 0) {
						userWon();
					}

					pcTurn(faceUpDeck.get(faceUpDeck.size() - 1));
					// plus 2 cards to pc
				} else if ((faceUpDeck.get(faceUpDeck.size() - 1).getColor().equals(userHand.get(t).getColor())
						&& userHand.get(t).getNumber() == 9)) {
					// || (faceUpDeck.get(faceUpDeck.size() -
					// 1).getColor().equals(userHand.get(t).getColor())
					// && userHand.get(t).getNumber() == 9)) {

					pcHand.add(unoDeck.remove(0));
					pcHand.add(unoDeck.remove(0));

					// Show PC Images
					hBoxPc.getChildren().clear();
					for (int i = 0; i < pcHand.size(); i++) {
						hBoxPc.getChildren()
								.add(new ImageView(new Image(getClass().getResourceAsStream("/UNO_cards/back.jpg"), 150,
										150, true, true)));
					}

					faceUpDeck.add(userHand.remove(t));
					gridPane.add(faceUpDeck.get(faceUpDeck.size() - 1).getCardImage(), 3, 0);

					if (userHand.size() == 0) {
						userWon();
					}

					pcTurn(faceUpDeck.get(faceUpDeck.size() - 1));

				} else if (userHand.get(t).getColor().equals("wild") || userHand.get(t).getNumber() == 10) {
					wildCard();
					faceUpDeck.add(userHand.remove(t));
					hBoxUser.getChildren().remove(t);
					gridPane.add(faceUpDeck.get(faceUpDeck.size() - 1).getCardImage(), 3, 0);

					if (userHand.size() == 0) {
						userWon();
					}

				}

			});

		}

	}

	public void pcTurn(Card faceUpCard) {
		// calledUno=false;
		if (unoDeck.isEmpty()) {
			System.out.println("pcTurn unoDeck is empty, unoDeck=faceUpDeck");
			 unoDeck.addAll(faceUpDeck);
			//shuffleCards();
			//unoDeck = new ArrayList<Card>(faceUpDeck);
			// faceUpDeck.clear();
			// faceUpDeck.add(unoDeck.get(unoDeck.size() - 1));
		}

		boolean gotCard = false;
		// try {
		for (int i = 0; i < pcHand.size(); i++) {

			if (pcHand.get(i).getColor().equals("wild") || pcHand.get(i).getNumber() == 10) {// wild
																								// card
				try {
					faceUpDeck.add(pcHand.remove(i));
					newColorWild();
					gridPane.add(faceUpDeck.get(faceUpDeck.size() - 1).getCardImage(), 3, 0);
					gotCard = true;
					break;
				} catch (Exception w) {
					System.out.print("pcTurn wild error");
				}
				// plus 1 card to user
			} else if ((faceUpCard.getColor().equals(pcHand.get(i).getColor()) && pcHand.get(i).getNumber() == 8)) {
				// || (faceUpCard.getColor().equals(pcHand.get(i).getColor()) &&
				// pcHand.get(i).getNumber() == 8)) {
				try {
					faceUpDeck.add(pcHand.remove(i));
					gridPane.add(faceUpDeck.get(faceUpDeck.size() - 1).getCardImage(), 3, 0);

					userHand.add(unoDeck.remove(0));
					hBoxUser.getChildren().add(userHand.get(userHand.size() - 1).getCardImage());

					// userTurn(faceUpDeck.get(faceUpDeck.size() - 1));
					gotCard = true;
					break;
				} catch (Exception w) {
					shuffleCards();
				}
				// plus 2 cards to user
			} else if ((faceUpCard.getColor().equals(pcHand.get(i).getColor()) && pcHand.get(i).getNumber() == 9)) {
				// || (faceUpCard.getColor().equals(pcHand.get(i).getColor()) &&
				// pcHand.get(i).getNumber() == 9)) {
				try {
					faceUpDeck.add(pcHand.remove(i));
					gridPane.add(faceUpDeck.get(faceUpDeck.size() - 1).getCardImage(), 3, 0);

					userHand.add(unoDeck.remove(0));
					hBoxUser.getChildren().add(userHand.get(userHand.size() - 1).getCardImage());

					userHand.add(unoDeck.remove(0));
					hBoxUser.getChildren().add(userHand.get(userHand.size() - 1).getCardImage());

					gotCard = true;
					break;
				} catch (Exception w) {
					shuffleCards();
				}
			} else if (faceUpCard.getColor().equals(pcHand.get(i).getColor())) {
				try {
					faceUpDeck.add(pcHand.remove(i));
					gridPane.add(faceUpDeck.get(faceUpDeck.size() - 1).getCardImage(), 3, 0);
					gotCard = true;
					break;
				} catch (Exception w) {
					shuffleCards();
				}
			} else if (faceUpCard.getNumber() == pcHand.get(i).getNumber()) {
				// return pcHand.remove(i);
				try {
					faceUpDeck.add(pcHand.remove(i));
					gridPane.add(faceUpDeck.get(faceUpDeck.size() - 1).getCardImage(), 3, 0);
					gotCard = true;
					break;
				} catch (Exception w) {
					shuffleCards();
				}

			}

		}

		if (gotCard == false) {
			try {
				pcHand.add(unoDeck.remove(0));
				//hBoxPc.getChildren().add(new ImageView(
				//		new Image(getClass().getResourceAsStream("/UNO_cards/back.jpg"), 150, 150, true, true)));
			} catch (Exception w) {
				shuffleCards();
			}
		}

		if (pcHand.size() == 1) {// PC calls UNO
			pcCallsUno();
		} else if (pcHand.size() == 0) {
			hBoxPc.getChildren().remove(0);
			pcWon();
		}

		hBoxPc.getChildren().clear();
		for (int i = 0; i < pcHand.size(); i++) {
			hBoxPc.getChildren().add(new ImageView(
					new Image(getClass().getResourceAsStream("/UNO_cards/back.jpg"), 150, 150, true, true)));
		}
		userTurn(faceUpDeck.get(faceUpDeck.size() - 1));

	}

	public void draw5Cards() {

		pcHand.add(unoDeck.remove(0));
		pcHand.add(unoDeck.remove(0));
		pcHand.add(unoDeck.remove(0));
		pcHand.add(unoDeck.remove(0));
		pcHand.add(unoDeck.remove(0));

		userHand.add(unoDeck.remove(0));
		userHand.add(unoDeck.remove(0));
		userHand.add(unoDeck.remove(0));
		userHand.add(unoDeck.remove(0));
		userHand.add(unoDeck.remove(0));

	}

	public int whoStarts() {
		// Calclation to see how starts
		int userNumber = unoDeck.get(0).getNumber();
		int pcNumber = unoDeck.get(1).getNumber();

		String userColor = unoDeck.get(0).getColor();
		String pcColor = unoDeck.get(1).getColor();

		if (userNumber == pcNumber) {

			// red=1 yellow=2 blue=3 green=4
			if (userColor == "red") {
				userNumber += 1;
			} else if (userColor == "yellow") {
				userNumber += 2;
			} else if (userColor == "blue") {
				userNumber += 3;
			} else if (userColor == "green") {
				userNumber += 4;
			}

			if (pcColor == "red") {
				pcNumber += 1;
			} else if (pcColor == "yellow") {
				pcNumber += 2;
			} else if (pcColor == "blue") {
				pcNumber += 3;
			} else if (pcColor == "green") {
				pcNumber += 4;
			}

			if (userNumber > pcNumber) {
				return 1;
			} else {
				return -1;
			}
		} else {
			if (userNumber > pcNumber) {
				return 1;
			} else {
				return -1;
			}
		}

	}

	public void shuffleCardsToStart() {
		unoDeck.add(new Card("blue", 1, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/1_blue.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("blue", 2, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/2_blue.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("blue", 3, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/3_blue.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("blue", 4, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/4_blue.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("blue", 5, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/5_blue.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("blue", 6, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/6_blue.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("blue", 7, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/7_blue.jpg"), 150, 150, true, true))));

		unoDeck.add(new Card("green", 1, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/1_green.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("green", 2, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/2_green.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("green", 3, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/3_green.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("green", 4, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/4_green.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("green", 5, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/5_green.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("green", 6, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/6_green.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("green", 7, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/7_green.jpg"), 150, 150, true, true))));

		unoDeck.add(new Card("yellow", 1, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/1_yellow.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("yellow", 2, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/2_yellow.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("yellow", 3, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/3_yellow.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("yellow", 4, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/4_yellow.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("yellow", 5, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/5_yellow.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("yellow", 6, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/6_yellow.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("yellow", 7, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/7_yellow.jpg"), 150, 150, true, true))));

		unoDeck.add(new Card("red", 1, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/1_red.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("red", 2, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/2_red.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("red", 3, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/3_red.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("red", 4, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/4_red.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("red", 5, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/5_red.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("red", 6, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/6_red.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("red", 7, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/7_red.jpg"), 150, 150, true, true))));

		long seed = System.nanoTime();
		Collections.shuffle(unoDeck, new Random(seed));

	}

	public void shuffleCards() {
		unoDeck.add(new Card("blue", 1, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/1_blue.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("blue", 2, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/2_blue.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("blue", 3, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/3_blue.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("blue", 4, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/4_blue.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("blue", 5, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/5_blue.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("blue", 6, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/6_blue.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("blue", 7, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/7_blue.jpg"), 150, 150, true, true))));

		unoDeck.add(new Card("green", 1, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/1_green.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("green", 2, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/2_green.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("green", 3, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/3_green.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("green", 4, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/4_green.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("green", 5, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/5_green.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("green", 6, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/6_green.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("green", 7, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/7_green.jpg"), 150, 150, true, true))));

		unoDeck.add(new Card("yellow", 1, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/1_yellow.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("yellow", 2, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/2_yellow.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("yellow", 3, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/3_yellow.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("yellow", 4, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/4_yellow.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("yellow", 5, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/5_yellow.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("yellow", 6, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/6_yellow.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("yellow", 7, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/7_yellow.jpg"), 150, 150, true, true))));

		unoDeck.add(new Card("red", 1, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/1_red.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("red", 2, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/2_red.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("red", 3, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/3_red.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("red", 4, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/4_red.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("red", 5, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/5_red.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("red", 6, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/6_red.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("red", 7, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/7_red.jpg"), 150, 150, true, true))));

		unoDeck.add(new Card("blue", 8, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/1_plus_blue.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("red", 8, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/1_plus_red.jpg"), 150, 150, true, true))));

		unoDeck.add(new Card("green", 9, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/2_plus_green.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("yellow", 9, new ImageView(
				new Image(getClass().getResourceAsStream("/UNO_cards/2_plus_yellow.jpg"), 150, 150, true, true))));

		unoDeck.add(new Card("wild", 10,
				new ImageView(new Image(getClass().getResourceAsStream("/UNO_cards/wild.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("wild", 10,
				new ImageView(new Image(getClass().getResourceAsStream("/UNO_cards/wild.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("wild", 10,
				new ImageView(new Image(getClass().getResourceAsStream("/UNO_cards/wild.jpg"), 150, 150, true, true))));
		unoDeck.add(new Card("wild", 10,
				new ImageView(new Image(getClass().getResourceAsStream("/UNO_cards/wild.jpg"), 150, 150, true, true))));

		long seed = System.nanoTime();
		Collections.shuffle(unoDeck, new Random(seed));

	}
}

