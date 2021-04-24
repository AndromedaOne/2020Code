/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.sensors;

import com.typesafe.config.Config;

import frc.robot.Config4905;
import frc.robot.sensors.ballfeedersensor.BallFeederSensorBase;
import frc.robot.sensors.ballfeedersensor.MockBallFeederSensor;
import frc.robot.sensors.ballfeedersensor.RealBallFeederSensor;
import frc.robot.sensors.camera.*;
import frc.robot.sensors.gyro.Gyro;
import frc.robot.sensors.gyro.MockGyro;
import frc.robot.sensors.gyro.RealNavXGyroSensor;
import frc.robot.sensors.gyro.RomiGyro;
import frc.robot.sensors.limelightcamera.LimeLightCameraBase;
import frc.robot.sensors.limelightcamera.MockLimeLightCamera;
import frc.robot.sensors.limelightcamera.RealLimelightCamera;
import frc.robot.sensors.ultrasonicsensor.MockUltrasonicSensor;
import frc.robot.sensors.ultrasonicsensor.RealUltrasonicSensor;
import frc.robot.sensors.ultrasonicsensor.UltrasonicSensor;

/**
 * The Container that controls whether the sensors are real or mock. Uses the
 * config to do this.
 */
public class SensorsContainer {
  private BallFeederSensorBase m_ballFeederSensor;
  private Camera m_camera0;
  private Camera m_camera1;
  private LimeLightCameraBase m_limelightCameraBase;
  private Gyro m_gyro;
  private UltrasonicSensor m_powerCellDetector;

  public SensorsContainer() {
    final Config sensorConfig = Config4905.getConfig4905().getSensorConfig();

    if (sensorConfig.hasPath("navx")) {
      System.out.println("Using real NavX Gyro sensor");
      m_gyro = new RealNavXGyroSensor();
    } else if (sensorConfig.hasPath("RomiGyro")) {
      System.out.println("Using RomiGyro");
      m_gyro = new RomiGyro();
    } else {
      System.out.println("Using mock Navx Gyro sensor");
      m_gyro = new MockGyro();
    }

    if (sensorConfig.hasPath("sensors.ballFeederSensor")) {
      System.out.println("Using real ball feeder sensor");
      m_ballFeederSensor = new RealBallFeederSensor("ballFeederSensor");
    } else {
      System.out.println("Using mock ball feeder sensor");
      m_ballFeederSensor = new MockBallFeederSensor();
    }

    if (sensorConfig.hasPath("sensors.cameras")) {
      if (sensorConfig.hasPath("sensors.cameras.camera0")) {
        System.out.println("Using real camera with id: " + sensorConfig.getInt("sensors.cameras.camera0.port"));
        m_camera0 = new RealCamera(0, sensorConfig.getInt("sensors.cameras.camera0.port"));
      }
      if (sensorConfig.hasPath("sensors.cameras.camera1")) {
        System.out.println("Using real camera with id: " + sensorConfig.getInt("sensors.cameras.camera1.port"));
        m_camera1 = new RealCamera(1, sensorConfig.getInt("sensors.cameras.camera1.port"));
      }
    } else {
      System.out.println("Using fake cameras");
      m_camera0 = new MockCamera();
    }

    if (sensorConfig.hasPath("limelight")) {
      System.out.println("Using real LimeLight");
      m_limelightCameraBase = new RealLimelightCamera();
    } else {
      System.out.println("Using fake LimeLight");
      m_limelightCameraBase = new MockLimeLightCamera();
    }
    if (sensorConfig.hasPath("sensors.ultrasonicSensor.powerCellDetector")) {
      m_powerCellDetector = new RealUltrasonicSensor("ultrasonicSensor.powerCellDetector");
      System.out.println("Using Real Power Cell Detector");
    } else {
      m_powerCellDetector = new MockUltrasonicSensor();
      System.out.println("Using Fake Power Cell Detector");
    }
  }

  public Gyro getGyro() {
    return m_gyro;
  }

  public BallFeederSensorBase getBallFeederSensor() {
    return m_ballFeederSensor;
  }

  public Camera getCamera0() {
    return m_camera0;
  }

  public Camera getCamera1() {
    return m_camera1;
  }

  public LimeLightCameraBase getLimeLight() {
    return m_limelightCameraBase;
  }

  public UltrasonicSensor getPowercellDetector() {
    return m_powerCellDetector;
  }
}
