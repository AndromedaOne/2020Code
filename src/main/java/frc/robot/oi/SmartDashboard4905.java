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
import frc.robot.Config4905;
import frc.robot.commands.CheckRomiVelocityConversionFactor;
import frc.robot.commands.ConfigReload;
import frc.robot.commands.DriveBackwardTimed;
import frc.robot.commands.RunAllFeederMotors;
import frc.robot.commands.RunIntakeOut;
import frc.robot.commands.ToggleLimelightLED;
import frc.robot.commands.TuneShooterFeedForward;
import frc.robot.commands.cannon.PressurizeCannon;
import frc.robot.commands.cannon.ShootCannon;
import frc.robot.commands.pidcommands.MoveUsingEncoderTester;
import frc.robot.commands.romiBallMopper.MopBallMopper;
import frc.robot.commands.romiBallMopper.ResetBallMopper;
import frc.robot.groupcommands.parallelgroup.ShootWithRPM;
import frc.robot.sensors.SensorsContainer;
import frc.robot.subsystems.SubsystemsContainer;

/**
 * Add your docs here.
 */
public class SmartDashboard4905 {
  SendableChooser<Command> m_autoChooser = new SendableChooser<>();

  public SmartDashboard4905(SubsystemsContainer subsystemsContainer, SensorsContainer sensorsContainer) {
    AutoModes4905.initializeAutoChooser(subsystemsContainer, sensorsContainer, m_autoChooser);
    SmartDashboard.putData("DriveBackward", new DriveBackwardTimed(1, subsystemsContainer.getDrivetrain()));
    SmartDashboard.putNumber("MoveUsingEncoderTester Distance To Move", 24);
    SmartDashboard.putData("MoveUsingEncoderTester", new MoveUsingEncoderTester(subsystemsContainer.getDrivetrain()));

    SmartDashboard.putNumber("Auto Delay", 0);

    SmartDashboard.putData("Reload Config", new ConfigReload());

    SmartDashboard.putData("Enable Limelight LEDs", new ToggleLimelightLED(true, sensorsContainer));

    SmartDashboard.putData("Disable Limelight LEDs", new ToggleLimelightLED(false, sensorsContainer));
    SmartDashboard.putData("PressurizeCannon", new PressurizeCannon());
    SmartDashboard.putData("Shoot Cannon", new ShootCannon());

    if (Config4905.getConfig4905().isRomi()) {
      romiCommands(subsystemsContainer);
    } else if (Config4905.getConfig4905().isTheDroidYoureLookingFor()) {
      theDroidYoureLookingForCommands(subsystemsContainer);
    }
  }

  public Command getSelectedAutoChooserCommand() {
    return m_autoChooser.getSelected();
  }

  private void romiCommands(SubsystemsContainer subsystemsContainer) {
    SmartDashboard.putData("CheckRomiVelocityConversionFactor",
        new CheckRomiVelocityConversionFactor(subsystemsContainer.getDrivetrain()));
    SmartDashboard.putData("Mop Ball", new MopBallMopper());
    SmartDashboard.putData("Reset ball Mopper", new ResetBallMopper());
  }

  private void theDroidYoureLookingForCommands(SubsystemsContainer subsystemsContainer) {
    SmartDashboard.putData("ShootRPM",
        new ShootWithRPM(subsystemsContainer.getShooter(), subsystemsContainer.getFeeder(), true));

    SmartDashboard.putData("Run All feeder motors", new RunAllFeederMotors(subsystemsContainer.getFeeder()));
    SmartDashboard.putData("Tune Shooter Feed Forward", new TuneShooterFeedForward(subsystemsContainer.getShooter()));
    SmartDashboard.putData("Run Intake Out", new RunIntakeOut(subsystemsContainer.getIntake(), () -> false, 1));
  }
}
