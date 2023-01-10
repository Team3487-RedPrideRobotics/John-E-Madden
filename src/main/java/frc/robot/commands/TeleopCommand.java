// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Outtake;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class TeleopCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drive m_drive;
  private final Outtake m_outtake;
  private final Intake m_intake;
  private int topDecide = 1;
  private int frontDecide = 1;
  private int backDecide = 1;
  private Timer reset_timer;
 

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public TeleopCommand(Drive drive, Outtake outtake, Intake intake) {
    m_drive = drive;
    m_outtake = outtake;
    m_intake = intake;

    reset_timer = new Timer();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_intake);
    addRequirements(m_drive);
    addRequirements(m_outtake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_outtake.resetAimEncoder();
    reset_timer.reset();
    reset_timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double[] sticks = new double[] {RobotContainer.getInstance().getLeftYAxis(), RobotContainer.getInstance().getRightYAxis()};
    m_drive.zoomZoom(sticks[0]*Math.sqrt(0.5), sticks[1]*Math.sqrt(0.5));
    
    if (RobotContainer.getInstance().getXButton() == 1) {
      if(RobotContainer.getInstance().getXInput().getXButtonPressed()){
        reset_timer.reset();
      }
      if(reset_timer.get() >= 2){
        m_outtake.resetAimEncoder();
      }
    }
    
    topDecide = 1;

    m_outtake.shoot(RobotContainer.getInstance().getAButton()*1*topDecide);

    frontDecide = 1;
    backDecide = 1;

    // System.out.println("Y: " + RobotContainer.getInstance().getYButton() + ", Cons: " + Constants.OuttakeEdits.INTAKE_SPEED);
    //m_intake.intake((RobotContainer.getInstance().getYButton() * intakeFrontSpeed.getDouble(Constants.IntakeEdits.INTAKE_FRONT_SPEED)));
    m_intake.intakeBall((RobotContainer.getInstance().getYButton() * Constants.IntakeEdits.INTAKE_FRONT_SPEED * frontDecide), (RobotContainer.getInstance().getYButton() * Constants.IntakeEdits.INTAKE_BACK_SPEED*backDecide));
    //if(RobotContainer.getInstance().getXInput().getXButton()){
    //  m_drive.cry();
    //} (Don't cry robot it's okay :( ))

    // aimer
    if(RobotContainer.getInstance().getXInput().getRightBumper()){
      m_outtake.setAimSpeed(0.3);
    }else if(RobotContainer.getInstance().getXInput().getLeftBumper()){
      m_outtake.setAimSpeed(-0.3);
    }else{
      m_outtake.setAimingVoltage(0);
    }



    // autoaiming and firing
    if(RobotContainer.getInstance().getXInput().getBButton()){
      m_outtake.autofire();
    }

    // shooting ball
    if(RobotContainer.getInstance().getXInput().getRightTriggerAxis() > Constants.IntakeEdits.INTAKE_DEADZONE){
      m_intake.intakeBall(Constants.IntakeEdits.INTAKE_FRONT_SPEED, Constants.IntakeEdits.INTAKE_BACK_SPEED);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}