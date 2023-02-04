package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.autos.automodes.Auto;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

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
    //private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kRightBumper.value);

    /* Operator Buttons */
    private final JoystickButton intake = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
    private final JoystickButton outtake = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
    private final JoystickButton elevatorUp = new JoystickButton(operator, XboxController.Button.kY.value);
    private final JoystickButton elevatorDown = new JoystickButton(operator, XboxController.Button.kA.value);
    private final JoystickButton slideOut = new JoystickButton(operator, XboxController.Button.kX.value);
    private final JoystickButton slideIn = new JoystickButton(operator, XboxController.Button.kB.value);
    private final POVButton hoodDown = new POVButton(operator, 0);
    private final POVButton hoodUp = new POVButton(operator, 180);

    /* Subsystems */
    public static final SwerveDrivetrain m_drivetrain = new SwerveDrivetrain();
    public static final Elevator m_elevator = new Elevator();
    public static final Intake m_intake = new Intake();
    public static final Slide m_slide = new Slide();

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

        /* Operator Buttons */

        intake.whileTrue(new InstantCommand(() -> m_intake.setIntake(Constants.INTAKE_TEST_SPEED)));
        intake.onFalse(new InstantCommand(() -> m_intake.setIntake(0)));
        outtake.whileTrue(new InstantCommand(() -> m_intake.setIntake(-Constants.INTAKE_TEST_SPEED)));
        outtake.onFalse(new InstantCommand(() -> m_intake.setIntake(0)));

        elevatorUp.onTrue(new InstantCommand(() -> m_elevator.setElevatorSetpoint(214380/5)));
        elevatorUp.onFalse(new InstantCommand(() -> m_elevator.setElevatorSpeeed(0)));
        elevatorDown.onTrue(new InstantCommand(() -> m_elevator.setElevatorSetpoint(0)));
        elevatorDown.onFalse(new InstantCommand(() -> m_elevator.setElevatorSpeeed(0)));

        slideOut.onTrue(new InstantCommand(() -> m_slide.setSlide(20000)));
        slideOut.onFalse(new InstantCommand(() -> m_slide.setSlideSpeed(0)));
        slideIn.onTrue(new InstantCommand(() -> m_slide.setSlide(10000)));
        slideIn.onFalse(new InstantCommand(() -> m_slide.setSlideSpeed(0)));

        hoodUp.whileTrue(new InstantCommand(() -> m_intake.setHoodSpeed(Constants.HOOD_TEST_SPEED)));
        hoodUp.onFalse(new InstantCommand(() -> m_intake.setHoodSpeed(0)));
        hoodDown.whileTrue(new InstantCommand(() -> m_intake.setHoodSpeed(-Constants.HOOD_TEST_SPEED)));
        hoodDown.onFalse(new InstantCommand(() -> m_intake.setHoodSpeed(0)));

    }

    public Command getAutonomousCommand() {

        return Auto.exampleAuto();

    }

}