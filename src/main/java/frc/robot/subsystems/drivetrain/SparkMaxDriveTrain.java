/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.drivetrain;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.Config4905;
import frc.robot.actuators.SparkMaxController;

public class SparkMaxDriveTrain extends RealDriveTrain {
  // public static SparkMaxController

  private final SparkMaxController m_frontLeft;
  private final SparkMaxController m_backLeft;
  private final SparkMaxController m_frontRight;
  private final SparkMaxController m_backRight;

  // motors on the Left side of the drive
  private final SpeedControllerGroup m_leftmotors;

  // motors on the right side of the drive
  private final SpeedControllerGroup m_rightmotors;

  private int ticksPerInch;

  public SparkMaxDriveTrain() {
    Config drivetrainConfig = Config4905.getConfig4905().getDrivetrainConfig();

    m_frontLeft = new SparkMaxController(drivetrainConfig, "frontleft");
    m_backLeft = new SparkMaxController(drivetrainConfig, "backleft");
    m_frontRight = new SparkMaxController(drivetrainConfig, "frontright");
    m_backRight = new SparkMaxController(drivetrainConfig, "backright");

    // motors on the left side of the drive
    m_leftmotors = new SpeedControllerGroup(m_frontLeft, m_backLeft);

    // motors on the right side of the drive.
    m_rightmotors = new SpeedControllerGroup(m_frontRight, m_backRight);

    ticksPerInch = drivetrainConfig.getInt("ticksPerInch");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public double getRobotPositionInches() {
    double encoderPositionAvg = (m_frontLeft.getEncoderPositionTicks() + m_backLeft.getEncoderPositionTicks()
        + m_frontRight.getEncoderPositionTicks() + m_backRight.getEncoderPositionTicks()) / 4 * ticksPerInch;
    return encoderPositionAvg;
  }

  @Override
  public double getRobotVelocityInches() {
    double encoderVelocityAvg = (m_frontLeft.getEncoderVelocityTicks() + m_backLeft.getEncoderVelocityTicks()
        + m_frontRight.getEncoderVelocityTicks() + m_backRight.getEncoderVelocityTicks()) / 4 * ticksPerInch;
    return encoderVelocityAvg;
  }

  @Override
  protected SpeedControllerGroup getLeftSpeedControllerGroup() {
    return m_leftmotors;
  }

  @Override
  protected SpeedControllerGroup getRightSpeedControllerGroup() {
    return m_rightmotors;
  }
}