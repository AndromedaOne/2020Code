package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ResetGyro extends CommandBase{
    
    @Override
    public void initialize() {
        super.initialize();
        Robot.getInstance().getSensorsContainer().getGyro().reset();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
