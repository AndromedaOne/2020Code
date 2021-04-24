package frc.robot.groupcommands.athomechallengepathways;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DeployAndRunIntake;
import frc.robot.pathgeneration.pathgenerators.DriveTrainDiagonalPathGenerator;
import frc.robot.pathgeneration.pathgenerators.PathGeneratorBase;
import frc.robot.pathgeneration.waypoints.Waypoint;
import frc.robot.pathgeneration.waypoints.WaypointsBase;
import frc.robot.subsystems.drivetrain.DriveTrain;
import frc.robot.subsystems.intake.IntakeBase;

public class GalacticSearchPathBAllSixBalls extends SequentialCommandGroup {

  private class GalacticSearchWaypoints extends WaypointsBase {

    @Override
    protected void loadWaypoints() {
      addWayPoint(AtHomeChallengePoints.C1);
      addWayPoint(AtHomeChallengePoints.B3);
      addWayPoint(AtHomeChallengePoints.D5);
      addWayPoint(AtHomeChallengePoints.D6);
      addWayPoint(AtHomeChallengePoints.B7);
      addWayPoint(AtHomeChallengePoints.B8);
      addWayPoint(AtHomeChallengePoints.D10);
      addWayPoint(AtHomeChallengePoints.C11);

    }

  }

  public GalacticSearchPathBAllSixBalls(DriveTrain driveTrain, IntakeBase intake) {
    Waypoint initialPoint = AtHomeChallengePoints.C1;
    double maxOutput = 0.5;
    PathGeneratorBase diagonalPathGenerator = new DriveTrainDiagonalPathGenerator(getClass().getSimpleName(),
        new GalacticSearchWaypoints(), driveTrain, initialPoint, maxOutput, false, false);
    addCommands(
        new ParallelDeadlineGroup(diagonalPathGenerator.getPath(), new DeployAndRunIntake(intake, () -> false)));
  }

}