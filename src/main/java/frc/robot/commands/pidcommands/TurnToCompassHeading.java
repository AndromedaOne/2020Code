
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.pidcommands;

import com.typesafe.config.Config;

import frc.robot.Config4905;
import frc.robot.Robot;
import frc.robot.pidcontroller.*;
import frc.robot.telemetries.Trace;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TurnToCompassHeading extends PIDCommand4905 {

  private double m_compassHeading;

  /**
   * Creates a new TurnToCompassHeading.
   * 
   * @param compassHeading input an angle in degrees.
   */
  public TurnToCompassHeading(double compassHeading) {
    super(
        // The controller that the command will use
        new PIDController4905SampleStop("TurnToCompassHeading", 0, 0, 0, 0),
        // This should return the measurement
        Robot.getInstance().getSensorsContainer().getGyro()::getCompassHeading,
        // This should return the setpoint (can also be a constant)
        compassHeading,
        // This uses the output
        output -> {
          // Use the output here
          Robot.getInstance().getSubsystemsContainer().getDrivetrain().moveUsingGyro(0, output, false, false);
        });
    this.m_compassHeading = compassHeading;
    addRequirements(Robot.getInstance().getSubsystemsContainer().getDrivetrain());
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().enableContinuousInput(0, 360);

    m_setpoint = () -> m_compassHeading;

  }

  public void initialize() {
    Trace.getInstance().logCommandStart(this);
    Config pidConfig = Config4905.getConfig4905().getCommandConstantsConfig();
    super.initialize();
    getController().setP(pidConfig.getDouble("GyroPIDCommands.TurningPTerm"));
    getController().setI(pidConfig.getDouble("GyroPIDCommands.TurningITerm"));
    getController().setD(pidConfig.getDouble("GyroPIDCommands.TurningDTerm"));
    getController().setMinOutputToMove(pidConfig.getDouble("GyroPIDCommands.minOutputToMove"));
    getController().setTolerance(pidConfig.getDouble("GyroPIDCommands.positionTolerance"),
        pidConfig.getDouble("GyroPIDCommands.velocityTolerance"));
    Trace.getInstance().logCommandInfo(this, "Turning to Compass Heading: " + m_compassHeading);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }

  public void end(boolean interrupted) {
    super.end(interrupted);
    Trace.getInstance().logCommandStop(this);
    Robot.getInstance().getSubsystemsContainer().getDrivetrain().stop();
  }

  public void setCompassHeading(double compassHeading) {
    this.m_compassHeading = compassHeading;

  }
}
