package application;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import application.Box2DHelper.Displayable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Wheel implements Displayable {
	
	private float pixelRadius;
	private Body body;
	private String name;
	private Circle circle;
	private Line line;
	private boolean hasLine;
	private Box2DHelper world;
	private Pane root;
	
	public Wheel(float x, float y, float pixelRadius, BodyType bodyType, float density, float friction, float restitution, boolean hasLine, Color color, String name, Box2DHelper world, Pane root) {
		
		this.pixelRadius = pixelRadius;
		this.name = name;
		this.hasLine = hasLine;
		this.world = world;
		this.root = root;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		bodyDef.setPosition(this.world.coordPixelsToWorld(x, y));
		this.body = this.world.createDisplayableBody(bodyDef, this);
		
		CircleShape shape = new CircleShape();
	    shape.setRadius(this.world.scalarPixelsToWorld(this.pixelRadius));
	    
	    FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.density = density;
	    fixtureDef.friction = friction;
	    fixtureDef.restitution = restitution;
	    
	    this.body.createFixture(fixtureDef);
	    this.body.setUserData(this.name);
	    
	    this.circle = new Circle(this.pixelRadius, color);
		this.circle.setStroke(Color.BLACK);
		this.circle.setStrokeWidth(2);
		this.line = new Line();
		this.line.setStroke(Color.BLACK);
		this.line.setStrokeWidth(2);
		this.root.getChildren().addAll(this.circle, this.line);
		
	}
	
	public float getPixelRadius() {
		return this.pixelRadius;
	}
	
	public float getWorldRadius() {
		return this.world.scalarPixelsToWorld(this.pixelRadius);
	}
	
	public Circle getCircle() {
		return this.circle;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public void display() {
		
		Vec2 pos = this.world.getBodyPixelCoord(this.body);
		float angle = this.body.getAngle();
		this.circle.setCenterX(pos.x);
		this.circle.setCenterY(pos.y);
		this.circle.setRotate(Math.toDegrees(angle));
		
		if (this.hasLine) {
			
			this.line.setStartX(pos.x - getPixelRadius() * Math.cos(angle));
			this.line.setStartY(pos.y + getPixelRadius() * Math.sin(angle));
			this.line.setEndX(pos.x + getPixelRadius() * Math.cos(angle));
			this.line.setEndY(pos.y - getPixelRadius() * Math.sin(angle));
			
		}
		
	}
	
	@Override
	public void removeNode() {
		this.root.getChildren().remove(this.circle);
	}
	
	@Override
	public Body getMainBody() {
		return this.body;
	}

}
