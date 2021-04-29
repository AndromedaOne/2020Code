package frc.robot.commands;

import java.util.Arrays;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class RunRomiIntakeAtTime extends CommandBase{

    double[] m_timesToStartRunning;
    double m_intervalToRun;
    double m_maxTime;
    Timer m_timer;
        
    public RunRomiIntakeAtTime(double intervalToRun, double... timesToStartRunning) {
        m_timesToStartRunning = timesToStartRunning;
        m_intervalToRun = intervalToRun;
        m_timer = new Timer();
        m_maxTime = Arrays.stream(m_timesToStartRunning).max().getAsDouble();
    }

    @Override
    public void initialize() {
        super.initialize();
        m_timer.reset();
        m_timer.start();
    }
    @Override
    public void execute() {
        boolean shouldIntakeBeRunning = Arrays.stream(m_timesToStartRunning).anyMatch((time) -> m_timer.hasElapsed(time) && !m_timer.hasElapsed(time + m_intervalToRun));
        if(shouldIntakeBeRunning) {
            Robot.getInstance().getSubsystemsContainer().getRomiIntake().runBackward();
        }else {
            Robot.getInstance().getSubsystemsContainer().getRomiIntake().stop();
        }
    }

    @Override
    public boolean isFinished() {
        return m_timer.hasElapsed(m_maxTime + m_intervalToRun);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        Robot.getInstance().getSubsystemsContainer().getRomiIntake().stop();
    }
    
}
