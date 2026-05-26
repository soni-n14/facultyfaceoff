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
import javafx.animation.AnimationTimer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Main entry point and root JavaFX Application that initialises the home screen
 * and launches the game.
 */
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
    public static Button kirshButton;
    public static Button welshButton;
    public static Button tsaiButton;
    public static Button skipButton;
    public static Button upgradeButton;
    public static Button sellButton;

    public static Scene scene;

    public static Circle rangePreview;
    public static Circle rangePreviewPlaced;
    public static ImageView towerPreview;

    public static Button startButton;
    public static MediaPlayer mediaPlayer;
    public static MediaPlayer winPlayer;
    public static MediaPlayer losePlayer;

    @Override
    public void start(Stage stage) {
        setUpHomeScreen(stage);
        startButton.setOnAction(e -> {
            runGame(stage);
        });
    }

    /**
     * Initialises all game systems and starts the game loop after the start button
     * is pressed.
     *
     * @param stage the primary JavaFX stage used to display the game scene
     */
    public void runGame(Stage stage) {
        String musicPath = Main.class.getResource("/fxml/music.mp3").toExternalForm();
        Media media = new Media(musicPath);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();

        setUpCanvasAndScene(stage);

        setUpPreviews();
        setUpGrid();
        setUpText();
        setUpButtons();

        GameWorld.running = true;
        GameWorld.startGameLoop();
        WaveManager.runIt();
        InputManager.setUpKeybindManager();

    }

    /**
     * Creates the 800x600 canvas and scene and attaches them to the stage.
     *
     * @param stage the stage to configure with the game scene
     */
    public void setUpCanvasAndScene(Stage stage) {
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();
        pane = new Pane(canvas);

        scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Faculty Face Off");
        stage.show();
    }

    public void setUpHomeScreen(Stage stage) {
        Pane startPane = new Pane();
        startPane.setStyle("-fx-background-color: RED;");

        Text title = new Text("FACULTY FACE OFF");
        title.setX(170);
        title.setY(250);
        // title.setRotate(30);
        title.setStyle("-fx-font-size: 50px; -fx-font-weight: bold;");

        startButton = new Button("PLAY");
        startButton.setLayoutX(350);
        startButton.setLayoutY(450);
        startButton.setPrefSize(100, 50);
        startButton.setStyle("-fx-font-size: 20px; -fx-background-color: black; -fx-text-fill: white");

        javafx.animation.AnimationTimer timer = new javafx.animation.AnimationTimer() {
            double phize = 1.0;
            int direktion = 1;

            @Override
            public void handle(long now) {
                phize += 0.01 * direktion;

                if (phize > 1.4 || phize < 0.6) {
                    direktion = -direktion;
                }

                title.setScaleX(phize);
                title.setScaleY(phize);
            }
        };
        timer.start();

        startPane.getChildren().addAll(title, startButton);
        Scene startScene = new Scene(startPane, 800, 600);

        stage.setScene(startScene);
        stage.setTitle("facultyfaceoff");
        stage.show();
    }

    /**
     * Creates and adds the range-preview circles and tower image preview overlay to
     * the pane.
     */
    public void setUpPreviews() {
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

    /**
     * Draws the tile grid lines and colours the enemy path tiles on the canvas.
     */
    public void setUpGrid() {
        Image bg = new Image(Main.class.getResourceAsStream("/fxml/classroom.png"));
        gc.drawImage(bg, 0, 0, 800, 600);

        gc.setStroke(Color.LIGHTGRAY);
        for (int x = 0; x <= 800; x += 64) {
            gc.strokeLine(x, 0, x, 600);
        }
        for (int y = 0; y <= 600; y += 64) {
            gc.strokeLine(0, y, 800, y);
        }

        GameWorld.paintPathAndOccupy(gc);
    }

    /**
     * Creates and styles the HUD text nodes for wave, timer, money, and base
     * health, then adds them to the pane.
     */
    public void setUpText() {
        waveText = new Text("Wave: 0");
        timeText = new Text("0:15");
        moneyText = new Text("Money: 100");
        baseHealthText = new Text(Health.baseHealth + "/" + Health.maxBaseHealth);

        UIStyles.setWaveText(waveText, 380, 70);
        UIStyles.setWaveText(timeText, 392, 100);
        UIStyles.setMoneyText(moneyText, 650, 50);
        UIStyles.setBaseHealthText(baseHealthText, 357, 40);

        pane.getChildren().addAll(waveText, timeText, moneyText, baseHealthText);
    }

    /**
     * Creates, styles, and wires click handlers for all tower-placement and upgrade
     * buttons.
     */
    public void setUpButtons() {
        signoreButton = new Button("Signore: $50");
        farmButton = new Button("Farm: $300");
        kirshButton = new Button("Kirsh: $300");
        welshButton = new Button("Welsh: $350");
        tsaiButton = new Button("Tsai: $125");
        skipButton = new Button("Skip");
        upgradeButton = new Button("Upgrade");
        upgradeButton.setVisible(false);
        sellButton = new Button("Sell");
        sellButton.setVisible(false);

        UIStyles.styleTowerPlacementButton(kirshButton, 350, 400);
        UIStyles.styleTowerPlacementButton(signoreButton, 350, 500);
        UIStyles.styleTowerPlacementButton(farmButton, 500, 500);
        UIStyles.styleTowerPlacementButton(welshButton, 500, 400);
        UIStyles.styleTowerPlacementButton(tsaiButton, 200, 400);
        UIStyles.styleTowerPlacementButton(skipButton, 200, 500);
        UIStyles.styleTowerPlacementButton(upgradeButton, 500, 200);
        UIStyles.styleTowerPlacementButton(sellButton, 500, 300);

        skipButton.setOnMouseClicked(e -> {
                com.apcsa.waves.WaveManager.skip();
        });

        pane.getChildren().addAll(signoreButton, kirshButton, skipButton, farmButton, welshButton, tsaiButton, upgradeButton, sellButton);

        InputManager.setUpImageClick(signoreButton);
        InputManager.setUpImageClick(farmButton);
        InputManager.setUpImageClick(kirshButton);
        InputManager.setUpImageClick(welshButton);
        InputManager.setUpImageClick(tsaiButton);
    }

    public static void showWinScreen() {
        javafx.application.Platform.runLater(() -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            String winSound = Main.class.getResource("/fxml/win.wav").toExternalForm();
            Media winMedia = new Media(winSound);
            winPlayer = new MediaPlayer(winMedia);
            winPlayer.play();

            Pane winPane = new Pane();
            winPane.setStyle("-fx-background-color: black;");

            Text winText = new Text("YOU WIN!");
            winText.setX(220);
            winText.setY(250);
            winText.setStyle("-fx-font-size: 70px; -fx-fill: gold; -fx-font-family: 'Impact';");

            Button restartBtn = new Button("PLAY AGAIN");
            restartBtn.setLayoutX(330);
            restartBtn.setLayoutY(350);
            restartBtn.setPrefSize(140, 50);
            restartBtn.setOnAction(e -> {
                if (winPlayer != null) winPlayer.stop();
                Health.baseHealth = 100;
                com.apcsa.world.Money.resetMoney();
                com.apcsa.waves.WaveManager.resetWave();
                com.apcsa.world.GameWorld.stopGameLoop();
                com.apcsa.waves.WaveManager.allEnemiesOut = true;
                GameWorld.enemies.clear();
                GameWorld.towers.clear();
                GameWorld.occupied.clear();
                Main m = new Main();
                m.start((Stage) scene.getWindow());
            });

            winPane.getChildren().addAll(winText, restartBtn);
            scene.setRoot(winPane);
        });
    }

    public static void showGameOver() {
        javafx.application.Platform.runLater(() -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            String loseSound = Main.class.getResource("/fxml/lose.wav").toExternalForm();
            Media loseMedia = new Media(loseSound);
            losePlayer = new MediaPlayer(loseMedia);
            losePlayer.play();

            Pane gameOverPane = new Pane();
            gameOverPane.setStyle("-fx-background-color: black;");

            Text loseText = new Text("YOU LOSE!");
            loseText.setX(210);
            loseText.setY(200);
            loseText.setStyle("-fx-font-size: 70px; -fx-fill: red; -fx-font-family: 'Impact';");

            Text overText = new Text("GAME OVER");
            overText.setX(250);
            overText.setY(280);
            overText.setStyle("-fx-font-size: 60px; -fx-fill: red; -fx-font-family: 'Impact';");

            Button restartBtn = new Button("RESTART");
            restartBtn.setLayoutX(350);
            restartBtn.setLayoutY(380);
            restartBtn.setPrefSize(100, 50);
            restartBtn.setOnAction(e -> {
                if (losePlayer != null) losePlayer.stop();
                Health.baseHealth = 100;
                com.apcsa.world.Money.resetMoney();
                com.apcsa.waves.WaveManager.resetWave();
                com.apcsa.world.GameWorld.stopGameLoop();
                com.apcsa.waves.WaveManager.allEnemiesOut = true;
                GameWorld.enemies.clear();
                GameWorld.towers.clear();
                GameWorld.occupied.clear();
                Main m = new Main();
                m.start((Stage) scene.getWindow());
            });

            gameOverPane.getChildren().addAll(loseText, overText, restartBtn);
            scene.setRoot(gameOverPane);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}