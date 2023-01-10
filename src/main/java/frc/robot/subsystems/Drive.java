// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drive extends SubsystemBase {

  private DifferentialDrive gonkDrive;
  PWMSparkMax leftDrive1;
  CANSparkMax leftDrive2;
  PWMSparkMax rightDrive1;
  CANSparkMax rightDrive2;

  /* Testing for Simulation
  PWMSparkMax leftDrive1;
  PWMSparkMax leftDrive2;
  PWMSparkMax rightDrive1;
  PWMSparkMax rightDrive2;
  */

  private MotorControllerGroup leftDrive;
  private MotorControllerGroup rightDrive;

  /** Creates a new ExampleSubsystem. */
  public Drive() {
    // sparks for the drive 
    leftDrive1 = new PWMSparkMax(Constants.DriveConstants.LEFT_DRIVE_SPARKS[0]);
    leftDrive2 = new CANSparkMax(Constants.DriveConstants.LEFT_DRIVE_SPARKS[1], MotorType.kBrushless);
    rightDrive1 = new PWMSparkMax(Constants.DriveConstants.RIGHT_DRIVE_SPARKS[0]);
    rightDrive2 = new CANSparkMax(Constants.DriveConstants.RIGHT_DRIVE_SPARKS[1], MotorType.kBrushless);
    
    
    /* ONLY FOR TESTS
    leftDrive1 = new PWMSparkMax(Constants.DriveConstants.LEFT_DRIVE_SPARKS[0]);
    leftDrive2 = new PWMSparkMax(Constants.DriveConstants.LEFT_DRIVE_SPARKS[1]);
    rightDrive1 = new PWMSparkMax(Constants.DriveConstants.RIGHT_DRIVE_SPARKS[0]);
    rightDrive2 = new PWMSparkMax(Constants.DriveConstants.RIGHT_DRIVE_SPARKS[1]);
    */

    // we play stick and stone and make stickstone (combine sparks to do the same thing)
    leftDrive = new MotorControllerGroup(leftDrive1, leftDrive2);
    rightDrive = new MotorControllerGroup(rightDrive1, rightDrive2);
    
    // grug needs to make both work together (inverts some inputs)
    leftDrive.setInverted(Constants.DriveEdits.LEFT_DRIVE_REVERSE);
    rightDrive.setInverted(Constants.DriveEdits.RIGHT_DRIVE_REVERSE);

    // the driver
    gonkDrive = new DifferentialDrive(leftDrive , rightDrive);
  }

  @Override
  public void periodic() {
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  // methods
  public void zoomZoom(double leftSpeed, double rightSpeed) {
    gonkDrive.tankDrive(leftSpeed, rightSpeed);
  }

  /**
   * tank drive without input smoothing
   * @param leftSpeed
   * @param rightSpeed
   */
  public void tankDriveRaw(double leftSpeed, double rightSpeed){
    leftDrive.set(leftSpeed);
    rightDrive.set(rightSpeed);
  }
}
