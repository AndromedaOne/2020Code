package frc.robot.commands;

import java.lang.module.ModuleDescriptor.Requires;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ResetGyro extends CommandBase{

    public ResetGyro() {
        addRequirements(Robot.getInstance().getSubsystemsContainer().getDrivetrain());
    }
    
    @Override
    public void initialize() {
        super.initialize();
        Robot.getInstance().getSensorsContainer().getGyro().reset();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
