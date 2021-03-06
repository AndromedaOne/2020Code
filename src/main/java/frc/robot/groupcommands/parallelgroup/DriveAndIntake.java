/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.groupcommands.parallelgroup;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.DeployAndRunIntake;
import frc.robot.commands.pidcommands.MoveUsingEncoder;
import frc.robot.subsystems.drivetrain.DriveTrain;
import frc.robot.subsystems.intake.IntakeBase;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DriveAndIntake extends ParallelCommandGroup {
  /**
   * Creates a new DriveAndIntake.
   */
  public DriveAndIntake(DriveTrain drivetrain, IntakeBase intakeBase, double distance, double maxSpeed) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());super();
    MoveUsingEncoder moveUsingEncoder = new MoveUsingEncoder(drivetrain, distance, maxSpeed);
    addCommands(moveUsingEncoder, new DeployAndRunIntake(intakeBase, () -> moveUsingEncoder.isFinished()));
  }

  public DriveAndIntake(DriveTrain drivetrain, IntakeBase intakeBase, double distance) {
    this(drivetrain, intakeBase, distance, 0);
  }
}
