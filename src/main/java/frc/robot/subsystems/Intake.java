package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.CTREConfigs;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

    private TalonFX intakeMotor;
    private TalonFX wristMotor;

    private IntakeState intakeState;
    private IntakeState lastIntakeState;
    private IntakeState lastLastIntakeState;

    public Intake() {

        intakeMotor = new TalonFX(Constants.INTAKE_MOTOR, "elevatoryiboi");
        wristMotor = new TalonFX(Constants.INTAKE_WRIST, "elevatoryiboi");

        configWristMotor();
        configIntakeMotor();

        wristMotor.setSelectedSensorPosition(0);

        lastIntakeState = IntakeState.STOP;
        lastLastIntakeState = IntakeState.STOP;

    } 

    public void setIntake(double speed) {

        intakeMotor.set(ControlMode.PercentOutput, speed);

    }

    public void setIntakeState(IntakeState state) {

        setWristPosition(state.wristPosition);

        lastLastIntakeState = lastIntakeState;
        lastIntakeState = intakeState;
        intakeState = state;

    }

    public void setWristPosition(double position) {

        wristMotor.set(ControlMode.Position, position);

    }

    public double getWristPosition() {

        return wristMotor.getSelectedSensorPosition();

    }

    public IntakeState getIntakeState() {

        return intakeState;

    }

    public void continuousWristMotion(double speed) {

        wristMotor.set(ControlMode.PercentOutput, speed);

    }

    public enum IntakeState {

        INTAKE_CUBE(Constants.WRIST_INTAKE_CUBE),
        INTAKE_CUBE_GROUND(Constants.WRIST_INTAKE_CUBE_FLOOR),
        INTAKE_CONE_GROUND(Constants.WRIST_INTAKE_CONE_FLOOR),
        INTAKE_CONE_SUBSTATION(Constants.WRIST_INTAKE_CONE_SUBSTATION),
        CONE_HIGH(Constants.WRIST_OUTTAKE_HIGH_CONE),
        CONE_MID(Constants.WRIST_OUTTAKE_MID_CONE),
        CUBE_HIGH(Constants.WRIST_OUTTAKE_HIGH_CUBE),
        CUBE_MID(Constants.WRIST_OUTTAKE_MID_CUBE),
        CONE_LOW(Constants.WRIST_OUTTAKE_LOW_CONE),
        CUBE_LOW(Constants.WRIST_OUTTAKE_LOW_CUBE),
        STOP(Constants.RESET_WRIST);

        public double wristPosition;
        private IntakeState(double wristPosition){
            this.wristPosition = wristPosition;
        }
 
    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("wrist encoder", wristMotor.getSelectedSensorPosition());

        if(lastIntakeState == IntakeState.INTAKE_CONE_SUBSTATION || lastIntakeState == IntakeState.INTAKE_CONE_GROUND || lastLastIntakeState == IntakeState.INTAKE_CONE_SUBSTATION || lastLastIntakeState == IntakeState.INTAKE_CONE_GROUND) {
            setIntake(0.2);
        } else if(intakeState == IntakeState.STOP) {
            setIntake(0);
        }

    }

    public void configIntakeMotor() {
        intakeMotor.configFactoryDefault();
        intakeMotor.configAllSettings(CTREConfigs.intakeFXConfig);
        intakeMotor.setInverted(Constants.INTAKE_INVERTED);
        intakeMotor.setNeutralMode(Constants.INTAKE_NEUTRAL_MODE);
    }

    public void configWristMotor() {
        wristMotor.configFactoryDefault();
        wristMotor.configAllSettings(CTREConfigs.wristFXConfig);
        wristMotor.setInverted(Constants.WRIST_INVERTED);
        wristMotor.setNeutralMode(Constants.WRIST_NEUTRAL_MODE);
    }



}