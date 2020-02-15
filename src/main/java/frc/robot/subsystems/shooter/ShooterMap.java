package frc.robot.subsystems.shooter;

import java.util.Iterator;
import java.util.List;

import com.typesafe.config.Config;

import frc.robot.Config4905;
import frc.robot.lib.interpolate.InterpolatingDouble;
import frc.robot.lib.interpolate.InterpolatingTreeMap;

public class ShooterMap {
  private static InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> m_shooterMap = new InterpolatingTreeMap<>();
  private List<Double> m_shootMapList;

  public ShooterMap() {
    Config m_shooterConfig = Config4905.getConfig4905().getShooterConfig();

    m_shootMapList = m_shooterConfig.getDoubleList("shootingmap");

    Iterator<Double> it = m_shootMapList.iterator();

    while (it.hasNext()) {
      double nextValue = it.next();
      if (!it.hasNext()) {
        System.err.println("WARN: Uneven Number of ShooterMap Values in Config");
        break;
      }
      m_shooterMap.put(new InterpolatingDouble(nextValue), new InterpolatingDouble(it.next()));
    }
  }

  public InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> getShooterMap() {
    return m_shooterMap;
  }
}