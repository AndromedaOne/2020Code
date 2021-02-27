/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.drivetrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class DriveTrain extends SubsystemBase {
  /**
   * Creates a new DriveTrainBase.
   */
  public DriveTrain() {
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("RobotPositionInches", getRobotPositionInches());
    SmartDashboard.putNumber("RobotVelocityInches", getRobotVelocityInches());
  }

  public abstract void move(double forwardBackSpeed, double rotateAmount, boolean squaredInput);

  public abstract double getRobotPositionInches();

  public abstract double getRobotVelocityInches();

  public void init() {

  }

  /**
   * This moves the robot and corrects for any rotation using the gyro
   * 
   * @param useDelay The delay will delay how long the gyro will wait to correct
   *                 after turning this allows the robot to drift naturally as you
   *                 turn
   */
  public abstract void moveUsingGyroForHumanDriver(double forwardBackward, double rotation, boolean useDelay,
      boolean useSquaredInputs);

  public abstract void moveUsingGyroForAutomated(double forwardBackward, double rotation, double heading);

  public void stop() {
    move(0, 0, false);
  }

  public abstract void resetCurrentDelay();
}
