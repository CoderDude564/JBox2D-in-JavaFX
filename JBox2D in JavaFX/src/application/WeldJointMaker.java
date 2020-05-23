package application;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.joints.WeldJoint;
import org.jbox2d.dynamics.joints.WeldJointDef;

public class WeldJointMaker {
	
	public WeldJoint weld;
	
	public WeldJointMaker(Body bodyA, Body bodyB, boolean collideConnected, Vec2 localAnchorA, Vec2 localAnchorB, float referenceAngle, Box2DHelper world) {

		WeldJointDef jointDef = new WeldJointDef();
		jointDef.bodyA = bodyA;
		jointDef.bodyB = bodyB;
		jointDef.collideConnected = collideConnected;
		jointDef.localAnchorA.set(localAnchorA);
		jointDef.localAnchorB.set(localAnchorB);
		jointDef.referenceAngle = referenceAngle;
		jointDef.dampingRatio = 1;
		jointDef.frequencyHz = 0;
		this.weld = (WeldJoint) world.createJoint(jointDef);
		
	}
	
	public void setUserData(Object userData) {
		this.weld.setUserData(userData);
	}

}
