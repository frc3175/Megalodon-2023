package frc.robot.commands;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDrivetrain;

public class OdometryAlign extends CommandBase {
  private final SwerveDrivetrain m_drivetrain;

  private PPSwerveControllerCommand pathDrivingCommand;

  private PathPlannerTrajectory m_trajectory;
  private final PathConstraints m_constraints;
  private final PathPoint m_finalPoint;


  public OdometryAlign(SwerveDrivetrain drivetrain, PathConstraints constraints, PathPoint finalPoint) {
   
    m_drivetrain = drivetrain;
    m_constraints = constraints;
    m_finalPoint = finalPoint;

    addRequirements(m_drivetrain);

  }

  @Override
  public void initialize() {

    var m_pose = m_drivetrain.getPose();

    m_trajectory = PathPlanner.generatePath(m_constraints, new PathPoint(new Translation2d(m_pose.getX(), m_pose.getY()), m_pose.getRotation(), m_pose.getRotation()), m_finalPoint);

    pathDrivingCommand = m_drivetrain.followTrajectoryCommand(m_trajectory, false);
    pathDrivingCommand.schedule();

  }

  @Override
  public void end(boolean interrupted)
  {
    pathDrivingCommand.cancel();
    m_drivetrain.stopSwerve();
  }
  
}