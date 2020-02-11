/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.oi;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.lib.ButtonsEnumerated;

/**
 * Add your docs here.
 */
public class SubsystemController {
  private XboxController m_subsystemController = new XboxController(1);

  // public JoystickButton getDeployRunIntakeButton() {
  // }
  public JoystickButton getDeployAndRunIntakeButton() {
    return ButtonsEnumerated.LEFTBUMPERBUTTON.getJoystickButton(m_subsystemController);
  }

  public JoystickButton getFeedWhenReadyButton() {
    return ButtonsEnumerated.RIGHTBUMPERBUTTON.getJoystickButton(m_subsystemController);
  }

  public double getLeftStickForwardBackwardValue() {
    return deadband(-m_subsystemController.getY(GenericHID.Hand.kLeft));
  }

  private double deadband(double stickValue) {
    if (Math.abs(stickValue) < 0.05) {
      return 0.0;
    } else {
      return stickValue;
    }
  }
}
