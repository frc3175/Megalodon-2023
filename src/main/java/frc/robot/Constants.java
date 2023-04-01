package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.pathplanner.lib.auto.PIDConstants;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
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

    /* Azimuth Motor PID Values */
    public static final double AZIMUTH_P = 0.2;
    public static final double AZIMUTH_I = 0.0;
    public static final double AZIMUTH_D = 0.1;
    public static final double AZIMUTH_F = 0;

    /* Drive Motor Characterization Values */
    public static final double DRIVE_S = (0.48665 / 12); //Values from SysId divided by 12 to convert to volts for CTRE
    public static final double DRIVE_V = (2.4132 / 12);
    public static final double DRIVE_A = (0.06921 / 12);

    /* Drive Motor PID Values */
    public static final double DRIVE_P = 0.1;
    public static final double DRIVE_I = 0.0;
    public static final double DRIVE_D = 0.0;
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
    public static final double MAX_ANGULAR_VELOCITY = Math.PI * 4.12*0.5; //radians per second (theoretical calculation)
    public static final double A_RATE_LIMITER = 2.0; //Slew Rate Limiter Constant
    
    /* Constraint for the motion profiled robot angle controller */
    public static final TrapezoidProfile.Constraints THETA_CONTROLLER_CONSTRAINTS =
        new TrapezoidProfile.Constraints(
            Math.PI, (Math.PI * Math.PI));

    /*============================
                 LEDs 
    ==============================*/

    /* CANdle ID */
    public static final int CANdleID = 6;

    /* Purple RGB */
    public static final int PURPLE_R = 255;
    public static final int PURPLE_G = 0;
    public static final int PURPLE_B = 191;

    /* Yellow RGB */
    public static final int YELLOW_R = 255;
    public static final int YELLOW_G = 130;
    public static final int YELLOW_B = 0;

    /* Red RGB */
    public static final int RED_R = 255;
    public static final int RED_G = 0;
    public static final int RED_B = 0;

    /* Green RGB */
    public static final int GREEN_R = 0;
    public static final int GREEN_G = 255;
    public static final int GREEN_B = 0;

    /*============================
                Intake 
    ==============================*/

    /* Intake CAN IDs */
    public static final int INTAKE_MOTOR = 14;
    public static final int INTAKE_WRIST = 19;

    /* Intake motor current limits */
    public static final boolean INTAKE_ENABLE_CURRENT_LIMIT = true;
    public static final int INTAKE_CONTINUOUS_CURRENT_LIMIT = 10;
    public static final int INTAKE_PEAK_CURRENT_LIMIT = 30;
    public static final double INTAKE_PEAK_CURRENT_DURATION = 0.1;

    /* Wrist motor current limits */
    public static final boolean WRIST_ENABLE_CURRENT_LIMIT = true;
    public static final int WRIST_CONTINUOUS_CURRENT_LIMIT = 5;
    public static final int WRIST_PEAK_CURRENT_LIMIT = 30;
    public static final double WRIST_PEAK_CURRENT_DURATION = 0.1;

    /* Motor reversals */
    public static final boolean WRIST_INVERTED = false;
    public static final boolean INTAKE_INVERTED = true;

    /* Intake and wrist neutral modes */
    public static final NeutralMode INTAKE_NEUTRAL_MODE = NeutralMode.Brake;
    public static final NeutralMode WRIST_NEUTRAL_MODE = NeutralMode.Brake;

    /*Intake PID */
    public static final double INTAKE_P = 0.1;
    public static final double INTAKE_I = 0;
    public static final double INTAKE_D = 0;
    public static final double INTAKE_F = 0.0495;

    /* Wrist PID */
    public static final double WRIST_P = 0.05;
    public static final double WRIST_I = 0;
    public static final double WRIST_D = 0;
    public static final double WRIST_F = 0.001;

    /* Wrist Velocities */ //NOTE: units are falcon units/100ms
    public static final int WRIST_RESET_VELOCITY = 20000;
    public static final int WRIST_SINGLE_SUB_VELOCITY = 20000;
    public static final int WRIST_LOW_VELOCITY = 20000;
    public static final int WRIST_FLOOR_INTAKE_VELOCITY = 20000;
    public static final int WRIST_MID_CONE_VELOCITY = 12000;
    public static final int WRIST_MID_CUBE_VELOCITY = 5000;
    public static final int WRIST_HIGH_CONE_VELOCITY = 5000;
    public static final int WRIST_HIGH_CUBE_VELOCITY  = 5000;
    public static final int WRIST_DOUBLE_SUB_VELOCITY = 20000;

    /* Wrist Accelerations */ //NOTE: units are falcon units/100ms/sec
    public static final int WRIST_RESET_ACCEL = 200000;
    public static final int WRIST_SINGLE_SUB_ACCEL = 200000;
    public static final int WRIST_LOW_ACCEL = 200000;
    public static final int WRIST_FLOOR_INTAKE_ACCEL = 200000;
    public static final int WRIST_MID_CONE_ACCEL = 100000;
    public static final int WRIST_MID_CUBE_ACCEL = 10000;
    public static final int WRIST_HIGH_CONE_ACCEL = 50000;
    public static final int WRIST_HIGH_CUBE_ACCEL  = 10000;
    public static final int WRIST_DOUBLE_SUB_ACCEL = 10000;

    /* Wrist Curve Strengths */
    public static final int WRIST_RESET_CURVE = 3;
    public static final int WRIST_SINGLE_SUB_CURVE = 3;
    public static final int WRIST_LOW_CURVE = 3;
    public static final int WRIST_FLOOR_INTAKE_CURVE = 3;
    public static final int WRIST_MID_CONE_CURVE = 3;
    public static final int WRIST_MID_CUBE_CURVE = 3;
    public static final int WRIST_HIGH_CONE_CURVE = 3;
    public static final int WRIST_HIGH_CUBE_CURVE  = 3;
    public static final int WRIST_DOUBLE_SUB_CURVE = 3;

    /* Motion Magic */
    public static final double WRIST_CRUISE_VELOCITY = 5000; // falcon ticks/100ms
    public static final double WRIST_ACCELERATION = 50000; // falcon ticks/100ms/sec
    public static final int WRIST_CURVE_STRENGTH = 3; // value between 0 and 8

    /* Speeds */
    public static final double OUTTAKE_CONE = -3190; //negative
    public static final double OUTTAKE_CUBE = 800; //positive
    public static final double SHOOT_CUBE = 3190; //positive
    public static final double INTAKE_CONE = 3190; //positive
    public static final double INTAKE_CUBE = -1500; //negative
    public static final double INTAKE_STOP = 0;
    public static final double HOLD_VOLTAGE = 100; //always running while cone is held

    /* Positions */

    /* Cone Placement Positions */
    public static final double WRIST_OUTTAKE_HIGH_CONE = 52985; //52800 at Esky
    public static final double WRIST_OUTTAKE_MID_CONE = 44542; //45423 at Esky
    public static final double WRIST_OUTTAKE_LOW_CONE = 57435; //5745 at Esky

    /* Cube Placement Positions */
    public static final double WRIST_OUTTAKE_HIGH_CUBE = 40000; //50965 at Esky
    public static final double WRIST_OUTTAKE_MID_CUBE = 0; //53965 at Esky
    public static final double WRIST_OUTTAKE_LOW_CUBE = 53965; //53965 at Esky

    /* Cone Intake Positions */
    public static final double WRIST_INTAKE_CONE_FLOOR = 60816; //60816 at Esky
    //DOUBLE SUBSTATION:
    public static final double WRIST_INTAKE_CONE_SUBSTATION = 25088; //54226 at Esky
    //SINGLE SUBSTATION:
    public static final double WRIST_SINGLE_INTAKE = 23898; //23898 at Esky

    /* Cube Intake Positions */
    public static final double WRIST_INTAKE_CUBE = 64248; //64248 at Esky
    public static final double WRIST_INTAKE_CUBE_FLOOR = 64248; //64248 at Esky

    public static final double RESET_WRIST = 0; //Wrist Zero

    /*============================
                Elevator 
    ==============================*/

    /* CAN ID */
    public static final int ELEVATOR_MOTOR = 30;

    /* Elevator Current Limit */
    public static final boolean ELEVATOR_ENABLE_CURRENT_LIMIT = true;
    public static final int ELEVATOR_CONTINUOUS_CURRENT_LIMIT = 10;
    public static final int ELEVATOR_PEAK_CURRENT_LIMIT = 30;
    public static final double ELEVATOR_PEAK_CURRENT_DURATION = 0.1;

    /* Elevator Settings */
    public static final boolean ELEVATOR_INVERTED = true;
    public static final NeutralMode ELEVATOR_NEUTRAL_MODE = NeutralMode.Coast;

    /* Setpoints */
    public static final int ELEVATOR_DOWN = 100; //Elevator Zero

    /* Cube Placement */
    public static final int CUBE_HIGH = 117334; //TEST VALUE
    public static final int CUBE_MID = 31647; //TEST VALUE

    /* Cone Placement */
    public static final int CONE_HIGH = 114467; //TEST VALUE
    public static final int CONE_MID = 51572; //TEST VALUE

    /* Double Substation */
    public static final int SUBSTATION = 42000; //TEST VALUE

    //TODO: tune elevator single sub setpoint here
    /* Single Substation */
    public static final int ELEVATOR_SINGLE_SUB = 0;

    /* Elevator PID */
    public static final double ELEVATOR_P = 0.1;
    public static final double ELEVATOR_I = 0;
    public static final double ELEVATOR_D = 0;
    public static final double ELEVATOR_F = 0.0;

    /* Motion Magic */
    public static final double ELEVATOR_CRUISE_VELOCITY = 20000; // falcon ticks/100ms
    public static final double ELEVATOR_ACCELERATION = 250000; // falcon ticks/100ms/sec
    public static final int ELEVATOR_CURVE_STRENGTH = 5; // value between 0 and 8

    /*============================
                Slide
    ==============================*/

    /* Slide CAN ID */
    public static final int SLIDE_MOTOR = 20;

    /* Slide Current Limits */
    public static final boolean SLIDE_ENABLE_CURRENT_LIMIT = true;
    public static final int SLIDE_CONTINUOUS_CURRENT_LIMIT = 10;
    public static final int SLIDE_PEAK_CURRENT_LIMIT = 30;
    public static final double SLIDE_PEAK_CURRENT_DURATION = 0.1;

    /* Slide Settings */
    public static final boolean SLIDE_INVERTED = true;
    public static final NeutralMode SLIDE_NEUTRAL_MODE = NeutralMode.Brake;

    /* Setpoints */
    public static final int SLIDE_INTAKE = 0; //Should be 0
    public static final int SLIDE_IN = 0; //Should be 0

    /* Double Sub */
    public static final int SLIDE_SUBSTATION = 0; //Should be 0

    //TODO: tune slide single sub setpoint here
    /* Single Sub */
    public static final int SLIDE_SINGLE_SUB = 0;

    /* Cone Placement Setpoints */
    public static final int SLIDE_CONE_MID = 4000; //0 at Esky
    public static final int SLIDE_CONE_HIGH = 17377; //13000 at Esky

    /* Cube Placement Setpoints */
    public static final int SLIDE_CUBE_MID = 22063; //8020 at Esky
    public static final int SLIDE_CUBE_HIGH = 16971; //21729 at Esky

    /* Slide Velocities */
    //NOTE: all velocities are in falcon ticks/100ms
    public static final int SLIDE_IN_VELOCITY = 5000; //velocity when moving the slide in
    public static final int SLIDE_SUBSTATION_VELOCITY = 0; //velocity when sending the slide to double sub (0)

    //TODO: tune slide single sub velocity here
    public static final int SLIDE_SINGLE_SUB_VELOCITY = 20000;

    public static final int SLIDE_CONE_MID_VELOCITY = 5000; //velocity when going to mid cones
    public static final int SLIDE_CONE_HIGH_VELOCITY = 1500; //velocity when going to high cones
    public static final int SLIDE_CUBE_HIGH_VELOCITY = 2000; //velocity when going to high cubes
    public static final int SLIDE_CUBE_MID_VELOCITY = 20000; //velocity when going to mid cubes

    /* Slide PID */
    public static final double SLIDE_P = 0.1;
    public static final double SLIDE_I = 0;
    public static final double SLIDE_D = 0;
    public static final double SLIDE_F = 0;

    /* Motion Magic */
    public static final double SLIDE_CRUISE_VELOCITY = 5000; // falcon ticks/100ms
    public static final double SLIDE_ACCELERATION = 100000; // falcon ticks/100ms/sec
    public static final int SLIDE_CURVE_STRENGTH = 2; // number between 1 and 8

    /*============================
               Kinematics
    ==============================*/

    /* Drivetrain Dimensions */
    public static final double DRIVETRAIN_WIDTH = Units.inchesToMeters(18.75);
    public static final double DRIVETRAIN_LENGTH = Units.inchesToMeters(22.75);
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(4);
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;

    public static final Translation2d[] moduleTranslations = new Translation2d[]{
        new Translation2d(DRIVETRAIN_LENGTH / 2.0, DRIVETRAIN_WIDTH / 2.0),
        new Translation2d(DRIVETRAIN_LENGTH / 2.0, -DRIVETRAIN_WIDTH / 2.0),
        new Translation2d(-DRIVETRAIN_LENGTH / 2.0, DRIVETRAIN_WIDTH / 2.0),
        new Translation2d(-DRIVETRAIN_LENGTH / 2.0, -DRIVETRAIN_WIDTH / 2.0)
    };

    public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(moduleTranslations);

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

    public static final PIDConstants AUTO_TRANSLATION_CONSTANTS = new PIDConstants(0.01, 0, 0);
    public static final PIDConstants AUTO_ROTATION_CONSTANTS = new PIDConstants(2.8, 0, 0);

    /*============================
                Misc.
    ==============================*/

    public static final double OPEN_LOOP_RAMP = 0.25;
    public static final double CLOSED_LOOP_RAMP = 0.0;

    /* Delays */
    public static final double HIGH_DELAY = 0; //How long to delay moving the slide and wrist when going high
    public static final double MID_DELAY = 0; //How long to delay moving the slide and wrist when going mid
    public static final double RESET_DELAY = 0; //How long to delay moving the slide and wrist when going to reset

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