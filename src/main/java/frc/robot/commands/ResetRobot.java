package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.RobotState;
import frc.robot.RobotState.BotState;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ResetRobot extends CommandBase {

    private RobotState m_robotState;
    private Intake m_intake;
    
    public ResetRobot(RobotState robotState, Intake intake) {

        m_robotState = robotState;
        m_intake = intake;

        addRequirements(m_robotState, m_intake);

    }

    @Override
    public void execute() {

        if(m_robotState.getGamepieceState()) {

            m_robotState.setRobotState(BotState.RESET_CONE);
            m_intake.setIntake(Constants.HOLD_VOLTAGE);

        } else {

            m_robotState.setRobotState(BotState.RESET_CUBE);
            m_intake.setIntake(Constants.INTAKE_STOP);

        }
        
        
    }

}
