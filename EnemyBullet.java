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


public class EnemyBullet extends GameObject
{
	private int x;
	private int y;
	private int velX;
	private int velY;
	private Image image;
	private boolean isAlive;
	private boolean isLaunched;

	public EnemyBullet(int x, int y, Image image)
	{
		super(x, y, image);
		velX = 0;
		velY = 0;
		isAlive = true;
		isLaunched = false;
	}
}