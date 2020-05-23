package application;

import java.util.ArrayList;

import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointDef;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Box2DHelper {
	
	private float transX;
	private float transY;
	private float scaleFactor;
	private float yFlip;
	private float height;
	private float timeStep;
	private Body groundBody;
	public ArrayList<Displayable> displayObjects;
	public World world;
	public Box2DContactListener contactListener;
	public MouseSpring mouseSpring;
	
	public Box2DHelper(float width, float height) {
		this(width, height, 25, 1.0f / 60f);
	}
	
	public Box2DHelper(float width, float height, float scaleFactor, float timeStep) {
		
		this.height = height;
		this.transX = width / 2;
		this.transY = height / 2;
		this.scaleFactor = scaleFactor;
		this.yFlip = -1;
		this.timeStep = timeStep;
		this.displayObjects = new ArrayList<Displayable>();
		
	}
	
	public void listenForCollisions(ContactListener contactListener) {
		this.world.setContactListener(this.contactListener);
	}
	
	public void listenForCollisions(Class<?> parent) {
		
		this.contactListener = new Box2DContactListener(parent);
		this.world.setContactListener(this.contactListener);
		
	}
	
	public void setScaleFactor(float scaleFactor) {
		this.scaleFactor = scaleFactor;
	}
	
	public void stepAndDisplay() {
		this.stepAndDisplay(this.timeStep, 10, 8);
	}
	
	public void stepAndDisplay(float timeStep, int velocityIterations, int positionIterations) {
		
		this.step(timeStep, velocityIterations, positionIterations);
		this.display();
		
	}
	
	public void step() {
		this.step(this.timeStep, 10, 8);		
	}
	
	public void step(float timeStep, int velocityIterations, int positionIterations) {
		this.world.step(timeStep, velocityIterations, positionIterations);
	}
	
	public void display() {
		
		for (Displayable displayable : this.displayObjects)
			displayable.display();
		
		if (this.mouseSpring != null)
			if (this.mouseSpring.ifFunctional())
				this.mouseSpring.display();
		
	}
	
	public float getTimeStep() {
		return this.timeStep;
	}
	
	public void setWarmStarting(boolean warmStart) {
		this.world.setWarmStarting(warmStart);
	}
	
	public void setContinuousPhysics(boolean continuous) {
		this.world.setContinuousPhysics(continuous);
	}
	
	public void createWorld() {
		this.createWorld(new Vec2(0.0f, -10.0f));		
	}
	
	public void createWorld(Vec2 gravity) {
		this.createWorld(gravity, true, true, true);
	}

	public void createWorld(Vec2 gravity, boolean doSleep, boolean warmStarting, boolean continous) {
		
		this.world = new World(gravity);
		this.world.setAllowSleep(doSleep);
		this.setWarmStarting(warmStarting);
		this.setContinuousPhysics(continous);
		
	}
	
	public void setGravity(float x, float y) {
		this.world.setGravity(new Vec2(x, y));
	}
	
	public Vec2 coordWorldToPixels(Vec2 world) {
		return this.coordWorldToPixels(world.x, world.y);
	}
		
	public Vec2 coordWorldToPixels(float worldX, float worldY) {
			
		float pixelX = map(worldX, 0f, 1f, this.transX, this.transX + this.scaleFactor);
		float pixelY = map(worldY, 0f, 1f, this.transY, this.transY + this.scaleFactor);
		
		if (this.yFlip == -1.0f)
			pixelY = map(pixelY, 0f, this.height, this.height, 0f);
		
		return new Vec2(pixelX, pixelY);
	
	}

	public Vec2 coordPixelsToWorld(Vec2 pixel) {
		return this.coordPixelsToWorld(pixel.x, pixel.y);
	}

	public Vec2 coordPixelsToWorld(float pixelX, float pixelY) {
			
		float worldX = map(pixelX, this.transX, this.transX + this.scaleFactor, 0f, 1f);
		float worldY = pixelY;
			
		if (this.yFlip == -1.0f)
			worldY = map(pixelY, this.height, 0f, 0f, this.height);
		
		worldY = map(worldY, this.transY, this.transY + this.scaleFactor, 0f, 1f);
		return new Vec2(worldX, worldY);
		
	}

	public float scalarPixelsToWorld(float val) {
		return val / this.scaleFactor;
	}

	public float scalarWorldToPixels(float val) {
		return val * this.scaleFactor;
	}
	
	public Vec2 vectorPixelsToWorld(float pixelX, float pixelY) {
		return this.vectorPixelsToWorld(new Vec2(pixelX, pixelY));
	}

	public Vec2 vectorPixelsToWorld(Vec2 pixelVector) {
		
		Vec2 worldVector = new Vec2(pixelVector.x / this.scaleFactor, pixelVector.y / this.scaleFactor);
		worldVector.y = worldVector.y * this.yFlip;
		return worldVector;
	
	}
	
	public Vec2 vectorWorldToPixels(float worldX, float worldY) {
		return this.vectorWorldToPixels(new Vec2(worldX, worldY));
	}

	public Vec2 vectorWorldToPixels(Vec2 worldVector) {
			
		Vec2 pixelVector = new Vec2(worldVector.x * this.scaleFactor, worldVector.y * this.scaleFactor);
		pixelVector.y = pixelVector.y * this.yFlip;
		return pixelVector;
		
	}
	
	public Body createDisplayableBody(BodyDef bodyDef, Displayable displayable) {
		
		this.displayObjects.add(displayable);
		return this.createBody(bodyDef);
		
	}
	
	public Body createBody(BodyDef bodyDef) {
		return this.world.createBody(bodyDef);
	}
		
	public Joint createJoint(JointDef jointDef) {
		return this.world.createJoint(jointDef);
	}
	
	public void destroyDisplayableBody(Displayable displayable) {
		
		this.displayObjects.remove(displayable);
		this.destroyBody(displayable.getMainBody());
		displayable.removeNode();
		displayable = null;
		
	}
	
	public void destroyBody(Body body) {
		
		this.world.destroyBody(body);
		body = null;
		
	}
	
	public void destroyJoint(Joint joint) {
		
		this.world.destroyJoint(joint);
		joint = null;
		
	}
	
	public Vec2 getBodyPixelCoord(Body body) {
		return this.coordWorldToPixels(body.getTransform().p); 
	}
	
	public static float map(float value, float istart, float istop, float ostart, float ostop) {
	    return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	}
	
	public void setGroundBody(Body body) {
		this.groundBody = body;
	}
	
	public Body getGroundBody() {
		return this.groundBody;
	}
	
	public void createMouseJoint(Pane root) {
		
		this.mouseSpring = new MouseSpring(this, root);
		
		root.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
			this.mouseSpring.update((float) event.getX(), (float) event.getY());
		});
		
		root.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
			
			float mouseX = (float) event.getX();
			float mouseY = (float) event.getY();
			
			for (Displayable displayable : this.displayObjects)
				if (displayable.getMainBody().getFixtureList().testPoint(this.coordPixelsToWorld(mouseX, mouseY))) {
					
					this.mouseSpring.bind(mouseX, mouseY, displayable.getMainBody());
					break;
					
				}
			
		});
		
		root.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
			this.mouseSpring.destroy();
		});
		
	}
	
	public interface Displayable {
		
		public void display();
		public void removeNode();
		public Body getMainBody();
		
	}
	
}
