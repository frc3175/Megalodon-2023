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
    public void execute() {

        m_intake.setIntake(0);

        if(m_robotState.getGamepieceState()) {

            m_robotState.setRobotState(BotState.LOW_CONE);

        } else {

            m_robotState.setRobotState(BotState.LOW_CUBE);

        }

    }
    
}
