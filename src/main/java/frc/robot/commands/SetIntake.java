package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotState;
import frc.robot.RobotState.BotState;
import frc.robot.subsystems.Intake;

public class SetIntake extends CommandBase {

    private RobotState m_robotState;
    private Intake m_intake;

    public SetIntake(Intake intake, RobotState robotState) {

        m_robotState = robotState;
        m_intake = intake;

        addRequirements(robotState, m_intake);

    }

    @Override
    public void initialize() {

        if(m_robotState.getGamepieceState()) {

            if(m_intake.isSingleConeState()) {

                m_robotState.setBotState(BotState.INTAKE_CONE_SINGLE);
                m_intake.setIntake(Constants.INTAKE_CONE);

            } else {

                m_robotState.setBotState(BotState.INTAKE_CONE_FLOOR);
                m_intake.setIntake(Constants.INTAKE_CONE);

            }
            


        } else {

            m_robotState.setBotState(BotState.INTAKE_CUBE);
            m_intake.setIntake(Constants.INTAKE_CUBE);

        }

    }

    public boolean isFinished() {
        return true;
    }

    
}
