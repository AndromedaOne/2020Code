/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.oi;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.ConfigReload;
import frc.robot.commands.DriveBackwardTimed;
import frc.robot.commands.LimeLightToggle;
import frc.robot.commands.pidcommands.MoveUsingEncoder;
import frc.robot.commands.pidcommands.TurnDeltaAngle;
import frc.robot.commands.pidcommands.TurnToCompassHeading;
import frc.robot.subsystems.SubsystemsContainer;

/**
 * Add your docs here.
 */
public class SmartDashboard4905 {
  SendableChooser<Command> m_autoChooser = new SendableChooser<>();

  public SmartDashboard4905(SubsystemsContainer subsystemsContainer) {

    SmartDashboard.putData("DriveBackward", new DriveBackwardTimed(3, subsystemsContainer.getDrivetrain()));
    SmartDashboard.putData("MoveUsingEncoder", new MoveUsingEncoder(subsystemsContainer.getDrivetrain(), 12));

    SmartDashboard.putData("North", new TurnToCompassHeading(0));
    SmartDashboard.putData("South", new TurnToCompassHeading(180));
    SmartDashboard.putData("East", new TurnToCompassHeading(90));
    SmartDashboard.putData("West", new TurnToCompassHeading(270));
    SmartDashboard.putData("Turn -45", new TurnDeltaAngle(-45));
    SmartDashboard.putData("Turn -90", new TurnDeltaAngle(-90));
    SmartDashboard.putData("Turn -180", new TurnDeltaAngle(-180));
    SmartDashboard.putData("Turn 45", new TurnDeltaAngle(45));
    SmartDashboard.putData("Turn 90", new TurnDeltaAngle(90));
    SmartDashboard.putData("Turn 180", new TurnDeltaAngle(180));

    SmartDashboard.putNumber("Auto Delay", 0);

    SmartDashboard.putData("reloadConfig", new ConfigReload());
    SmartDashboard.putData("Turn On Limelight Light", new LimeLightToggle());

    AutoModes4905.initializeAutoChooser(subsystemsContainer, m_autoChooser);
  }

  public Command getSelectedAutoChooserCommand() {
    return m_autoChooser.getSelected();
  }
}
