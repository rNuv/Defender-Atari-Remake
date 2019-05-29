import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.media.AudioClip;
import java.net.URL;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

public class Defender extends Application implements EventHandler<InputEvent>
{
	private GraphicsContext gc1;
	private GraphicsContext gc2;
	private Image shipImg;
	private Image background;
	private int x = 0;
	private int y = 0;
	private Canvas canvas1;
	private Canvas canvas2;
	private AnimateObjects animate;
	private ArrayList<GameObject> players = new ArrayList<>();
	private Scene scene1, scene2;

	public class AnimateObjects extends AnimationTimer
	{

		public void handle(long now)
		{
			gc2.clearRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
			gc2.drawImage(players.get(0).getImage(), 180 + x, 100 + y);
		}

	}

	public void handle(final InputEvent event)
	{
		if (((KeyEvent)event).getCode() == KeyCode.LEFT )
			x-=5;
		if (((KeyEvent)event).getCode() == KeyCode.RIGHT )
			x+=5;
		if (((KeyEvent)event).getCode() == KeyCode.UP )
			y-=5;
		if (((KeyEvent)event).getCode() == KeyCode.DOWN )
			y+=5;
	}

	public void start(Stage stage)
	{
		stage.setTitle("Defender");
		Group root1 = new Group();
		Group root2 = new Group();
		canvas1 = new Canvas(800, 450);
		canvas2 = new Canvas(800, 450);

		root1.getChildren().add(canvas1);
		root2.getChildren().add(canvas2);

		Scene scene1 = new Scene(root1);
		Scene scene2 = new Scene(root2, Color.BLACK);
		scene1.addEventHandler(KeyEvent.KEY_PRESSED,this);
		scene2.addEventHandler(KeyEvent.KEY_PRESSED,this);
		stage.setScene(scene1);

		Button button = new Button("Play Game");
		button.setTranslateX(280);
		button.setTranslateY(315);
		button.setPrefSize(230, 35);
		button.setStyle("-fx-background-color: #E76B03; ");

		button.setOnAction(value ->  {
			stage.setScene(scene2);
        });

		root1.getChildren().add(button);

		gc1 = canvas1.getGraphicsContext2D();
		gc2 = canvas2.getGraphicsContext2D();
		background = new Image("TitleScreen.jpg");
		shipImg = new Image("ship.png");

		gc1.drawImage(background, 0, 0);
		players.add(new GameObject(180, 100, shipImg));
		gc2.drawImage(players.get(0).getImage(), 180, 100);
	
		animate = new AnimateObjects();
		animate.start();
	
		stage.show();
	}

	public static void main(String[]args)
	{
		launch();
	}
}