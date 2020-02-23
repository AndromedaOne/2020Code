package frc.robot.sensors.ballfeedersensor;

public class MockBallFeederSensor extends BallFeederSensorBase {

  @Override
  public boolean[] isThereBall() {
    // Returning an empty array, implementation of ball feeder sensor *must* check
    // to see if it is returned an empty array
    return new boolean[10];
  }

  @Override
  public boolean isBall(EnumBallLocation location) {
    return false;
  }

  @Override
  public int getNumberOfPowerCellsInFeeder() {
    return 0;
  }
}