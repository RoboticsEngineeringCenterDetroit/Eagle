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

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static SpeedController driveLeftFrontMotor;
    public static SpeedController driveLeftBackMotor;
    public static SpeedController driveRightFrontMotor;
    public static SpeedController driveRightBackMotor;
    public static RobotDrive driveRobotDrive41;
    public static Servo shooterShooterServo;
    public static SpeedController shooterShooterMotor;
    public static SpeedController harvesterHarvesterMotor;
    public static Relay harvesterHarvesterSolenoid;
    public static Relay gearplacerGearPlacerSolenoid;
    public static SpeedController climberClimberMotor;
    public static Servo climberRopeGripperServo;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveLeftFrontMotor = new Talon(0);
        LiveWindow.addActuator("Drive", "Left Front Motor", (Talon) driveLeftFrontMotor);
        
        driveLeftBackMotor = new Talon(1);
        LiveWindow.addActuator("Drive", "Left Back Motor", (Talon) driveLeftBackMotor);
        
        driveRightFrontMotor = new Talon(2);
        LiveWindow.addActuator("Drive", " Right Front Motor", (Talon) driveRightFrontMotor);
        
        driveRightBackMotor = new Talon(3);
        LiveWindow.addActuator("Drive", " Right Back Motor", (Talon) driveRightBackMotor);
        
        driveRobotDrive41 = new RobotDrive(driveLeftFrontMotor, driveLeftBackMotor,
              driveRightFrontMotor, driveRightBackMotor);
        
        driveRobotDrive41.setSafetyEnabled(false);
        driveRobotDrive41.setExpiration(0.1);
        driveRobotDrive41.setSensitivity(0.5);
        driveRobotDrive41.setMaxOutput(1.0);

        shooterShooterServo = new Servo(4);
        LiveWindow.addActuator("Shooter", "Shooter Servo ", shooterShooterServo);
        
        shooterShooterMotor = new Talon(5);
        LiveWindow.addActuator("Shooter", "Shooter Motor", (Talon) shooterShooterMotor);
        
        harvesterHarvesterMotor = new Talon(6);
        LiveWindow.addActuator("Harvester", "HarvesterMotor", (Talon) harvesterHarvesterMotor);
        
        harvesterHarvesterSolenoid = new Relay(0);
        LiveWindow.addActuator("Harvester", "HarvesterSolenoid", harvesterHarvesterSolenoid);
        
        gearplacerGearPlacerSolenoid = new Relay(1);
        LiveWindow.addActuator("Gear placer", "GearPlacerSolenoid", gearplacerGearPlacerSolenoid);
        
        climberClimberMotor = new Talon(7);
        LiveWindow.addActuator("Climber", "ClimberMotor", (Talon) climberClimberMotor);
        
        climberRopeGripperServo = new Servo(8);
        LiveWindow.addActuator("Climber", "RopeGripperServo", climberRopeGripperServo);
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
