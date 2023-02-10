package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotState;
import frc.robot.RobotState.BotState;
import frc.robot.subsystems.Intake;

public class SubstationIntake extends CommandBase {

    private Intake m_intake;
    private RobotState m_robotState;

    public SubstationIntake(Intake intake, RobotState robotState) {

        m_intake = intake;
        m_robotState = robotState;

        addRequirements(m_intake, m_robotState);

    }

    @Override
    public void execute() {

        m_robotState.setBotState(BotState.INTAKE_CONE_SUBSTATION);
        m_intake.setIntake(Constants.INTAKE_CONE);

    }
    
}
