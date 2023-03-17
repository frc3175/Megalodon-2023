package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.CTREConfigs;
import frc.robot.Constants;

public class Slide extends SubsystemBase {

    private TalonFX slideMotor;

    private SlideState slideState;

    Timer timer = new Timer();

    public Slide() {

        slideMotor = new TalonFX(Constants.SLIDE_MOTOR, "elevatoryiboi");
        
        configSlideMotor();

        resetSlideEncoder();

    }

    public void setSlide(double setpoint) {

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

        timer.reset();
        timer.start();

        while(timer.get() < state.slideDelay) {
            //do nothing
        }

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

        INTAKE(Constants.SLIDE_INTAKE, 0),
        RETRACTED(Constants.SLIDE_IN, 0),
        CUBE_HIGH(Constants.SLIDE_CUBE_HIGH, Constants.SLIDE_DELAY_UP),
        CUBE_MID(Constants.SLIDE_CUBE_MID, Constants.SLIDE_DELAY_UP),
        CONE_HIGH(Constants.SLIDE_CONE_HIGH, Constants.SLIDE_DELAY_UP),
        CONE_MID(Constants.SLIDE_CONE_MID, Constants.SLIDE_DELAY_UP),
        SUBSTATION(Constants.SLIDE_SUBSTATION, Constants.SLIDE_DELAY_UP);

        public int slideSetpoint;
        public double slideDelay;
        private SlideState(int setpoint, double slideDelay) {
            this.slideSetpoint = setpoint;
            this.slideDelay = slideDelay;
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
