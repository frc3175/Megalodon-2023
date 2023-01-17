package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

    private CANSparkMax outtakeMotor;
    private TalonFX intakeMotor;
    private TalonFX clawMotor;

    public Intake() {

        outtakeMotor = new CANSparkMax(Constants.OUTTAKE_MOTOR, Constants.OUTTAKE_MOTOR_TYPE);
        intakeMotor = new TalonFX(Constants.INTAKE_MOTOR);
        clawMotor = new TalonFX(Constants.CLAW_MOTOR);

        outtakeMotor.setInverted(Constants.OUTTAKE_INVERTED);
        outtakeMotor.getPIDController().setP(Constants.OUTTAKE_P);
        outtakeMotor.getPIDController().setI(Constants.OUTTAKE_I);
        outtakeMotor.getPIDController().setD(Constants.OUTTAKE_D);
        outtakeMotor.getPIDController().setFF(Constants.OUTTAKE_P);
        outtakeMotor.setIdleMode(Constants.OUTTAKE_IDLE_MODE);
        outtakeMotor.setSmartCurrentLimit(Constants.OUTTAKE_STALL_LIMIT, Constants.OUTTAKE_FREE_LIMIT);

        intakeMotor.setInverted(Constants.INTAKE_INVERTED);
        intakeMotor.setNeutralMode(Constants.INTAKE_NEUTRAL_MODE);
        
        clawMotor.setInverted(Constants.CLAW_INVERTED);
        clawMotor.setNeutralMode(Constants.CLAW_NEUTRAL_MODE);

    } 

}
