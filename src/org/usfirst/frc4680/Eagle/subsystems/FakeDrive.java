package org.usfirst.frc4680.Eagle.subsystems;

import org.usfirst.frc4680.Eagle.RobotMap;
import org.usfirst.frc4680.Eagle.commands.manualDrive;



import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class FakeDrive extends Subsystem {

    public RobotDrive robotDrive41;
    private double heading;
    private double dist;

    public FakeDrive() {
    		robotDrive41 = new RobotDrive(9, 10);
    		heading = 0.0;
    		dist = 0.0;
    }

	public void initDefaultCommand() {

        setDefaultCommand(new manualDrive());

    }
    
	
    public void driver(Joystick joystick) {
	    	//robotDrive41.tankDrive(joystick, 1, joystick, 5, true);
	    	robotDrive41.arcadeDrive(joystick, 1, joystick, 4, true);
    }
    
    public void resetEncoderPosition()
    {
    		dist = 0;
    }
    
    public double getHeading() {
		return heading;
    }
    
    public double angleDelta(double src, double dest) {
    		double delta = (dest - src) % 360.0;
    		if(Math.abs(delta) > 180) {
    			delta = delta - (Math.signum(delta) * 360);
    		}
    		return delta;
    }
    

    public boolean turnTo(double destAngle) {
	    	double tolerance = 2.0;
	    	double currentAngle = getHeading();
	    	//System.out.println("destAngle " + destAngle + " currentAngle " + currentAngle);
	    	
	    	if(destAngle - currentAngle > tolerance){
	    		//System.out.println("turn left");
	    		robotDrive41.tankDrive(0.6, -0.6);
	    		heading += 1.0;
	    		return false;
	    	}else if(destAngle - currentAngle < -tolerance) {
	    		//System.out.println("turn right");
	    		robotDrive41.tankDrive(-0.6,  0.6);
	    		heading -= 1.0;
	    		return false;
	    	}else {
	    		robotDrive41.tankDrive(0,  0);
	    		return true;
	    	}
    }
    
    public void driveStraight(double speed, double angle) {  	
    	
		double curve = -angleDelta(getHeading(), angle) / 90.0;
    	    //SmartDashboard.putNumber("drivestraight curve", curve);
    	    robotDrive41.drive(-speed, rangeLimit(curve));
    	    dist += speed/2;
    }
       
    public double getDistance(){
	   return dist;
   }
   
   public void stop() {
	   robotDrive41.stopMotor();
   }
   
   public double rangeLimit(double value) {
	   return Math.max(-1.0, Math.min(1.0, value));
   }
}

