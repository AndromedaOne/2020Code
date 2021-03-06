/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import static frc.robot.sensors.ballfeedersensor.EnumBallLocation.*;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.sensors.ballfeedersensor.BallFeederSensorBase;
import frc.robot.subsystems.feeder.FeederBase;
import frc.robot.subsystems.feeder.FeederStates;
import frc.robot.telemetries.Trace;

public class DefaultFeederCommand extends CommandBase {

  BallFeederSensorBase m_feederSensor;
  private FeederBase m_feeder;
  private static FeederStates feederState;
  private static final double DEFAULT_STAGES_ONE_AND_TWO_SPEED = 0.25;
  private static final double DEFAULT_DIFFERENCE_STAGE_TWO_AND_THREE_SPEED = 0.0;
  private static final double DEFAULT_STAGE_THREE_SPEED = 0.1;
  private static final double STAGE_TWO_SLOW_SPEED = 0.1;
  private static final double STAGE_THREE_SLOW_SPEED = STAGE_TWO_SLOW_SPEED
      + DEFAULT_DIFFERENCE_STAGE_TWO_AND_THREE_SPEED;
  private static int numberOfPowerCellsInFeeder = 0;
  private int m_stageOneEndSensorTriggeredCounter = 0;
  private int m_stageOneLeftRightSensorTriggeredCounter = 0;
  private Timer m_timer;
  private Timer m_emptyTimer;
  public static final double EMPTY_TIME_THRESHOLD = 0.75; // After this time in seconds, if none of the feeder sensors
                                                          // have been triggered, then the feeder state will be set to
                                                          // empty.
  private static final double MOVING_STAGE_TIMEOUT = 5;

  /**
   * Creates a new FeederCommand.
   */
  public DefaultFeederCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.getInstance().getSubsystemsContainer().getFeeder());
    this.m_feeder = Robot.getInstance().getSubsystemsContainer().getFeeder();
    m_feederSensor = Robot.getInstance().getSensorsContainer().getBallFeederSensor();
    feederState = FeederStates.EMPTY;
    m_timer = new Timer();
    m_emptyTimer = new Timer();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Trace.getInstance().logCommandStart(this);
    Robot.getInstance().getSubsystemsContainer().getShooter().closeShooterHood();
    feederState = FeederStates.EMPTY;
    m_stageOneEndSensorTriggeredCounter = 0;
    m_stageOneLeftRightSensorTriggeredCounter = 0;
    m_emptyTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    numberOfPowerCellsInFeeder = 0;
    if (Robot.getInstance().isDisabled()) {
      feederState = FeederStates.EMPTY;
      return;
    }

    boolean[] ballSensorValues = m_feederSensor.isThereBall();
    int sensoredTriggered = 0;

    for (boolean ballSensed : ballSensorValues) {
      if (ballSensed) {
        sensoredTriggered++;
      }
    }
    if (sensoredTriggered != 0) {
      m_emptyTimer.reset();
    }
    if (m_emptyTimer.hasElapsed(EMPTY_TIME_THRESHOLD)) {
      feederState = FeederStates.EMPTY;
    }

    switch (feederState) {
    case EMPTY:
      m_feeder.stopBothStages();
      numberOfPowerCellsInFeeder = 0;
      m_timer.stop();
      if (ballSensorValues[STAGE_1_LEFT.getIndex()] || ballSensorValues[STAGE_1_RIGHT.getIndex()]
          || ballSensorValues[STAGE_1_END.getIndex()]) {
        setFeederState(FeederStates.ONE_LOADING);
      }
      break;

    case ONE_LOADING:

      m_feeder.runBothStages(DEFAULT_STAGES_ONE_AND_TWO_SPEED, DEFAULT_STAGE_THREE_SPEED);
      if (ballSensorValues[STAGE_2_BEGINNING_MIDDLE.getIndex()] && !ballSensorValues[STAGE_2_BEGINNING.getIndex()]) {
        setFeederState(FeederStates.ONE_LOADED);
      }
      break;

    case ONE_LOADED:
      m_timer.stop();
      if (ballSensorValues[STAGE_1_LEFT.getIndex()] || ballSensorValues[STAGE_1_RIGHT.getIndex()]
          || ballSensorValues[STAGE_1_END.getIndex()]) {
        setFeederState(FeederStates.ONE_LOADED);
      }
      m_feeder.stopBothStages();
      numberOfPowerCellsInFeeder = 1;
      break;

    case UNKNOWN:
      if (!ballSensorValues[STAGE_2_BEGINNING.getIndex()] && !ballSensorValues[STAGE_2_BEGINNING_MIDDLE.getIndex()]
          && !ballSensorValues[STAGE_1_END.getIndex()]) {
        setFeederState(FeederStates.EMPTY);
      }
      numberOfPowerCellsInFeeder = 3;
      m_feeder.stopBothStages();
      break;
    }
    if (ballSensorValues[STAGE_1_END.getIndex()]) {
      m_stageOneEndSensorTriggeredCounter++;
      if (m_stageOneEndSensorTriggeredCounter > 5) {
        numberOfPowerCellsInFeeder++;
      }
    } else {
      m_stageOneEndSensorTriggeredCounter = 0;
    }
    if (ballSensorValues[STAGE_1_LEFT.getIndex()] || ballSensorValues[STAGE_1_RIGHT.getIndex()]) {
      m_stageOneLeftRightSensorTriggeredCounter++;
      if (m_stageOneLeftRightSensorTriggeredCounter > 2) {
        numberOfPowerCellsInFeeder++;
      }
    } else {
      m_stageOneLeftRightSensorTriggeredCounter = 0;
    }
  }

  /**
   * This is the safest state to be in because the feeder will not auto load any
   * more if it is in third loaded.
   */
  public static void setFeederStateToThreeLoaded() {
    feederState = FeederStates.THIRD_LOADED;
  }

  private void setFeederState(FeederStates feederStateParam) {
    feederState = feederStateParam;
    m_timer.reset();
    m_timer.start();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_feeder.stopBothStages();
    Trace.getInstance().logCommandStop(this);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public static int getNumberOfPowerCellsInFeeder() {
    return numberOfPowerCellsInFeeder;
  }
}
