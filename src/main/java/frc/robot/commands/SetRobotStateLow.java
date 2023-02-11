package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotState;
import frc.robot.RobotState.BotState;
import frc.robot.subsystems.Intake;

public class SetRobotStateLow extends CommandBase {

    private RobotState m_robotState;
    private Intake m_intake;

    public SetRobotStateLow(RobotState robotState, Intake intake) {

        m_robotState = robotState;
        m_intake = intake;

        addRequirements(m_robotState, m_intake);

    }

    @Override
    public void initialize() {

        if(m_robotState.getGamepieceState()) {

            m_robotState.setBotState(BotState.LOW_CONE);

        } else {

            m_robotState.setBotState(BotState.LOW_CUBE);

        }

    }

    @Override
    public boolean isFinished() {

        return true;

    }
    
}
