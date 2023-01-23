package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Constants;

public class Slide {

    private TalonFX slideMotor;

    private SlideState slideState;

    public Slide() {

        slideMotor = new TalonFX(Constants.SLIDE_MOTOR);
        slideMotor.setNeutralMode(Constants.SLIDE_NEUTRAL_MODE);
        slideMotor.setInverted(Constants.SLIDE_INVERTED);

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

    public void setSlideState(SlideState state) {

        setSlide(state.slideSetpoint);
        this.slideState = state;

    }

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
    
}
