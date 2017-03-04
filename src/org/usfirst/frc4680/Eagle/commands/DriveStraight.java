package org.usfirst.frc4680.Eagle.commands;

import org.usfirst.frc4680.Eagle.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveStraight extends Command {

	double speed;
	double direction;
	
	
    public DriveStraight() {
        super();
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drive);
    }

    // Called once when the command executes
    protected void initialize() {
    	direction = Robot.drive.getHeading();
    	//SmartDashboard.putNumber("drivestraight direction", direction);
    }
    
    protected void execute() {
    	speed = Robot.oi.driveJoystick.getRawAxis(1);
    	Robot.drive.driveStraight(-speed, direction);
    }

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
