package org.usfirst.frc4680.Eagle.commands;

import org.usfirst.frc4680.Eagle.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GoBackFourInches extends Command {
	double startDistance;
	double finishDistance;
	double direction;
	
    public GoBackFourInches() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startDistance = Robot.drive.getDistance();
    	finishDistance = startDistance - 2.5;
    	direction = Robot.drive.getHeading();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.driveStraight(-0.20, direction);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       if(finishDistance >= Robot.drive.getDistance()){
    	return true;
       }else{
    	   return false;
       }
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
