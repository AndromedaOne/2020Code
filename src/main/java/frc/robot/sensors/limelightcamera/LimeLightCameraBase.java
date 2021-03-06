package frc.robot.sensors.limelightcamera;

public abstract class LimeLightCameraBase {
  public abstract double horizontalDegreesToTarget();

  public abstract double verticalRadiansToTarget();

  public abstract double distanceToTarget(double targetHeight);

  public abstract double distanceToPowerPort();

  public abstract void setPipeline(int pipelineNumber);

  public abstract void updateSmartDashboardReadings();

  public abstract boolean targetLock();

  public abstract void enableLED();

  public abstract void disableLED();
}