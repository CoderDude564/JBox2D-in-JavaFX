package application;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

public class DistanceJointMaker {
	
	public DistanceJoint spring;
	
	public DistanceJointMaker(Body bodyA, Body bodyB, boolean collideConnected, Vec2 localAnchorA, Vec2 localAnchorB, float dampingRatio, float frequencyHz, float length, Box2DHelper world) {

		DistanceJointDef jointDef = new DistanceJointDef();
		jointDef.bodyA = bodyA;
		jointDef.bodyB = bodyB;
		jointDef.collideConnected = collideConnected;
		jointDef.localAnchorA.set(localAnchorA);
		jointDef.localAnchorB.set(localAnchorB);
		jointDef.dampingRatio = dampingRatio;
		jointDef.frequencyHz = frequencyHz;
		jointDef.length = length;
		this.spring = (DistanceJoint) world.createJoint(jointDef);
		
	}
	
	public void setUserData(Object userData) {
		this.spring.setUserData(userData);
	}

}
