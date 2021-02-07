/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.groupcommands.athomechallengepathways;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.*;
import frc.robot.commands.WaitToLoad;
import frc.robot.commands.pidcommands.MoveUsingEncoder;
import frc.robot.commands.pidcommands.TurnToFaceCommand;
import frc.robot.groupcommands.parallelgroup.ShootWithDistance;
import frc.robot.groupcommands.sequentialgroup.DelayedSequentialCommandGroup;
import frc.robot.subsystems.drivetrain.DriveTrain;
import frc.robot.subsystems.feeder.FeederBase;
import frc.robot.subsystems.shooter.ShooterBase;
// NOTE: Back button ends the wait.
//1. Shoot all balls at Green distance
//2. Drive backward to Reload
//3. Wait until drive input
//4. Drive forward to yellow
//5. Shoot
//6. Drive backward to reload
//7. wait
//8. Drive to blue
//9. Shoot
//10. Back to reload
//11. wait
//12. Drive to red
//13. Shoot
//14. Drive backward to reload
//15. wait
//16. Drive to blue
//17. Shoot

public class InterstellarAccuracyChallenge extends SequentialCommandGroup {
  /**
   * Creates a new InterstellarAccuracyChallenge.
   */
  private final double m_maxOutPut = 0.5;
  private final double reloadToGreen = 220;
  private final double reloadToYellow = 160;
  private final double reloadToBlue = 100;
  private final double reloadToRed = 40;

  private final double greenZoneShootingDistance = 70;
  private final double yellowZoneShootingDistance = 130;
  private final double blueZoneShootingDistance = 190;
  private final double redZoneShootingDistance = 250;

  public InterstellarAccuracyChallenge(DriveTrain driveTrain, ShooterBase shooter, FeederBase feeder) {

    addCommands(new DelayedSequentialCommandGroup(
        // 1. start at 70 inches
        new TurnToFaceCommand(Robot.getInstance().getSensorsContainer().getLimeLight()::horizontalDegreesToTarget),
        new ShootWithDistance(shooter, feeder, greenZoneShootingDistance),
        // 2.
        new ParallelDeadlineGroup(
            new SequentialCommandGroup(new MoveUsingEncoder(driveTrain, -reloadToGreen, 0, m_maxOutPut),
                new WaitToLoad(driveTrain), new MoveUsingEncoder(driveTrain, reloadToYellow, 0, m_maxOutPut),
                new TurnToFaceCommand(
                    Robot.getInstance().getSensorsContainer().getLimeLight()::horizontalDegreesToTarget)),
            new DefaultFeederCommand()),

        new ShootWithDistance(shooter, feeder, yellowZoneShootingDistance),

        new ParallelDeadlineGroup(
            new SequentialCommandGroup(new MoveUsingEncoder(driveTrain, -reloadToYellow, 0, m_maxOutPut),
                new WaitToLoad(driveTrain), new MoveUsingEncoder(driveTrain, reloadToBlue, 0, m_maxOutPut),
                new TurnToFaceCommand(
                    Robot.getInstance().getSensorsContainer().getLimeLight()::horizontalDegreesToTarget)),
            new DefaultFeederCommand()),

        new ShootWithDistance(shooter, feeder, blueZoneShootingDistance),

        new ParallelDeadlineGroup(
            new SequentialCommandGroup(new MoveUsingEncoder(driveTrain, -reloadToBlue, 0, m_maxOutPut),
                new WaitToLoad(driveTrain), new MoveUsingEncoder(driveTrain, reloadToRed, 0, m_maxOutPut),
                new TurnToFaceCommand(
                    Robot.getInstance().getSensorsContainer().getLimeLight()::horizontalDegreesToTarget)),
            new DefaultFeederCommand()),

        new ShootWithDistance(shooter, feeder, redZoneShootingDistance),

        new ParallelDeadlineGroup(
            new SequentialCommandGroup(new MoveUsingEncoder(driveTrain, -reloadToRed, 0, m_maxOutPut),
                new WaitToLoad(driveTrain), new MoveUsingEncoder(driveTrain, reloadToBlue, 0, m_maxOutPut),
                new TurnToFaceCommand(
                    Robot.getInstance().getSensorsContainer().getLimeLight()::horizontalDegreesToTarget)),
            new DefaultFeederCommand()),

        new ShootWithDistance(shooter, feeder, blueZoneShootingDistance)));

  }
}
