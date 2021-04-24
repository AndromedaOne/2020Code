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
import frc.robot.commands.RunAllFeederMotors;
import frc.robot.commands.RunIntakeOut;
import frc.robot.commands.ToggleLimelightLED;
import frc.robot.commands.TuneShooterFeedForward;
import frc.robot.commands.pidcommands.MoveUsingEncoderTester;
import frc.robot.commands.pidcommands.TurnToCompassHeadingTester;
import frc.robot.groupcommands.RomiCommands.RomiChallenge1;
import frc.robot.groupcommands.RomiCommands.RomiExamplePath;
import frc.robot.groupcommands.athomechallengepathways.BarrelRacingPath;
import frc.robot.groupcommands.athomechallengepathways.BouncePath;
import frc.robot.groupcommands.athomechallengepathways.GalacticSearchPathA;
import frc.robot.groupcommands.athomechallengepathways.GalacticSearchPathAAllSixBalls;
import frc.robot.groupcommands.athomechallengepathways.GalacticSearchPathB;
import frc.robot.groupcommands.athomechallengepathways.GalacticSearchPathBAllSixBalls;
import frc.robot.groupcommands.athomechallengepathways.HyperDriveChallenge;
import frc.robot.groupcommands.athomechallengepathways.InterstellarAccuracyChallenge;
import frc.robot.groupcommands.athomechallengepathways.PowerPortStart;
import frc.robot.groupcommands.athomechallengepathways.SlalomPath;
import frc.robot.groupcommands.parallelgroup.ShootWithRPM;
import frc.robot.sensors.SensorsContainer;
import frc.robot.subsystems.SubsystemsContainer;

/**
 * Add your docs here.
 */
public class SmartDashboard4905 {
  SendableChooser<Command> m_autoChooser = new SendableChooser<>();

  public SmartDashboard4905(SubsystemsContainer subsystemsContainer, SensorsContainer sensorsContainer) {

    SmartDashboard.putData("DriveBackward", new DriveBackwardTimed(1, subsystemsContainer.getDrivetrain()));
    SmartDashboard.putNumber("MoveUsingEncoderTester Distance To Move", 24);
    SmartDashboard.putData("MoveUsingEncoderTester", new MoveUsingEncoderTester(subsystemsContainer.getDrivetrain()));

    SmartDashboard.putData("TurnToCompassHeadingTester",
        new TurnToCompassHeadingTester(SmartDashboard.getNumber("Compass Heading", 0)));

    SmartDashboard.putNumber("Auto Delay", 0);

    SmartDashboard.putData("Reload Config", new ConfigReload());

    SmartDashboard.putData("ShootRPM",
        new ShootWithRPM(subsystemsContainer.getShooter(), subsystemsContainer.getFeeder(), true));

    SmartDashboard.putData("Enable Limelight LEDs", new ToggleLimelightLED(true, sensorsContainer));

    SmartDashboard.putData("Disable Limelight LEDs", new ToggleLimelightLED(false, sensorsContainer));

    AutoModes4905.initializeAutoChooser(subsystemsContainer, sensorsContainer, m_autoChooser);

    SmartDashboard.putData("Galactic Search Path A All Six Balls",
        new GalacticSearchPathAAllSixBalls(subsystemsContainer.getDrivetrain(), subsystemsContainer.getIntake()));

    SmartDashboard.putData("Galactic Search Path A", new GalacticSearchPathA());

    SmartDashboard.putData("Galactic Search Path B All Six Balls",
        new GalacticSearchPathBAllSixBalls(subsystemsContainer.getDrivetrain(), subsystemsContainer.getIntake()));

    SmartDashboard.putData("Galactic Search Path B", new GalacticSearchPathB());
    SmartDashboard.putData("InterstellerAccuracyChallenge", new InterstellarAccuracyChallenge(
        subsystemsContainer.getDrivetrain(), subsystemsContainer.getShooter(), subsystemsContainer.getFeeder()));
    SmartDashboard.putData("Bounce Path",
        new BouncePath(subsystemsContainer.getDrivetrain(), subsystemsContainer.getIntake()));
    SmartDashboard.putData("Slalom Path", new SlalomPath(subsystemsContainer.getDrivetrain()));
    SmartDashboard.putData("Barrel Racing Path", new BarrelRacingPath(subsystemsContainer.getDrivetrain()));
    SmartDashboard.putData("Hyper Drive Challenge", new HyperDriveChallenge(subsystemsContainer.getDrivetrain()));
    SmartDashboard.putData("Power Port Start", new PowerPortStart(subsystemsContainer.getDrivetrain(),
        subsystemsContainer.getShooter(), subsystemsContainer.getFeeder(), subsystemsContainer.getIntake()));

    SmartDashboard.putData("RomiExamplePath", new RomiExamplePath(subsystemsContainer.getDrivetrain()));
    SmartDashboard.putData("RomiChallenge1", new RomiChallenge1(subsystemsContainer.getDrivetrain()));

    SmartDashboard.putData("Run All feeder motors", new RunAllFeederMotors(subsystemsContainer.getFeeder()));
    SmartDashboard.putData("Tune Shooter Feed Forward", new TuneShooterFeedForward(subsystemsContainer.getShooter()));
    SmartDashboard.putData("Run Intake Out", new RunIntakeOut(subsystemsContainer.getIntake(), () -> false, 1));
  }

  public Command getSelectedAutoChooserCommand() {
    return m_autoChooser.getSelected();
  }
}
