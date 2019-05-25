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
	int x = 0;
	int y = 0;
	Canvas canvas;
	AnimateObjects animate;
	ArrayList<GameObject> players = new ArrayList<>();

	public class AnimateObjects extends AnimationTimer
	{

		public void handle(long now)
		{
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(players.get(0).getImage(), 180 + x, 100 + y);
		}

		/*public void startAnimation()
		{
			for(GameObject object: players)
			{
				x+=1;
				gc.drawImage(object.getImage(), 180 + x, 100);
			}
		}*/
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
		canvas = new Canvas(800, 400);
		root.getChildren().add(canvas);
		Scene scene = new Scene(root, Color.BLACK);
		scene.addEventHandler(KeyEvent.KEY_PRESSED,this);
		stage.setScene(scene);
		gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		shipImg = new Image("ship.png");

		players.add(new GameObject(180, 100, shipImg));
		gc.drawImage(players.get(0).getImage(), 180, 100);

		animate = new AnimateObjects();
		animate.start();
		stage.show();

		
	}

	public static void main(String[]args)
	{
		launch();
	}
}