package frc.robot.actuators;

import edu.wpi.first.wpilibj.Servo;

public class ServoMotor {

  private Servo m_servoMotor;

  public ServoMotor(int port) {
    m_servoMotor = new Servo(port);
    /*
     * values from motor data sheet
     * https://docs.revrobotics.com/15mm/actuators/servos/smart-robot-servo
     */

    m_servoMotor.setBounds(2.5, m_servoMotor.getRawBounds().deadbandMax, 1.5, m_servoMotor.getRawBounds().deadbandMin,
        0.5);

  }

  public void runForward() {
    m_servoMotor.setSpeed(0.6);
  }

  public void runBackward() {
    m_servoMotor.setSpeed(-0.6);
  }

  public void stop() {
    m_servoMotor.setSpeed(0);
  }

}