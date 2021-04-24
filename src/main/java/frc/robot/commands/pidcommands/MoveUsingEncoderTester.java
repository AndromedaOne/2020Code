package frc.robot.commands.pidcommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ConfigReload;
import frc.robot.subsystems.drivetrain.DriveTrain;
import frc.robot.telemetries.Trace;

public class MoveUsingEncoderTester extends SequentialCommandGroup {
  private MoveUsingEncoder m_command;

  public MoveUsingEncoderTester(DriveTrain drivetrain) {
    super();
    m_command = new MoveUsingEncoder(drivetrain,
        SmartDashboard.getNumber("MoveUsingEncoderTester Distance To Move", 12), 1);
    addCommands(new ConfigReload(), m_command);
  }

  @Override
  public void initialize() {
    super.initialize();
    m_command.setDistance(SmartDashboard.getNumber("MoveUsingEncoderTester Distance To Move", 12));
    Trace.getInstance().logCommandStart(this);
    Trace.getInstance().logCommandInfo(this, "Moving distance: " + m_command.getSetpoint() + " inches");
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    Trace.getInstance().logCommandStop(this);
  }

}
