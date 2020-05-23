package application;

import org.jbox2d.dynamics.BodyType;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	
	public static int width = 1382;
	public static int height = 704;
	public static Box2DHelper world;
	public static Pane root;
	public static Timeline timeline = new Timeline();
	
	@Override
	public void start(Stage window) throws Exception {
		
		window = new Stage();
		root = new Pane();
		root.setBackground(Background.EMPTY);
		
		Scene scene = new Scene(root, width, height, Color.gray(0.75));
		window.setTitle("Walker");
		window.setScene(scene);
		window.setMaximized(true);
		
		world = new Box2DHelper(width, height);
		world.createWorld();
		world.createMouseJoint(root);

		new Box(width / 2, 0, width, 20, BodyType.DYNAMIC, 0, 1, 0.5f, 0.4f, Color.gray(0.7, 0.1), "Platform", world, root);
		Box platform = new Box(width / 2, height - 10, width, 20, BodyType.STATIC, 0, 1, 0.5f, 0.4f, Color.gray(0.7, 0.1), "Platform", world, root);
		world.setGroundBody(platform.getMainBody());
		
		EventHandler<ActionEvent> stepEvent = new EventHandler<ActionEvent>() {
									
			@Override
			public void handle(ActionEvent event) {
				
				world.stepAndDisplay();
			
			}
			
		};
		
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(world.getTimeStep()), stepEvent, null, null));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		
		window.setOnCloseRequest(closeEvent -> {
			
			timeline.stop();
			Platform.exit();
			System.exit(0);
			
		});
		
		window.show();
		
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
