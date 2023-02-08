package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotState;
import frc.robot.RobotState.BotState;

public class SetRobotStateHigh extends CommandBase {

    private RobotState m_state;

    public SetRobotStateHigh(RobotState state) {

        m_state = state;

        addRequirements(m_state);

    }

    @Override
    public void execute() {

        if(m_state.getGamepieceState()) {

            m_state.setRobotState(BotState.HIGH_CONE);

        } else {

            m_state.setRobotState(BotState.HIGH_CUBE);

        }

    }
    
}
