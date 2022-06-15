package com.example.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GameGUI extends Application {
    Stage winnerStage = new Stage();
    Board b = new Board();
    Player[] players = new Player[2];
    Player currentPlayer = new Player();
    Label playerName1 = new Label();
    Label playerName2 = new Label();
    Label playerScore1 = new Label();
    Label playerScore2 = new Label();
    Label hintLabel = new Label();
    Rectangle[][] square = new Rectangle[7][7];
    Line[][] hLine = new Line[8][7];
    Line[][] vLine = new Line[7][8];
    Pane pane = new Pane();
    Image icon = new Image("file:icon.png");
    Image icon2 = new Image("file:icon2.png");

    //The text for the hint box
    private String hint =
            "The players take turns\n" +
            "drawing a line to connect\n" +
            "two dots. If a player\n" +
            "makes a line that\n" +
            "completes a box, the\n" +
            "player scores one point\n" +
            "and draws another line.\n"+
            "The player with the\n" +
            "most points is the winner.\n";

    //A restriction text for the hint box
    private String restriction =
            "You can't change that line!\n" +
            "    Choose another one";

    //The three following reset methods turn the shapes into the default colors
    private void squareColorReset() {
        int x = 50;
        int y = 50;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                square[i][j].setStyle("-fx-fill: #F8F8FF");
                x += 70;
            }
            x = 50;
            y += 70;
        }
    }

    private void horLineColorReset(){
        int x = 50;
        int y = 50;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                hLine[i][j].setStyle("-fx-stroke: #E6E6FA; -fx-stroke-width: 5px");
                x += 70;
            }
            x = 50;
            y += 70;
        }
    }

    private void verLineColorReset(){
        int x = 50;
        int y = 50;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                vLine[j][i].setStyle("-fx-stroke: #E6E6FA; -fx-stroke-width: 5px");
                y += 70;
            }
            x += 70;
            y = 50;
        }
    }

    //This method switches the current player
    private void switchPlayer(){
        if (currentPlayer.equals(players[0])) {
            currentPlayer = players[1];
            playerName2.setStyle("-fx-border-width: 3px; -fx-border-color: black; -fx-border-radius: 6px");
            playerName1.setStyle("-fx-border-width: 3px; -fx-border-color: #EAA157; -fx-border-radius: 6px");
        }
        else {
            currentPlayer = players[0];
            playerName1.setStyle("-fx-border-width: 3px; -fx-border-color: black; -fx-border-radius: 6px");
            playerName2.setStyle("-fx-border-width: 3px; -fx-border-color: #EAA157; -fx-border-radius: 6px");
        }
    }

    private void fillTile(int color){
        //We call checkTileWin method two times because there is
        //a possibility that the player fills two squares with a single line
        //Note that we switch the player if he/she doesn't fill a square
        if (b.checkTileWin() != -1) {
            int position = b.checkTileWin();
            int y = position % 10;
            int x = position / 10;
            b.setTileStatus(x, y);
            b.setTileColor(x, y, color);
            currentPlayer.setScore(1);
            if (color == 1)
                square[x][y].setStyle("-fx-fill: blue;");
            else if (color == 2)
                square[x][y].setStyle("-fx-fill: red;");
            if (b.checkTileWin() != -1) {
                position = b.checkTileWin();
                y = position % 10;
                x = position / 10;
                b.setTileStatus(x, y);
                b.setTileColor(x, y, color);
                currentPlayer.setScore(1);
                if (color == 1)
                    square[x][y].setStyle("-fx-fill: blue;");
                else if (color == 2)
                    square[x][y].setStyle("-fx-fill: red;");
            }
            playerScore1.setText("      " + String.valueOf(players[0].getScore()));
            playerScore2.setText("      " + String.valueOf(players[1].getScore()));

            if (b.filledTilesCounter() == 49)
                showWinner(getWinner());
        }
        else if (b.checkTileWin() == -1){
            switchPlayer();
        }
    }

    private String getWinner(){
        if (players[0].getScore() > players[1].getScore())
            return players[0].getName();
        else if (players[1].getScore() > players[0].getScore())
            return players[1].getName();
        return " ";
    }

    //This method shows the winner (based on scores)
    //At this point the player can either restart the game or close it
    private void showWinner(String n){
        String name = n;
        VBox winnerBox = new VBox(10);
        Label player_wins = new Label(name + " Wins!");
        player_wins.setFont(Font.font("Comic Sans MS", 25));
        Button restartButton = new Button("Restart game");
        restartButton.setMinSize(150, 30);
        //This mouse event turns everything into the default form when the restart button is clicked
        restartButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                squareColorReset();
                horLineColorReset();
                verLineColorReset();
                b.resetTiles();
                b.resetHorLines();
                b.resetVerLines();
                players[0].resetScore();
                players[1].resetScore();
                currentPlayer = players[0];
                playerName1.setStyle("-fx-border-width: 3px; -fx-border-color: black; -fx-border-radius: 6px");
                playerName2.setStyle("-fx-border-width: 3px; -fx-border-color: #EAA157; -fx-border-radius: 6px");
                playerScore1.setText("      " + String.valueOf(players[0].getScore()));
                playerScore2.setText("      " + String.valueOf(players[1].getScore()));
                hintLabel.setText(hint);
                winnerStage.close();
            }
        });
        Button exitButton = new Button("Exit");
        exitButton.setMinSize(100, 30);
        exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Platform.exit();
            }
        });
        winnerBox.getChildren().addAll(player_wins, restartButton, exitButton);
        winnerBox.setAlignment(Pos.CENTER);
        Scene winnerScene = new Scene(winnerBox, 400, 200);
        winnerStage.setScene(winnerScene);
        winnerStage.setResizable(false);
        winnerStage.getIcons().add(icon2);
        winnerStage.show();
    }

    /*---------------------------------------------------------------------------------------------------------------*/
    /*---------------------------------------------------------------------------------------------------------------*/
    /*---------------------------------------------------------------------------------------------------------------*/
    @Override
    public void start(Stage primaryStage) throws IOException {
        AnchorPane welcomePane = new AnchorPane();
        AnchorPane gameSceneAnchor = new AnchorPane();
        VBox hintBox = new VBox();
        VBox vbox = new VBox(20.0);
        VBox menuBox = new VBox(50.0);
        VBox nameScoreVBox = new VBox(10.0);

        //The following loop draws squares and saves the objs in an array
       int x = 50;
       int y = 50;
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 7; j++){
                Rectangle squareObj = new Rectangle(x, y, 70.0, 70.0);
                squareObj.setStyle("-fx-fill: #F8F8FF");
                square[i][j] = squareObj;
                pane.getChildren().add(square[i][j]);
                x += 70;
            }
            x = 50;
            y += 70;
        }

        //The two following loops draw vertical and horizontal lines
        //then save the objs in two different arrays
        x = 50;
        y = 50;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 7; j++){
                Line horLine = new Line(x, y, x + 70, y);
                horLine.setStyle("-fx-stroke: #E6E6FA; -fx-stroke-width: 5px");
                hLine[i][j] = horLine;
                pane.getChildren().add(hLine[i][j]);
                x += 70;
            }
            x = 50;
            y += 70;
        }

        x = 50;
        y = 50;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 7; j++){
                Line verLine = new Line(x, y, x, y + 70);
                verLine.setStyle("-fx-stroke: #E6E6FA; -fx-stroke-width: 5px");
                vLine[j][i] = verLine;
                pane.getChildren().add(vLine[j][i]);
                y += 70;
            }
            x += 70;
            y = 50;
        }

        //The following loop draws the circles
        for (int i = 50; i <= 540; i += 70){
            for (int j = 50; j <= 540; j += 70){
                Circle circle = new Circle(i, j, 6, Color.BLACK);
                pane.getChildren().add(circle);
            }
        }

        VBox buttonVBox = new VBox(10);
        Button restartButton = new Button("Restart Game");
        restartButton.setMinSize(200.0, 50.0);
        restartButton.setFont(Font.font(18));
        Button surrender = new Button("Surrender");
        surrender.setMinSize(200.0, 50.0);
        surrender.setFont(Font.font(18));
        //This mouse event turns everything into the default form when the restart button is clicked
        restartButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                squareColorReset();
                horLineColorReset();
                verLineColorReset();
                b.resetTiles();
                b.resetHorLines();
                b.resetVerLines();
                players[0].resetScore();
                players[1].resetScore();
                currentPlayer = players[0];
                playerName1.setStyle("-fx-border-width: 3px; -fx-border-color: black; -fx-border-radius: 6px");
                playerName2.setStyle("-fx-border-width: 3px; -fx-border-color: #EAA157; -fx-border-radius: 6px");
                playerScore1.setText("      " + String.valueOf(players[0].getScore()));
                playerScore2.setText("      " + String.valueOf(players[1].getScore()));
                hintLabel.setText(hint);
            }
        });
        //This event ends the game at any point (based on the player who has chosen to surrender)
        surrender.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (currentPlayer == players[0])
                    showWinner(players[1].getName());
                else
                    showWinner(players[0].getName());
            }
        });
        buttonVBox.getChildren().addAll(restartButton, surrender);
        hintLabel.setText(hint);
        hintLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
        hintBox.getChildren().add(hintLabel);
        hintBox.setAlignment(Pos.CENTER);
        hintBox.setMinSize(210, 230);
        gameSceneAnchor.getChildren().addAll(buttonVBox, pane, hintBox);
        gameSceneAnchor.setTopAnchor(buttonVBox, 260.0);
        gameSceneAnchor.setRightAnchor(buttonVBox, 25.0);
        gameSceneAnchor.setTopAnchor(pane, 25.0);
        gameSceneAnchor.setLeftAnchor(pane, 20.0);
        gameSceneAnchor.setTopAnchor(hintBox, 385.0);
        gameSceneAnchor.setRightAnchor(hintBox, 20.0);
        hintBox.setStyle("-fx-background-color: #DDB8A6; -fx-border-width: 3px; -fx-border-color: black; -fx-border-radius: 6px;" +
                "-fx-background-radius: 8px;");
        pane.setStyle("-fx-border-color: black; -fx-border-width: 3px; -fx-border-radius: 6px;" +
                "-fx-background-color: #F8F8FF; -fx-padding: 20px 20px 20px 20px; -fx-background-radius: 8px;");


        //This event handler is defined for when the player clicks on a line
        EventHandler<MouseEvent> linesHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //We check if any horizontal line has been clicked
                for (int i = 0; i < 8; i++)
                    for (int j = 0; j < 7; j++)
                        if (event.getSource() == hLine[i][j]) {
                            //If the selected line is not already filled we continue
                            if (b.getHorLineStatus(i, j) != 1) {
                                b.setHorLineStatus(i, j);

                                //Based on current player's color, the line gets filled
                                //Then we check if the player has filled a square or not
                                if (currentPlayer.getColor() == 1) {
                                    hLine[i][j].setStyle("-fx-stroke: #00008B; -fx-stroke-width: 5px");
                                    fillTile(1);
                                }

                                else if (currentPlayer.getColor() == 2) {
                                    hLine[i][j].setStyle("-fx-stroke: #8B0000; -fx-stroke-width: 5px");
                                    fillTile(2);
                                }
                                hintLabel.setText("It's " + currentPlayer.getName() + "'s turn");
                            }
                            //If the selected line is already filled the hint box will guide the player
                            else if (b.getHorLineStatus(i, j) == 1)
                                hintLabel.setText(restriction);
                        }

                //We check if any vertical line has been clicked
                for (int i = 0; i < 7; i++)
                    for(int j = 0; j < 8; j++)
                        if (event.getSource() == vLine[i][j]){
                            //If the selected line is not already filled we continue
                            if (b.getVerLineStatus(i, j) != 1) {
                                b.setVerLineStatus(i, j);

                                //Based on current player's color, the line gets filled
                                //Then we check if the player has filled a square or not
                                if (currentPlayer.getColor() == 1) {
                                    vLine[i][j].setStyle("-fx-stroke: #00008B; -fx-stroke-width: 5px");
                                    fillTile(1);
                                }

                                else if (currentPlayer.getColor() == 2) {
                                    vLine[i][j].setStyle("-fx-stroke: #8B0000; -fx-stroke-width: 5px");
                                    fillTile(2);
                                }
                                hintLabel.setText("It's " + currentPlayer.getName() + "'s turn");
                            }
                            //If the selected line is already filled the hint box will guide the player
                            else if (b.getVerLineStatus(i, j) == 1)
                                hintLabel.setText(restriction);
                        }
            }
        };

        //The two following event handlers are for changing the shape of mouse cursor
        //whenever the player places the cursor on the lines
        EventHandler<MouseEvent> enterHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameSceneAnchor.setCursor(Cursor.HAND);
            }
        };

        EventHandler<MouseEvent> exitHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameSceneAnchor.setCursor(Cursor.DEFAULT);
            }
        };

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 7; j++){
                hLine[i][j].setOnMouseClicked(linesHandler);
                hLine[i][j].setOnMouseEntered(enterHandler);
                hLine[i][j].setOnMouseExited(exitHandler);
            }
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 8; j++){
                vLine[i][j].setOnMouseClicked(linesHandler);
                vLine[i][j].setOnMouseEntered(enterHandler);
                vLine[i][j].setOnMouseExited(exitHandler);
            }

        Button startButton = new Button("Start");
        startButton.setMinSize(200.0, 60.0);
        startButton.setFont(Font.font("Comic Sans MS", 25));
        welcomePane.getChildren().add(startButton);
        welcomePane.setTopAnchor(startButton, 400.0);
        welcomePane.setRightAnchor(startButton, 325.0);

        Image image = new Image("file:Background.png");
        BackgroundImage bimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        Background bground = new Background(bimage);
        welcomePane.setBackground(bground);

        Image gameImage = new Image("file:GameBackground.png");
        BackgroundImage backImage = new BackgroundImage(gameImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        Background backG = new Background(backImage);
        gameSceneAnchor.setBackground(backG);

        Scene welcomeScreen = new Scene(welcomePane, 850, 640);
        Scene gameScreen = new Scene(gameSceneAnchor, 850, 640);
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Dots And Boxes");
        primaryStage.setResizable(false);
        primaryStage.setScene(welcomeScreen);
        primaryStage.show();
        winnerStage.initModality(Modality.WINDOW_MODAL);
        winnerStage.initOwner(primaryStage);

        //After clicking Start, two register widows will come up to get players name
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Player player1 = new Player("1001");
                Player player2 = new Player("1002");
                players[0] = player1;
                players[1] = player2;
                HBox colorsBox = new HBox(10);
                VBox vBox1 = new VBox(10);
                VBox vBox2 = new VBox(10);
                Stage registerWindow = new Stage();
                Label player1Label = new Label("Player 1");
                player1Label.setFont(Font.font(20));
                Label player2Label = new Label("Player 2");
                player2Label.setFont(Font.font(20));
                Label enterName1 = new Label("Enter your name");
                enterName1.setFont(Font.font(20));
                Label enterName2 = new Label("Enter your name");
                enterName2.setFont(Font.font(20));
                Label chooseColor = new Label("Choose your color:");
                chooseColor.setFont(Font.font(15));
                Label yourColorIs = new Label("Your color is:");
                yourColorIs.setFont(Font.font(15));
                TextField name1 = new TextField("Player 1");
                name1.setMaxWidth(200.0);
                TextField name2 = new TextField("Player 2");
                name2.setMaxWidth(200.0);
                Button regPlayer1 = new Button("Next");
                Button regPlayer2 = new Button("OK");
                Rectangle color1 = new Rectangle(200, 100, 50, 50);
                color1.setStyle("-fx-fill: blue; -fx-stroke: white; -fx-stroke-width: 4px");
                Rectangle color2 = new Rectangle(200, 100, 50, 50);
                color2.setStyle("-fx-fill: red; -fx-stroke: white; -fx-stroke-width: 4px");
                Rectangle player2Color = new Rectangle(200, 100, 50, 50);
                colorsBox.getChildren().addAll(color1, color2);
                colorsBox.setAlignment(Pos.CENTER);
                vBox1.getChildren().addAll(player1Label, enterName1, name1, chooseColor, colorsBox, regPlayer1);
                vBox1.setAlignment(Pos.CENTER);
                vBox2.getChildren().addAll(player2Label, enterName2, name2, yourColorIs, player2Color, regPlayer2);
                vBox2.setAlignment(Pos.CENTER);

                Scene registerScene1 = new Scene(vBox1, 450, 300);
                Scene registerScene2 = new Scene(vBox2, 450, 300);
                registerWindow.setScene(registerScene1);
                registerWindow.setTitle("Registration");
                registerWindow.setResizable(false);
                registerWindow.getIcons().add(icon2);
                registerWindow.initModality(Modality.WINDOW_MODAL);
                registerWindow.initOwner(primaryStage);
                registerWindow.show();

                //The cursor shape changes if the player keeps the cursor on the squares
                //It lets the player know that the squares are selectable objects
                EventHandler<MouseEvent> enterSquare = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        registerScene1.setCursor(Cursor.HAND);
                    }
                };
                EventHandler<MouseEvent> exitSquare = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        registerScene1.setCursor(Cursor.DEFAULT);
                    }
                };
                color1.setOnMouseEntered(enterSquare);
                color1.setOnMouseExited(exitSquare);
                color2.setOnMouseEntered(enterSquare);
                color2.setOnMouseExited(exitSquare);

                //Player 1 can choose a color by clicking on one of the two squares
                //Player 2's color will be automatically assigned
                color1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        color1.setStyle("-fx-fill: blue; -fx-stroke: black; -fx-stroke-width: 4px");
                        color2.setStyle("-fx-fill: red; -fx-stroke: white; -fx-stroke-width: 4px");
                        player2Color.setStyle("-fx-fill: red; -fx-stroke: black; -fx-stroke-width: 4px");
                        players[0].setColor(1);
                        players[1].setColor(2);
                    }
                });

                //Player 1 can choose a color by clicking on one of the two squares
                //Player 2's color will be automatically assigned
                color2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        color1.setStyle("-fx-fill: blue; -fx-stroke: white; -fx-stroke-width: 4px");
                        color2.setStyle("-fx-fill: red; -fx-stroke: black; -fx-stroke-width: 4px");
                        player2Color.setStyle("-fx-fill: blue; -fx-stroke: black; -fx-stroke-width: 4px");
                        players[0].setColor(2);
                        players[1].setColor(1);
                    }
                });

                //By clicking Next the first player's name is saved and the next scene comes up
                //Player 1 is the current player
                regPlayer1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (players[0].getColor() != 0 && players[1].getColor() != 0) {
                            player1.setName(name1.getText());
                            currentPlayer = players[0];
                            registerWindow.setScene(registerScene2);
                            registerWindow.show();
                        }
                    }
                });

                //By clicking OK, the second player's name is saved
                // the register stage gets closed and the Main game screen comes up
                regPlayer2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        player2.setName(name2.getText());
                        registerWindow.close();
                        primaryStage.setScene(gameScreen);
                        primaryStage.show();
                        //After getting players names we show them in the main game window
                        playerName1.setText(" " + players[0].getName() + ":");
                        playerName2.setText(" " + players[1].getName() + ":");
                        playerName1.setStyle("-fx-border-width: 3px; -fx-border-color: black; -fx-border-radius: 6px");
                        playerName2.setStyle("-fx-border-width: 3px; -fx-border-color: #EAA157; -fx-border-radius: 6px");
                        playerScore1.setText("      0");
                        playerScore2.setText("      0");
                        playerName1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25.0));
                        playerName2.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25.0));
                        playerScore1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25.0));
                        playerScore2.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25.0));
                        nameScoreVBox.getChildren().addAll(playerName1, playerScore1, playerName2, playerScore2);
                        nameScoreVBox.setAlignment(Pos.CENTER_LEFT);
                        nameScoreVBox.setStyle("-fx-background-color: #EAA157; -fx-background-radius: 8px;" +
                                "-fx-border-color: black; -fx-border-width: 3px; -fx-border-radius: 6px;" +
                                "-fx-padding: 15px 10px 15px 10px");
                        nameScoreVBox.setMaxSize(210, 250);
                        nameScoreVBox.setMinSize(210, 200);
                        gameSceneAnchor.getChildren().addAll(nameScoreVBox);
                        gameSceneAnchor.setTopAnchor(nameScoreVBox, 25.0);
                        gameSceneAnchor.setRightAnchor(nameScoreVBox, 20.0);
                    }
                });

            }
        });

    }
    /*---------------------------------------------------------------------------------------------------------------*/
    /*---------------------------------------------------------------------------------------------------------------*/
    /*---------------------------------------------------------------------------------------------------------------*/

    public static void main(String[] args) {
        launch();
    }
}