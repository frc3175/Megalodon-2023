package frc.robot;

import frc.robot.subsystems.Elevator.ElevatorState;
import frc.robot.subsystems.Intake.IntakeState;
import frc.robot.subsystems.Slide.SlideState;

import frc.robot.subsystems.Slide;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class RobotState extends SubsystemBase {

    private Slide m_slide;
    private Elevator m_elevator;
    private Intake m_intake;
    private BotState robotState;
    private boolean m_isConeState;

    public RobotState(Intake intake, Elevator elevator, Slide slide) {

        m_intake = intake;
        m_slide = slide;
        m_elevator = elevator;

    }

    public void setRobotState(BotState state) {

        robotState = state;

        m_slide.setSlideState(state.slideState);
        m_elevator.setElevatorState(state.elevatorState);

    }

    public BotState getRobotState() {

        return robotState;

    }

    public void setGamepieceState(boolean isConeState) {

        m_isConeState = isConeState;

    }

    public boolean getGamepieceState() {

        return m_isConeState;

    }

    public enum BotState {

        INTAKE_CONE_SUBSTATION(IntakeState.INTAKE_CONE, SlideState.SUBSTATION, ElevatorState.SUBSTATION, true),
        INTAKE_CUBE_FLOOR(IntakeState.INTAKE_CUBE, SlideState.INTAKE, ElevatorState.FLOOR, false),
        INTAKE_CONE_FLOOR(IntakeState.INTAKE_CONE, SlideState.INTAKE, ElevatorState.FLOOR, true),
        INTAKE_CUBE_SUBSTATION(IntakeState.INTAKE_CUBE, SlideState.SUBSTATION, ElevatorState.SUBSTATION, false),
        HIGH_CONE(IntakeState.CONE_HIGH, SlideState.CONE_HIGH, ElevatorState.HIGH_CONE, true),
        HIGH_CUBE(IntakeState.CUBE_HIGH, SlideState.CUBE_HIGH, ElevatorState.HIGH_CUBE, false),
        MID_CONE(IntakeState.CONE_MID, SlideState.CONE_MID, ElevatorState.MID_CONE, true),
        MID_CUBE(IntakeState.CUBE_MID, SlideState.CUBE_MID, ElevatorState.MID_CUBE, false),
        LOW_CONE(IntakeState.CONE_LOW, SlideState.INTAKE, ElevatorState.FLOOR, true),
        LOW_CUBE(IntakeState.CUBE_LOW, SlideState.INTAKE, ElevatorState.FLOOR, false),
        RESET(IntakeState.STOP, SlideState.INTAKE, ElevatorState.FLOOR, false);
    
        public IntakeState intakeState;
        public SlideState slideState;
        public ElevatorState elevatorState;
        public boolean coneMode;
        private BotState(IntakeState intakeState, SlideState slideState, ElevatorState elevatorState, boolean coneMode) {
            this.intakeState = intakeState;
            this.slideState = slideState;
            this.elevatorState = elevatorState;
            this.coneMode = coneMode;
        }
    
    }
    

}