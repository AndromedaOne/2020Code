package frc.robot.sensors.gyro;

import java.util.TimerTask;

import com.kauailabs.navx.frc.AHRS;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.Config4905;

public class RealNavXGyroSensor extends Gyro {
  AHRS gyro; /* Alternatives: SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */

  private long kInitializeDelay = 3000;
  private long kDefaultPeriod = 50;
  private java.util.Timer controlLoop;

  /**
   * Trys creating the gyro and if it can not then it reports an error to the
   * DriveStation.
   */
  public RealNavXGyroSensor() {
    try {
      /* Communicate w/navX MXP via the MXP SPI Bus. */
      /* Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB */
      /*
       * See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for
       * details.
       */
      Config conf = Config4905.getConfig4905().getSensorConfig();
      Config navXConfig = conf.getConfig("navx");
      String navXPort = navXConfig.getString("port");
      System.out.println("Creating a NavX Gyro on port: " + navXPort);
      if (navXPort.equals("MXP")) {
        gyro = new AHRS(SPI.Port.kMXP);
      } else if (navXPort.equals("SPI")) {
        gyro = new AHRS(SPI.Port.kOnboardCS0);
      } else {
        System.err.println("ERROR: Unkown NavX Port: " + navXPort);
        return;
      }
      System.out.println("Created NavX instance");
      // New thread to initialize the initial angle
      controlLoop = new java.util.Timer();
      SetInitialAngleReading task = new SetInitialAngleReading(this);
      controlLoop.schedule(task, kInitializeDelay, kDefaultPeriod);

    } catch (RuntimeException ex) {
      DriverStation.reportError("Error instantiating navX MXP: " + ex.getMessage(), true);
    }
  }

  private class SetInitialAngleReading extends TimerTask {

    RealNavXGyroSensor m_navX;

    public SetInitialAngleReading(RealNavXGyroSensor navX) {
      m_navX = navX;
    }

    @Override
    public void run() {
      System.out.println("Setting Initial Gyro Angle");
      if (!gyro.isCalibrating()) {
        m_navX.setInitialZAngleReading(gyro.getAngle());
        m_navX.setInitialXAngleReading(gyro.getPitch());
        m_navX.setInitialYAngleReading(gyro.getRoll());
        cancel();
      }
    }
  }

  /**
   * Gets the Z angle and subtracts the initial angle member variable from it.
   * 
   * @return gyro.getAngle() - initialAngleReading
   */
  @Override
  protected double getRawZAngle() {
    return gyro.getAngle();
  }

  @Override
  protected double getRawXAngle() {
    return gyro.getPitch();
  }

  @Override
  protected double getRawYAngle() {
    return gyro.getRoll();
  }

}
