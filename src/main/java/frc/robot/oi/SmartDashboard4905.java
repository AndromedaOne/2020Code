/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.oi;

import java.util.List;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Config4905;
import frc.robot.commands.ConfigReload;
import frc.robot.commands.DriveBackwardTimed;
import frc.robot.commands.ToggleLimelightLED;
import frc.robot.commands.pidcommands.MoveUsingEncoderTester;
import frc.robot.commands.pidcommands.TurnToCompassHeadingTester;
import frc.robot.groupcommands.athomechallengepathways.AtHomeChallengePoints;
import frc.robot.groupcommands.athomechallengepathways.GalacticSearchPathA;
import frc.robot.groupcommands.athomechallengepathways.GalacticSearchPathB;
import frc.robot.groupcommands.athomechallengepathways.TestPath;
import frc.robot.groupcommands.parallelgroup.ShootWithDistance;
import frc.robot.pathgeneration.pathgenerators.DriveTrainDiagonalPathGenerator;
import frc.robot.pidcontroller.TracingPIDController;
import frc.robot.sensors.SensorsContainer;
import frc.robot.subsystems.SubsystemsContainer;

/**
 * Add your docs here.
 */
public class SmartDashboard4905 {
  SendableChooser<Command> m_autoChooser = new SendableChooser<>();

  public SmartDashboard4905(SubsystemsContainer subsystemsContainer, SensorsContainer sensorsContainer) {

    SmartDashboard.putData("DriveBackward", new DriveBackwardTimed(1, subsystemsContainer.getDrivetrain()));
    SmartDashboard.putData("MoveUsingEncoderTester", new MoveUsingEncoderTester(subsystemsContainer.getDrivetrain()));

    SmartDashboard.putData("TurnToCompassHeadingTester",
        new TurnToCompassHeadingTester(SmartDashboard.getNumber("Compass Heading", 0)));

    SmartDashboard.putNumber("Auto Delay", 0);

    SmartDashboard.putData("Reload Config", new ConfigReload());

    SmartDashboard.putData("Shoot 10 feet",
        new ShootWithDistance(subsystemsContainer.getShooter(), subsystemsContainer.getFeeder(), 120));

    SmartDashboard.putData("Enable Limelight LEDs", new ToggleLimelightLED(true, sensorsContainer));

    SmartDashboard.putData("Disable Limelight LEDs", new ToggleLimelightLED(false, sensorsContainer));

    AutoModes4905.initializeAutoChooser(subsystemsContainer, sensorsContainer, m_autoChooser);

    SmartDashboard.putData("Galactic Search Path A",
        new GalacticSearchPathA(subsystemsContainer.getDrivetrain(), subsystemsContainer.getIntake()));

    SmartDashboard.putData("Galactic Search Path B",
        new GalacticSearchPathB(subsystemsContainer.getDrivetrain(), subsystemsContainer.getIntake()));

    DriveTrainDiagonalPathGenerator driveTrainDiagonalPathGenerator = new DriveTrainDiagonalPathGenerator(
        new TestPath(), subsystemsContainer.getDrivetrain(), AtHomeChallengePoints.E3);
    SmartDashboard.putData("Drive Test Path", driveTrainDiagonalPathGenerator.getPath());

    // Start of path Planning code:

    Config drivetrainConfig = Config4905.getConfig4905().getDrivetrainConfig();
    double ksVolts = drivetrainConfig.getDouble("pathplanningconstants.ksVolts");
    double kvVoltSecondsPerMeter = drivetrainConfig.getDouble("pathplanningconstants.kvVoltSecondsPerMeter");
    double kaVoltSecondsSquaredPerMeter = drivetrainConfig
        .getDouble("pathplanningconstants.kaVoltSecondsSquaredPerMeter");
    double kPDriveVel = drivetrainConfig.getDouble("pathplanningconstants.kPDriveVel");

    double kTrackwidthMeters = drivetrainConfig.getDouble("pathplanningconstants.kTrackwidthMeters");
    DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);

    double kMaxSpeedMetersPerSecond = drivetrainConfig.getDouble("pathplanningconstants.kMaxSpeedMetersPerSecond");
    double kMaxAccelerationMetersPerSecondSquared = drivetrainConfig
        .getDouble("pathplanningconstants.kMaxAccelerationMetersPerSecondSquared");
    double kRamseteB = drivetrainConfig.getDouble("pathplanningconstants.kRamseteB");
    double kRamseteZeta = drivetrainConfig.getDouble("pathplanningconstants.kRamseteZeta");

    DifferentialDriveVoltageConstraint autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
        new SimpleMotorFeedforward(ksVolts, kvVoltSecondsPerMeter, kaVoltSecondsSquaredPerMeter), kDriveKinematics, 10);

    TrajectoryConfig config = new TrajectoryConfig(kMaxSpeedMetersPerSecond, kMaxAccelerationMetersPerSecondSquared)
        // Add kinematics to ensure max speed is actually obeyed
        .setKinematics(kDriveKinematics)
        // Apply the voltage constraint
        .addConstraint(autoVoltageConstraint);

    Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        List.of(new Translation2d(Units.inchesToMeters(150), Units.inchesToMeters(-30)),
            new Translation2d(Units.inchesToMeters(120), Units.inchesToMeters(-60)),
            new Translation2d(Units.inchesToMeters(90), Units.inchesToMeters(-30))),
        new Pose2d(0, 0, new Rotation2d(180)),
        // Pass config
        config);

    RamseteCommand ramseteCommand = new RamseteCommand(exampleTrajectory, subsystemsContainer.getDrivetrain()::getPose,
        new RamseteController(kRamseteB, kRamseteZeta),
        new SimpleMotorFeedforward(ksVolts, kvVoltSecondsPerMeter, kaVoltSecondsSquaredPerMeter), kDriveKinematics,
        subsystemsContainer.getDrivetrain()::getWheelSpeeds,
        // the following two pidcontrollers are intentionally reversed in order so that
        // positive angle from the gyro correspons to rotating clockwise looking down on
        // the robot.
        new TracingPIDController("RightVelocity", kPDriveVel, 0.0, 0.0),
        new TracingPIDController("LeftVelocity", kPDriveVel, 0.0, 0.0),

        // RamseteCommand passes volts to the callback
        subsystemsContainer.getDrivetrain()::tankDriveVolts, subsystemsContainer.getDrivetrain());
    subsystemsContainer.getDrivetrain().resetOdometry(exampleTrajectory.getInitialPose());

    SmartDashboard.putData("Drive Path planning test", ramseteCommand);

  }

  public Command getSelectedAutoChooserCommand() {
    return m_autoChooser.getSelected();
  }
}
