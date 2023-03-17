package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.CTREConfigs;
import frc.robot.Constants;

public class Slide extends SubsystemBase {

    private TalonFX slideMotor;

    private SlideState slideState;

    public Slide() {

        slideMotor = new TalonFX(Constants.SLIDE_MOTOR, "elevatoryiboi");
        
        configSlideMotor();

        resetSlideEncoder();

    }

    public void setSlide(double setpoint) {

        //TODO: This is where you change the slide into motion magic mode. Change "ControlMode.Position" to "ControlMode.MotionMagic"
        slideMotor.set(ControlMode.Position, setpoint);

    }

    public void resetSlideEncoder() {

        slideMotor.setSelectedSensorPosition(0);

    }

    public double getSlideEncoder() {

        return slideMotor.getSelectedSensorPosition();

    }

    public void setSlideSpeed(double speed) {

        slideMotor.set(ControlMode.PercentOutput, speed);

    }

    public void setSlideState(SlideState state) {

        setSlide(state.slideSetpoint);
        this.slideState = state;

    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("slide pos", getSlideEncoder()); }

    public SlideState getSlideState() {
        return slideState;
    }

    public enum SlideState {

        INTAKE(Constants.SLIDE_INTAKE),
        RETRACTED(Constants.SLIDE_IN),
        CUBE_HIGH(Constants.SLIDE_CUBE_HIGH),
        CUBE_MID(Constants.SLIDE_CUBE_MID),
        CONE_HIGH(Constants.SLIDE_CONE_HIGH),
        CONE_MID(Constants.SLIDE_CONE_MID),
        SUBSTATION(Constants.SLIDE_SUBSTATION);

        public int slideSetpoint;
        private SlideState(int setpoint) {
            this.slideSetpoint = setpoint;
        }

    }

    public void configSlideMotor() {
        slideMotor.configFactoryDefault();
        slideMotor.configAllSettings(CTREConfigs.slideFXConfig);
        slideMotor.setInverted(Constants.SLIDE_INVERTED);
        slideMotor.setNeutralMode(Constants.SLIDE_NEUTRAL_MODE);
        slideMotor.configMotionCruiseVelocity(Constants.SLIDE_CRUISE_VELOCITY);
        slideMotor.configMotionAcceleration(Constants.SLIDE_ACCELERATION);
        slideMotor.configMotionSCurveStrength(Constants.SLIDE_CURVE_STRENGTH);
    }
    
}
