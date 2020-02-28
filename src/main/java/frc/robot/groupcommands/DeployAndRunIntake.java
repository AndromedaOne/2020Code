/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.groupcommands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ExtendIntake;
import frc.robot.commands.RunIntakeIn;
import frc.robot.subsystems.intake.IntakeBase;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DeployAndRunIntake extends SequentialCommandGroup {
  /**
   * Creates a new DeployAndRunIntake.
   */
  private IntakeBase m_intakeBase;
  BooleanSupplier m_finishedCondition;

  public DeployAndRunIntake(IntakeBase intakeBase, BooleanSupplier finishedCondition) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super();
    m_intakeBase = intakeBase;
    m_finishedCondition = finishedCondition;
    addCommands(new ExtendIntake(m_intakeBase), new RunIntakeIn(m_intakeBase, finishedCondition));

  }

  @Override
  public void initialize() {
    super.initialize();
  }

  @Override
  public boolean isFinished() {
    return m_finishedCondition.getAsBoolean();
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
  }
}
