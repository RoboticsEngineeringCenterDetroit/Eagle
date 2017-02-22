package org.usfirst.frc4680.Eagle.commands;

import org.usfirst.frc4680.Eagle.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class GearPlaceCenterCrossBaseline extends CommandGroup {

	
    public GearPlaceCenterCrossBaseline() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    		addSequential(new DriveDirectionDistance(0.0, 74.0));
    		addSequential(new TimedCommand(5));
    		addSequential(new DriveDirectionDistance(0, -28.0));
    		addSequential(new DriveDirectionDistance(0, -28.0));
    		addSequential(new DriveDirectionDistance(0, 60));
    		
        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    }
}
