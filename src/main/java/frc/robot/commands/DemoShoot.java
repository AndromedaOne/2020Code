package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.feeder.FeederBase;
import frc.robot.subsystems.shooter.ShooterBase;

public class DemoShoot extends CommandBase {

  private FeederBase m_feeder;
  private ShooterBase m_shooter;
  private Timer m_timer;
  public static final double TIME_BEFORE_SHOOTING = 1.0;
  public static final double SPEED_OF_FEEDER = 0.5;
  public static final double SHOOTER_WHEEL_SPEED = 0.5;
  public static final double SERIES_WHEEL_SPEED = SHOOTER_WHEEL_SPEED;

  public DemoShoot(FeederBase feeder, ShooterBase shooter) {
    m_feeder = feeder;
    m_shooter = shooter;
    m_timer = new Timer();
  }

  @Override
  public void initialize() {
    super.initialize();
    m_timer.reset();
    m_timer.start();
  }

  @Override
  public void execute() {
    super.execute();
    if (m_timer.hasElapsed(TIME_BEFORE_SHOOTING)) {
      m_feeder.runBothStages(SPEED_OF_FEEDER, SPEED_OF_FEEDER);
    }

    m_shooter.setShooterSeriesPower(SERIES_WHEEL_SPEED);
    m_shooter.setShooterWheelPower(SHOOTER_WHEEL_SPEED);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    m_feeder.stopBothStages();
    m_shooter.setShooterSeriesPower(0);
    m_shooter.setShooterWheelPower(0);
  }

}
