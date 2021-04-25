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
import frc.robot.actuators.TalonSRXController;

public class TalonSRXDriveTrain extends RealDriveTrain {
  private final TalonSRXController m_frontLeft;
  private final TalonSRXController m_backLeft;
  private final TalonSRXController m_frontRight;
  private final TalonSRXController m_backRight;

  // motors on the Left side of the drive
  private SpeedControllerGroup m_leftmotors;

  // motors on the right side of the drive
  private SpeedControllerGroup m_rightmotors;

  private double ticksPerInch;

  public TalonSRXDriveTrain() {
    Config drivetrainConfig = Config4905.getConfig4905().getDrivetrainConfig();

    m_frontLeft = new TalonSRXController(drivetrainConfig, "frontleft");
    m_backLeft = new TalonSRXController(drivetrainConfig, "backleft");
    m_frontRight = new TalonSRXController(drivetrainConfig, "frontright");
    m_backRight = new TalonSRXController(drivetrainConfig, "backright");

    // motors on the left side of the drive
    m_leftmotors = new SpeedControllerGroup(m_frontLeft, m_backLeft);

    // motors on the right side of the drive.
    m_rightmotors = new SpeedControllerGroup(m_frontRight, m_backRight);

    ticksPerInch = drivetrainConfig.getDouble("ticksPerInch");
    System.out.println("Done configuring TalonSRXDriveTrain");
  }

  @Override
  public double getRobotPositionInches() {
    double encoderPositionAvg = 0;
    int encoders = 0;
    if (m_frontLeft.hasEncoder()) {
      encoders++;
      encoderPositionAvg += m_frontLeft.getEncoderPositionTicks();
    }
    if (m_backLeft.hasEncoder()) {
      encoders++;
      encoderPositionAvg += m_backLeft.getEncoderPositionTicks();
    }
    if (m_frontRight.hasEncoder()) {
      encoders++;
      encoderPositionAvg += m_frontRight.getEncoderPositionTicks();
    }
    if (m_backRight.hasEncoder()) {
      encoders++;
      encoderPositionAvg += m_backRight.getEncoderPositionTicks();
    }
    if (encoders > 0) {
      encoderPositionAvg = encoderPositionAvg / (ticksPerInch * encoders);
    }
    return encoderPositionAvg;
  }

  @Override
  public double getRobotVelocityInches() {
    double encoderVelocityAvg = 0;
    int encoders = 0;
    if (m_frontLeft.hasEncoder()) {
      encoders++;
      encoderVelocityAvg += m_frontLeft.getEncoderVelocityTicks();
    }
    if (m_backLeft.hasEncoder()) {
      encoders++;
      encoderVelocityAvg += m_backLeft.getEncoderVelocityTicks();
    }
    if (m_frontRight.hasEncoder()) {
      encoders++;
      encoderVelocityAvg += m_frontRight.getEncoderVelocityTicks();
    }
    if (m_backRight.hasEncoder()) {
      encoders++;
      encoderVelocityAvg += m_backRight.getEncoderVelocityTicks();
    }
    if (encoders > 0) {
      encoderVelocityAvg = encoderVelocityAvg / (ticksPerInch * encoders);
    }
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

  @Override
  protected double getLeftRateMetersPerSecond() {
    return (m_backLeft.getEncoderVelocityTicks() + m_frontLeft.getEncoderVelocityTicks()) * 0.5;
  }

  @Override
  protected double getRightRateMetersPerSecond() {
    return (m_backRight.getEncoderVelocityTicks() + m_frontRight.getEncoderVelocityTicks()) * 0.5;
  }

  @Override
  protected double getLeftSideMeters() {
    // TODO Auto-generated method stub
    double averageTicks = (m_backLeft.getEncoderPositionTicks() + m_frontLeft.getEncoderPositionTicks());
    double averageMeters = averageTicks * ticksPerInch * SparkMaxDriveTrain.metersPerInch;
    return averageMeters;
  }

  @Override
  protected double getRightsSideMeters() {
    // TODO Auto-generated method stub
    double averageTicks = (m_frontLeft.getEncoderPositionTicks() + m_frontRight.getEncoderPositionTicks());
    double averageMeters = averageTicks * ticksPerInch * SparkMaxDriveTrain.metersPerInch;
    return averageMeters;
  }

  @Override
  protected void resetEncoders() {
    throw new RuntimeException("Need to fill in the method ot reset encoders in talonsrx drivetrain");
  }
}
