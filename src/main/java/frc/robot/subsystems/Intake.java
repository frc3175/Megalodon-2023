package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

    private TalonFX intakeMotor;
    private TalonFX clawMotor;
    private Timer timer;

    public Intake() {

        intakeMotor = new TalonFX(Constants.INTAKE_MOTOR);
        clawMotor = new TalonFX(Constants.INTAKE_HOOD);

        intakeMotor.setInverted(Constants.INTAKE_INVERTED);
        intakeMotor.setNeutralMode(Constants.INTAKE_NEUTRAL_MODE);

        clawMotor.setInverted(Constants.HOOD_INVERTED);
        clawMotor.setNeutralMode(Constants.HOOD_NEUTRAL_MODE);

        timer = new Timer();

    } 

    public void setIntake(double speed) {

        intakeMotor.set(ControlMode.PercentOutput, speed);

    }

    public void moveIntakeHood(double speed, int currentLimit) {

        if(intakeMotor.getStatorCurrent() < currentLimit) {
            intakeMotor.set(ControlMode.PercentOutput, speed);
        } else {
            timer.reset();
            while(timer.get() < 0.2) {
                intakeMotor.set(ControlMode.PercentOutput, speed);
            }
            intakeMotor.set(ControlMode.PercentOutput, 0);
        }
        

    }



}