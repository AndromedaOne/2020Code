package frc.robot.sensors.gyro;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public abstract class NavXGyroSensor implements Gyro {

  /**
   * Gets the Z angle and subtracts the initial angle member variable from it.
   * 
   * @return gyro.getAngle() - initialAngleReading
   */
  public abstract double getZAngle();

  public abstract double getXAngle();

  public abstract double getYAngle();

  /**
   * Returns the current compass heading of the robot Between 0 - 360
   */
  public abstract double getCompassHeading();

  public abstract void updateSmartDashboardReadings();

  public Rotation2d getRotation2d() {
    double zAngle = -getZAngle();
    return Rotation2d.fromDegrees(zAngle);
  }
}
