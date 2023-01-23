package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Constants;

public class Elevator {

    private TalonFX elevMotor;

    public Elevator() {

        elevMotor = new TalonFX(Constants.ELEVATOR_MOTOR);
        elevMotor.setNeutralMode(Constants.ELEVATOR_NEUTRAL_MODE);
        elevMotor.setInverted(Constants.ELEVATOR_INVERTED);

    }

    public void setElevatorSetpoint(double setpoint) {

        elevMotor.set(ControlMode.Position, setpoint);

    }

    public void resetElevatorEncoder() {

        elevMotor.setSelectedSensorPosition(0);

    }

    public double getElevatorEncoder() {

        return elevMotor.getSelectedSensorPosition();

    }

    public void setElevatorState(ElevatorState state) {

        switch(state){

            case FLOOR:
                setElevatorSetpoint(Constants.ELEVATOR_DOWN);
                break;
            case CLEAR_BUMPER:
                setElevatorSetpoint(Constants.CLEAR_BUMPER);
                break;
            case SUBSTATION:
                setElevatorSetpoint(Constants.SUBSTATION);
                break;
            case MID_CUBE:
                setElevatorSetpoint(Constants.CUBE_MID);
                break;
            case MID_CONE:
                setElevatorSetpoint(Constants.CONE_MID);
                break;
            case HIGH_CUBE:
                setElevatorSetpoint(Constants.CUBE_HIGH);
                break;
            case HIGH_CONE:
                setElevatorSetpoint(Constants.CONE_HIGH);
                break;

        }

    }

    public enum ElevatorState {

        FLOOR,
        CLEAR_BUMPER,
        SUBSTATION,
        MID_CUBE,
        MID_CONE,
        HIGH_CUBE,
        HIGH_CONE

    }
    
}
