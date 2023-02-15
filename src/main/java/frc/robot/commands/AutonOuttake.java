package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotState;
import frc.robot.subsystems.Intake;

public class AutonOuttake extends CommandBase {

    private RobotState m_robotState;
    private Intake m_intake;

    public AutonOuttake(Intake intake, RobotState robotState) {

        m_robotState = robotState;
        m_intake = intake;

        addRequirements(robotState, m_intake);

    }

    @Override
    public void initialize() {
        m_intake.resetIntakeEncoder();
    }

    @Override
    public void execute() {

        if(m_robotState.getGamepieceState()) {

            if(Math.abs(m_intake.getIntakeEncoder()) < 10000) {
                m_intake.setIntake(Constants.OUTTAKE_CONE);
            }

        } else {

            if(Math.abs(m_intake.getIntakeEncoder()) < 10000) {
                m_intake.setIntake(Constants.OUTTAKE_CUBE);
            }

        }


    }

    @Override
    public void end(boolean isFinished) {

        m_intake.setIntake(0);

    }

    @Override
    public boolean isFinished() {

        if(Math.abs(m_intake.getIntakeEncoder()) < 10000) {
            return false;
        } else {
            return true;
        }

    }

    
}