package frc.robot;

import frc.robot.subsystems.Elevator.ElevatorState;
import frc.robot.subsystems.Intake.IntakeState;
import frc.robot.subsystems.Slide.SlideState;

import frc.robot.subsystems.Slide;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class RobotState extends SubsystemBase {

    private BotState robotState;
    private boolean m_isConeState;

    public RobotState(Intake intake, Elevator elevator, Slide slide) {

        setBotState(BotState.RESET_CUBE);

    }

    public void setBotState(BotState state) {

        robotState = state;

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

        INTAKE_CONE_SUBSTATION(SlideState.SUBSTATION, ElevatorState.SUBSTATION, true, IntakeState.INTAKE_CONE_SUBSTATION),
        HIGH_CONE(SlideState.CONE_HIGH, ElevatorState.HIGH_CONE, true, IntakeState.CONE_HIGH),
        HIGH_CUBE(SlideState.CUBE_HIGH, ElevatorState.HIGH_CUBE, false, IntakeState.CUBE_HIGH),
        MID_CONE(SlideState.CONE_MID, ElevatorState.MID_CONE, true, IntakeState.CONE_MID),
        MID_CUBE(SlideState.CUBE_MID, ElevatorState.MID_CUBE, false, IntakeState.CUBE_MID),
        LOW_CONE(SlideState.INTAKE, ElevatorState.FLOOR, true, IntakeState.CONE_LOW),
        LOW_CUBE(SlideState.INTAKE, ElevatorState.FLOOR, false, IntakeState.CUBE_LOW),
        RESET_CONE(SlideState.INTAKE, ElevatorState.FLOOR, true, IntakeState.STOP_CONE),
        RESET_CUBE(SlideState.INTAKE, ElevatorState.FLOOR, false, IntakeState.STOP_CUBE),
        INTAKE_CONE_SINGLE(SlideState.SINGLE_SUB, ElevatorState.SINGLE_SUB, true, IntakeState.INTAKE_CONE_SINGLE),
        INTAKE_CONE_FLOOR(SlideState.INTAKE, ElevatorState.FLOOR, true, IntakeState.INTAKE_CONE_GROUND),
        INTAKE_CUBE(SlideState.INTAKE, ElevatorState.FLOOR, false, IntakeState.INTAKE_CUBE_GROUND);
    
        public SlideState slideState;
        public ElevatorState elevatorState;
        public IntakeState intakeState;
        public boolean coneMode;
        private BotState(SlideState slideState, ElevatorState elevatorState, boolean coneMode, IntakeState intakeState) {
            this.intakeState = intakeState;
            this.slideState = slideState;
            this.elevatorState = elevatorState;
            this.coneMode = coneMode;
        }
    
    }

    @Override 
    public void periodic() {

        SmartDashboard.putBoolean("Gamepiece State", getGamepieceState());
        SmartDashboard.putString("Robot State", getRobotState().toString());

    }
    

}