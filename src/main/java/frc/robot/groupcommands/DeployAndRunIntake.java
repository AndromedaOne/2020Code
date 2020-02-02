/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.groupcommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ExtendIntake;
import frc.robot.commands.RunIntake;
import frc.robot.sensors.ballfeedersensor.BallFeederSensorBase;
import frc.robot.subsystems.intake.IntakeBase;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DeployAndRunIntake extends SequentialCommandGroup {
  /**
   * Creates a new DeployAndRunIntake.
   */
  private IntakeBase m_intakeBase;
  private BallFeederSensorBase m_ballFeederSensorBase;

  public DeployAndRunIntake(IntakeBase intakeBase, BallFeederSensorBase ballFeederSensorBase) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super();
    m_intakeBase = intakeBase;
    m_ballFeederSensorBase = ballFeederSensorBase;
  }

  @Override
  public void initialize() {
    // TODO Auto-generated method stub
    super.initialize();
    if (m_ballFeederSensorBase.getNumberOfPowerCellsInFeeder() < 5) {
      addCommands(new ExtendIntake(m_intakeBase), new RunIntake(m_intakeBase));
    }
  }
}
