/* package frc.robot.autos.automodes;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import java.util.HashMap;
import java.util.Map;

public final class Auto {
  private static final Map<String, Command> eventMap = new HashMap<>(Map.ofEntries(
          Map.entry("Stop", new InstantCommand(RobotContainer.m_drivetrain::stopSwerve)),
          Map.entry("example2", Commands.print("Example 2 triggered")),
          Map.entry("example3", Commands.print("Example 3 triggered"))
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

} */
