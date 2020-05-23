package application;

import java.lang.reflect.Method;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

public class Box2DContactListener implements ContactListener {
	
	Class<?> parent;
	
	Method beginMethod;
	Method endMethod;
	Method postMethod;
	Method preMethod;
	
	Box2DContactListener(Class<?> parent){
		
		this.parent = parent;
		
		try {
			beginMethod = parent.getMethod("beginContact", new Class[] { Contact.class });
		}
		
		catch (Exception e) {
            System.out.println("You are missing the beginContact() method. " + e);
        }

		
		try {
			endMethod = parent.getMethod("endContact", new Class[] { Contact.class });
        } 
		
		catch (Exception e) {
            System.out.println("You are missing the endContact() method. " + e);
        }
		
		
		try {
			postMethod = parent.getMethod("postSolve", new Class[] { Contact.class, ContactImpulse.class });
        } 
		
		catch (Exception e) {
            System.out.println("You are missing the postSolve() method. " + e);
        }
		
		
		try {
			preMethod = parent.getMethod("preSolve", new Class[] { Contact.class, Manifold.class });
        }
		
		catch (Exception e) {
            System.out.println("You are missing the preSolve() method. " + e);
        }
		
	}

	@SuppressWarnings("deprecation")
	public void beginContact(Contact contact) {
		
        if (beginMethod != null) {
        	
            try {
            	beginMethod.invoke(parent.newInstance(), new Object[] { contact });
            } 
            
            catch (Exception ex) {
            	
                System.out.println("Could not invoke the \"beginContact()\" method for some reason.");
                ex.printStackTrace();
                beginMethod = null;
                
            }
            
        }
		
	}

	@SuppressWarnings("deprecation")
	public void endContact(Contact contact) {
		
        if (endMethod != null) {
        	
            try {
            	endMethod.invoke(parent.newInstance(), new Object[] { contact });
            } 
            
            catch (Exception ex) {
            	
                System.out.println("Could not invoke the \"removeContact()\" method for some reason.");
                ex.printStackTrace();
                endMethod = null;
                
            }
            
        }
		
	}

	@SuppressWarnings("deprecation")
	public void postSolve(Contact contact, ContactImpulse contactImpulse) {
		
        if (postMethod != null) {
        	
            try {
            	postMethod.invoke(parent.newInstance(), new Object[] {contact, contactImpulse});
            } 
            
            catch (Exception ex) {
            	
                System.out.println("Could not invoke the \"postSolve()\" method for some reason.");
                ex.printStackTrace();
                postMethod = null;
                
            }
            
        }
        
	}
	

	@SuppressWarnings("deprecation")
	public void preSolve(Contact contact, Manifold manifold) {
		
        if (preMethod != null) {
        	
            try {
            	preMethod.invoke(parent.newInstance(), new Object[] {contact, manifold});
            } 
            
            catch (Exception ex) {
            	
                System.out.println("Could not invoke the \"preSolve()\" method for some reason.");
                ex.printStackTrace();
                preMethod = null;
                
            }
            
        }
        
	}

}
