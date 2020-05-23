package application;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

public class RevoluteJointMaker {
	
	public RevoluteJoint revoluteJoint;
	
	public RevoluteJointMaker(Body bodyA, Body bodyB, boolean collideConnected, Vec2 localAnchorA, Vec2 localAnchorB, Box2DHelper world) {
		
		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.bodyA = bodyA;
		jointDef.bodyB = bodyB;
		jointDef.collideConnected = collideConnected;
		jointDef.localAnchorA.set(localAnchorA);
		jointDef.localAnchorB.set(localAnchorB);
		this.revoluteJoint = (RevoluteJoint) world.createJoint(jointDef);
		
	}
	
	public void applyLimit(float lowerAngle, float upperAngle) {
		
		this.revoluteJoint.enableLimit(true);
		this.revoluteJoint.setLimits(lowerAngle, upperAngle);
		
	}
	
	public void applyMotor(float maxMotorTorque, float motorSpeed) {
		
		this.revoluteJoint.enableMotor(true);
		this.revoluteJoint.setMaxMotorTorque(maxMotorTorque);
		this.revoluteJoint.setMotorSpeed(motorSpeed);
		
	}
	
	public void setUserData(Object userData) {
		this.revoluteJoint.setUserData(userData);
	}

}
