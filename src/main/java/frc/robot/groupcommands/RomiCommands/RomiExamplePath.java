// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.groupcommands.RomiCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.pidcommands.TurnToCompassHeading;
import frc.robot.pathgeneration.pathgenerators.DriveTrainDiagonalPathGenerator;
import frc.robot.pathgeneration.pathgenerators.PathGeneratorBase;
import frc.robot.pathgeneration.waypoints.Waypoint;
import frc.robot.pathgeneration.waypoints.WaypointsBase;
import frc.robot.subsystems.drivetrain.DriveTrain;

public class RomiExamplePath extends SequentialCommandGroup {

  private class ExampleWayPoints extends WaypointsBase {

    @Override
    protected void loadWaypoints() {
      addWayPoint(new Waypoint(0, 0));
      addWayPoint(new Waypoint(0, 28));
      addWayPoint(new Waypoint(28, 28));
      addWayPoint(new Waypoint(28, 0));
      addWayPoint(new Waypoint(0, 0));
    }

  }

  /** Creates a new RomiExamplePath. */
  public RomiExamplePath(DriveTrain driveTrain) {
    PathGeneratorBase path = new DriveTrainDiagonalPathGenerator(getClass().getSimpleName(), new ExampleWayPoints(),
        driveTrain, new Waypoint(0, 0), 0.5, true, true);
    addCommands(path.getPath(), new TurnToCompassHeading(0));
  }
}
