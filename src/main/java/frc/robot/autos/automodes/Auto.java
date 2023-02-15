package frc.robot.autos.automodes;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.AutonOuttake;
import frc.robot.commands.ResetRobot;
import frc.robot.commands.SetIntake;
import frc.robot.commands.SetOuttake;
import frc.robot.commands.SetRobotStateHigh;
import frc.robot.commands.SetRobotStateLow;
import frc.robot.commands.SetRobotStateMid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import java.util.HashMap;
import java.util.Map;

public final class Auto {

  private static final Map<String, Command> eventMap = new HashMap<>(Map.ofEntries(
          Map.entry("Stop", new InstantCommand(RobotContainer.m_drivetrain::stopSwerve)),
          Map.entry("CubeMode", new InstantCommand(() -> RobotContainer.m_robotState.setGamepieceState(false))),
          Map.entry("ConeMode", new InstantCommand(() -> RobotContainer.m_robotState.setGamepieceState(true))),
          Map.entry("HighCone", new SequentialCommandGroup(new SetRobotStateHigh(RobotContainer.m_robotState, RobotContainer.m_intake),
          new InstantCommand(() -> RobotContainer.m_elevator.setElevatorState(RobotContainer.m_robotState.getRobotState().elevatorState)),
          new WaitCommand(0.8),
          new ParallelCommandGroup(new InstantCommand(() -> RobotContainer.m_slide.setSlide(Constants.SLIDE_CONE_HIGH - 3000)),
          new InstantCommand(() -> RobotContainer.m_intake.setIntakeState(RobotContainer.m_robotState.getRobotState().intakeState))))),
          Map.entry("HighCube", new SequentialCommandGroup(new SetRobotStateHigh(RobotContainer.m_robotState, RobotContainer.m_intake),
          new InstantCommand(() -> RobotContainer.m_elevator.setElevatorState(RobotContainer.m_robotState.getRobotState().elevatorState)),
          new WaitCommand(0.8),
          new ParallelCommandGroup(new InstantCommand(() -> RobotContainer.m_slide.setSlide(Constants.SLIDE_CUBE_HIGH)),
          new InstantCommand(() -> RobotContainer.m_intake.setIntakeState(RobotContainer.m_robotState.getRobotState().intakeState))))),
          Map.entry("Mid", new SetRobotStateMid(RobotContainer.m_robotState, RobotContainer.m_intake)),
          Map.entry("Low", new SetRobotStateLow(RobotContainer.m_robotState, RobotContainer.m_intake)),
          Map.entry("Intake", new SetIntake(RobotContainer.m_intake, RobotContainer.m_robotState)),
          Map.entry("Outtake", new SequentialCommandGroup(new WaitCommand(0.5), new AutonOuttake(RobotContainer.m_intake, RobotContainer.m_robotState))),
          Map.entry("Delay1", new WaitCommand(1)),
          Map.entry("Reset", new SequentialCommandGroup(new ResetRobot(RobotContainer.m_robotState, RobotContainer.m_intake),
          new ParallelCommandGroup(new InstantCommand(() -> RobotContainer.m_slide.setSlideState(RobotContainer.m_robotState.getRobotState().slideState)),
          new InstantCommand(() -> RobotContainer.m_intake.setIntakeState(RobotContainer.m_robotState.getRobotState().intakeState))),
          new WaitCommand(0.3),
          new InstantCommand(() -> RobotContainer.m_elevator.setElevatorState(RobotContainer.m_robotState.getRobotState().elevatorState))))
  ));

  private static final SwerveAutoBuilder autoBuilder = new SwerveAutoBuilder(
          RobotContainer.m_drivetrain::getPose,
          RobotContainer.m_drivetrain::resetOdometry,
          Constants.AUTO_TRANSLATION_CONSTANTS,
          Constants.AUTO_ROTATION_CONSTANTS,
          RobotContainer.m_drivetrain::setChassisSpeeds,
          eventMap,
          RobotContainer.m_drivetrain
  );

  public static CommandBase exampleAuto() {
    return autoBuilder.fullAuto(PathPlanner.loadPathGroup("Example Auto", new PathConstraints(1, 1)));
  }

  public static CommandBase none() {
    return Commands.none();
  }

  public static CommandBase ThreeGamepieceSafe() {

    return autoBuilder.fullAuto(PathPlanner.loadPathGroup("3-Gamepiece-Safe", new PathConstraints(1, 1)));

  }

  public static CommandBase PreloadParkCube() {

    return autoBuilder.fullAuto(PathPlanner.loadPathGroup("Preload-Park-Cube", new PathConstraints(1, 1)));

  }

  public static CommandBase PreloadParkCone() {

    return autoBuilder.fullAuto(PathPlanner.loadPathGroup("Preload-Park-Cone", new PathConstraints(1, 1)));


  }

  private Auto() {
    throw new UnsupportedOperationException("This is a utility class!");
  }

}