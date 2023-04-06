package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.autos.automodes.Auto;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.CANdleSystem.LEDState;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    /* Controllers */
    private final XboxController driver = new XboxController(0);
    private final XboxController operator = new XboxController(1);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;
    //private final int evade = XboxController.Button.kRightBumper.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kX.value);
    private final JoystickButton outtake = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton shootCube =  new JoystickButton(driver, XboxController.Button.kB.value);

    /* Operator Buttons */
    private final JoystickButton cubeMode = new JoystickButton(operator, XboxController.Button.kStart.value);
    private final JoystickButton coneMode = new JoystickButton(operator, XboxController.Button.kBack.value);
    private final JoystickButton robotHigh = new JoystickButton(operator, XboxController.Button.kX.value);
    private final JoystickButton reset = new JoystickButton(operator, XboxController.Button.kA.value);
    private final JoystickButton substation = new JoystickButton(operator, XboxController.Button.kLeftStick.value);
    private final JoystickButton robotMid = new JoystickButton(operator, XboxController.Button.kY.value);
    private final JoystickButton robotLow = new JoystickButton(operator, XboxController.Button.kB.value);
    private final JoystickButton intake = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
    private final JoystickButton retractWrist = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
    private final POVButton singleSubstation = new POVButton(operator, 270);
    private final POVButton floorCone = new POVButton(operator, 90);

    private final JoystickButton rightJoy = new JoystickButton(operator, XboxController.Button.kRightStick.value);
    private final POVButton elevUp = new POVButton(operator, 0);
    private final POVButton elevDown = new POVButton(operator, 180);
    // private final JoystickButton aButton = new JoystickButton(driver, XboxController.Button.kA.value);

    /* Subsystems */
    public static final SwerveDrivetrain m_drivetrain = new SwerveDrivetrain();
    public static final Elevator m_elevator = new Elevator();
    public static final Intake m_intake = new Intake();
    public static final Slide m_slide = new Slide();
    public static final RobotState m_robotState = new RobotState(m_intake, m_elevator, m_slide);
    public static final CANdleSystem m_candleSubsystem = new CANdleSystem();

    /* Autos */
    private static final SendableChooser<CommandBase> autoChooser = new SendableChooser<>();

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

        m_drivetrain.setDefaultCommand(
            new SwerveDrive(
                m_drivetrain, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> false,
                () -> driver.getRightBumper()
            )
        );

        /* Auto Chooser */
        autoChooser.setDefaultOption("BLUE 1 + Balance", Auto.PreloadParkConeBlue());
        autoChooser.addOption("BLUE 1 + Mobility + Balance + Yeet", Auto.PreloadMobilityParkBlue());
        autoChooser.addOption("BLUE Cable 2", Auto.TWoGamepieceCableBlue());
        autoChooser.addOption("BLUE Non-Cable 2", Auto.TwoGamepieceNonCableBlue());
        autoChooser.addOption("BLUE Non-Cable 3", Auto.ThreeLowNonCableBlue());
        autoChooser.addOption("BLUE Cable 3", Auto.ThreeLowCableBlue());
        autoChooser.addOption("RED 1 + Balance", Auto.PreloadParkConeRed());
        autoChooser.addOption("RED 1 + Mobillity + Balance + Yeet", Auto.PreloadMobilityParkRed());
        autoChooser.addOption("RED Cable 2", Auto.TWoGamepieceCableRed());
        autoChooser.addOption("RED Non-Cable 2", Auto.TwoGamepieceNonCableRed());
        autoChooser.addOption("RED Non-Cable 3", Auto.ThreeLowNonCableRed());
        autoChooser.addOption("RED Cable 3", Auto.threeLowCableRed());
        SmartDashboard.putData("Auto mode", autoChooser);

        // Configure the button bindings
        configureButtonBindings();
        
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {

        /* Driver Buttons */

        zeroGyro.onTrue(new InstantCommand(() -> m_drivetrain.zeroGyro()));
        
        intake.whileTrue(new SequentialCommandGroup(new SetIntake(m_intake, m_robotState),
        new InstantCommand(() -> m_elevator.setElevatorState(m_robotState.getRobotState().elevatorState)),
        new ParallelCommandGroup(new InstantCommand(() -> m_slide.setSlideState(m_robotState.getRobotState().slideState)),
        new InstantCommand(() -> m_intake.setIntakeState(m_robotState.getRobotState().intakeState)))));

        intake.onFalse(new SequentialCommandGroup(new ResetRobot(m_robotState, m_intake),
        new ParallelCommandGroup(new InstantCommand(() -> m_slide.setSlideState(m_robotState.getRobotState().slideState)),
        new InstantCommand(() -> m_intake.setWristPosition(Constants.RESET_WRIST, Constants.WRIST_RESET_VELOCITY, Constants.WRIST_RESET_ACCEL, Constants.WRIST_RESET_CURVE))),
        new WaitCommand(0.3),
        new InstantCommand(() -> m_elevator.setElevatorState(m_robotState.getRobotState().elevatorState))));

        outtake.whileTrue(new SetOuttake(m_intake, m_robotState));
        outtake.onFalse(new SequentialCommandGroup(new ResetRobot(m_robotState, m_intake),
                                                new ParallelCommandGroup(new InstantCommand(() -> m_slide.setSlideState(m_robotState.getRobotState().slideState)),
                                                new InstantCommand(() -> m_intake.setIntakeState(m_robotState.getRobotState().intakeState))),
                                                new WaitCommand(Constants.RESET_DELAY),
                                                new InstantCommand(() -> m_elevator.setElevatorState(m_robotState.getRobotState().elevatorState))));

        /* Operator Buttons */

        coneMode.onTrue(new InstantCommand(() -> m_robotState.setGamepieceState(true)));
        cubeMode.onTrue(new InstantCommand(() -> m_robotState.setGamepieceState(false)));

        robotHigh.onTrue(new SequentialCommandGroup(new SetRobotStateHigh(m_robotState, m_intake),
        new ParallelCommandGroup(
        new InstantCommand(() -> m_slide.setSlideState(m_robotState.getRobotState().slideState)),
        new InstantCommand(() -> m_elevator.setElevatorState(m_robotState.getRobotState().elevatorState))),
        new WaitCommand(Constants.HIGH_DELAY),
        new InstantCommand(() -> m_intake.setIntakeState(m_robotState.getRobotState().intakeState)),
        new WaitCommand(1),
        new TeleAutoOuttake(m_intake, m_robotState, m_slide, m_elevator)));

        reset.onTrue(new SequentialCommandGroup(new ResetRobot(m_robotState, m_intake),
                                                new ParallelCommandGroup(new InstantCommand(() -> m_slide.setSlideState(m_robotState.getRobotState().slideState)),
                                                new InstantCommand(() -> m_intake.setIntakeState(m_robotState.getRobotState().intakeState))),
                                                new WaitCommand(Constants.RESET_DELAY),
                                                new InstantCommand(() -> m_elevator.setElevatorState(m_robotState.getRobotState().elevatorState))));

        robotMid.onTrue(new SequentialCommandGroup(new SetRobotStateMid(m_robotState, m_intake),
        new ParallelCommandGroup(
        new InstantCommand(() -> m_slide.setSlideState(m_robotState.getRobotState().slideState)),
        new InstantCommand(() -> m_elevator.setElevatorState(m_robotState.getRobotState().elevatorState))),
        new WaitCommand(Constants.MID_DELAY),
        new InstantCommand(() -> m_intake.setIntakeState(m_robotState.getRobotState().intakeState)),
        new WaitCommand(0.4),
        new TeleAutoOuttake(m_intake, m_robotState, m_slide, m_elevator)));

        robotLow.onTrue(new SequentialCommandGroup(new SetRobotStateLow(m_robotState, m_intake),
        new InstantCommand(() -> m_elevator.setElevatorState(m_robotState.getRobotState().elevatorState)),
        new ParallelCommandGroup(new InstantCommand(() -> m_slide.setSlideState(m_robotState.getRobotState().slideState)),
        new InstantCommand(() -> m_intake.setIntakeState(m_robotState.getRobotState().intakeState)))));

        substation.onTrue(new SequentialCommandGroup(new SubstationIntake(m_intake, m_robotState),
        new InstantCommand(() -> m_slide.setSlideState(m_robotState.getRobotState().slideState)),
        new InstantCommand(() -> m_elevator.setElevatorState(m_robotState.getRobotState().elevatorState)),
        new WaitCommand(Constants.HIGH_DELAY),
        new ParallelCommandGroup(
        new InstantCommand(() -> m_intake.setIntakeState(m_robotState.getRobotState().intakeState)))));

        coneMode.onTrue(new InstantCommand(() ->m_candleSubsystem.setLEDSTate(LEDState.CONE)));
        cubeMode.onTrue(new InstantCommand(() -> m_candleSubsystem.setLEDSTate(LEDState.CUBE))); 

        singleSubstation.onTrue(new InstantCommand(() -> m_intake.setSingleConeState(true)));
        floorCone.onTrue(new InstantCommand(() -> m_intake.setSingleConeState(false)));

        rightJoy.onTrue(new InstantCommand(() -> m_intake.zeroWrist()));

        elevDown.whileTrue(new InstantCommand(() -> m_intake.continuousWristMotion(-0.2)));
        elevDown.onFalse(new InstantCommand(() -> m_intake.continuousWristMotion(0)));

        elevUp.whileTrue(new InstantCommand(() -> m_intake.continuousWristMotion(0.2)));
        elevUp.onFalse(new InstantCommand(() -> m_intake.continuousWristMotion(0)));

        /* aButton.whileTrue(new AutoBalanceUsingRate(m_drivetrain));
        aButton.onFalse(new InstantCommand(() -> m_drivetrain.stopSwerve())); */

        retractWrist.onTrue(new InstantCommand(() -> m_intake.setWristPosition((m_robotState.getRobotState().intakeState.wristPosition - 15000), Constants.WRIST_RESET_VELOCITY, Constants.WRIST_RESET_ACCEL, Constants.WRIST_RESET_CURVE)));
        retractWrist.onFalse(new InstantCommand(() -> m_intake.setWristPosition(m_robotState.getRobotState().intakeState.wristPosition, Constants.WRIST_RESET_VELOCITY, Constants.WRIST_RESET_ACCEL, Constants.WRIST_RESET_CURVE)));

        retractWrist.whileTrue(new InstantCommand(() -> m_intake.setIntake(Constants.HOLD_VOLTAGE)));
        retractWrist.onFalse(new InstantCommand(() -> m_intake.setIntake(0)));

        shootCube.onTrue(new InstantCommand(() -> m_intake.setIntake(Constants.SHOOT_CUBE)));
        shootCube.onFalse(new InstantCommand(() -> m_intake.setIntake(0)));

    }

    public Command getAutonomousCommand() {
        
        return autoChooser.getSelected();

    }

}