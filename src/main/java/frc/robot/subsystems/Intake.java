package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

    private TalonFX intakeMotor;
    private TalonFX clawMotor;

    private IntakeState intakeState;

    private boolean intakeRunning = false;

    public Intake() {

        intakeMotor = new TalonFX(Constants.INTAKE_MOTOR);
        clawMotor = new TalonFX(Constants.INTAKE_HOOD);

        intakeMotor.setNeutralMode(Constants.INTAKE_NEUTRAL_MODE);

        clawMotor.setInverted(Constants.HOOD_INVERTED);
        clawMotor.setNeutralMode(Constants.HOOD_NEUTRAL_MODE);

    } 

    public void setIntake(double speed) {

        intakeMotor.set(ControlMode.PercentOutput, speed);

    }

    public void intakeHoodDown() {

        intakeMotor.set(ControlMode.Position, Constants.HOOD_DOWN);

    }

    public void intakeHoodUp() {

        intakeMotor.set(ControlMode.Position, Constants.HOOD_UP);

    }

    public void setIntakeState(IntakeState state) {

        if(state.hoodDown) {
            intakeHoodDown();
        } else {
            intakeHoodUp();
        }

        while(!intakeRunning) {
            //do nothing
        }

        intakeMotor.setInverted(state.intakeInverted);

        setIntake(state.intakeSpeed);

        intakeState = state;

    }

    public IntakeState getIntakeState() {
        return intakeState;
    }

    public void setIntakeRunning() {
        intakeRunning = true;
    }

    public void stopIntake() {
        intakeRunning = false;
    }


    public enum IntakeState {

        INTAKE_CONE(false, false, Constants.INTAKE_CONE),
        INTAKE_CUBE(true, false, Constants.INTAKE_CUBE),
        CONE_HIGH(true, false, Constants.OUTTAKE_HIGH_CONE),
        CONE_MID(true, true, Constants.OUTTAKE_MID_CONE),
        CUBE_HIGH(true, true, Constants.OUTTAKE_HIGH_CUBE),
        CUBE_MID(false, false, Constants.OUTTAKE_MID_CUBE),
        CONE_LOW(false, false, Constants.OUTTAKE_LOW_CONE),
        CUBE_LOW(false, false, Constants.OUTTAKE_LOW_CUBE),
        STOP(false, false, 0.0);

        public boolean intakeInverted;
        public boolean hoodDown;
        public double intakeSpeed;
        private IntakeState(boolean intakeInverted, boolean hoodDown, double intakeSpeed){
            this.intakeInverted = intakeInverted;
            this.hoodDown = hoodDown;
            this.intakeSpeed = intakeSpeed;
        }
 
    }



}