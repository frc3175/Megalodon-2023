package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.autos.automodes.Auto;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Intake.IntakeState;

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
    private final JoystickButton intake = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
    private final JoystickButton outtake = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

    /* Operator Buttons */
    private final JoystickButton cubeMode = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
    private final JoystickButton coneMode = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
    private final JoystickButton robotHigh = new JoystickButton(operator, XboxController.Button.kY.value);
    private final JoystickButton reset = new JoystickButton(operator, XboxController.Button.kA.value);
    private final JoystickButton substation = new JoystickButton(operator, XboxController.Button.kLeftStick.value);
    private final JoystickButton robotMid = new JoystickButton(operator, XboxController.Button.kB.value);
    private final POVButton robotLow = new POVButton(operator, 180);

    //TODO: test buttons
    private final JoystickButton start = new JoystickButton(operator, XboxController.Button.kStart.value);
    private final JoystickButton back = new JoystickButton(operator, XboxController.Button.kBack.value);

    /* Subsystems */
    public static final SwerveDrivetrain m_drivetrain = new SwerveDrivetrain();
    public static final Elevator m_elevator = new Elevator();
    public static final Intake m_intake = new Intake();
    public static final Slide m_slide = new Slide();
    public static final RobotState m_robotState = new RobotState(m_intake, m_elevator, m_slide);

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
        autoChooser.setDefaultOption("Example Auto", Auto.exampleAuto());
        autoChooser.addOption("None", Auto.none());

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
        
        intake.whileTrue(new SetIntake(m_intake, m_robotState));
        intake.onFalse(new InstantCommand(() -> m_intake.setIntake(0)));

        outtake.whileTrue(new SetOuttake(m_intake, m_robotState));
        outtake.onFalse(new InstantCommand(() -> m_intake.setIntake(0)));

        /* Operator Buttons */

        coneMode.onTrue(new InstantCommand(() -> m_robotState.setGamepieceState(true)));
        cubeMode.onTrue(new InstantCommand(() -> m_robotState.setGamepieceState(false)));

        robotHigh.onTrue(new SetRobotStateHigh(m_robotState, m_intake));
        reset.onTrue(new ResetRobot(m_robotState, m_intake));
        robotMid.onTrue(new SetRobotStateMid(m_robotState, m_intake));
        robotLow.onTrue(new SetRobotStateLow(m_robotState, m_intake));

        substation.onTrue(new SubstationIntake(m_intake, m_robotState));

        //TODO: Intake testing code

        start.onTrue(new SetIntake(m_intake, m_robotState));
        start.whileFalse(new InstantCommand(() -> m_intake.setIntakeState(IntakeState.STOP)));

        back.whileTrue(new InstantCommand(() -> m_intake.continuousWristMotion(-0.2)));
        back.whileFalse(new InstantCommand(() -> m_intake.continuousWristMotion(0)));


    }

    public Command getAutonomousCommand() {

        return autoChooser.getSelected();

    }

}