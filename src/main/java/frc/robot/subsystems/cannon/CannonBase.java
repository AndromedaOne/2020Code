// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.cannon;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class CannonBase extends SubsystemBase {
  /** Creates a new CannonBase. */
  public CannonBase() {
  }

  public abstract void pressurize();

  public abstract void shoot();

  public abstract boolean isPressurized();

  public abstract void changeElevation(double speed);

  public abstract void holdElevation();

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
