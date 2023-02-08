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
    public void execute() {

        BotState state = m_robotState.getRobotState();

        if(state == BotState.RESET) {

            if(m_robotState.getGamepieceState()) {

                m_intake.setWristPosition(Constants.WRIST_INTAKE_CONE_FLOOR);
                m_intake.setIntake(Constants.INTAKE_CONE);

            } else {

                m_intake.setWristPosition(Constants.WRIST_INTAKE_CUBE_FLOOR);
                m_intake.setIntake(Constants.INTAKE_CUBE);

            }

        } else {

            m_intake.setWristPosition(state.intakeState.wristPosition);
            m_intake.setIntake(state.intakeState.intakeSpeed);

        }


    }

    
}
