package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.pathplanner.lib.auto.PIDConstants;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.lib.util.SwerveModuleConstants;

public final class Constants {

    /*============================
               Swerve 
    ==============================*/

    public static final int PIGEON = 19;

    /* Azimuth reversed */
    public static boolean FRONT_LEFT_AZIMUTH_REVERSED = false;
    public static boolean FRONT_RIGHT_AZIMUTH_REVERSED = false;
    public static boolean BACK_LEFT_AZIMUTH_REVERSED = false;
    public static boolean BACK_RIGHT_AZIMUTH_REVERSED = false;

    /* Drive motors reversed */
    public static boolean FRONT_LEFT_DRIVE_REVERSED = true;
    public static boolean FRONT_RIGHT_DRIVE_REVERSED = true;
    public static boolean BACK_LEFT_DRIVE_REVERSED = true;
    public static boolean BACK_RIGHT_DRIVE_REVERSED = true;

    /* CANCoders reversed */
    public static boolean FRONT_LEFT_CANCODER_REVERSED = false;
    public static boolean FRONT_RIGHT_CANCODER_REVERSED = false;
    public static boolean BACK_LEFT_CANCODER_REVERSED = false;
    public static boolean BACK_RIGHT_CANCODER_REVERSED = false;

    /* Module Specific Constants */
        /* Front Left Module - Module 0 */
        public static final class Mod0 {
            public static final int DRIVE_MOTOR_ID = 6;
            public static final int ANGLE_MOTOR_ID = 8;
            public static final int CANCODER_ID = 10;
            public static final Rotation2d ANGLE_OFFSET = Rotation2d.fromDegrees(30.05);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(DRIVE_MOTOR_ID, ANGLE_MOTOR_ID, CANCODER_ID, ANGLE_OFFSET);
        }

        /* Front Right Module - Module 1 */
        public static final class Mod1 {
            public static final int DRIVE_MOTOR_ID = 17;
            public static final int ANGLE_MOTOR_ID = 13;
            public static final int CANCODER_ID = 4;
            public static final Rotation2d ANGLE_OFFSET = Rotation2d.fromDegrees(82.88);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(DRIVE_MOTOR_ID, ANGLE_MOTOR_ID, CANCODER_ID, ANGLE_OFFSET);
        }
        
        /* Back Left Module - Module 2 */
        public static final class Mod2 {
            public static final int DRIVE_MOTOR_ID = 11;
            public static final int ANGLE_MOTOR_ID = 9;
            public static final int CANCODER_ID = 7;
            public static final Rotation2d ANGLE_OFFSET = Rotation2d.fromDegrees(132.89);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(DRIVE_MOTOR_ID, ANGLE_MOTOR_ID, CANCODER_ID, ANGLE_OFFSET);
        }

        /* Back Right Module - Module 3 */
        public static final class Mod3 {
            public static final int DRIVE_MOTOR_ID = 3;
            public static final int ANGLE_MOTOR_ID = 5;
            public static final int CANCODER_ID = 12;
            public static final Rotation2d ANGLE_OFFSET = Rotation2d.fromDegrees(237.90);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(DRIVE_MOTOR_ID, ANGLE_MOTOR_ID, CANCODER_ID, ANGLE_OFFSET);
        }

    /* Gyro reversed */
    public static final boolean INVERT_GYRO = false;

    /* Angle Motor PID Values */
    public static final double AZIMUTH_P = 0.2;
    public static final double AZIMUTH_I = 0.0;
    public static final double AZIMUTH_D = 0.1;
    public static final double AZIMUTH_F = 0;

    /* Drive Motor Characterization Values */
    public static final double DRIVE_S = (0.48665 / 12); //Values from SysId divided by 12 to convert to volts for CTRE
    public static final double DRIVE_V = (2.4132 / 12);
    public static final double DRIVE_A = (0.06921 / 12);

    /* Drive Motor PID Values */
    public static final double DRIVE_P = 0.1;  //0.1
    public static final double DRIVE_I = 0.0;
    public static final double DRIVE_D = 0.0;
    //public static final SimpleMotorFeedforward DRIVE_F = new SimpleMotorFeedforward(DRIVE_S, DRIVE_V, DRIVE_A);
    public static final double DRIVE_F = 0;

    /* Azimuth Current Limiting */
    public static final int AZIMUTH_CONTINUOUS_CURRENT_LIMIT = 25;
    public static final int AZIMUTH_PEAK_CURRENT_LIMIT = 40;
    public static final double AZIMUTH_PEAK_CURRENT_DURATION = 0.1;
    public static final boolean AZIMUTH_ENABLE_CURRENT_LIMIT = true;

    /* Drive Current Limiting */
    public static final int DRIVE_CONTINUOUS_CURRENT_LIMIT = 35;
    public static final int DRIVE_PEAK_CURRENT_LIMIT = 60;
    public static final double DRIVE_PEAK_CURRENT_DURATION = 0.1;
    public static final boolean DRIVE_ENABLE_CURRENT_LIMIT = true;

    /* Neutral Modes */
    public static final NeutralMode AZIMUTH_NEUTRAL_MODE = NeutralMode.Coast;
    public static final NeutralMode DRIVE_NEUTRAL_MODE = NeutralMode.Brake;

    /* Swerve Gear Ratios */
    public static final double DRIVE_GEAR_RATIO = (6.75 / 1.0);
    public static final double AZIMUTH_GEAR_RATIO = (-150.0 / 7);

    /* Swerve Profiling Values */
    public static final double MAX_SPEED = (Units.feetToMeters(16.2)); //meters per second (theoretical from SDS)
    public static final double MAX_ANGULAR_VELOCITY = Math.PI * 4.12; //radians per second (theoretical calculation)
    public static final double TURN_IN_PLACE_SPEED = 0.5;
    public static final double A_RATE_LIMITER = 2.0; //Slew Rate Limiter Constant
    
    /* Constraint for the motion profilied robot angle controller */
    public static final TrapezoidProfile.Constraints THETA_CONTROLLER_CONSTRAINTS =
        new TrapezoidProfile.Constraints(
            Math.PI, (Math.PI * Math.PI));

    /*============================
                Intake 
    ==============================*/

    public static final int INTAKE_MOTOR = 14;
    public static final int INTAKE_WRIST = 2;

    public static final boolean INTAKE_ENABLE_CURRENT_LIMIT = true;
    public static final int INTAKE_CONTINUOUS_CURRENT_LIMIT = 10;
    public static final int INTAKE_PEAK_CURRENT_LIMIT = 30;
    public static final double INTAKE_PEAK_CURRENT_DURATION = 0.1;

    public static final boolean WRIST_ENABLE_CURRENT_LIMIT = true;
    public static final int WRIST_CONTINUOUS_CURRENT_LIMIT = 5;
    public static final int WRIST_PEAK_CURRENT_LIMIT = 30;
    public static final double WRIST_PEAK_CURRENT_DURATION = 0.1;

    public static final boolean WRIST_INVERTED = false;
    public static final boolean INTAKE_INVERTED = true;

    public static final NeutralMode INTAKE_NEUTRAL_MODE = NeutralMode.Brake;
    public static final NeutralMode WRIST_NEUTRAL_MODE = NeutralMode.Brake;

    /* Hood PID */
    public static final double WRIST_P = 0.05;
    public static final double WRIST_I = 0;
    public static final double WRIST_D = 0;
    public static final double WRIST_F = 0.001;

    /* Speeds */
    public static final double OUTTAKE_CONE = -0.5;
    public static final double OUTTAKE_CUBE = 0.5;
    public static final double INTAKE_CONE = 0.5;
    public static final double INTAKE_CUBE = -0.5;

    /* Positions */
    public static final double WRIST_OUTTAKE_HIGH_CONE = -0.5;
    public static final double WRIST_OUTTAKE_MID_CONE = -0.5;
    public static final double WRIST_OUTTAKE_LOW_CONE = -0.5;
    public static final double WRIST_OUTTAKE_HIGH_CUBE = 0.5;
    public static final double WRIST_OUTTAKE_MID_CUBE = 0.5;
    public static final double WRIST_OUTTAKE_LOW_CUBE = 0.5;
    public static final double WRIST_INTAKE_CONE_FLOOR = 60000;
    public static final double WRIST_INTAKE_CONE_SUBSTATION = 0.5;
    public static final double WRIST_INTAKE_CUBE = -0.5;
    public static final double WRIST_INTAKE_CUBE_FLOOR = 60000;
    public static final double RESET_WRIST = 0;

    public static final double INTAKE_TEST_SPEED = 0.5;
    public static final double HOOD_TEST_SPEED = 0.5;

    /*============================
                Elevator 
    ==============================*/

    public static final int ELEVATOR_MOTOR = 19;

    public static final boolean ELEVATOR_ENABLE_CURRENT_LIMIT = true;
    public static final int ELEVATOR_CONTINUOUS_CURRENT_LIMIT = 10;
    public static final int ELEVATOR_PEAK_CURRENT_LIMIT = 30;
    public static final double ELEVATOR_PEAK_CURRENT_DURATION = 0.1;

    public static final boolean ELEVATOR_INVERTED = true;
    public static final NeutralMode ELEVATOR_NEUTRAL_MODE = NeutralMode.Coast;

    /* Setpoints */
    public static final int ELEVATOR_DOWN = 0;
    public static final int CUBE_HIGH = 214380;
    public static final int CUBE_MID = 100000; //TODO: 150000
    public static final int CONE_HIGH = 214380;
    public static final int CONE_MID = 100000; //TODO: 150000
    public static final int SUBSTATION = 200000;
    public static final int CLEAR_BUMPER = 5000;

    /* Elevator PID */
    public static final double ELEVATOR_P = 1;
    public static final double ELEVATOR_I = 0;
    public static final double ELEVATOR_D = 0;
    public static final double ELEVATOR_F = 0.0;

    public static final double ELEVATOR_TEST_SPEED = 0.5;

    /*============================
                Slide
    ==============================*/

    public static final int SLIDE_MOTOR = 20;

    public static final boolean SLIDE_ENABLE_CURRENT_LIMIT = true;
    public static final int SLIDE_CONTINUOUS_CURRENT_LIMIT = 10;
    public static final int SLIDE_PEAK_CURRENT_LIMIT = 30;
    public static final double SLIDE_PEAK_CURRENT_DURATION = 0.1;

    public static final boolean SLIDE_INVERTED = true;
    public static final NeutralMode SLIDE_NEUTRAL_MODE = NeutralMode.Brake;

    public static final double SLIDE_DELAY_UP = 0;

    /* Setpoints */
    public static final int SLIDE_INTAKE = 1000;
    public static final int SLIDE_IN = 0;
    public static final int SLIDE_CONE_MID = 20000;
    public static final int SLIDE_CONE_HIGH = 20000;
    public static final int SLIDE_CUBE_MID = 5000;
    public static final int SLIDE_CUBE_HIGH = 5000;
    public static final int SLIDE_SUBSTATION = 1250;

    /* Slide PID */
    public static final double SLIDE_P = 0.1;
    public static final double SLIDE_I = 0;
    public static final double SLIDE_D = 0;
    public static final double SLIDE_F = 0;

    public static final double SLIDE_TEST_SPEED = 0.5;

    /*============================
               Kinematics
    ==============================*/

    public static final double DRIVETRAIN_WIDTH = Units.inchesToMeters(18.75);
    public static final double DRIVETRAIN_LENGTH = Units.inchesToMeters(18.75);
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(4);
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;

    public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
        new Translation2d(DRIVETRAIN_LENGTH / 2.0, DRIVETRAIN_WIDTH / 2.0),
        new Translation2d(DRIVETRAIN_LENGTH / 2.0, -DRIVETRAIN_WIDTH / 2.0),
        new Translation2d(-DRIVETRAIN_LENGTH / 2.0, DRIVETRAIN_WIDTH / 2.0),
        new Translation2d(-DRIVETRAIN_LENGTH / 2.0, -DRIVETRAIN_WIDTH / 2.0));

    /*============================
                Vision
    ==============================*/

    /**
     * Physical location of the camera on the robot, relative to the center of the robot.
     */
    public static final Transform3d CAMERA_TO_ROBOT =
        new Transform3d(new Translation3d(-0.33, 0.0, 0.0), new Rotation3d());
    public static final Transform3d ROBOT_TO_CAMERA = CAMERA_TO_ROBOT.inverse();

    public static final double CAMERA_HEIGHT = 0.22;
    public static final double TAG_HEIGHT = 0.5; //TODO: temporary value

    /*============================
                Auton
    ==============================*/

    public static final double AUTO_P_X_CONTROLLER = 0.1; //0.1 for auto 
    public static final double AUTO_P_Y_CONTROLLER = 0.1; //1.4884 for auto
    public static final double AUTO_P_THETA_CONTROLLER = 1.375; //2.8 for auto
    public static final double AUTO_I_THETA_CONTROLLER = 0;
    public static final double AUTO_D_THETA_CONTROLLER = 0.0;
    public static final double AUTO_MAX_SPEED = Units.feetToMeters(4.9);
    public static final double AUTO_MAX_ACCELERATION_MPS_SQUARED = 3;

    public static final TrapezoidProfile.Constraints X_AUTO_CONSTRAINTS = new TrapezoidProfile.Constraints(1, 0.5);
    public static final TrapezoidProfile.Constraints Y_AUTO_CONSTRAINTS = new TrapezoidProfile.Constraints(0.25, 0.5);
    public static final TrapezoidProfile.Constraints THETA_AUTO_CONSTRAINTS = new TrapezoidProfile.Constraints(8, 8); //pi, (pi, pi)

    public static final ProfiledPIDController AUTO_X_CONTROLLER = new ProfiledPIDController(AUTO_P_X_CONTROLLER, 0, 0, X_AUTO_CONSTRAINTS);
    public static final ProfiledPIDController AUTO_Y_CONTROLLER = new ProfiledPIDController(AUTO_P_Y_CONTROLLER, 0, 0, Y_AUTO_CONSTRAINTS);
    public static final ProfiledPIDController AUTO_THETA_CONTROLLER = new ProfiledPIDController(AUTO_P_THETA_CONTROLLER, AUTO_I_THETA_CONTROLLER, AUTO_D_THETA_CONTROLLER, THETA_AUTO_CONSTRAINTS);

    public static final PIDConstants AUTO_TRANSLATION_CONSTANTS = new PIDConstants(0.01, 0, 0);
    public static final PIDConstants AUTO_ROTATION_CONSTANTS = new PIDConstants(2.8, 0, 0);

    /*============================
                Misc.
    ==============================*/

    public static final double OPEN_LOOP_RAMP = 0.25;
    public static final double CLOSED_LOOP_RAMP = 0.0;

    /*============================
         Controller Constants
    ==============================*/

    /* Controller Constants */
    public static final double STICK_DEADBAND = 0.1;
    public static final int DRIVER_PORT = 0;
    public static final int OPERATOR_PORT = 1;
    public static final double OP_RUMBLE_PERCENT = 0.4;
    public static final double DRIVER_RUMBLE_PERCENT = 0.4;
    public static final RumbleType DRIVER_RUMBLE_LEFT = RumbleType.kLeftRumble;
    public static final RumbleType OP_RUMBLE_LEFT = RumbleType.kLeftRumble;
    public static final RumbleType DRIVER_RUMBLE_RIGHT = RumbleType.kRightRumble;
    public static final RumbleType OP_RUMBLE_RIGHT = RumbleType.kRightRumble;
    public static final double DRIVING_INTAKE_RUMBLE = 0.3;

} 