package frc.robot.drivetrainpathgeneration.pathgenerators;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.pidcommands.MoveUsingEncoder;
import frc.robot.commands.pidcommands.TurnToCompassHeading;
import frc.robot.drivetrainpathgeneration.waypoints.Waypoint;
import frc.robot.drivetrainpathgeneration.waypoints.WaypointsBase;
import frc.robot.subsystems.drivetrain.DriveTrain;

public class RectangularPathGenerator extends PathGeneratorBase {

    private double m_currentX;
    private double m_currentY;
    private SequentialCommandGroup m_path;
    private DriveTrain m_driveTrain;

    public RectangularPathGenerator(DriveTrain drivetrain, WaypointsBase waypoints) {
        super(waypoints);
        m_currentX = 0;
        m_currentY = 0;
        m_path = new SequentialCommandGroup();
        m_driveTrain = drivetrain;
    }

    @Override
    protected void generatePathForNextWaypoint(Waypoint waypoint) {
        double deltaY = waypoint.getY() - m_currentY;
        double deltaX = waypoint.getX() - m_currentX;

        m_path.addCommands(
            new TurnToCompassHeading(0),
            new MoveUsingEncoder(m_driveTrain, deltaY),
            new TurnToCompassHeading(90),
            new MoveUsingEncoder(m_driveTrain, deltaX)
        );

        m_currentX += deltaX;
        m_currentY += deltaY;
    }

    @Override
    protected CommandBase getGeneratedPath() {
        // TODO Auto-generated method stub
        return m_path;
    }

    
    
}
