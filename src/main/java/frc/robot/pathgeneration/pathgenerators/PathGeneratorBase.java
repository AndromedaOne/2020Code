package frc.robot.pathgeneration.pathgenerators;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.pathgeneration.waypoints.Waypoint;
import frc.robot.pathgeneration.waypoints.WaypointsBase;

public abstract class PathGeneratorBase {

  private WaypointsBase m_waypoints;

  public PathGeneratorBase(WaypointsBase waypoints) {
    m_waypoints = waypoints;
  }

  public CommandBase getPath() {
    iterateThroughWaypointsToGeneratePath();
    System.out.println("End of generated path\n");
    return getGeneratedPath();
  }

  private void iterateThroughWaypointsToGeneratePath() {
    for (Waypoint w : m_waypoints) {
      generatePathForNextWaypoint(w);
    }
  }

  protected abstract void generatePathForNextWaypoint(Waypoint waypoint);

  protected abstract CommandBase getGeneratedPath();

}
