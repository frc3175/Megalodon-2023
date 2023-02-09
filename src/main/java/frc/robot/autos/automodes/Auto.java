package frc.robot.autos.automodes;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.SetIntake;
import frc.robot.commands.SetOuttake;
import frc.robot.commands.SetRobotStateHigh;
import frc.robot.commands.SetRobotStateLow;
import frc.robot.commands.SetRobotStateMid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import java.util.HashMap;
import java.util.Map;

public final class Auto {

  private static final Map<String, Command> eventMap = new HashMap<>(Map.ofEntries(
          Map.entry("Stop", new InstantCommand(RobotContainer.m_drivetrain::stopSwerve)),
          Map.entry("CubeMode", new InstantCommand(() -> RobotContainer.m_robotState.setGamepieceState(false))),
          Map.entry("ConeMode", new InstantCommand(() -> RobotContainer.m_robotState.setGamepieceState(true))),
          Map.entry("High", new SetRobotStateHigh(RobotContainer.m_robotState, RobotContainer.m_intake)),
          Map.entry("Mid", new SetRobotStateMid(RobotContainer.m_robotState, RobotContainer.m_intake)),
          Map.entry("Low", new SetRobotStateLow(RobotContainer.m_robotState, RobotContainer.m_intake)),
          Map.entry("Intake", new SetIntake(RobotContainer.m_intake, RobotContainer.m_robotState)),
          Map.entry("Outtake", new SetOuttake(RobotContainer.m_intake, RobotContainer.m_robotState))
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

  private Auto() {
    throw new UnsupportedOperationException("This is a utility class!");
  }

}