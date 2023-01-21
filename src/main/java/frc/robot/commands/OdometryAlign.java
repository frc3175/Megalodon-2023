package frc.robot.commands;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PoseEstimatorSubsystem;
import frc.robot.subsystems.SwerveDrivetrain;

public class OdometryAlign extends CommandBase {
  private final SwerveDrivetrain m_drivetrain;

  private PPSwerveControllerCommand pathDrivingCommand;

  private PathPlannerTrajectory m_trajectory;
  private final PathConstraints m_constraints;
  private final PathPoint m_finalPoint;
  private final PoseEstimatorSubsystem m_poseEstimator;


  public OdometryAlign(SwerveDrivetrain drivetrain, PathConstraints constraints, PathPoint finalPoint, PoseEstimatorSubsystem poseEstimator) {
   
    m_drivetrain = drivetrain;
    m_constraints = constraints;
    m_finalPoint = finalPoint;
    m_poseEstimator = poseEstimator;

    addRequirements(m_drivetrain, m_poseEstimator);

  }

  @Override
  public void initialize() {

    var m_pose = m_poseEstimator.getCurrentPose();

    m_trajectory = PathPlanner.generatePath(m_constraints, new PathPoint(new Translation2d(m_pose.getX(), m_pose.getY()), m_pose.getRotation(), m_pose.getRotation()),
                                            m_finalPoint);

    /* m_trajectory = PathPlanner.generatePath(m_constraints, new PathPoint(new Translation2d(m_pose.getX(), m_pose.getY()), m_pose.getRotation(), m_pose.getRotation()),
                                            new PathPoint(new Translation2d(0, -1), new Rotation2d(0))); */

    pathDrivingCommand = m_drivetrain.followTrajectoryCommand(m_trajectory, false, m_poseEstimator);
    pathDrivingCommand.schedule();

  }

  @Override
  public void end(boolean interrupted)
  {
    pathDrivingCommand.cancel();
    m_drivetrain.stopSwerve();
  }
  
}