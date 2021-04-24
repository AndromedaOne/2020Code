package frc.robot.pathgeneration.pathgenerators;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.pathgeneration.waypoints.Waypoint;
import frc.robot.pathgeneration.waypoints.WaypointsBase;
import frc.robot.utils.AngleConversionUtils;

public abstract class DiagonalPathGenerator extends PathGeneratorBase {

  private Waypoint m_currentWaypoint;
  private SequentialCommandGroup m_path;
  private boolean m_useReverse = false;
  private double m_oldAngle;
  private double m_nextAngle;

  private enum RobotDirection {
    forward, reverse
  }

  private RobotDirection m_lastDirection = RobotDirection.forward;

  public DiagonalPathGenerator(String pathName, WaypointsBase waypoints, Waypoint initialWaypoint, boolean useReverse) {
    super(waypoints);
    System.out.println("Generating path for " + pathName);
    m_useReverse = useReverse;
    m_currentWaypoint = initialWaypoint;
    m_path = new SequentialCommandGroup();
  }

  @Override
  protected void generatePathForNextWaypoint(Waypoint waypoint) {
    System.out.print("Waypoint 1: ");
    m_currentWaypoint.prettyPrint();
    System.out.print("Waypoint 2: ");
    waypoint.prettyPrint();

    double distance = m_currentWaypoint.distance(waypoint);
    double deltaX = waypoint.getX() - m_currentWaypoint.getX();
    double deltaY = waypoint.getY() - m_currentWaypoint.getY();
    double angleInDegreesCenteredAt0 = Math.toDegrees(Math.atan(deltaX / deltaY));
    if (deltaY < 0) {
      // the atan function will only return an angle between -pi/2 and pi/2, which
      // translates
      // to a compass heading between 270 and 90 degrees through due north (0). so it
      // will
      // never return a compass heading in the direction of south (180). if the robot
      // needs to
      // move in a southerly direction, which translates to a delta y that is
      // negative, we need
      // to flip the angle.
      angleInDegreesCenteredAt0 += 180;
    }
    m_nextAngle = AngleConversionUtils.ConvertAngleToCompassHeading(angleInDegreesCenteredAt0);
    double newAngle = m_nextAngle;
    if (m_useReverse && (AngleConversionUtils.isTurnToCompassHeadingGreaterThan90(m_oldAngle, newAngle))) {
      if (m_lastDirection == RobotDirection.forward) {
        m_lastDirection = RobotDirection.reverse;
      } else {
        m_lastDirection = RobotDirection.forward;
      }
    }
    if (m_lastDirection == RobotDirection.reverse) {
      newAngle = m_nextAngle - 180;
      distance = -distance;
    }
    m_oldAngle = m_nextAngle;
    double compassAngle = AngleConversionUtils.ConvertAngleToCompassHeading(newAngle);
    if (distance != 0) {
      m_path.addCommands(createTurnCommand(compassAngle));
      m_path.addCommands(createMoveCommand(distance, compassAngle));
    }
    m_currentWaypoint = waypoint;
    System.out
        .println("Direction: " + m_lastDirection.toString() + "\tdistance: " + distance + "\tangle: " + compassAngle);
  }

  protected abstract CommandBase createTurnCommand(double angle);

  protected abstract CommandBase createMoveCommand(double distance, double angle);

  @Override
  protected CommandBase getGeneratedPath() {
    return m_path;
  }

}
