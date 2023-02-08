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

    public Intake() {

        intakeMotor = new TalonFX(Constants.INTAKE_MOTOR, "elevatoryiboi");
        wristMotor = new TalonFX(Constants.INTAKE_WRIST, "elevatoryiboi");

        configWristMotor();
        configIntakeMotor();

        wristMotor.setSelectedSensorPosition(0);

    } 

    public void setIntake(double speed) {

        intakeMotor.set(ControlMode.PercentOutput, speed);

    }

    public void setIntakeState(IntakeState state) {

        setIntake(state.intakeSpeed);

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

    public enum IntakeState {

        INTAKE_CUBE(Constants.WRIST_INTAKE_CUBE, Constants.INTAKE_CUBE),
        INTAKE_CUBE_GROUND(Constants.WRIST_INTAKE_CUBE_FLOOR, Constants.INTAKE_CUBE),
        INTAKE_CONE_GROUND(Constants.WRIST_INTAKE_CONE_FLOOR, Constants.INTAKE_CONE),
        INTAKE_CONE_SUBSTATION(Constants.WRIST_INTAKE_CONE_SUBSTATION, Constants.INTAKE_CONE),
        CONE_HIGH(Constants.WRIST_OUTTAKE_HIGH_CONE, Constants.OUTTAKE_HIGH_CONE),
        CONE_MID(Constants.WRIST_OUTTAKE_MID_CONE, Constants.OUTTAKE_MID_CONE),
        CUBE_HIGH(Constants.WRIST_OUTTAKE_HIGH_CUBE, Constants.OUTTAKE_HIGH_CUBE),
        CUBE_MID(Constants.WRIST_OUTTAKE_MID_CUBE, Constants.OUTTAKE_MID_CUBE),
        CONE_LOW(Constants.WRIST_OUTTAKE_LOW_CONE, Constants.OUTTAKE_LOW_CONE),
        CUBE_LOW(Constants.WRIST_OUTTAKE_LOW_CUBE, Constants.OUTTAKE_LOW_CUBE),
        STOP(Constants.RESET_WRIST, 0.0);

        public double wristPosition;
        public double intakeSpeed;
        private IntakeState(double wristPosition, double intakeSpeed){
            this.wristPosition = wristPosition;
            this.intakeSpeed = intakeSpeed;
        }
 
    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("wrist encoder", wristMotor.getSelectedSensorPosition());

    }

    public void configIntakeMotor() {
        intakeMotor.configFactoryDefault();
        intakeMotor.configAllSettings(CTREConfigs.intakeFXConfig);
        intakeMotor.setInverted(false);
        intakeMotor.setNeutralMode(Constants.INTAKE_NEUTRAL_MODE);
    }

    public void configWristMotor() {
        wristMotor.configFactoryDefault();
        wristMotor.configAllSettings(CTREConfigs.wristFXConfig);
        wristMotor.setInverted(Constants.WRIST_INVERTED);
        wristMotor.setNeutralMode(Constants.WRIST_NEUTRAL_MODE);
    }



}