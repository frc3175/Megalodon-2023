package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotState;
import frc.robot.RobotState.BotState;
import frc.robot.subsystems.Intake;

public class SetRobotStateMid extends CommandBase {

    private RobotState m_robotState;
    private Intake m_intake;

    public SetRobotStateMid(RobotState robotState, Intake intake) {

        m_robotState = robotState;
        m_intake = intake;

        addRequirements(m_robotState, m_intake);

    }

    @Override
    public void initialize() {

        if(m_robotState.getGamepieceState()) {

            m_robotState.setBotState(BotState.MID_CONE);

        } else {

            m_robotState.setBotState(BotState.MID_CUBE);

        }

    }

    @Override
    public boolean isFinished() {

        return true;

    }
    
}
