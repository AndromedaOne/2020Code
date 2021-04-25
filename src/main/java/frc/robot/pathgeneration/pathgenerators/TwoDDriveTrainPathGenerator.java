package frc.robot.pathgeneration.pathgenerators;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Config4905;
import frc.robot.subsystems.drivetrain.DriveTrain;

public class TwoDDriveTrainPathGenerator extends TwoDPathGenerator {

  DriveTrain m_driveTrain;

  public TwoDDriveTrainPathGenerator(String jsonFileName, DriveTrain driveTrain, boolean resetOdometry, String name) {
    super(jsonFileName, Config4905.getConfig4905().getDrivetrainConfig(), resetOdometry, name);
    m_driveTrain = driveTrain;
  }

  public TwoDDriveTrainPathGenerator(String jsonFileName, DriveTrain driveTrain, String name) {
    this(jsonFileName, driveTrain, true, name);
  }

  @Override
  protected Pose2d getPos() {
    return m_driveTrain.getPose();
  }

  @Override
  protected DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return m_driveTrain.getWheelSpeeds();
  }

  @Override
  protected void tankDriveVolts(double left, double right) {
    m_driveTrain.tankDriveVolts(left, right);
  }

  @Override
  protected Subsystem getSubsystem() {
    return m_driveTrain;
  }

  @Override
  protected void resetOdometryToZero(Pose2d initialPosition) {
    m_driveTrain.resetOdometry(initialPosition);
  }

}
