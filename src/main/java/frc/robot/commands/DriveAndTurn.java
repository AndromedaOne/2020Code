package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.oi.DriveController;
import frc.robot.subsystems.drivetrain.DriveTrain;

public class DriveAndTurn extends CommandBase{
    DriveTrain m_driveTrain;
    int count = 0;

    public DriveAndTurn() {
        m_driveTrain = Robot.getInstance().getSubsystemsContainer().getDrivetrain();
        addRequirements(m_driveTrain);
    }

    @Override
    public void initialize() {
        super.initialize();
        count = 0;

    }

    @Override
    public void execute() {
        count++;
        double modifier = countModifier(count);
        m_driveTrain.move(0.3, 0.1 + modifier, false);
    }

    private double countModifier(int count) {
        return count * 0.0;
    }

    @Override
    public void end(boolean interrupted) {
        m_driveTrain.stop();
    }
}
