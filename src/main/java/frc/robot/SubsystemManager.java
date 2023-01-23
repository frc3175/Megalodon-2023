package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Slide;

public class SubsystemManager extends CommandBase {

    private RobotState m_state;
    private Intake m_intake;
    private Slide m_slide;
    private Elevator m_elevator;

    public SubsystemManager(Intake intake, Slide slide, Elevator elevator, RobotState state) {

        m_intake = intake;
        m_slide = slide;
        m_elevator = elevator;
        m_state = state;

        addRequirements(m_intake, m_slide, m_elevator);
        
    }

    @Override
    public void initialize() {

        m_intake.setIntakeState(m_state.intakeState);
        m_slide.setSlideState(m_state.slideState);
        m_elevator.setElevatorState(m_state.elevatorState);

    }

}
