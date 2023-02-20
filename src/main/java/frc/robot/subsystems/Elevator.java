package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.math.Conversions;
import frc.robot.CTREConfigs;
import frc.robot.Constants;

public class Elevator extends SubsystemBase {

    private TalonFX elevMotor;

    private ElevatorState elevState;

    public Elevator() {

        elevMotor = new WPI_TalonFX(Constants.ELEVATOR_MOTOR, "elevatoryiboi");
        
        configElevMotor();

        resetElevatorEncoder();

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

        setElevatorSetpoint(state.elevSetpoint);
        elevState = state;

    }

    public void setElevatorSpeeed(double speed) {


        elevMotor.set(ControlMode.PercentOutput, speed);

    }

    public ElevatorState getElevatorState() {

        return elevState;

    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("elevator encoder", getElevatorEncoder());
        SmartDashboard.putNumber("elevator speed", (Conversions.falconToRPM(elevMotor.getSelectedSensorVelocity(), 15) / 6380));
        SmartDashboard.putNumber("elevator current draw", elevMotor.getSupplyCurrent());

    }

    public enum ElevatorState {

        FLOOR(Constants.ELEVATOR_DOWN),
        CLEAR_BUMPER(Constants.CLEAR_BUMPER),
        SUBSTATION(Constants.SUBSTATION),
        MID_CUBE(Constants.CUBE_MID),
        MID_CONE(Constants.CONE_MID),
        HIGH_CUBE(Constants.CUBE_HIGH),
        HIGH_CONE(Constants.CONE_HIGH);

        public final int elevSetpoint;
        private ElevatorState(int setpoint) {
            this.elevSetpoint = setpoint;
        }

    }

    public void configElevMotor() {
        elevMotor.configFactoryDefault();
        elevMotor.configAllSettings(CTREConfigs.elevatorFXConfig);
        elevMotor.setInverted(Constants.ELEVATOR_INVERTED);
        elevMotor.setNeutralMode(Constants.ELEVATOR_NEUTRAL_MODE);
    }
    
}
