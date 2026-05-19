package com.apcsa;

import com.apcsa.ui.InputManager;
import com.apcsa.ui.UIStyles;
import com.apcsa.waves.WaveManager;
import com.apcsa.world.GameWorld;
import com.apcsa.world.Health;

import javafx.scene.canvas.GraphicsContext;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.image.*;


public class Main extends Application {

    public static Canvas canvas;
    public static GraphicsContext gc;

    public static Pane pane;

    public static Text waveText;
    public static Text timeText;
    public static Text moneyText;
    public static Text baseHealthText;

    public static Button signoreButton;
    public static Button farmButton;
    public static Button skipButton;
    public static Button upgradeButton;

    public static Scene scene;

    public static Circle rangePreview;
    public static Circle rangePreviewPlaced;
    public static ImageView towerPreview;

    public static Button startButton;

    @Override
    public void start(Stage stage) {
        setUpHomeScreen(stage);
        startButton.setOnAction(e -> { runGame(stage); });
    }


    public void runGame(Stage stage) {

        setUpCanvasAndScene(stage);
    
        setUpPreviews();
        setUpGrid();
        setUpText();
        setUpButtons();


        GameWorld.startGameLoop();
        WaveManager.runIt();
        InputManager.setUpKeybindManager();

    }


    public void setUpCanvasAndScene(Stage stage){
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();
        pane = new Pane(canvas);

        scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Teacher Defense");
        stage.show();
    }

    public void setUpHomeScreen(Stage stage){
        Pane startPane = new Pane();

        Text title = new Text("tittle text here ");
        title.setX(320);
        title.setY(240);

        startButton = new Button("START");

        startButton.setLayoutX(350);
        startButton.setLayoutY(400);
        startButton.setPrefSize(100, 40);

        startPane.getChildren().addAll(title, startButton);
        Scene startScene = new Scene(startPane, 800, 600);

        stage.setScene(startScene);
        stage.setTitle("faccultyfaceoff");
        stage.show();
    }

    public void setUpPreviews(){
        rangePreview = new Circle();
        rangePreview.setFill(Color.rgb(128, 128, 128, 0.25));
        rangePreview.setStroke(Color.GRAY);
        rangePreview.setVisible(false);
        rangePreview.setMouseTransparent(true);

        rangePreviewPlaced = new Circle();
        rangePreviewPlaced.setFill(Color.rgb(128, 128, 128, 0.25));
        rangePreviewPlaced.setStroke(Color.GRAY);
        rangePreviewPlaced.setVisible(false);
        rangePreviewPlaced.setMouseTransparent(true);
        
        towerPreview = new ImageView();
        towerPreview.setVisible(false);
        towerPreview.setFitWidth(64);
        towerPreview.setFitHeight(64);

        pane.getChildren().addAll(rangePreview, towerPreview, rangePreviewPlaced);
    }

    public void setUpGrid(){
        gc.setStroke(Color.LIGHTGRAY);
        for (int x = 0; x <= 800; x += 64) {
            gc.strokeLine(x, 0, x, 600);
        }
        for (int y = 0; y <= 600; y += 64) {
            gc.strokeLine(0, y, 800, y);
        }

        GameWorld.paintPathAndOccupy(gc);
    }

    public void setUpText(){
        waveText = new Text("Wave: 0");
        timeText = new Text("0:15");
        moneyText = new Text("Money: 100");
        baseHealthText = new Text(Health.baseHealth+"/"+Health.maxBaseHealth);

        UIStyles.setWaveText(waveText, 380, 70);
        UIStyles.setWaveText(timeText, 392, 100);
        UIStyles.setMoneyText(moneyText, 650, 50);
        UIStyles.setBaseHealthText(baseHealthText, 357, 40);

        pane.getChildren().addAll(waveText, timeText, moneyText, baseHealthText);
    }

    public void setUpButtons(){
        signoreButton = new Button("Signore");
        farmButton = new Button("Farm");
        skipButton = new Button("Skip");
        upgradeButton = new Button("Upgrade");
        upgradeButton.setVisible(false);

        UIStyles.styleTowerPlacementButton(signoreButton, 350, 500);
        UIStyles.styleTowerPlacementButton(farmButton, 500, 500);
        UIStyles.styleTowerPlacementButton(skipButton, 200, 500);
        UIStyles.styleTowerPlacementButton(upgradeButton, 500, 200);

        pane.getChildren().addAll(signoreButton, skipButton, farmButton, upgradeButton);

        InputManager.setUpImageClick(signoreButton);
        InputManager.setUpImageClick(farmButton);
    }

    public static void main(String[] args) {
        launch(args);
    }

}