package org.usfirst.frc4680.Eagle.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class FuelUnloader extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private final SpeedController unloadMotor;
	
	public FuelUnloader() {
		unloadMotor = new Spark(2);
	}
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    	
    public void unload(double speed){
    		unloadMotor.set(speed);
    }
    
}

