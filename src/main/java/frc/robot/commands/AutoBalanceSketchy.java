package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.SwerveDrivetrain;

public class AutoBalanceSketchy extends CommandBase {

    private SwerveDrivetrain m_drivetrain;
    private boolean isReady;
    Timer m_timer;

    public AutoBalanceSketchy(SwerveDrivetrain drivetrain) {

        m_drivetrain = drivetrain;
        isReady = false;
        m_timer = new Timer();

       // m_controller = new PIDController(1, 0, 0);

        addRequirements(m_drivetrain);

    }

    @Override 
    public void initialize() {

        isReady = false;
        m_timer.reset();

    }

    @Override
    public void execute() {

        if(!isReady) {

            if(m_drivetrain.getPitch() < 12) {
                m_drivetrain.drive(new Translation2d(-0.8, 0), 0, false, false);
            } else if(m_drivetrain.getPitch() > 12 && m_timer.get() < 2.2) {
                m_timer.start();
            } else if(m_drivetrain.getPitch() > 12 && m_timer.get() > 2.2){
                isReady = true;
            } else {
                m_drivetrain.drive(new Translation2d(-0.8, 0), 0, false, false);
            }

        } else {

            if(m_drivetrain.getPitch() < 12){
                m_drivetrain.stopSwerve();
            } else {
                m_drivetrain.drive(new Translation2d(-0.2, 0), 0, false, false);
            }

        }

        SmartDashboard.putNumber("timer", m_timer.get());
        

    }

}