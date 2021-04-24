// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.drivetrain;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Config4905;
import frc.robot.actuators.SparkController;

public class RomiDriveTrain extends RealDriveTrain {
  private final SparkController m_leftMotor;
  private final SparkController m_rightMotor;

  private final SpeedControllerGroup m_left;
  private final SpeedControllerGroup m_right;

  private final double m_ticksPerInch;

  public RomiDriveTrain() {
    Config drivetrainConfig = Config4905.getConfig4905().getDrivetrainConfig();
    m_leftMotor = new SparkController(drivetrainConfig, "left");
    m_rightMotor = new SparkController(drivetrainConfig, "right");

    m_left = new SpeedControllerGroup(m_leftMotor);
    m_right = new SpeedControllerGroup(m_rightMotor);

    double ticksPerRevolution = drivetrainConfig.getInt("ticksPerRevolution");
    double wheelDiameterInch = drivetrainConfig.getDouble("wheelDiameterInch");
    m_ticksPerInch = ticksPerRevolution / (wheelDiameterInch * Math.PI);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  protected SpeedControllerGroup getLeftSpeedControllerGroup() {
    return m_left;
  }

  @Override
  protected SpeedControllerGroup getRightSpeedControllerGroup() {
    return m_right;
  }

  @Override
  public double getRobotPositionInches() {
    double encoderPositionAvg = 0;
    int encoders = 0;
    if (m_leftMotor.hasEncoder()) {
      ++encoders;
      encoderPositionAvg += m_leftMotor.getEncoderPositionTicks();
      SmartDashboard.putNumber("left encoder", m_leftMotor.getEncoderPositionTicks());
    }
    if (m_rightMotor.hasEncoder()) {
      ++encoders;
      encoderPositionAvg += m_rightMotor.getEncoderPositionTicks();
      SmartDashboard.putNumber("right encoder", m_rightMotor.getEncoderPositionTicks());
    }
    if (encoders > 0) {
      encoderPositionAvg = encoderPositionAvg / (m_ticksPerInch * encoders);
    }
    return encoderPositionAvg;
  }

  @Override
  public double getRobotVelocityInches() {
    // TODO Auto-generated method stub
    return 0;
  }
}
