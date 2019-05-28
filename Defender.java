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

public class Defender extends Application implements EventHandler<InputEvent>
{
	GraphicsContext gc;
	Image shipImg;
	Image background;
	int x = 0;
	int y = 0;
	Canvas canvas;
	AnimateObjects animate;
	private Menu menu;
	ArrayList<GameObject> players = new ArrayList<>();

	private enum STATE{
		MENU,
		GAME
	};

	private STATE state = STATE.MENU;

	public class AnimateObjects extends AnimationTimer
	{

		public void handle(long now)
		{
			//gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(players.get(0).getImage(), 180 + x, 100 + y);
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
		Group root = new Group();

		canvas = new Canvas(800, 450);
		root.getChildren().add(canvas);

		Scene scene = new Scene(root);
		scene.addEventHandler(KeyEvent.KEY_PRESSED,this);
		stage.setScene(scene);

		gc = canvas.getGraphicsContext2D();
		background = new Image("TitleScreen.jpg");
		shipImg = new Image("ship.png");

		if(state == STATE.MENU)
		{
			gc.drawImage(background, 0, 0);

		}
		else if(state == STATE.GAME)
		{
			players.add(new GameObject(180, 100, shipImg));
			gc.drawImage(players.get(0).getImage(), 180, 100);

			animate = new AnimateObjects();
			animate.start();
		}
		stage.show();


	}

	public static void main(String[]args)
	{
		launch();
	}
}