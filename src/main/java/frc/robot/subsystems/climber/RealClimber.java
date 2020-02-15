/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.climber;

import com.typesafe.config.Config;

import frc.robot.Config4905;
import frc.robot.actuators.DoubleSolenoid4905;
import frc.robot.actuators.SparkMaxController;

public class RealClimber extends ClimberBase {
  /**
   * Creates a new RealClimber.
   */
  public DoubleSolenoid4905 leftGrapplingHook;
  public DoubleSolenoid4905 rightGrapplingHook;
  public SparkMaxController leftWinch;
  public SparkMaxController rightWinch;
  public DoubleSolenoid4905 leftBrake;
  public DoubleSolenoid4905 rightBrake;

  public RealClimber() {
    Config climberConf = Config4905.getConfig4905().getClimberConfig();
    leftGrapplingHook = new DoubleSolenoid4905(climberConf, "leftGrapplingHook");
    leftWinch = new SparkMaxController(climberConf, "leftWinch");
    rightGrapplingHook = new DoubleSolenoid4905(climberConf, "rightGrapplingHook");
    rightWinch = new SparkMaxController(climberConf, "rightWinch");

    // leftBrake = new DoubleSolenoid4905(climberConf, "leftBrake");
    // rightBrake = new DoubleSolenoid4905(climberConf, "rightBrake");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void driveLeftWinch(double speed) {
    // TODO Auto-generated method stub
    leftWinch.set(speed);
  }

  @Override
  public void driveRightWinch(double speed) {
    // TODO Auto-generated method stub
    rightWinch.set(speed);
  }

  @Override
  public void ascend() {
    // TODO Auto-generated method stub
    driveLeftWinch(1);
    driveRightWinch(1);
  }

  @Override
  public void stopLeftWinch() {
    // TODO Auto-generated method stub
    leftWinch.set(0);
  }

  @Override
  public void stopRightWinch() {
    // TODO Auto-generated method stub
    rightWinch.set(0);
  }

  @Override
  public void adjustWinch(double adjust) {
    // TODO Auto-generated method stub
    leftWinch.set(adjust);
    rightWinch.set(adjust);
  }

  @Override
  public void extendArms() {
    // TODO Auto-generated method stub
    leftGrapplingHook.extendPiston();
    rightGrapplingHook.extendPiston();
  }

  @Override
  public void retractArms() {
    // TODO Auto-generated method stub
    leftGrapplingHook.retractPiston();
    rightGrapplingHook.retractPiston();
  }

  @Override
  public void stopArms() {
    // TODO Auto-generated method stub
    leftGrapplingHook.stopPiston();
    rightGrapplingHook.stopPiston();
  }

}
