package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Constants;

public class Slide {

    private TalonFX slideMotor;

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

        switch(state) {

            case INTAKE:
                setSlide(Constants.SLIDE_INTAKE);
                break;
            case RETRACTED:
                setSlide(Constants.SLIDE_IN);
                break;
            case CUBE_HIGH:
                setSlide(Constants.CUBE_HIGH);
                break;
            case CUBE_MID:
                setSlide(Constants.CUBE_MID);
                break;
            case CONE_HIGH:
                setSlide(Constants.CONE_HIGH);
                break;
            case CONE_MID:
                setSlide(Constants.CONE_MID);
                break;
            case SUBSTATION:
                setSlide(Constants.SUBSTATION);
                break;

        }

    }

    public enum SlideState {

        INTAKE,
        RETRACTED,
        CUBE_HIGH,
        CUBE_MID,
        CONE_HIGH,
        CONE_MID,
        SUBSTATION

    }
    
}
