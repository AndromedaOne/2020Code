/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.feeder.FeederBase;
import frc.robot.telemetries.Trace;

public class FeedBothStagesIntoShooter extends CommandBase {
  /**
   * Creates a new FeedWhenReady.
   */
  FeederBase m_feederBase;
  BooleanSupplier m_endCondition;
  BooleanSupplier m_shooterIsReady;

  public FeedBothStagesIntoShooter(FeederBase feederBase, BooleanSupplier endCondition, BooleanSupplier shooterIsReady) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_feederBase = feederBase;
    addRequirements(m_feederBase);
    m_endCondition = endCondition;
    m_shooterIsReady = shooterIsReady;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    super.initialize();
    Trace.getInstance().logCommandStart("FeedBothStagesIntoShooter");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_shooterIsReady.getAsBoolean()){
      m_feederBase.driveBothStages();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    m_feederBase.stopBothStages();
    Trace.getInstance().logCommandStop("FeedBothStagesIntoShooter");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_endCondition.getAsBoolean();
  }
}
