/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.oi;

import frc.robot.commands.DeployAndRunIntakeStarter;
import frc.robot.commands.FeedBothStagesIntoShooter;
import frc.robot.commands.ReverseFeederAndShooter;
import frc.robot.commands.RunIntakeOut;
import frc.robot.sensors.SensorsContainer;
import frc.robot.subsystems.SubsystemsContainer;

/**
 * The container that controls all of the classes in package OI.
 */
public class OIContainer {
  private DriveController m_driveController;
  private SmartDashboard4905 m_smartDashboard;
  private SubsystemController m_subsystemController;

  public OIContainer(SubsystemsContainer subsystemsContainer, SensorsContainer sensorsContainer) {
    m_driveController = new DriveController(subsystemsContainer, sensorsContainer);
    m_smartDashboard = new SmartDashboard4905(subsystemsContainer, sensorsContainer);
    m_subsystemController = new SubsystemController();

    m_subsystemController.getDeployAndRunIntakeButton().whenPressed(new DeployAndRunIntakeStarter(
        subsystemsContainer.getIntake(), () -> !m_subsystemController.getDeployAndRunIntakeButton().get()));
    m_subsystemController.getFeedWhenReadyButton()
        .whenPressed(new FeedBothStagesIntoShooter(subsystemsContainer.getFeeder(), subsystemsContainer.getShooter(),
            () -> !m_subsystemController.getFeedWhenReadyButton().get()));
    m_subsystemController.getRunIntakeOutButton().whenPressed(
        new RunIntakeOut(subsystemsContainer.getIntake(), () -> !m_subsystemController.getRunIntakeOutButton().get()));
    m_subsystemController.getReverseFeederButton()
        .whenPressed(new ReverseFeederAndShooter(subsystemsContainer.getFeeder(), subsystemsContainer.getShooter(),
            () -> !m_subsystemController.getReverseFeederButton().get()));
  }

  public DriveController getDriveController() {
    return m_driveController;
  }

  public SmartDashboard4905 getSmartDashboard() {
    return m_smartDashboard;
  }

  public SubsystemController getSubsystemController() {
    return m_subsystemController;
  }
}