package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotState;
import frc.robot.RobotState.BotState;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Slide;

public class SubstationIntake extends CommandBase {

    private Intake m_intake;
    private Slide m_slide;
    private Elevator m_elevator;
    private RobotState m_robotState;

    public SubstationIntake(Intake intake, Slide slide, Elevator elevator, RobotState robotState) {

        m_intake = intake;
        m_slide = slide;
        m_elevator = elevator;
        m_robotState = robotState;

        addRequirements(m_intake, m_slide, m_elevator, m_robotState);

    }

    @Override
    public void execute() {

        m_robotState.setRobotState(BotState.INTAKE_CONE_SUBSTATION);
        m_intake.setIntake(Constants.INTAKE_CONE);

    }
    
}
