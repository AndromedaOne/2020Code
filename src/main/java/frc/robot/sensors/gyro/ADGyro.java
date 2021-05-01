package frc.robot.sensors.gyro;

import com.analog.adis16470.frc.ADIS16470_IMU;

public class ADGyro extends Gyro4905 {

    private ADIS16470_IMU m_imu;
    public ADGyro() {
        m_imu = new ADIS16470_IMU();
    }

    @Override
    public void calibrate() {
        m_imu.calibrate();
    }

    @Override
    public void reset() {
        m_imu.reset();

    }

    @Override
    public double getAngle() {
        return -getRawZAngle();
    }

    @Override
    public double getRate() {
        return m_imu.getRate();
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    protected double getRawZAngle() {
        return m_imu.getGyroInstantZ();
    }

    @Override
    protected double getRawXAngle() {
        return m_imu.getGyroInstantX();
    }

    @Override
    protected double getRawYAngle() {
        return m_imu.getGyroInstantY();
    }
    
}
