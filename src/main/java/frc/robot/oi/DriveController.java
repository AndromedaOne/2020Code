/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.oi;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.DemoShoot;
import frc.robot.lib.ButtonsEnumerated;
import frc.robot.sensors.SensorsContainer;
import frc.robot.subsystems.SubsystemsContainer;

/**
 * Add your docs here.
 */
public class DriveController {
  private XboxController m_driveController = new XboxController(0);
  private JoystickButton turnToNorth;
  private JoystickButton turnToEast;
  private JoystickButton turnToSouth;
  private JoystickButton turnToWest;
  private JoystickButton turnToFace;
  private JoystickButton demoShoot;
  private JoystickButton letOutLeftWinch;
  private JoystickButton letOutRightWinch;
  private POVButton climbLevel;
  private JoystickButton turnOnLimelight;
  private JoystickButton turnOffLimelight;
  private JoystickButton interstellerAccuracyWait;
  private JoystickButton powerPortButton;

  public DriveController(SubsystemsContainer subsystemsContainer, SensorsContainer sensorsContainer) {
    demoShoot = new JoystickButton(m_driveController, ButtonsEnumerated.ABUTTON.getValue());
    demoShoot.whileHeld(new DemoShoot(subsystemsContainer.getFeeder(), subsystemsContainer.getShooter()));

    // climbLevel = new POVButton(m_driveController,
    // POVDirectionNames.NORTH.getValue());
    // climbLevel.whileHeld(new Climb());
    // turnOnLimelight = new JoystickButton(m_driveController,
    // ButtonsEnumerated.BACKBUTTON.getValue());
    // turnOnLimelight.whenPressed(new ToggleLimelightLED(true, sensorsContainer));
    // turnOffLimelight = new JoystickButton(m_driveController,
    // ButtonsEnumerated.STARTBUTTON.getValue());
    // turnOffLimelight.whenPressed(new ToggleLimelightLED(false,
    // sensorsContainer));
    letOutLeftWinch = new JoystickButton(m_driveController, ButtonsEnumerated.LEFTSTICKBUTTON.getValue());
    letOutRightWinch = new JoystickButton(m_driveController, ButtonsEnumerated.RIGHTSTICKBUTTON.getValue());
  }

  /**
   * Returns the position of the forward/backward stick with FORWARD being a
   * positive value to stick with our conventions.
   * 
   * @return The position of the left drive stick (up and down).
   */
  public double getForwardBackwardStick() {
    return deadband(-m_driveController.getY(GenericHID.Hand.kLeft));
  }

  private double deadband(double stickValue) {
    if (Math.abs(stickValue) < 0.01) {
      return 0.0;
    } else {
      return stickValue;
    }
  }

  /**
   * Returns the position of the left/right stick with LEFT being a positive value
   * to stick with our conventions.
   * 
   * @return the position of the right drive stick (left to right).
   */
  public double getRotateStick() {
    return deadband(-m_driveController.getX(GenericHID.Hand.kRight));
  }

  public double getLeftTriggerValue() {
    return deadband(m_driveController.getTriggerAxis(Hand.kLeft));
  }

  public double getRightTriggerValue() {
    return deadband(m_driveController.getTriggerAxis(Hand.kRight));
  }

  public boolean getLeftBumperPressed() {
    return m_driveController.getBumperPressed(Hand.kLeft);
  }

  public boolean getLeftBumperReleased() {
    return m_driveController.getBumperReleased(Hand.kLeft);
  }

  public JoystickButton getLetOutLeftWinchButton() {
    return letOutLeftWinch;
  }

  public JoystickButton getLetOutRightWinchButton() {
    return letOutRightWinch;
  }

  public JoystickButton getInterstellerWaitButton() {
    return interstellerAccuracyWait;
  }
}
