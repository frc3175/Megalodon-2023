package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotState;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.SwerveDrivetrain;

public class AutoBalance extends CommandBase {

    private SwerveDrivetrain m_drivetrain;
    private RobotState m_robotState;
    private Intake m_intake;
 
    public AutoBalance(SwerveDrivetrain drivetrain, RobotState robotState, Intake intake) {

        m_drivetrain = drivetrain;
        m_robotState = robotState;
        m_intake = intake;

       // m_controller = new PIDController(1, 0, 0);

        addRequirements(m_drivetrain);

    }

    @Override
    public void execute() {

        //NOTE TO IZZY: MAKE SURE TO CHANGE IN ISFINISHED TOO!!!!! 
        if(m_drivetrain.getPitch() < 12){
            m_drivetrain.stopSwerve();
        } else {
            m_drivetrain.drive(new Translation2d(-0.2, 0), 0, false, false, false);
        }

    }
    
    @Override
    public boolean isFinished() {

        if(m_drivetrain.getPitch() < 12){
            return true;
        } else {
            return false;
        }

    }
    
}
