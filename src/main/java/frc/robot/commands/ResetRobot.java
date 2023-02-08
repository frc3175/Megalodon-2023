package frc.robot.commands;

import frc.robot.RobotState;
import frc.robot.RobotState.BotState;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ResetRobot extends CommandBase {

    private RobotState m_robotState;
    
    public ResetRobot(RobotState robotState) {

        m_robotState = robotState;

        addRequirements(m_robotState);

    }

    @Override
    public void execute() {

        if(m_robotState.getGamepieceState()) {

            m_robotState.setRobotState(BotState.RESET_CONE);

        } else {

            m_robotState.setRobotState(BotState.RESET_CUBE);

        }
        

    }

}
