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
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.CheckRomiVelocityConversionFactor;
import frc.robot.commands.ConfigReload;
import frc.robot.commands.DriveBackwardTimed;
import frc.robot.commands.ResetGyro;
import frc.robot.commands.RunRomiIntakeAtTime;
import frc.robot.commands.ToggleLimelightLED;
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
import frc.robot.pathgeneration.pathgenerators.PathGeneratorBase;
import frc.robot.pathgeneration.pathgenerators.TwoDDriveTrainPathGenerator;
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
        new ShootWithRPM(subsystemsContainer.getShooter(), subsystemsContainer.getFeeder(), 3000));

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

    PathGeneratorBase turnRightGenerator = new TwoDDriveTrainPathGenerator("TurnRight.wpilib.json",
        subsystemsContainer.getDrivetrain(), "RomiTurnRight");
    SmartDashboard.putData("Romi 2d Turn Right", turnRightGenerator.getPath());

    PathGeneratorBase sPathGenerator = new TwoDDriveTrainPathGenerator("SCurve.wpilib.json",
        subsystemsContainer.getDrivetrain(), "RomiSPath");
    SmartDashboard.putData("Romi 2d S Path", sPathGenerator.getPath());

    PathGeneratorBase straitPathGenerator = new TwoDDriveTrainPathGenerator("StraitLine.wpilib.json",
        subsystemsContainer.getDrivetrain(), "RomiStraitPath");
    SmartDashboard.putData("Romi 2d Strait Path", straitPathGenerator.getPath());

    PathGeneratorBase slalomPathGenerator = new TwoDDriveTrainPathGenerator("RomiSlalom.wpilib.json",
        subsystemsContainer.getDrivetrain(), "RomiSlalomPath");
    SmartDashboard.putData("Romi 2d Slalom", slalomPathGenerator.getPath());

    PathGeneratorBase barrelPathGenerator = new TwoDDriveTrainPathGenerator("RomiBarrelRacing.wpilib.json",
        subsystemsContainer.getDrivetrain(), "RomiBarrelPath");
    SmartDashboard.putData("Romi 2d Barrel", barrelPathGenerator.getPath());

    PathGeneratorBase bouncePathGenerator1 = new TwoDDriveTrainPathGenerator("RomiBounceP1.wpilib.json",
        subsystemsContainer.getDrivetrain(), "RomiBouncePathP1");
    PathGeneratorBase bouncePathGenerator2 = new TwoDDriveTrainPathGenerator("RomiBounceP2.wpilib.json",
        subsystemsContainer.getDrivetrain(), false, "RomiBouncePathP2");
    PathGeneratorBase bouncePathGenerator3 = new TwoDDriveTrainPathGenerator("RomiBounceP3.wpilib.json",
        subsystemsContainer.getDrivetrain(), false, "RomiBouncePathP3");
    PathGeneratorBase bouncePathGenerator4 = new TwoDDriveTrainPathGenerator("RomiBounceP4.wpilib.json",
        subsystemsContainer.getDrivetrain(), false, "RomiBouncePathP4");

    ResetGyro resetGyro = new ResetGyro();
    SequentialCommandGroup bouncePath = new SequentialCommandGroup(bouncePathGenerator1.getPath(),
        bouncePathGenerator2.getPath(), bouncePathGenerator3.getPath(), bouncePathGenerator4.getPath(), resetGyro);

    CommandBase enableIntakeAtTimes = new RunRomiIntakeAtTime(1.2, 0.7, 4.0, 8.1);

    ParallelCommandGroup bounceAndIntake = new ParallelCommandGroup(bouncePath, enableIntakeAtTimes);

    SmartDashboard.putData("RomiBounce", bounceAndIntake);

    SmartDashboard.putData("CheckRomiVelocityConversionFactor",
        new CheckRomiVelocityConversionFactor(subsystemsContainer.getDrivetrain()));

  }

  public Command getSelectedAutoChooserCommand() {
    return m_autoChooser.getSelected();
  }
}
