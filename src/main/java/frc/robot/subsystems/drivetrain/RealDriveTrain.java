/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.drivetrain;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Config4905;
import frc.robot.Robot;
import frc.robot.sensors.gyro.NavXGyroSensor;

public abstract class RealDriveTrain extends DriveTrain {
  // Gyro variables
  private NavXGyroSensor navX;
  private double savedAngle = 0;
  private double newRotateValue = 0;
  private boolean gyroCorrect = false;
  private double currentDelay = 0;
  private double kDelay = 0;
  private double kProportion = 0.0;
  // the robot's main drive
  private DifferentialDrive m_drive;

  public RealDriveTrain() {
    Config drivetrainConfig = Config4905.getConfig4905().getDrivetrainConfig();
    navX = Robot.getInstance().getSensorsContainer().getNavXGyro();
    kDelay = drivetrainConfig.getDouble("gyrocorrect.kdelay");
    kProportion = drivetrainConfig.getDouble("gyrocorrect.kproportion");
    System.out.println("kProportion = " + kProportion);
  }

  public void init() {
    m_drive = new DifferentialDrive(getLeftSpeedControllerGroup(), getRightSpeedControllerGroup());
  }

  /**
   * This moves the robot and corrects for any rotation using the gyro
   * 
   * @param useDelay The delay will delay how long the gyro will wait to correct
   *                 after turning this allows the robot to drift naturally as you
   *                 turn
   */
  public void moveUsingGyro(double forwardBackward, double rotation, boolean useDelay, boolean useSquaredInputs) {
    moveUsingGyro(forwardBackward, rotation, useDelay, useSquaredInputs, navX.getCompassHeading());
  }

  /**
   * This moves the robot and corrects for any rotation using the gyro
   * 
   * @param useDelay The delay will delay how long the gyro will wait to correct
   *                 after turning this allows the robot to drift naturally as you
   *                 turn
   * @param heading  Setting a heading will allow you to set what angle the robot
   *                 will correct to. This is useful in auto after the robot turns
   *                 you can tell it to correct to the heading it should have turn
   *                 to.
   */
  public void moveUsingGyro(double forwardBackward, double rotation, boolean useDelay, boolean useSquaredInputs,
      double heading) {

    double robotDeltaAngle = navX.getCompassHeading() - heading;
    double robotAngle = navX.getZAngle() + robotDeltaAngle;
    /*
     * If we are rotating or our delay time is lower than our set Delay do not use
     * gyro correct This allows the robot to rotate naturally after we turn
     */
    if (isRotating(rotation) || (useDelay && !delayGreaterThanThreshold(currentDelay, kDelay))
        || (forwardBackward == 0.0)) {
      gyroCorrect = false;
      savedAngle = robotAngle;
      currentDelay++;
    } else {
      gyroCorrect = true;
    }

    if (rotation != 0) {
      currentDelay = 0;
    }

    if (gyroCorrect) {
      double correctionEquation = (savedAngle - robotAngle) * kProportion;
      newRotateValue = correctionEquation;
    } else {
      newRotateValue = rotation;
    }
    move(forwardBackward, newRotateValue, useSquaredInputs);
  }

  /**
   * @param heading The heading is assumed to be an angle x such that 0 <= x < 360
   * 
   * 
   */
  public void moveUsingGyro(double forwardBackward, double rotation, double heading) {
    double zAngle = navX.getZAngle();
    double absoluteHeading = convertHeadingToAbsoluteAngle(heading, zAngle);
    double robotDeltaAngle = absoluteHeading - zAngle;
    boolean gyroCorrect = true;
    if (isRotating(rotation)) {
      gyroCorrect = false;
    }
    newRotateValue = rotation;
    if (gyroCorrect) {
      double correctionEquation = robotDeltaAngle * kProportion;
      newRotateValue = correctionEquation;
    }
    move(forwardBackward, newRotateValue, false);
  }

  private boolean isRotating(double rotation) {
    return rotation != 0;
  }

  private boolean delayGreaterThanThreshold(double delay, double threshold) {
    return delay > threshold;
  }

  private double convertHeadingToAbsoluteAngle(double heading, double zAngle) {

    double headingCenteredAt0 = heading;
    if (heading >= 180) {
      headingCenteredAt0 -= 360;
    }

    int completeRotations = (int) (zAngle / 360);
    double answer = headingCenteredAt0 + completeRotations * 360;

    if (Math.abs(zAngle - answer) > 180) {

      if (zAngle > 0) {
        answer += 360;
      } else {
        answer -= 360;
      }
    }

    return answer;
  }

  /**
   * Drives the robot using arcadeDrive
   * 
   * @param forwardBackSpeed Positive values go forward, negative goes backwards
   * @param rotateAmount     Per the right hand rule, positive goes
   *                         counter-clockwise and negative goes clockwise.
   */
  public void move(final double forwardBackSpeed, final double rotateAmount, final boolean squaredInput) {
    m_drive.arcadeDrive(forwardBackSpeed, rotateAmount, squaredInput);
    if (forwardBackSpeed < 0) {
      Robot.getInstance().getSubsystemsContainer().getLEDs("LEDStringOne").setBlinking();
      ;
    } else if (forwardBackSpeed > 0) {
      Robot.getInstance().getSubsystemsContainer().getLEDs("LEDStringOne").setSolid();
    }
  }

  protected abstract SpeedControllerGroup getLeftSpeedControllerGroup();

  protected abstract SpeedControllerGroup getRightSpeedControllerGroup();
}