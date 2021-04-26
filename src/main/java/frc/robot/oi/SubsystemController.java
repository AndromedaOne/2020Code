/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.oi;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Config4905;
import frc.robot.Robot;
import frc.robot.lib.ButtonsEnumerated;
import frc.robot.sensors.limelightcamera.LimeLightCameraBase;
import frc.robot.subsystems.feeder.FeederBase;
import frc.robot.subsystems.shooter.ShooterBase;

/**
 * Add your docs here.
 */
public class SubsystemController {
  private XboxController m_subsystemController = new XboxController(1);
  private Config m_shooterConfig;
  private ShooterBase m_shooterSubsystem;
  private FeederBase m_feederSubsystem;
  private JoystickButton m_shootFromInitLine;
  private JoystickButton m_shootFromFrontTrench;
  private JoystickButton m_shootFromBackTrench;
  private JoystickButton m_shootWithLimeDistance;
  private JoystickButton m_runIntakeOut;
  private LimeLightCameraBase m_limeLight;

  public SubsystemController() {
    m_shooterConfig = Config4905.getConfig4905().getShooterConfig();
    m_shooterSubsystem = Robot.getInstance().getSubsystemsContainer().getShooter();
    m_feederSubsystem = Robot.getInstance().getSubsystemsContainer().getFeeder();
    m_limeLight = Robot.getInstance().getSensorsContainer().getLimeLight();

    /*
     * m_shootFromBackTrench = new JoystickButton(m_subsystemController,
     * ButtonsEnumerated.ABUTTON.getValue()); m_shootFromBackTrench.whenPressed(new
     * ShootWithRPM(m_shooterSubsystem, m_feederSubsystem,
     * Config4905.getConfig4905().getCommandConstantsConfig().getDouble(
     * "ShootingRPM.backtrench"),
     * Config4905.getConfig4905().getCommandConstantsConfig().getDouble(
     * "ShootingRPM.backtrench") * 1.5)); m_shootFromInitLine = new
     * JoystickButton(m_subsystemController, ButtonsEnumerated.XBUTTON.getValue());
     * m_shootFromInitLine.whenPressed(new ShootWithRPM(m_shooterSubsystem,
     * m_feederSubsystem,
     * Config4905.getConfig4905().getCommandConstantsConfig().getDouble(
     * "ShootingRPM.initline"),
     * Config4905.getConfig4905().getCommandConstantsConfig().getDouble(
     * "ShootingRPM.initline") * 1.5));
     */
    /*
     * m_shootFromFrontTrench = new JoystickButton(m_subsystemController,
     * ButtonsEnumerated.BBUTTON.getValue()); m_shootFromFrontTrench.whenPressed(new
     * ShootWithRPM(m_shooterSubsystem, m_feederSubsystem,
     * Config4905.getConfig4905().getCommandConstantsConfig().getDouble(
     * "ShootingRPM.fronttrench"),
     * Config4905.getConfig4905().getCommandConstantsConfig().getDouble(
     * "ShootingRPM.fronttrench") * 1.5)); m_shootFromBackTrench = new
     * JoystickButton(m_subsystemController, ButtonsEnumerated.ABUTTON.getValue());
     * m_shootFromBackTrench.whenPressed(new ShootWithRPM(m_shooterSubsystem,
     * m_feederSubsystem,
     * Config4905.getConfig4905().getCommandConstantsConfig().getDouble(
     * "ShootingRPM.backtrench"),
     * Config4905.getConfig4905().getCommandConstantsConfig().getDouble(
     * "ShootingRPM.backtrench") * 1.5)); m_shootWithLimeDistance = new
     * JoystickButton(m_subsystemController, ButtonsEnumerated.YBUTTON.getValue());
     * m_shootWithLimeDistance.whenPressed(new
     * ShootWithLimeLight(m_shooterSubsystem, m_feederSubsystem, m_limeLight));
     */

    m_runIntakeOut = new JoystickButton(m_subsystemController, ButtonsEnumerated.BACKBUTTON.getValue());

  }

  public JoystickButton getDeployAndRunIntakeButton() {
    return ButtonsEnumerated.LEFTBUMPERBUTTON.getJoystickButton(m_subsystemController);
  }

  public JoystickButton getRunIntakeOutButton() {
    return m_runIntakeOut;
  }

  public JoystickButton getFeedWhenReadyButton() {
    return ButtonsEnumerated.RIGHTBUMPERBUTTON.getJoystickButton(m_subsystemController);
  }

  public JoystickButton getReverseFeederButton() {
    return ButtonsEnumerated.STARTBUTTON.getJoystickButton(m_subsystemController);
  }

  public double getLeftStickForwardBackwardValue() {
    return deadband(-m_subsystemController.getY(GenericHID.Hand.kLeft));
  }

  public double getRightStickForwardBackwardValue() {
    return deadband(-m_subsystemController.getY(GenericHID.Hand.kRight));
  }

  public double getLeftTriggerValue() {
    return deadband(m_subsystemController.getTriggerAxis(Hand.kLeft));
  }

  public double getRightTriggerValue() {
    return deadband(m_subsystemController.getTriggerAxis(Hand.kRight));
  }

  public double getRightStickLeftRightValue() {
    return deadband(-m_subsystemController.getX(GenericHID.Hand.kRight));
  }

  public JoystickButton getResetShooterManualAdjustmentButton() {
    return ButtonsEnumerated.LEFTSTICKBUTTON.getJoystickButton(m_subsystemController);
  }

  private double deadband(double stickValue) {
    if (Math.abs(stickValue) < 0.1) {
      return 0.0;
    } else {
      return stickValue;
    }
  }
}
