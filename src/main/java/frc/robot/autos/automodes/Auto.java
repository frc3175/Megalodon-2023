package frc.robot.autos.automodes;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.AutoBalance;
import frc.robot.commands.AutoBalanceReverse;
import frc.robot.commands.AutoBalanceSketchy;
import frc.robot.commands.AutonOuttake;
import frc.robot.commands.ResetRobot;
import frc.robot.commands.SetIntake;
import frc.robot.commands.SetRobotStateHigh;
import frc.robot.commands.SetRobotStateLow;
import frc.robot.commands.SetRobotStateMid;
import frc.robot.commands.TurnTo180;
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

          Map.entry("HoldVoltage", new InstantCommand(() -> RobotContainer.m_intake.setIntake(Constants.HOLD_VOLTAGE))),

          Map.entry("HighCone", new SequentialCommandGroup(new SetRobotStateHigh(RobotContainer.m_robotState, RobotContainer.m_intake),
          new ParallelCommandGroup(
          new InstantCommand(() -> RobotContainer.m_slide.setSlideState(RobotContainer.m_robotState.getRobotState().slideState)),
          new InstantCommand(() -> RobotContainer.m_elevator.setElevatorState(RobotContainer.m_robotState.getRobotState().elevatorState))),
          new WaitCommand(Constants.HIGH_DELAY),
          new InstantCommand(() -> RobotContainer.m_intake.setIntakeState(RobotContainer.m_robotState.getRobotState().intakeState)),
          new WaitCommand(1),
          new AutonOuttake(RobotContainer.m_intake, RobotContainer.m_robotState, RobotContainer.m_slide, RobotContainer.m_elevator))),

          Map.entry("HighCube", new SequentialCommandGroup(new SetRobotStateHigh(RobotContainer.m_robotState, RobotContainer.m_intake),
          new ParallelCommandGroup(
          new InstantCommand(() -> RobotContainer.m_slide.setSlideState(RobotContainer.m_robotState.getRobotState().slideState)),
          new InstantCommand(() -> RobotContainer.m_elevator.setElevatorState(RobotContainer.m_robotState.getRobotState().elevatorState))),
          new WaitCommand(Constants.HIGH_DELAY),
          new InstantCommand(() -> RobotContainer.m_intake.setIntakeState(RobotContainer.m_robotState.getRobotState().intakeState)),
          new WaitCommand(1),
          new AutonOuttake(RobotContainer.m_intake, RobotContainer.m_robotState, RobotContainer.m_slide, RobotContainer.m_elevator))),

          Map.entry("Mid", new SetRobotStateMid(RobotContainer.m_robotState, RobotContainer.m_intake)),

          Map.entry("Low", new SequentialCommandGroup(new SetRobotStateLow(RobotContainer.m_robotState, RobotContainer.m_intake),
          new InstantCommand(() -> RobotContainer.m_elevator.setElevatorState(RobotContainer.m_robotState.getRobotState().elevatorState)),
          new ParallelCommandGroup(new InstantCommand(() -> RobotContainer.m_slide.setSlideState(RobotContainer.m_robotState.getRobotState().slideState)),
          new InstantCommand(() -> RobotContainer.m_intake.setIntakeState(RobotContainer.m_robotState.getRobotState().intakeState))))),

          Map.entry("IntakeState", new SetIntake(RobotContainer.m_intake, RobotContainer.m_robotState)),

          Map.entry("Intake", new InstantCommand(() -> RobotContainer.m_intake.setIntakeState(RobotContainer.m_robotState.getRobotState().intakeState))),

          Map.entry("IntakeWrist", new InstantCommand(() -> RobotContainer.m_intake.setWristPosition(Constants.WRIST_INTAKE_CUBE, Constants.WRIST_LOW_VELOCITY, Constants.WRIST_LOW_ACCEL, Constants.WRIST_LOW_CURVE))),

          Map.entry("Outtake", new SequentialCommandGroup(new AutonOuttake(RobotContainer.m_intake, RobotContainer.m_robotState, RobotContainer.m_slide, RobotContainer.m_elevator))),

          Map.entry("Shoot", new InstantCommand(() -> RobotContainer.m_intake.setIntake(3190))),

          Map.entry("Delay1", new WaitCommand(1)),

          Map.entry("TurnTo180", new TurnTo180(RobotContainer.m_drivetrain)),

          Map.entry("Zero", new InstantCommand(() -> RobotContainer.m_drivetrain.m_gyro.setYaw(RobotContainer.m_drivetrain.getYaw().getDegrees() + 180))),

          Map.entry("AutoBalance", new AutoBalanceSketchy(RobotContainer.m_drivetrain)),

          Map.entry("AutoBalanceFromStation", new AutoBalance(RobotContainer.m_drivetrain, RobotContainer.m_robotState, RobotContainer.m_intake)),

          Map.entry("AutoBalanceReverse", new AutoBalanceReverse(RobotContainer.m_drivetrain)),

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
          true,
          RobotContainer.m_drivetrain
  );

  public static CommandBase none() {
    return Commands.none();
  }

  public static CommandBase ThreeLowCableBlue() {

    return autoBuilder.fullAuto(PathPlanner.loadPathGroup("3-Low-Cable-Blue", new PathConstraints(4, 3)));

  }

  public static CommandBase ThreeLowNonCableBlue() {

    return autoBuilder.fullAuto(PathPlanner.loadPathGroup("3-Low-No-Cable-Blue", new PathConstraints(4, 3)));

  }

  public static CommandBase TWoGamepieceCableBlue() {

    return autoBuilder.fullAuto(PathPlanner.loadPathGroup("2-Gamepiece-Cable-Blue", new PathConstraints(3.5, 3)));

  }

  public static CommandBase TwoGamepieceNonCableBlue() {

    return autoBuilder.fullAuto(PathPlanner.loadPathGroup("2-Gamepiece-No-Cable-Blue", new PathConstraints(2.5, 3)));
  }

  public static CommandBase PreloadParkConeBlue() {

    return autoBuilder.fullAuto(PathPlanner.loadPathGroup("Preload-Park-Cone-Blue", new PathConstraints(1, 1)));

  }

  public static CommandBase PreloadMobilityParkBlue() {

    return autoBuilder.fullAuto(PathPlanner.loadPathGroup("Preload-Mobility-Park-Blue", new PathConstraints(2, 3)));

  }

  public static CommandBase OnlyMobilityBalance() {

    return autoBuilder.fullAuto(PathPlanner.loadPathGroup("Only-Mobility-Balance", new PathConstraints(2, 3)));

  }

  public static CommandBase threeLowCableRed() {

    return autoBuilder.fullAuto(PathPlanner.loadPathGroup("3-Low-Cable-Red", new PathConstraints(4, 3)));

  }

  public static CommandBase ThreeLowNonCableRed() {

    return autoBuilder.fullAuto(PathPlanner.loadPathGroup("3-Low-No-Cable-Red", new PathConstraints(4, 3)));

  }

  public static CommandBase TWoGamepieceCableRed() {

    return autoBuilder.fullAuto(PathPlanner.loadPathGroup("2-Gamepiece-Cable-Red", new PathConstraints(3.5, 3)));

  }

  public static CommandBase TwoGamepieceNonCableRed() {

    return autoBuilder.fullAuto(PathPlanner.loadPathGroup("2-Gamepiece-No-Cable-Red", new PathConstraints(2.5, 3)));
  }

  public static CommandBase PreloadParkConeRed() {

    return autoBuilder.fullAuto(PathPlanner.loadPathGroup("Preload-Park-Cone-Red", new PathConstraints(1, 1)));

  }

  public static CommandBase PreloadMobilityParkRed() {

    return autoBuilder.fullAuto(PathPlanner.loadPathGroup("Preload-Mobility-Park-Red", new PathConstraints(2, 3)));

  }



  private Auto() {
    throw new UnsupportedOperationException("This is a utility class!");
  }

}