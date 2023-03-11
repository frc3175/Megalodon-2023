package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.SwerveDrivetrain;

public class AutoBalance extends CommandBase {

    private SwerveDrivetrain m_drivetrain;

    private PIDController m_controller;
    private boolean isDone;

    public AutoBalance(SwerveDrivetrain drivetrain) {

        isDone = false;
        m_drivetrain = drivetrain;

       // m_controller = new PIDController(1, 0, 0);

        addRequirements(m_drivetrain);

    }

    @Override
    public void execute() {

        if(m_drivetrain.getPitch() < 12){
            m_drivetrain.stopSwerve();
        } else {
            m_drivetrain.drive(new Translation2d(-0.2, 0), 0, false, false);
        }

    } 
    
}
