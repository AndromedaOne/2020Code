package frc.robot;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

// implemented as a singleton
public class Config4905 {

  private Config m_nameConfig;

  /**
   * This config should live on the robot and have hardware- specific configs.
   */
  private Config m_environmentalConfig;

  /**
   * This config lives in the jar and has hardware-independent configs.
   */
  private Config m_defaultConfig = ConfigFactory.parseResources("application.conf");

  /**
   * Combined config
   */
  private Config m_config;

  private Config climberConfig;

  private Config drivetrainConfig;

  private Config feederConfig;

  private Config intakeConfig;

  private Config shooterConfig;

  private Config sensorConfig;

  private Config commandConstantsConfig;

  private Config ledConfig;

  private Config harvesterConfig;

  private static Config4905 m_config4905 = null;

  // current linux home dir on a roborio
  private final String m_linuxPathToHomeStr = "/home/lvuser/";

  private String m_baseDir;

  String m_robotName;

  private Config4905() {
    // first look to see if this is a roborio
    if (Files.exists(Paths.get(m_linuxPathToHomeStr))) {
      m_baseDir = m_linuxPathToHomeStr;
      m_nameConfig = ConfigFactory.parseFile(new File(m_linuxPathToHomeStr + "name.conf"));
      m_robotName = m_nameConfig.getString("robot.name");
    } else {
      // try to figure out which Romi we're on by looking at the SSID's we're
      // connected to
      // all of our Romi's will start with "4905_Romi" and potentially have some
      // identifier
      // after Romi. so search for 4905_Romi and extract the romi name and use this
      // as the robot name
      try {
        Process proc = Runtime.getRuntime().exec("netsh wlan show interfaces");
        BufferedReader bReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String line;
        while ((line = bReader.readLine()) != null) {
          if (line.matches("(.*)SSID(.*)4905_Romi(.*)")) {
            System.out.println("SSID line: " + line);
            String[] tokens = line.trim().split("\\s+");
            System.out.println("Romi Robot name = " + tokens[2]);
            m_robotName = tokens[2];
          }
        }
      } catch (IOException e) {
        System.out.println("ERROR: cannot find name of Romi via SSID");
        e.printStackTrace();
      }
      // the next line retrieves the path to the jar file that is being
      // executed. this should be in a standard place in the repo. from there
      // we can find the deploy directory and the configs
      String relativePathToDeploy = "/src/main/deploy";
      String jarDir = System.getProperty("user.dir");
      if (!Files.exists(Paths.get(jarDir + relativePathToDeploy))) {
        System.out.println("ERROR: could not find robot config directory: " + jarDir + relativePathToDeploy);
      }
      // don't look for name.conf file, just use Romi
      m_baseDir = jarDir + "/src/main/";
    }
    m_environmentalConfig = ConfigFactory
        .parseFile(new File(m_baseDir + "deploy/robotConfigs/" + m_robotName + "/robot.conf"));
    m_config = m_environmentalConfig.withFallback(m_defaultConfig).resolve();
    reload();
    System.out.println("Robot name = " + m_robotName);
  }

  public static Config4905 getConfig4905() {
    if (m_config4905 == null) {
      m_config4905 = new Config4905();
    }
    return m_config4905;
  }

  private Config load(String fileName) {
    String filePath = m_baseDir + "deploy/robotConfigs/" + m_robotName + "/" + fileName;
    Config config = ConfigFactory.parseFile(new File(filePath)).withFallback(m_defaultConfig).resolve();
    System.out.println("loaded config " + fileName + " from " + filePath);
    System.out.println(config);
    return config;
  }

  public void reload() {
    commandConstantsConfig = load("commandconstants.conf");
    sensorConfig = load("sensors.conf");
    shooterConfig = load("Shooter.conf");
    intakeConfig = load("Intake.conf");
    feederConfig = load("Feeder.conf");
    drivetrainConfig = load("drivetrain.conf");
    climberConfig = load("Climber.conf");
    ledConfig = load("LED.conf");
    harvesterConfig = load("harvester.conf");
  }

  public Config getClimberConfig() {
    return climberConfig;
  }

  public boolean doesClimberExist() {
    if (m_config.hasPath("subsystems.climber")) {
      return true;
    } else {
      return false;
    }
  }

  public Config getDrivetrainConfig() {
    return drivetrainConfig;
  }

  public boolean doesDrivetrainExist() {
    if (m_config.hasPath("subsystems.driveTrain")) {
      return true;
    } else {
      return false;
    }
  }

  public Config getFeederConfig() {
    return feederConfig;
  }

  public boolean doesFeederExist() {
    if (m_config.hasPath("subsystems.feeder")) {
      return true;
    } else {
      return false;
    }
  }

  public Config getLEDConfig() {
    return ledConfig;
  }

  public boolean doesLEDExist() {
    if (m_config.hasPath("subsystems.LED")) {
      return true;
    } else {
      return false;
    }
  }

  public Config getIntakeConfig() {
    return intakeConfig;
  }

  public boolean doesIntakeExist() {
    if (m_config.hasPath("subsystems.intake")) {
      return true;
    } else {
      return false;
    }
  }

  public Config getShooterConfig() {
    return shooterConfig;
  }

  public boolean doesShooterExist() {
    if (m_config.hasPath("subsystems.shooter")) {
      return true;
    } else {
      return false;
    }
  }

  public boolean doesHarvesterExist() {
    if (m_config.hasPath("subsystems.harvester")) {
      return true;
    } else {
      return false;
    }
  }

  public Config getHarvesterConfig() {
    return harvesterConfig;
  }

  public Config getSensorConfig() {
    return sensorConfig;
  }

  public Config getCommandConstantsConfig() {
    return commandConstantsConfig;
  }
}