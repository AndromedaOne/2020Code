/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Config4905;
import frc.robot.Robot;
import frc.robot.oi.DriveController;
import frc.robot.subsystems.drivetrain.*;

/**
 * Allows you to drive the robot using the drive controller.
 */
public class TeleOpCommand extends CommandBase {

//Make the controllers a little easier to get to.  
  private DriveController m_driveController = Robot.getInstance().getOIContainer().getDriveController();
  private DriveTrain m_driveTrain;
  private Config m_drivetrainConfig = Config4905.getConfig4905().getDrivetrainConfig();
  private boolean m_slowMode = false;
  private SlowModeStates m_slowModeState = SlowModeStates.NOTSLOWRELEASED;
  private static boolean movingForward = false;

  public static boolean getMovingForward() {
    return movingForward;
  }

  private enum SlowModeStates {
    NOTSLOWPRESSED, NOTSLOWRELEASED, SLOWPRESSED, SLOWRELEASED
  }

  /**
   * Takes inputs from the two joysticks on the drive controller.
   */
  public TeleOpCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.getInstance().getSubsystemsContainer().getDrivetrain());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drivetrainConfig = Config4905.getConfig4905().getDrivetrainConfig();
    m_driveTrain = Robot.getInstance().getSubsystemsContainer().getDrivetrain();
    movingForward = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double forwardBackwardStickValue = m_driveController.getForwardBackwardStick();
    double rotateStickValue = m_driveController.getRotateStick();

    switch (m_slowModeState) {
    case NOTSLOWRELEASED:
      if (m_driveController.getLeftBumperPressed()) {
        m_slowMode = true;
        Robot.getInstance().getSubsystemsContainer().getLEDs("LEDStringOne").setYellow(1.0);
        m_slowModeState = SlowModeStates.SLOWPRESSED;
        System.out.println("Slowmode state: " + m_slowModeState.toString() + "  SlowMode: " + m_slowMode);
      }
      break;
    case NOTSLOWPRESSED:
      if (m_driveController.getLeftBumperReleased()) {
        m_slowModeState = SlowModeStates.NOTSLOWRELEASED;
        System.out.println("Slowmode state: " + m_slowModeState.toString() + "  SlowMode: " + m_slowMode);
      }
      break;
    case SLOWRELEASED:
      if (m_driveController.getLeftBumperPressed()) {
        m_slowMode = false;
        Robot.getInstance().getSubsystemsContainer().getLEDs("LEDStringOne").setPurple(1.0);
        ;
        m_slowModeState = SlowModeStates.NOTSLOWPRESSED;
        System.out.println("Slowmode state: " + m_slowModeState.toString() + "  SlowMode: " + m_slowMode);
      }
      break;
    case SLOWPRESSED:
      if (m_driveController.getLeftBumperReleased()) {
        m_slowModeState = SlowModeStates.SLOWRELEASED;
        System.out.println("Slowmode state: " + m_slowModeState.toString() + "  SlowMode: " + m_slowMode);
      }
      break;
    default:
      System.err.println("WARN: Unknown slowmode state: " + m_slowModeState.toString());
      break;
    }

    if (m_slowMode) {
      forwardBackwardStickValue *= m_drivetrainConfig.getDouble("teleop.forwardbackslowscale");
      rotateStickValue *= m_drivetrainConfig.getDouble("teleop.rotateslowscale");
    }

    rotateStickValue *= Math.sqrt(1.0);

    movingForward = Math.abs(forwardBackwardStickValue) >= 0.1;
    m_driveTrain.moveUsingGyro(forwardBackwardStickValue, -rotateStickValue, true, true);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
