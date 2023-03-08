package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.SwerveDrivetrain;

public class AutoBalance extends CommandBase {

    private SwerveDrivetrain m_drivetrain;

    private PIDController m_controller;

    public AutoBalance(SwerveDrivetrain drivetrain) {

        m_drivetrain = drivetrain;

       // m_controller = new PIDController(1, 0, 0);

        addRequirements(m_drivetrain);

    }

    @Override
    public void execute() {

        /*if(m_drivetrain.getPitch() > 3 || m_drivetrain.getPitchDerivative() > (15/0.02)) {
            m_drivetrain.drive(new Translation2d(0.1, 0), 0, true, false);
        } else if(m_drivetrain.getPitch() < -3 || m_drivetrain.getPitchDerivative() < (-15/0.02)) {
            m_drivetrain.drive(new Translation2d(-0.1, 0), 0, true, false);
        } else {
            m_drivetrain.stopSwerve();
        } */

        if(m_drivetrain.getPitch() > 5 || m_drivetrain.getPitchDerivative() > (12/0.02)) {
            m_drivetrain.drive(new Translation2d(0.3, 0), 0, true, false);
        }  else {
            m_drivetrain.driveBack();
        }

       /*  if(m_drivetrain.getPitchDerivative() < (15/0.02)) {
            m_drivetrain.drive(new Translation2d(0.1, 0), 0, true, false);
        } else if(m_drivetrain.getPitchDerivative() > (-15/0.02)) {
            m_drivetrain.drive(new Translation2d(-0.1, 0), 0, true, false);
        } else {
            m_drivetrain.stopSwerve();
        }  */

        /*double input = m_controller.calculate(m_drivetrain.getPitch(), 0);
        m_controller.setTolerance(3);

        m_drivetrain.drive(new Translation2d(-input, 0), 0, true, false); */

    } 
    
}
