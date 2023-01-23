package frc.robot;

import frc.robot.subsystems.Elevator.ElevatorState;
import frc.robot.subsystems.Intake.IntakeState;
import frc.robot.subsystems.Slide.SlideState;

public enum RobotState {

    INTAKE_CONE_SUBSTATION(IntakeState.INTAKE_CONE, SlideState.SUBSTATION, ElevatorState.SUBSTATION),
    INTAKE_CUBE_FLOOR(IntakeState.INTAKE_CUBE, SlideState.INTAKE, ElevatorState.FLOOR),
    INTAKE_CONE_FLOOR(IntakeState.INTAKE_CONE, SlideState.INTAKE, ElevatorState.FLOOR),
    INTAKE_CUBE_SUBSTATION(IntakeState.INTAKE_CUBE, SlideState.SUBSTATION, ElevatorState.SUBSTATION),
    HIGH_CONE(IntakeState.CONE_HIGH, SlideState.CONE_HIGH, ElevatorState.HIGH_CONE),
    HIGH_CUBE(IntakeState.CUBE_HIGH, SlideState.CUBE_HIGH, ElevatorState.HIGH_CUBE),
    MID_CONE(IntakeState.CONE_MID, SlideState.CONE_MID, ElevatorState.MID_CONE),
    MID_CUBE(IntakeState.CUBE_MID, SlideState.CUBE_MID, ElevatorState.MID_CUBE),
    LOW_CONE(IntakeState.CONE_LOW, SlideState.INTAKE, ElevatorState.FLOOR),
    LOW_CUBE(IntakeState.CUBE_LOW, SlideState.INTAKE, ElevatorState.FLOOR),
    RESET(IntakeState.STOP, SlideState.INTAKE, ElevatorState.FLOOR);

    public IntakeState intakeState;
    public SlideState slideState;
    public ElevatorState elevatorState;
    private RobotState(IntakeState intakeState, SlideState slideState, ElevatorState elevatorState) {
        this.intakeState = intakeState;
        this.slideState = slideState;
        this.elevatorState = elevatorState;
    }

}
