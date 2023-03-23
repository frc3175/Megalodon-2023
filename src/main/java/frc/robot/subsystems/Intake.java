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
    private boolean isSingleConeState = false;

    private IntakeState intakeState;

    public Intake() {

        intakeMotor = new TalonFX(Constants.INTAKE_MOTOR, "elevatoryiboi");
        wristMotor = new TalonFX(Constants.INTAKE_WRIST, "elevatoryiboi");

        configWristMotor();
        configIntakeMotor();

        wristMotor.setSelectedSensorPosition(0);

    } 

    public void setSingleConeState(boolean m_isSingleConeState) {

        isSingleConeState = m_isSingleConeState;

    }

    public boolean isSingleConeState() {

        return isSingleConeState;

    }

    public void setIntake(double speed) {

        intakeMotor.set(ControlMode.PercentOutput, speed);

    }

    public void setIntakeState(IntakeState state) {

        setWristPosition(state.wristPosition, state.wristVelocity, state.wristAcceleration, state.wristCurve);

        intakeState = state;

    }

    public void resetIntakeEncoder() {

        intakeMotor.setSelectedSensorPosition(0);

    }

    public double getIntakeEncoder() {

        return intakeMotor.getSelectedSensorPosition();

    }

    public void zeroWrist() {

        wristMotor.setSelectedSensorPosition(0);

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

    public void setWristPosition(double position, double velocity, double acceleration, int curve) {

        wristMotor.configMotionCruiseVelocity(velocity);
        wristMotor.configMotionAcceleration(acceleration);
        wristMotor.configMotionSCurveStrength(curve);
        //TODO: This is where you change the intake into motion magic mode. Change "ControlMode.Position" to "ControlMode.MotionMagic"
        wristMotor.set(ControlMode.MotionMagic, position);

    }

    public enum IntakeState {

        INTAKE_CUBE(Constants.WRIST_INTAKE_CUBE, Constants.WRIST_LOW_VELOCITY, Constants.WRIST_LOW_ACCEL, Constants.WRIST_LOW_CURVE),
        INTAKE_CUBE_GROUND(Constants.WRIST_INTAKE_CUBE_FLOOR, Constants.WRIST_LOW_VELOCITY, Constants.WRIST_LOW_ACCEL, Constants.WRIST_LOW_CURVE),
        INTAKE_CONE_GROUND(Constants.WRIST_INTAKE_CONE_FLOOR, Constants.WRIST_LOW_VELOCITY, Constants.WRIST_LOW_ACCEL, Constants.WRIST_LOW_CURVE),
        INTAKE_CONE_SUBSTATION(Constants.WRIST_INTAKE_CONE_SUBSTATION, Constants.WRIST_DOUBLE_SUB_VELOCITY, Constants.WRIST_DOUBLE_SUB_ACCEL, Constants.WRIST_DOUBLE_SUB_CURVE),
        INTAKE_CONE_SINGLE(Constants.WRIST_SINGLE_INTAKE, Constants.WRIST_SINGLE_SUB_VELOCITY, Constants.WRIST_SINGLE_SUB_ACCEL, Constants.WRIST_SINGLE_SUB_CURVE),
        CONE_HIGH(Constants.WRIST_OUTTAKE_HIGH_CONE, Constants.WRIST_HIGH_CONE_VELOCITY, Constants.WRIST_HIGH_CONE_ACCEL, Constants.WRIST_HIGH_CONE_CURVE),
        CONE_MID(Constants.WRIST_OUTTAKE_MID_CONE, Constants.WRIST_MID_CONE_VELOCITY, Constants.WRIST_MID_CONE_ACCEL, Constants.WRIST_MID_CONE_CURVE),
        CUBE_HIGH(Constants.WRIST_OUTTAKE_HIGH_CUBE, Constants.WRIST_HIGH_CUBE_VELOCITY, Constants.WRIST_HIGH_CUBE_ACCEL, Constants.WRIST_HIGH_CUBE_CURVE),
        CUBE_MID(Constants.WRIST_OUTTAKE_MID_CUBE, Constants.WRIST_MID_CUBE_VELOCITY, Constants.WRIST_MID_CUBE_ACCEL, Constants.WRIST_MID_CUBE_CURVE),
        CONE_LOW(Constants.WRIST_OUTTAKE_LOW_CONE, Constants.WRIST_LOW_VELOCITY, Constants.WRIST_LOW_ACCEL, Constants.WRIST_LOW_CURVE),
        CUBE_LOW(Constants.WRIST_OUTTAKE_LOW_CUBE, Constants.WRIST_LOW_VELOCITY, Constants.WRIST_LOW_ACCEL, Constants.WRIST_LOW_CURVE),
        STOP_CONE(Constants.RESET_WRIST, Constants.WRIST_RESET_VELOCITY, Constants.WRIST_RESET_ACCEL, Constants.WRIST_RESET_CURVE),
        STOP_CUBE(Constants.RESET_WRIST, Constants.WRIST_RESET_VELOCITY, Constants.WRIST_RESET_ACCEL, Constants.WRIST_RESET_CURVE);

        public double wristPosition;
        public double wristVelocity;
        public double wristAcceleration;
        public int wristCurve;
        private IntakeState(double wristPosition, double wristVelocity, double wristAcceleration, int wristCurve){
            this.wristPosition = wristPosition;
            this.wristVelocity = wristVelocity;
            this.wristAcceleration = wristAcceleration;
            this.wristCurve = wristCurve;
        }
 
    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("wrist encoder", wristMotor.getSelectedSensorPosition());

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
        wristMotor.configMotionCruiseVelocity(Constants.WRIST_CRUISE_VELOCITY);
        wristMotor.configMotionAcceleration(Constants.WRIST_ACCELERATION);
        wristMotor.configMotionSCurveStrength(Constants.WRIST_CURVE_STRENGTH);
    }



}