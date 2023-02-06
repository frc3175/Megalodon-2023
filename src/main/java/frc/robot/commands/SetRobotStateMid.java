package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotState;
import frc.robot.RobotState.BotState;

public class SetRobotStateMid extends CommandBase {

    private RobotState m_robotState;

    public SetRobotStateMid(RobotState robotState) {

        m_robotState = robotState;

        addRequirements(m_robotState);

    }

    @Override
    public void execute() {

        if(m_robotState.getGamepieceState()) {

            m_robotState.setRobotState(BotState.MID_CONE);

        } else {

            m_robotState.setRobotState(BotState.MID_CUBE);

        }

    }
    
}
