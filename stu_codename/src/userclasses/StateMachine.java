/**
 * Your application code goes here
 */

package userclasses;

import generated.StateMachineBase;
import com.codename1.ui.*; 
import com.codename1.ui.events.*;
import com.codename1.ui.util.Resources;

/**
 *
 * @author Your name here
 */
public class StateMachine extends StateMachineBase {
    public StateMachine(String resFile) {
        super(resFile);
        // do not modify, write code in initVars and initialize class members there,
        // the constructor might be invoked too late due to race conditions that might occur
    }
    
    /**
     * this method should be used to initialize variables instead of
     * the constructor/class scope to avoid race conditions
     */
	protected void initVars(Resources res){
	}

    @Override
    protected void onCreateMain() {
    	super.onCreateMain();
    }

    protected void onMain_ButtonAction(Component c, ActionEvent event) {
    	super.onMain_ButtonAction(c, event);
    	Dialog.show("ÌâÄ¿", "ÄÚÈÝ", "OK", "CANAL");
    
    }

    @Override
    protected void beforeMain(Form f) {
    
    }

 
  

    @Override
    protected void beforeGUI1(Form f) {
    	
    }

    @Override
    protected void onCreateGUI1() {
    
    }

    @Override
    protected void postMain(Form f) {
    
    }

    @Override
    protected void exitMain(Form f) {
    
    }
}
