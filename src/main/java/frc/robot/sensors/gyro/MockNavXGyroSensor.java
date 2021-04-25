package frc.robot.sensors.gyro;

public class MockNavXGyroSensor extends NavXGyroSensor {

  @Override
  public double getZAngle() {
    return 0;
  }

  @Override
  public double getXAngle() {
    return 0;
  }

  @Override
  public double getYAngle() {
    return 0;
  }

  @Override
  public double getCompassHeading() {
    return 0;
  }

  @Override
  public void updateSmartDashboardReadings() {
  }

  @Override
  public void calibrate() {
    // TODO Auto-generated method stub

  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub

  }

  @Override
  public double getAngle() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double getRate() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void close() throws Exception {
    // TODO Auto-generated method stub

  }

}