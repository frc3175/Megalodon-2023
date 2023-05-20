package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDrivetrain;

public class SingleSubLineup extends CommandBase {

    private SwerveDrivetrain m_swerveDrivetrain;

    private Timer m_timer = new Timer();

    public SingleSubLineup(SwerveDrivetrain swerveDrivetrain) {

        m_swerveDrivetrain = swerveDrivetrain;

        addRequirements(m_swerveDrivetrain);

    }

    public void initialize() {

        m_timer.reset();
        m_timer.start();

    }

    public void execute() {

        // double xPose = m_swerveDrivetrain.getPose().getX();
        // double yPose = m_swerveDrivetrain.getPose().getY();

        // m_swerveDrivetrain.drive(new Translation2d(xPose, yPose - 0.2), 0, false, true, false);
    
            m_swerveDrivetrain.drive(new Translation2d(-1, 0), 0, false, true, false);

    }

    @Override
    public boolean isFinished() {

        if(m_timer.get() < 0.2) {
            return false;
        } else {
            m_swerveDrivetrain.stopSwerve();
            return true;
        }
        

    }
    
}
