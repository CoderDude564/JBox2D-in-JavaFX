package application;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import application.Box2DHelper.Displayable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Box implements Displayable {
	
	private float pixelWidth;
	private float pixelHeight;
	private Body body;
	private String name;
	private Rectangle rectangle;
	private Box2DHelper world;
	private Pane root;
	
	public Box(float x, float y, float pixelWidth, float pixelHeight, BodyType bodyType, float angle, float density, float friction, float restitution, Color color, String name, Box2DHelper world, Pane root) {
		
		this.pixelWidth = pixelWidth;
		this.pixelHeight = pixelHeight;
		this.name = name;
		this.world = world;
		this.root = root;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		bodyDef.setAngle(angle);
		bodyDef.setPosition(this.world.coordPixelsToWorld(x, y));
		this.body = this.world.createDisplayableBody(bodyDef, this);
		
		PolygonShape shape = new PolygonShape();
		float worldWidth = this.world.scalarPixelsToWorld(this.pixelWidth / 2);
	    float worldHeight = this.world.scalarPixelsToWorld(this.pixelHeight / 2);
	    shape.setAsBox(worldWidth, worldHeight);
	    
	    FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.density = density;
	    fixtureDef.friction = friction;
	    fixtureDef.restitution = restitution;
	    
	    this.body.createFixture(fixtureDef);
	    this.body.setUserData(this);
	    
	    this.rectangle = new Rectangle(x, y, this.pixelWidth, this.pixelHeight);
	    this.rectangle.setFill(color);
	    this.rectangle.setStroke(Color.BLACK);
	    this.rectangle.setStrokeWidth(2);
	    this.root.getChildren().add(this.rectangle);
	    
	}
	
	public float getPixelWidth() {
		return this.pixelWidth;
	}
	
	public float getPixelHeight() {
		return this.pixelHeight;
	}
	
	public float getWorldWidth() {
		return this.world.scalarPixelsToWorld(this.pixelWidth);
	}
	
	public float getWorldHeight() {
		return this.world.scalarPixelsToWorld(this.pixelHeight);
	}
	
	public Rectangle getRectangle() {
		return this.rectangle;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public void display() {
		
		Vec2 pos = this.world.getBodyPixelCoord(this.body);
		float angle = -this.body.getAngle();
		this.rectangle.setX(pos.x - this.pixelWidth / 2);
		this.rectangle.setY(pos.y - this.pixelHeight / 2);
		this.rectangle.setRotate(Math.toDegrees(angle));
		
	}
	
	@Override
	public void removeNode() {
		this.root.getChildren().remove(this.rectangle);
	}
	
	@Override
	public Body getMainBody() {
		return this.body;
	}
	
}