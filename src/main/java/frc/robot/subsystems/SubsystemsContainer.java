/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Config4905;
import frc.robot.subsystems.climber.ClimberBase;
import frc.robot.subsystems.climber.MockClimber;
import frc.robot.subsystems.climber.RealClimber;
import frc.robot.subsystems.drivetrain.DriveTrain;
import frc.robot.subsystems.drivetrain.MockDriveTrain;
import frc.robot.subsystems.drivetrain.RealDriveTrain;
import frc.robot.subsystems.feeder.FeederBase;
import frc.robot.subsystems.feeder.MockFeeder;
import frc.robot.subsystems.feeder.RealFeeder;
import frc.robot.subsystems.intake.IntakeBase;
import frc.robot.subsystems.intake.MockIntake;
import frc.robot.subsystems.intake.RealIntake;
import frc.robot.subsystems.shooter.MockShooter;
import frc.robot.subsystems.shooter.RealShooter;
import frc.robot.subsystems.shooter.ShooterBase;

/**
 * Add your docs here.
 */
public class SubsystemsContainer {

  // Declare member variables.
  ClimberBase m_climber;
  DriveTrain m_driveTrain;
  FeederBase m_feeder;
  IntakeBase m_intake;
  ShooterBase m_shooter;

  /**
   * The container responsible for setting all the subsystems to real or mock.
   * Uses config settings to determine this.
   * 
   */
  public SubsystemsContainer() {
    /*
     * Sets the member variables to use either a real or mock subsystem, so we can
     * use a robot that has them or is only a mule.
     *
     * The settings will be printed to the console.
     *
     * The order is the same as the package tree and is as follows: 1. Climber 2.
     * Drive Train 3. Feeder 4. Intake 5. Shooter
     */
    // 1. Drivetrain
    if (Config4905.getConfig4905().doesDrivetrainExist()) {
      System.out.println("Using real Drive Train.");
      m_driveTrain = new RealDriveTrain();
    } else {
      System.out.println("Using mock Drive Train.");
      m_driveTrain = new MockDriveTrain();
    }

    // 2. Climber
    if (Config4905.getConfig4905().doesClimberExist()) {
      System.out.println("Using real Climber.");
      m_climber = new RealClimber();
    } else {
      System.out.println("Using mock Climber.");
      m_climber = new MockClimber();
    }

    // 3. Feeder
    if (Config4905.getConfig4905().doesFeederExist()) {
      System.out.println("Using real Feeder.");
      m_feeder = new RealFeeder();
    } else {
      System.out.println("Using mock Feeder.");
      m_feeder = new MockFeeder();
    }

    // 4. Intake
    if (Config4905.getConfig4905().doesIntakeExist()) {
      System.out.println("Using real Intake.");
      m_intake = new RealIntake();
    } else {
      System.out.println("Using mock Intake.");
      m_intake = new MockIntake();
    }

    // 5. Shooter
    if (Config4905.getConfig4905().doesShooterExist()) {
      System.out.println("Using real Shooter.");
      m_shooter = new RealShooter();
    } else {
      System.out.println("Using mock Shooter.");
      m_shooter = new MockShooter();
    }

  }

  public DriveTrain getDrivetrain() {
    return m_driveTrain;
  }

}