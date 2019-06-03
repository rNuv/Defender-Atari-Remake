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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.Background;

public class Defender extends Application implements EventHandler<InputEvent>
{
	private GraphicsContext gc1;
	private GraphicsContext gc2;

	private ImageView backgroundImageView;
	//private Pane backgroundLayer;
	private double backgroundScrollSpeed = 7.0;

	private BackgroundImage bgimg;
	private StackPane root2;
	private Image shiprightImg;
	private Image shipleftImg;
	private Image titlescreen;
	private Image background;
	private Image bulletImg;
	private Image enemyImg;
	private Canvas canvas1;
	private Canvas canvas2;
	private AnimateObjects animate;
	private ArrayList<GameObject> gameobjectlist;
	private ArrayList<Enemy> enemies;
	private int enemy_count = 0;
	private Scene scene1, scene2;
	private Player player;

	public class AnimateObjects extends AnimationTimer
	{

		public void handle(long now)
		{
			gc2.clearRect(0, 0, canvas2.getWidth(), canvas2.getHeight());


			//gc2.drawImage(background, 0,0);


			for(int i = 0; i < gameobjectlist.size(); i++)
			{
				if(!(gameobjectlist.get(i).isAlive()))
				{
					gameobjectlist.remove(i);
				}
			}

			for(int i = 0; i < enemies.size(); i++)
			{
				if(!(enemies.get(i).isAlive()))
				{
					enemies.remove(i);
				}
			}

			for(GameObject i: gameobjectlist)
			{
				if(i instanceof Player)
				{
					i.setX(i.getVelX());
					i.setY(i.getVelY());
					gc2.drawImage(i.getImage(), i.getX(), i.getY());
				}
				else
				{
					if(player.getImage().getHeight() == 17.0)
					{
						if(!(i.isLaunched()))
						{
							i.setVelX(-10);
							i.changeLaunched();
						}
					}
					else
					{
						if(!(i.isLaunched()))
						{
							i.setVelX(10);
							i.changeLaunched();
						}
					}
					i.setX(i.getVelX());
					gc2.drawImage(i.getImage(), i.getX(), i.getY());
				}

				for(Enemy e: enemies)
				{
					if(!(i instanceof Player))
					{
						if(i.bounds().intersects(e.bounds()))
						{
							e.changeToDead();
							enemy_count--;
						}
					}
				}
			}


			if(enemy_count == 0)
			{
				enemies.add(new Enemy((int)(Math.random()*810)+10, (int)(Math.random()*400)+90, enemyImg));
				enemy_count++;
			}

			for(Enemy e: enemies)
			{
				gc2.drawImage(e.getImage(), e.getX(), e.getY());
			}
		}

	}

	public void handle(final InputEvent event)
	{
		if (((KeyEvent)event).getCode() == KeyCode.LEFT )
		{
			player.setImage(shipleftImg);
			player.setVelX(-7);
			//double x = backgroundImageView.getLayoutX() - backgroundScrollSpeed;
			//backgroundImageView.setLayoutX(x);
		}
		else if (((KeyEvent)event).getCode() == KeyCode.RIGHT )
		{
			player.setImage(shiprightImg);
			player.setVelX(7);
			//double x = backgroundImageView.getLayoutX() + backgroundScrollSpeed;
			//backgroundImageView.setLayoutX(x);
		}
		else if (((KeyEvent)event).getCode() == KeyCode.UP )
		{
			player.setVelY(-7);
		}
		else if (((KeyEvent)event).getCode() == KeyCode.DOWN )
		{
			player.setVelY(7);
		}

		if (((KeyEvent)event).getCode() == KeyCode.SPACE )
		{
			gameobjectlist.add(new GameObject(player.getX(), player.getY(), bulletImg));
		}

		if((((KeyEvent)event).getCode() == KeyCode.RIGHT) && (event.getEventType().toString().equals("KEY_RELEASED")))
		{
			player.setVelX(0);
		}
		else if((((KeyEvent)event).getCode() == KeyCode.LEFT) && (event.getEventType().toString().equals("KEY_RELEASED")))
		{
			player.setVelX(0);
		}
		else if((((KeyEvent)event).getCode() == KeyCode.UP) && (event.getEventType().toString().equals("KEY_RELEASED")))
		{
			player.setVelY(0);
		}
		else if((((KeyEvent)event).getCode() == KeyCode.DOWN) && (event.getEventType().toString().equals("KEY_RELEASED")))
		{
			player.setVelY(0);
		}
	}

	public void loadgame()
	{
	 // backgroundImageView = new ImageView( getClass().getResource( "background.jpg").toExternalForm());

	  // reposition the map. it is scrolling from bottom of the background to top of the background
	 // backgroundImageView.relocate( 0, -backgroundImageView.getImage().getWidth() + 900);

	  // add background to layer
	  //backgroundLayer.getChildren().add(backgroundImageView);
	}

	public void start(Stage stage)
	{
		stage.setTitle("Defender");
		Group root1 = new Group();
		StackPane root2 = new StackPane();
		canvas1 = new Canvas(900, 506);
		canvas2 = new Canvas(900, 506);

		//backgroundLayer = new Pane();

		root1.getChildren().add(canvas1);
		root2.getChildren().add(canvas2);
		//root2.getChildren().addAll(backgroundLayer, canvas2);



		Scene scene1 = new Scene(root1);
		Scene scene2 = new Scene(root2);
		scene1.addEventHandler(KeyEvent.KEY_PRESSED,this);
		scene2.addEventHandler(KeyEvent.KEY_PRESSED,this);
		scene2.addEventHandler(KeyEvent.KEY_RELEASED,this);
		stage.setScene(scene1);

		Button button = new Button("Play Game");
		button.setTranslateX(330);
		button.setTranslateY(315);
		button.setPrefSize(230, 35);
		button.setStyle("-fx-background-color: #E76B03; ");

		button.setOnAction(value ->  {
			stage.setScene(scene2);
        });

		root1.getChildren().add(button);

		gc1 = canvas1.getGraphicsContext2D();
		gc2 = canvas2.getGraphicsContext2D();
		gameobjectlist = new ArrayList<>();
		enemies = new ArrayList<>();
		titlescreen = new Image("TitleScreen.jpg");
		background = new Image("background.jpg");
		shiprightImg = new Image("shipright.png");
		shipleftImg = new Image("shipleft.png");
		bulletImg = new Image("bullet.png");
		enemyImg = new Image("enemy.png");
		player = new Player(180, 100, shiprightImg);
		gameobjectlist.add(player);

		bgimg = new BackgroundImage(background, BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		root2.setBackground(new Background(bgimg));

		gc1.drawImage(titlescreen, 0, 0);
		//gc2.drawImage(background, 0, 0);
		gc2.drawImage(player.getImage(), player.getX(), player.getY());

		animate = new AnimateObjects();
		animate.start();

		stage.show();
		//loadgame();
	}

	public static void main(String[]args)
	{
		launch();
	}
}