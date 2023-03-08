package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.SwerveDrivetrain;

public class AutoBalance extends CommandBase {

    private SwerveDrivetrain m_drivetrain;

    public AutoBalance(SwerveDrivetrain drivetrain) {

        m_drivetrain = drivetrain;

        addRequirements(m_drivetrain);

    }

    @Override
    public void execute() {

        if(m_drivetrain.getPitch() > 5) {
            m_drivetrain.drive(new Translation2d(0.1, 0).times(Constants.MAX_SPEED), 0, true, false);
        } else if(m_drivetrain.getPitch() < 5) {
            m_drivetrain.drive(new Translation2d(-0.1, 0).times(Constants.MAX_SPEED), 0, true, false);
        } else {
            m_drivetrain.stopSwerve();
        }

    }
    
}
