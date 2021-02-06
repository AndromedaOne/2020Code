/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.groupcommands.athomechallengepathways;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.DeployAndRunIntake;
import frc.robot.commands.pidcommands.MoveUsingEncoder;
import frc.robot.commands.pidcommands.TurnToFaceCommand;
import frc.robot.groupcommands.parallelgroup.ShootWithDistance;
import frc.robot.groupcommands.sequentialgroup.DelayedSequentialCommandGroup;
import frc.robot.subsystems.drivetrain.DriveTrain;
import frc.robot.subsystems.feeder.FeederBase;
import frc.robot.subsystems.intake.IntakeBase;
import frc.robot.subsystems.shooter.ShooterBase;

public class PowerPortShoot extends SequentialCommandGroup {
  /**
   * Creates a new PowerPortShoot.
   */
  private final double m_maxOutPut = 0.5;
  private final double greenZoneShootingDistance = 180;
  private final double reIntroductionZoneDistance = 300;
  private final double reloadToGreen = reIntroductionZoneDistance - greenZoneShootingDistance;

  public PowerPortShoot(DriveTrain driveTrain, ShooterBase shooter, FeederBase feeder, IntakeBase intake) {
    addCommands(new DelayedSequentialCommandGroup(
        new TurnToFaceCommand(Robot.getInstance().getSensorsContainer().getLimeLight()::horizontalDegreesToTarget),
        new ShootWithDistance(shooter, feeder, greenZoneShootingDistance),
        new ParallelCommandGroup(new MoveUsingEncoder(driveTrain, -reloadToGreen, 0, m_maxOutPut),
            new DeployAndRunIntake(intake, () -> false))));
  }

  public void initialize() {
    super.initialize();
    System.out.println("Power Port Shoot Initialize");
  }

  public void end(boolean interrupted) {
    super.end(interrupted);
    System.out.println("Power Port Shoot End " + interrupted);
  }
}
