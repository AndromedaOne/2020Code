package frc.robot.pathgeneration.pathgenerators;

import java.io.IOException;
import java.nio.file.Paths;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.pathgeneration.waypoints.Waypoint;
import frc.robot.pathgeneration.waypoints.WaypointsBase;
import frc.robot.pidcontroller.RamseteCommand4905;
import frc.robot.pidcontroller.TracingPIDController;

public abstract class TwoDPathGenerator extends PathGeneratorBase {
  private Trajectory trajectory;
  private double m_sVolts;
  private double m_vVoltSecondsPerMeter;
  private double m_aVoltSecondsSquaredPerMeter;
  private double m_trackwidthMeters;
  private DifferentialDriveKinematics m_driveKinematics;
  private double m_ramseteB;
  private double m_ramseteZeta;
  private double m_pDriveVel;
  private boolean m_resetOdometryToZero;
  private String m_name;

  public TwoDPathGenerator(String jsonFileName, Config config, boolean resetOdometryToZero, String name) {
    super(new WaypointsBase() {

      @Override
      protected void loadWaypoints() {
        // Do Nothing, Path is already generated within the json file.
      }

    }); // Inital Waypoint is set to 0,0 because the intial point is taken care of
        // within get generated path.
    trajectory = null;
    try {
      trajectory = TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/paths/" + jsonFileName));
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    m_sVolts = config.getDouble("pathplanningconstants.ksVolts");
    m_vVoltSecondsPerMeter = config.getDouble("pathplanningconstants.kvVoltSecondsPerMeter");
    m_aVoltSecondsSquaredPerMeter = config.getDouble("pathplanningconstants.kaVoltSecondsSquaredPerMeter");
    m_pDriveVel = config.getDouble("pathplanningconstants.kPDriveVel");
    m_trackwidthMeters = config.getDouble("pathplanningconstants.kTrackwidthMeters");
    m_driveKinematics = new DifferentialDriveKinematics(m_trackwidthMeters);
    m_ramseteB = config.getDouble("pathplanningconstants.kRamseteB");
    m_ramseteZeta = config.getDouble("pathplanningconstants.kRamseteZeta");
    m_resetOdometryToZero = resetOdometryToZero;
    m_name = name;
  }

  @Override
  protected void generatePathForNextWaypoint(Waypoint waypoint) {
    // Do Nothing, Path is already generated within the json file.

  }

  protected abstract Pose2d getPos();

  protected abstract DifferentialDriveWheelSpeeds getWheelSpeeds();

  protected abstract void tankDriveVolts(double left, double right);

  protected abstract Subsystem getSubsystem();

  protected abstract void resetOdometryToZero(Pose2d initialPosition);

  @Override
  protected CommandBase getGeneratedPath() {
    RamseteCommand4905 ramseteCommand = new RamseteCommand4905(trajectory, () -> getPos(),
        new RamseteController(m_ramseteB, m_ramseteZeta),
        new SimpleMotorFeedforward(m_sVolts, m_vVoltSecondsPerMeter, m_aVoltSecondsSquaredPerMeter), m_driveKinematics,
        () -> getWheelSpeeds(), new TracingPIDController("LeftVelocity", m_pDriveVel, 0.0, 0.0),
        new TracingPIDController("RightVelocity", m_pDriveVel, 0.0, 0.0),
        // RamseteCommand passes volts to the callback
        (left, right) -> tankDriveVolts(left, right), getSubsystem());

    ramseteCommand.setLogName(m_name);
    if (!m_resetOdometryToZero) {
      return ramseteCommand;
    }
    CommandBase resetOdometry = new ResetOdometry();
    SequentialCommandGroup resetOdometryAndThenMove = new SequentialCommandGroup(resetOdometry, ramseteCommand);
    return resetOdometryAndThenMove;
  }

  private class ResetOdometry extends CommandBase {
    @Override
    public void initialize() {
      resetOdometryToZero(trajectory.getInitialPose());
    }

    @Override
    public boolean isFinished() {
      return true;
    }
  }

}
