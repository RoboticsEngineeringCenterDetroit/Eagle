// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4680.Eagle.subsystems;

import org.usfirst.frc4680.Eagle.RobotMap;
import org.usfirst.frc4680.Eagle.commands.*;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;
import com.ctre.PigeonImu;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class Drive extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final CANTalon rightFront = RobotMap.driveRightFront;
    private final CANTalon rightBack = RobotMap.driveRightBack;
    private final CANTalon leftFront = RobotMap.driveLeftFront;
    private final CANTalon leftBack = RobotMap.driveLeftBack;
    private final RobotDrive robotDrive41 = RobotMap.driveRobotDrive41;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    
    PigeonImu imu;
    double kPgain = 0.04; /* percent throttle per degree of error */
	double kDgain = 0.0004; /* percent throttle per angular velocity dps */

    static final double inchesPerEncCount = (18.85 / 4096);
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Drive() {
    		imu = new PigeonImu(leftBack);
    		imu.SetFusedHeading(0.0);
    		
    		// right side encoder
    		rightFront.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
    		rightFront.setEncPosition(0);
    		//rightFront.reverseSensor(true);
    		
    		// left side encoder
    		rightBack.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
    		rightBack.setEncPosition(0);
    		//rightBack.reverseSensor(false);    		
    }

	public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new manualDrive());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    
	
    public void driver(Joystick joystick) {
    	//robotDrive41.tankDrive(joystick, 1, joystick, 5, true);
    	robotDrive41.arcadeDrive(joystick, 1, joystick, 4, true);
    }
    
    public void resetEncoderPosition()
    {
		rightFront.setEncPosition(0);
		rightBack.setEncPosition(0);
    }
    
    public double getHeading() {
		PigeonImu.FusionStatus fusionStatus = new PigeonImu.FusionStatus();    	
		return imu.GetFusedHeading(fusionStatus);
    }
    
    public double angleDelta(double src, double dest) {
    		double delta = (dest - src) % 360.0;
    		if(Math.abs(delta) > 180) {
    			delta = delta - (Math.signum(delta) * 360);
    		}
    		return delta;
    }
    
    public void driveStraight(double speed, double angle) {
    	
    	double [] xyz_dps = new double [3];
		imu.GetRawGyro(xyz_dps);
		double currentAngularRate = xyz_dps[2];
    	
		double curve = -angleDelta(getHeading(), angle) * kPgain - (currentAngularRate) * kDgain;
    	SmartDashboard.putNumber("drivestraight curve", curve);
    	
    		robotDrive41.drive(-speed, rangeLimit(curve));
    }
    
   public double getDistance(){
	   
		//FeedbackDeviceStatus sensorStatus = rightFront.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative);
		//boolean sensorPluggedIn = (FeedbackDeviceStatus.FeedbackStatusPresent == sensorStatus);
	   int countL = -rightBack.getEncPosition();
	   int countR = rightFront.getEncPosition();
	   SmartDashboard.putNumber("countL", countL);
	   SmartDashboard.putNumber("countR", countR);
	  
	   double dist =  (countL + countR)/2 * inchesPerEncCount;
	   SmartDashboard.putNumber("distance", dist);
	   SmartDashboard.putNumber("heading", getHeading());
	   return dist;
   }
   
   public void stop() {
	   robotDrive41.stopMotor();
   }
   
   public double rangeLimit(double value) {
	   return Math.max(-1.0, Math.min(1.0, value));
   }
}

