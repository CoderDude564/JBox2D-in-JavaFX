package application;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.joints.MouseJoint;
import org.jbox2d.dynamics.joints.MouseJointDef;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class MouseSpring {
	
	private MouseJoint mouseJoint;
	private Line line;
	private Box2DHelper world;
	private Pane root;
	
	public MouseSpring(Box2DHelper world, Pane root) {
		
		this.mouseJoint = null;
		this.world = world;
		this.root = root;
		this.line = new Line(0, 0, 0, 0);
		this.line.setStrokeWidth(2);
		this.root.getChildren().add(this.line);
		
	}
	
	public void update(float x, float y) {
		
		if (this.mouseJoint != null)
			this.mouseJoint.setTarget(this.world.coordPixelsToWorld(x, y));
	
	}
	
	public void bind(float x, float y, Body body) {
		
		try {
			
			MouseJointDef mouseJoint = new MouseJointDef();
			mouseJoint.bodyA = this.world.getGroundBody();
			mouseJoint.bodyB = body;
			mouseJoint.target.set(this.world.coordPixelsToWorld(x, y));
			mouseJoint.maxForce = 10000f * body.m_mass;
			mouseJoint.frequencyHz = 5;
			mouseJoint.dampingRatio = 0.9f;
			mouseJoint.collideConnected = true;
			this.mouseJoint = (MouseJoint) world.createJoint(mouseJoint);
			
		}
		
		catch (NullPointerException error) {
			System.err.println("You need to initialise the ground body of the Box2DHelper object for mouseJoint to work. The ground body must be static.");
		}
		
	}
	
	public void destroy() {
		
		if (this.mouseJoint != null) {
			
			this.world.destroyJoint(this.mouseJoint);
			this.mouseJoint = null;
			this.line.setStartX(0);
			this.line.setStartY(0);
			this.line.setEndX(0);
			this.line.setEndY(0);
			
		}
		
	}
	
	public void display() {
		
		this.root.getChildren().remove(this.line);
		this.root.getChildren().add(this.line);
		Vec2 firstPoint = new Vec2(0, 0);
		Vec2 secondPoint = new Vec2(0, 0);
		this.mouseJoint.getAnchorA(firstPoint);
		this.mouseJoint.getAnchorB(secondPoint);
		firstPoint.set(this.world.coordWorldToPixels(firstPoint));
		secondPoint.set(this.world.coordWorldToPixels(secondPoint));
		this.line.setStartX(firstPoint.x);
		this.line.setStartY(firstPoint.y);
		this.line.setEndX(secondPoint.x);
		this.line.setEndY(secondPoint.y);
	
	}
	
	public boolean ifFunctional() {
		
		boolean result = true;
		
		if (this.mouseJoint == null)
			result = false;
		
		return result;
		
	}

}