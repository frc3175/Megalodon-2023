package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotState;
import frc.robot.RobotState.BotState;
import frc.robot.subsystems.Intake;

public class SetRobotStateHigh extends CommandBase {

    private RobotState m_state;
    private Intake m_intake;

    public SetRobotStateHigh(RobotState state, Intake intake) {

        m_state = state;
        m_intake = intake;

        addRequirements(m_state, m_intake);

    }

    @Override
    public void initialize() {

        if(m_state.getGamepieceState()) {

            m_state.setBotState(BotState.HIGH_CONE);

        } else {

            m_state.setBotState(BotState.HIGH_CUBE);

        }

    }

    @Override
    public boolean isFinished() {

        return true;

    }
    
}
