/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.actuators.ServoMotor;
import frc.robot.commands.TeleOpCommand;
import frc.robot.oi.OIContainer;
import frc.robot.sensors.SensorsContainer;
import frc.robot.sensors.limelightcamera.LimeLightCameraBase;
import frc.robot.subsystems.SubsystemsContainer;
import frc.robot.telemetries.Trace;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private SubsystemsContainer m_subsystemContainer;
  private SensorsContainer m_sensorsContainer;
  private OIContainer m_oiContainer;
  private LimeLightCameraBase limelight;
  private PWM romiShooter;

  private Robot() {
    romiShooter = new PWM(4);

  }

  static Robot m_instance;

  public static Robot getInstance() {
    if (m_instance == null) {
      m_instance = new Robot();
    }
    return m_instance;
  }

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.

    m_sensorsContainer = new SensorsContainer();
    m_subsystemContainer = new SubsystemsContainer();
    m_oiContainer = new OIContainer(m_subsystemContainer, m_sensorsContainer);
    m_subsystemContainer.setDefaultCommands();
    limelight = m_sensorsContainer.getLimeLight();
    limelight.disableLED();
    m_subsystemContainer.getDrivetrain().setCoast(true);
    m_subsystemContainer.getWings().stop();

    Robot.getInstance().getSubsystemsContainer().getLEDs("LEDStringOne").setRainbow();
    SmartDashboard.putNumber("ShooterRPMTarget", 3000);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

  @Override
  public void robotPeriodic() {

    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    m_sensorsContainer.getGyro().updateSmartDashboardReadings();
    m_sensorsContainer.getLimeLight().updateSmartDashboardReadings();
    m_sensorsContainer.getBallFeederSensor().isThereBall();
    SmartDashboard.putNumber("Powercell Detector", m_sensorsContainer.getPowercellDetector().getDistanceInches());
    m_subsystemContainer.getDrivetrain().updateSmartDashboardReadings();
    m_sensorsContainer.periodic();
    m_sensorsContainer.getAnalog41IRSensor().updateSmartDashboardReadings();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    if (DriverStation.getInstance().isFMSAttached()) {
      Trace.getInstance().matchStarted();
    }
    m_subsystemContainer.getDrivetrain().setCoast(true);
    Trace.getInstance().flushTraceFiles();
    limelight.disableLED();
    Robot.getInstance().getSubsystemsContainer().getLEDs("LEDStringOne").setRainbow();
    CommandScheduler.getInstance().schedule(new TeleOpCommand());
    romiShooter.setSpeed(0.0);

  }

  @Override
  public void disabledPeriodic() {
    // limelight.disableLED();
   
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_oiContainer.getSmartDashboard().getSelectedAutoChooserCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      CommandScheduler.getInstance().schedule(m_autonomousCommand);
    }
    if (DriverStation.getInstance().isFMSAttached()) {
      Trace.getInstance().matchStarted();
    }
    limelight.enableLED();
    m_subsystemContainer.getDrivetrain().setCoast(false);
    romiShooter.setSpeed(-1.0);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    if (Config4905.getConfig4905().doesHarvesterExist()) {
      Robot.getInstance().getSubsystemsContainer().getRomiIntake().runBackward();
    }
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    if (DriverStation.getInstance().isFMSAttached()) {
      Trace.getInstance().matchStarted();
    }
    limelight.disableLED();
    m_subsystemContainer.getDrivetrain().setCoast(false);
    Robot.getInstance().getSubsystemsContainer().getLEDs("LEDStringOne").setSolid();
    
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    if (Config4905.getConfig4905().doesHarvesterExist()) {
      Robot.getInstance().getSubsystemsContainer().getRomiIntake().runBackward();
    }
    romiShooter.setSpeed(-1.0);
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    m_subsystemContainer.getDrivetrain().setCoast(false);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  // getters for various OI things below

  public SubsystemsContainer getSubsystemsContainer() {
    return m_subsystemContainer;
  }

  public SensorsContainer getSensorsContainer() {
    return m_sensorsContainer;
  }

  public OIContainer getOIContainer() {
    return m_oiContainer;
  }

}