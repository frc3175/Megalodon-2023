package frc.robot;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.sensors.SensorTimeBase;

public final class CTREConfigs {

    public static TalonFXConfiguration swerveAngleFXConfig;
    public static TalonFXConfiguration swerveDriveFXConfig;
    public static CANCoderConfiguration swerveCanCoderConfig;
    public static TalonFXConfiguration intakeFXConfig;
    public static TalonFXConfiguration wristFXConfig;
    public static TalonFXConfiguration elevatorFXConfig;
    public static TalonFXConfiguration slideFXConfig;


    public CTREConfigs(){

        swerveAngleFXConfig = new TalonFXConfiguration();
        swerveDriveFXConfig = new TalonFXConfiguration();
        swerveCanCoderConfig = new CANCoderConfiguration();
        intakeFXConfig = new TalonFXConfiguration();
        wristFXConfig = new TalonFXConfiguration();
        elevatorFXConfig = new TalonFXConfiguration();
        slideFXConfig = new TalonFXConfiguration();

        /* Swerve Angle Motor Configurations */
        SupplyCurrentLimitConfiguration angleSupplyLimit = new SupplyCurrentLimitConfiguration(
            Constants.AZIMUTH_ENABLE_CURRENT_LIMIT, 
            Constants.AZIMUTH_CONTINUOUS_CURRENT_LIMIT, 
            Constants.AZIMUTH_PEAK_CURRENT_LIMIT, 
            Constants.AZIMUTH_PEAK_CURRENT_DURATION);

        swerveAngleFXConfig.slot0.kP = Constants.AZIMUTH_P;
        swerveAngleFXConfig.slot0.kI = Constants.AZIMUTH_I;
        swerveAngleFXConfig.slot0.kD = Constants.AZIMUTH_D;
        swerveAngleFXConfig.slot0.kF = Constants.AZIMUTH_F;
        swerveAngleFXConfig.supplyCurrLimit = angleSupplyLimit;

        /* Swerve Drive Motor Configuration */
        SupplyCurrentLimitConfiguration driveSupplyLimit = new SupplyCurrentLimitConfiguration(
            Constants.DRIVE_ENABLE_CURRENT_LIMIT, 
            Constants.DRIVE_CONTINUOUS_CURRENT_LIMIT, 
            Constants.DRIVE_PEAK_CURRENT_LIMIT, 
            Constants.DRIVE_PEAK_CURRENT_DURATION);

        swerveDriveFXConfig.slot0.kP = Constants.DRIVE_P;
        swerveDriveFXConfig.slot0.kI = Constants.DRIVE_I;
        swerveDriveFXConfig.slot0.kD = Constants.DRIVE_D;
        swerveDriveFXConfig.slot0.kF = Constants.DRIVE_F;        
        swerveDriveFXConfig.supplyCurrLimit = driveSupplyLimit;
        swerveDriveFXConfig.openloopRamp = Constants.OPEN_LOOP_RAMP;
        swerveDriveFXConfig.closedloopRamp = Constants.CLOSED_LOOP_RAMP;
        
        /* Swerve CANCoder Configuration */
        swerveCanCoderConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        swerveCanCoderConfig.sensorDirection = Constants.FRONT_LEFT_CANCODER_REVERSED;
        swerveCanCoderConfig.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
        swerveCanCoderConfig.sensorTimeBase = SensorTimeBase.PerSecond;

        /* Intake Motor Configuration */
        SupplyCurrentLimitConfiguration intakeSupplyLimit = new SupplyCurrentLimitConfiguration(
            Constants.INTAKE_ENABLE_CURRENT_LIMIT, 
            Constants.INTAKE_CONTINUOUS_CURRENT_LIMIT, 
            Constants.INTAKE_PEAK_CURRENT_LIMIT, 
            Constants.INTAKE_PEAK_CURRENT_DURATION);

        intakeFXConfig.supplyCurrLimit = intakeSupplyLimit;

        /* Wrist Motor Configuration */
        SupplyCurrentLimitConfiguration wristSupplyLimit = new SupplyCurrentLimitConfiguration(
            Constants.WRIST_ENABLE_CURRENT_LIMIT, 
            Constants.WRIST_CONTINUOUS_CURRENT_LIMIT, 
            Constants.WRIST_PEAK_CURRENT_LIMIT, 
            Constants.WRIST_PEAK_CURRENT_DURATION);

        wristFXConfig.supplyCurrLimit = wristSupplyLimit;
        wristFXConfig.slot0.kP = Constants.WRIST_P;
        wristFXConfig.slot0.kI = Constants.WRIST_I;
        wristFXConfig.slot0.kD = Constants.WRIST_D;
        wristFXConfig.slot0.kF = Constants.WRIST_F;

        /* Elevator Motor Configuration */
        SupplyCurrentLimitConfiguration elevatorSupplyLimit = new SupplyCurrentLimitConfiguration(
            Constants.ELEVATOR_ENABLE_CURRENT_LIMIT,
            Constants.ELEVATOR_CONTINUOUS_CURRENT_LIMIT,
            Constants.ELEVATOR_PEAK_CURRENT_LIMIT,
            Constants.ELEVATOR_PEAK_CURRENT_DURATION
        );

        elevatorFXConfig.supplyCurrLimit = elevatorSupplyLimit;
        elevatorFXConfig.slot0.kP = Constants.ELEVATOR_P;
        elevatorFXConfig.slot0.kI = Constants.ELEVATOR_I;
        elevatorFXConfig.slot0.kD = Constants.ELEVATOR_D;
        elevatorFXConfig.slot0.kF = Constants.ELEVATOR_F;

        /* Slide Motor Configuration */
        SupplyCurrentLimitConfiguration slideSupplyLimit = new SupplyCurrentLimitConfiguration(
            Constants.SLIDE_ENABLE_CURRENT_LIMIT,
            Constants.SLIDE_CONTINUOUS_CURRENT_LIMIT,
            Constants.SLIDE_PEAK_CURRENT_LIMIT,
            Constants.SLIDE_PEAK_CURRENT_DURATION
        );

       slideFXConfig.supplyCurrLimit = slideSupplyLimit;
       slideFXConfig.slot0.kP = Constants.SLIDE_P;
       slideFXConfig.slot0.kI = Constants.SLIDE_I;
       slideFXConfig.slot0.kD = Constants.SLIDE_D;
       slideFXConfig.slot0.kF = Constants.SLIDE_F;


    }
}