package frc.robot.commands.pidcommands;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import frc.robot.Config4905;
import frc.robot.Robot;
import frc.robot.pidcontroller.PIDCommand4905;
import frc.robot.pidcontroller.PIDController4905;
import frc.robot.subsystems.shooter.ShooterBase;
import frc.robot.telemetries.Trace;

public class RunShooterSeriesVelocity extends PIDCommand4905 {

  private SimpleMotorFeedforward m_feedForward;
  private static double m_computedFeedForward = 0;
  private ShooterBase m_shooter;
  private double m_setpoint = 0;
  private static Config m_pidConfig;
  private static Config m_shooterConfig;
  private XboxController m_controller;
  private final double kControllerScale;

  /**
   * @param shooter
   * @param m_subsystemController Requires a controller to allow the subsystem
   *                              driver to tune the PID setpoint via the
   *                              controller
   * @param setpoint
   */
  public RunShooterSeriesVelocity(ShooterBase shooter, double setpoint) {
    // PID Controller
    super(createPIDController(),
        // Measurement
        shooter::getShooterSeriesVelocity,
        // Setpoint
        setpoint,
        // Output
        output -> {
          // TODO actually put an encoder on the series wheel
          shooter.setShooterSeriesPower(1);
        });

    getController().setTolerance(m_pidConfig.getDouble("runshooterseriesvelocity.tolerance"));

    m_feedForward = createFeedForward();

    m_shooterConfig = Config4905.getConfig4905().getShooterConfig();

    kControllerScale = m_shooterConfig.getDouble("shooterwheeljoystickscale");
    m_shooter = shooter;
    m_setpoint = setpoint;
    Trace.getInstance().logCommandStart("RunShooterSeriesVelocity");
  }

  @Override
  public void execute() {
    double leftYAxis = Robot.getInstance().getOIContainer().getSubsystemController().getLeftStickForwardBackwardValue();
    // This adjusts the setpoint while the PID is running to allow the
    // Subsystems driver to tune the rpm on the fly
    m_setpoint += leftYAxis * kControllerScale;
    m_shooter.setSeriesPIDIsReady(getController().atSetpoint());
    m_computedFeedForward = m_feedForward.calculate(m_setpoint);
    super.execute();
  }

  @Override
  public void end(boolean interrupt) {
    m_shooter.setShooterSeriesPower(0);
  }

  private static PIDController4905 createPIDController() {
    m_pidConfig = Config4905.getConfig4905().getPidConstantsConfig();

    double kp = m_pidConfig.getDouble("runshooterseriesvelocity.p");
    double ki = m_pidConfig.getDouble("runshooterseriesvelocity.i");
    double kd = m_pidConfig.getDouble("runshooterseriesvelocity.d");

    return new PIDController4905("ShooterSeriesWheelPID", kp, ki, kd, 0);
  }

  private SimpleMotorFeedforward createFeedForward() {
    double ks = m_pidConfig.getDouble("runshooterseriesvelocity.s");
    double kv = m_pidConfig.getDouble("runshooterseriesvelocity.v");

    return new SimpleMotorFeedforward(ks, kv);
  }

}