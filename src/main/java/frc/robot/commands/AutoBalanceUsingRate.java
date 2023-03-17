package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDrivetrain;

public class AutoBalanceUsingRate extends CommandBase {

    private SwerveDrivetrain m_drivetrain;

    public AutoBalanceUsingRate(SwerveDrivetrain drivetrain) {

        m_drivetrain = drivetrain;

        addRequirements(m_drivetrain);

    }

    @Override
    public void execute() {

        //THIS IS RUN USING THE A BUTTON

        //TODO: This is where you tune the auto balance using rate of change. The number in the parentheses that is being
            //  compared to getPitchDerivative() is the number that you should be tuning.
        if(m_drivetrain.getPitchDerivative() < (-1 * 0.02)) {
            m_drivetrain.stopSwerve();
        } else {
            m_drivetrain.drive(new Translation2d(-0.2, 0), 0, false, false);
        }

    } 
    
}
