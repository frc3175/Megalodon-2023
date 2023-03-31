package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDrivetrain;

public class TurnTo180 extends CommandBase {

    private SwerveDrivetrain m_drivetrain;
    private Timer m_timer;

    public TurnTo180(SwerveDrivetrain drivetrain) {

        m_drivetrain = drivetrain;

        addRequirements(m_drivetrain);

    }

    @Override
    public void execute() {

        m_timer.start();

        if(m_timer.get() < 10) {

            m_drivetrain.drive(new Translation2d(0, 0), 0.5, false, false, false);

        }

    }
    
    
}
