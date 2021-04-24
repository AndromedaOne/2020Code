package frc.robot.sensors.gyro;

public class MockGyro extends Gyro {

  @Override
  protected double getRawZAngle() {
    return 0;
  }

  @Override
  protected double getRawXAngle() {
    return 0;
  }

  @Override
  protected double getRawYAngle() {
    return 0;
  }

}