/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Config4905;
import frc.robot.subsystems.intake.IntakeBase;
import frc.robot.telemetries.Trace;

public class RunIntakeOut extends CommandBase {
  /**
   * Creates a new RunIntake.
   */
  private IntakeBase m_intakeBase;
  private BooleanSupplier m_finishedCondition;
  private double m_outtakeSpeed;

  /**
   * Runs the intake out of the robot to out take balls at speed value set in the
   * config
   * 
   * @param intakeBase
   * @param finishedCondition
   */
  public RunIntakeOut(IntakeBase intakeBase, BooleanSupplier finishedCondition) {
    this(intakeBase, finishedCondition,
        Config4905.getConfig4905().getCommandConstantsConfig().getDouble("RunIntakeOut.outtakespeed"));
  }

  public RunIntakeOut(IntakeBase intakeBase, BooleanSupplier finishedCondition, double speed) {
    addRequirements(intakeBase);
    m_intakeBase = intakeBase;
    m_finishedCondition = finishedCondition;
    m_outtakeSpeed = speed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Trace.getInstance().logCommandStart(this);
    // m_intakeBase.deployIntake();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_intakeBase.runIntake(m_outtakeSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intakeBase.stopIntake();
    // m_intakeBase.retractIntake();
    Trace.getInstance().logCommandStop(this);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_finishedCondition.getAsBoolean();
  }
}
