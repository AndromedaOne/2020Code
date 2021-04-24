// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.actuators;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;

/** Add your docs here. */
public class SparkController extends Spark {
  private boolean m_hasEncoder = false;
  private Encoder m_encoder;

  public SparkController(Config subsystemConfig, String configString) {
    super(subsystemConfig.getInt("ports." + configString));
    System.out.println("Enabling Spark Controller \"" + configString + "\" for port "
        + subsystemConfig.getInt("ports." + configString));
    m_hasEncoder = subsystemConfig.getBoolean(configString + ".hasEncoder");
    if (m_hasEncoder) {
      m_encoder = new Encoder(subsystemConfig.getInt(configString + ".channelA"),
          subsystemConfig.getInt(configString + ".channelB"));
    }
  }

  public boolean hasEncoder() {
    return m_hasEncoder;
  }

  public double getEncoderPositionTicks() {
    if (m_hasEncoder) {
      return (m_encoder.getRaw());
    }
    return (0.0);
  }
}
