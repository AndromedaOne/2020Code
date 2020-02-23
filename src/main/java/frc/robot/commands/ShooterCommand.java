/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.shooter.ShooterBase;

public class ShooterCommand extends CommandBase {
  private int m_cellsToShoot;
  private int m_shotsRemaining;

  /**
   * Creates a new ShooterCommand.
   */
  public ShooterCommand(ShooterBase shooter, int numberOfCells) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_cellsToShoot = numberOfCells;
    m_shotsRemaining = 0;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shotsRemaining = m_cellsToShoot;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("Shoot");
    m_shotsRemaining = m_shotsRemaining - 1;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_shotsRemaining == 0) {
      return true;
    } else {
      return false;
    }
  }
}
