/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climber;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Config4905;
import frc.robot.Robot;
import frc.robot.sensors.gyro.Gyro;
import frc.robot.subsystems.climber.ClimberBase;

public class BalanceClimber extends CommandBase {

  ClimberBase climber = Robot.getInstance().getSubsystemsContainer().getClimber();
  Gyro gyroSensor = Robot.getInstance().getSensorsContainer().getGyro();

  private double tolerance;

  /**
   * Creates a new BalanceClimber.
   */
  public BalanceClimber() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(climber);
    Config conf = Config4905.getConfig4905().getClimberConfig();
    tolerance = conf.getDouble("tolerance");
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (gyroSensor.getZAngle() > tolerance) {
      climber.driveLeftWinch();
    } else {
      climber.driveRightWinch();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return gyroSensor.getZAngle() >= -tolerance && gyroSensor.getZAngle() <= tolerance;
  }
}
