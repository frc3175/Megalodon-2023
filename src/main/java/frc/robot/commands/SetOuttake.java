package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotState;
import frc.robot.subsystems.Intake;

public class SetOuttake extends CommandBase {

    private RobotState m_robotState;
    private Intake m_intake;

    public SetOuttake(Intake intake, RobotState robotState) {

        m_robotState = robotState;
        m_intake = intake;

        addRequirements(robotState, m_intake);

    }

    @Override
    public void execute() {

        if(m_robotState.getGamepieceState()) {

            m_intake.setIntake(Constants.OUTTAKE_CONE);

        } else {

            m_intake.setIntake(Constants.OUTTAKE_CUBE);

        }


    }

    @Override
    public void end(boolean isFinished) {

        m_intake.setIntake(0);

    }

    
}