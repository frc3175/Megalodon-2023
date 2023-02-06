package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotState;
import frc.robot.RobotState.BotState;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Intake.IntakeState;

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
        IntakeState intakeState = state.intakeState;

        m_intake.setIntakeState(intakeState);

    }

    
}
