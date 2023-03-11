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

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kX.value);
    private final JoystickButton outtake = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final POVButton zeroElev = new POVButton(operator, 90);
    private final POVButton elevUp = new POVButton(operator, 0);
    private final POVButton elevDown = new POVButton(operator, 180);

    /* Operator Buttons */
    private final JoystickButton cubeMode = new JoystickButton(operator, XboxController.Button.kStart.value);
    private final JoystickButton coneMode = new JoystickButton(operator, XboxController.Button.kBack.value);
    private final JoystickButton robotHigh = new JoystickButton(operator, XboxController.Button.kX.value);
    private final JoystickButton reset = new JoystickButton(operator, XboxController.Button.kA.value);
    private final JoystickButton substation = new JoystickButton(operator, XboxController.Button.kLeftStick.value);
    private final JoystickButton robotMid = new JoystickButton(operator, XboxController.Button.kY.value);
    private final JoystickButton robotLow = new JoystickButton(operator, XboxController.Button.kB.value);
    private final JoystickButton intake = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
    private final POVButton singleSubstation = new POVButton(operator, 270);
    //private final POVButton floor = new POVButton(operator, 90);

    //TODO: test buttons
    //private final JoystickButton start = new JoystickButton(operator, XboxController.Button.kStart.value);
    //private final POVButton dpadUp = new POVButton(operator, 0);
    //private final POVButton dpadDown = new POVButton(operator, 180);
    private final JoystickButton rightJoy = new JoystickButton(operator, XboxController.Button.kRightStick.value);
    private final JoystickButton rightBumper = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
    private final JoystickButton aButton = new JoystickButton(driver, XboxController.Button.kA.value);
    //private final JoystickButton rightStick = new JoystickButton(operator, XboxController.Button.kRightStick.value);
    //private final JoystickButton leftStick = new JoystickButton(operator, XboxController.Button.kLeftStick.value);

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
                () -> false
            )
        );

        /* Auto Chooser */
        autoChooser.setDefaultOption("Cone + Park", Auto.PreloadParkCone());
        autoChooser.addOption("Cable Preload + 1", Auto.TWoGamepieceCable());
        autoChooser.addOption("Non-Cable Preload + 1", Auto.TwoGamepieceNonCable());
        autoChooser.addOption("Non-Cable Preload + 1 + Park", Auto.TwoHighParkNoCable());
        autoChooser.addOption("Non-Cable 3 Low", Auto.ThreeLowNonCable());
        autoChooser.addOption("Cable 3 Low", Auto.ThreeLowCable());
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
        new InstantCommand(() -> m_intake.setWristPosition(Constants.RESET_WRIST))),
        new WaitCommand(0.3),
        new InstantCommand(() -> m_elevator.setElevatorState(m_robotState.getRobotState().elevatorState))));

        outtake.whileTrue(new SetOuttake(m_intake, m_robotState));
        outtake.onFalse(new InstantCommand(() -> m_intake.setIntake(0)));

        /* Operator Buttons */

        coneMode.onTrue(new InstantCommand(() -> m_robotState.setGamepieceState(true)));
        cubeMode.onTrue(new InstantCommand(() -> m_robotState.setGamepieceState(false)));

        robotHigh.onTrue(new SequentialCommandGroup(new SetRobotStateHigh(m_robotState, m_intake),
        new InstantCommand(() -> m_slide.setSlideState(m_robotState.getRobotState().slideState)),
        new InstantCommand(() -> m_elevator.setElevatorState(m_robotState.getRobotState().elevatorState)),
        new WaitCommand(0.8),
        new ParallelCommandGroup(
        new InstantCommand(() -> m_intake.setIntakeState(m_robotState.getRobotState().intakeState)))));

        reset.onTrue(new SequentialCommandGroup(new ResetRobot(m_robotState, m_intake),
                                                new ParallelCommandGroup(new InstantCommand(() -> m_slide.setSlideState(m_robotState.getRobotState().slideState)),
                                                new InstantCommand(() -> m_intake.setIntakeState(m_robotState.getRobotState().intakeState))),
                                                new WaitCommand(0.3),
                                                new InstantCommand(() -> m_elevator.setElevatorState(m_robotState.getRobotState().elevatorState))));

        robotMid.onTrue(new SequentialCommandGroup(new SetRobotStateMid(m_robotState, m_intake),
        new InstantCommand(() -> m_slide.setSlideState(m_robotState.getRobotState().slideState)),
        new InstantCommand(() -> m_elevator.setElevatorState(m_robotState.getRobotState().elevatorState)),
        new WaitCommand(0.6),
        new ParallelCommandGroup(
        new InstantCommand(() -> m_intake.setIntakeState(m_robotState.getRobotState().intakeState)))));

        robotLow.onTrue(new SequentialCommandGroup(new SetRobotStateLow(m_robotState, m_intake),
        new InstantCommand(() -> m_elevator.setElevatorState(m_robotState.getRobotState().elevatorState)),
        new ParallelCommandGroup(new InstantCommand(() -> m_slide.setSlideState(m_robotState.getRobotState().slideState)),
        new InstantCommand(() -> m_intake.setIntakeState(m_robotState.getRobotState().intakeState)))));

        substation.onTrue(new SequentialCommandGroup(new SubstationIntake(m_intake, m_robotState),
        new InstantCommand(() -> m_slide.setSlideState(m_robotState.getRobotState().slideState)),
        new InstantCommand(() -> m_elevator.setElevatorState(m_robotState.getRobotState().elevatorState)),
        new WaitCommand(0.8),
        new ParallelCommandGroup(
        new InstantCommand(() -> m_intake.setIntakeState(m_robotState.getRobotState().intakeState)))));

        coneMode.onTrue(new InstantCommand(() ->m_candleSubsystem.setLEDSTate(LEDState.CONE)));
        cubeMode.onTrue(new InstantCommand(() -> m_candleSubsystem.setLEDSTate(LEDState.CUBE))); 

        singleSubstation.onTrue(new InstantCommand(() -> m_intake.setSingleConeState(false)));
        //floor.onTrue(new InstantCommand(() -> m_intake.setSingleConeState(true)));


        //TODO: Intake testing code

        /*start.whileTrue(new InstantCommand(() -> m_intake.setWristPosition(50000)));

        dpadUp.whileTrue(new InstantCommand(() -> m_intake.continuousWristMotion(0.2)));
        dpadUp.whileFalse(new InstantCommand(() -> m_intake.continuousWristMotion(0)));

        back.whileTrue(new InstantCommand(() -> m_intake.continuousWristMotion(-0.2)));
        back.whileFalse(new InstantCommand(() -> m_intake.continuousWristMotion(0)));

        rightStick.whileTrue(new InstantCommand(() -> m_intake.setIntake(-0.5)));
        rightStick.whileFalse(new InstantCommand(() -> m_intake.setIntake(0)));

        leftSTick.whileTrue(new InstantCommand(() -> m_intake.setIntake(0.5)));
        leftSTick.whileFalse(new InstantCommand(() -> m_intake.setIntake(0))); */

        //dpadDown.whileTrue(new InstantCommand(() -> m_intake.continuousWristMotion(-0.2)));
        //dpadDown.whileFalse(new InstantCommand(() -> m_intake.continuousWristMotion(0)));

        //dpadUp.whileTrue(new InstantCommand(() -> m_intake.continuousWristMotion(0.2)));
        //dpadUp.whileFalse(new InstantCommand(() -> m_intake.continuousWristMotion(0)));

        rightJoy.onTrue(new InstantCommand(() -> m_intake.zeroWrist()));

        //leftBumper.onTrue(new InstantCommand(() -> m_intake.holdIntakePosition(50000)));
        //leftBumper.onFalse(new InstantCommand(() -> m_intake.holdIntakePosition(m_intake.getIntakeEncoder())));

        elevDown.whileTrue(new InstantCommand(() -> m_elevator.setElevatorSpeeed(-0.5)));
        elevDown.whileFalse(new InstantCommand(() -> m_elevator.setElevatorSpeeed(0)));

        elevUp.whileTrue(new InstantCommand(() -> m_elevator.setElevatorSpeeed(0.5)));
        elevUp.whileFalse(new InstantCommand(() -> m_elevator.setElevatorSpeeed(0)));

        zeroElev.onTrue(new InstantCommand(() -> m_elevator.setElevatorSetpoint(0)));

        rightBumper.whileTrue(new AutoBalance(m_drivetrain));
        rightBumper.onFalse(new InstantCommand(() -> m_drivetrain.stopSwerve()));

        aButton.whileTrue(new AutoBalanceSketchy(m_drivetrain));
        aButton.onFalse(new InstantCommand(() -> m_drivetrain.stopSwerve()));


    }

    public Command getAutonomousCommand() {

        return autoChooser.getSelected();

    }

}