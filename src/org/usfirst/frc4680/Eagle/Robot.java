// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4680.Eagle;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import org.usfirst.frc4680.Eagle.commands.*;
import org.usfirst.frc4680.Eagle.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    Command autonomousCommand;
    SendableChooser<Command> autoChooser;
    
    final static String logFileName = "/home/lvuser/logfile.csv";
    
    class LogEntryStruct {
    		public double timestamp;
    		public double heading;
    		public double encoderCountLeft;
    		public double encoderCountRight;
    		public double voltageLeft;
    		public double voltageRight;
    		
    		public String toString() {
    			return String.format("%f, %f, %d, %d, %f, %f",
    					           timestamp, heading,
    					           encoderCountLeft, encoderCountRight,
    					           voltageLeft, voltageRight);
    		}
    		
    		public static final String header = "timestamp, heading, encoderCountLeft, encoderCountRight, voltageLeft, voltageRight";
    }
    
    private LogEntryStruct logEntry;
    private ArrayList<LogEntryStruct> datalog;

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static Drive drive;
    public static Shooter shooter;
    public static Harvester harvester;
    public static Gearplacer gearplacer;
    public static Climber climber;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    RobotMap.init();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        drive = new Drive();
        shooter = new Shooter();
        harvester = new Harvester();
        gearplacer = new Gearplacer();
        climber = new Climber();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // instantiate the command used for the autonomous period
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        
        //autoChooser = new SendableChooser<Command>();
        //autoChooser.addDefault("GearPlaceCenterCrossBaseline", new GearPlaceCenterCrossBaseline());
        //autoChooser.addDefault("GearPlaceCenter", new GearPlaceCenter());
        //autoChooser.addDefault("GearPlaceRight", new GearPlaceRight());
        //autoChooser.addDefault("GearPlaceLeft", new GearPlaceLeft());
        //autoChooser.addObject("Drive Fifty Inches", new DriveDirectionDistance(0.0, 50.0));
        //SmartDashboard.putData("Autonomous mode chooser", autoChooser);

        //CameraServer.getInstance().startAutomaticCapture();
    }
   
    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    		if(!datalog.isEmpty()) {
    			System.out.println("Writing " + datalog.size() + " records to log file");
    			try { 
    				PrintStream file = new PrintStream(logFileName); 
    				file.println(LogEntryStruct.header);
    			    for(LogEntryStruct entry : datalog){
        				file.println(entry);
        			} 
    			    file.flush(); 
    			    file.close(); 
    			    System.out.println("Wrote log file: " + logFileName); 
    			} catch (IOException e) { 
    			    System.out.println("error: " + e.getMessage()); 
    			}
    			datalog.clear();
    		}
    }

    public void disabledPeriodic() {
    	drive.resetEncoderPosition();
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
    	
    	//autonomousCommand = new GearPlaceCenter(); 
    	//autonomousCommand = new GearPlaceLeft(); 
    	autonomousCommand = new GearPlaceRight(); 

    	
    	//autonomousCommand = (Command) autoChooser.getSelected();
    	System.out.println("autonomousCommand = " + autonomousCommand);
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        logEntry = new LogEntryStruct();
        logEntry.timestamp = Timer.getFPGATimestamp();
        Scheduler.getInstance().run();
        logEntry.heading = drive.getHeading();
        logEntry.encoderCountLeft = RobotMap.driveLeftFront.getEncPosition();
        logEntry.encoderCountRight = RobotMap.driveRightFront.getEncPosition();
        logEntry.voltageLeft = RobotMap.driveLeftFront.getOutputVoltage();
        logEntry.voltageRight = RobotMap.driveRightFront.getOutputVoltage();
        datalog.add(logEntry);
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
