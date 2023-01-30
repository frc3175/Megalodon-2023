package frc.robot.commands;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.PoseEstimator;
import frc.robot.subsystems.SwerveDrivetrain;

public class OdometryAlign extends CommandBase {
  private final SwerveDrivetrain m_drivetrain;

  private PPSwerveControllerCommand pathDrivingCommand;

  private PathPlannerTrajectory m_trajectory;
  private final PathConstraints m_constraints;
  private final PathPoint m_finalPoint;
  private final Limelight m_limelight;
  private final PoseEstimator m_poseEstimator;


  public OdometryAlign(SwerveDrivetrain drivetrain, PathConstraints constraints, PathPoint finalPoint, Limelight limelight, PoseEstimator poseEstimator) {
   
    m_drivetrain = drivetrain;
    m_constraints = constraints;
    m_finalPoint = finalPoint;
    m_limelight = limelight;
    m_poseEstimator = poseEstimator;

    addRequirements(m_drivetrain, m_poseEstimator);

  }

  @Override
  public void initialize() {

    Pose2d m_pose;

    if(m_limelight.getBotPose().length > 2) {

      m_pose = m_limelight.getConvertedPose2dPose();

    } else {

      m_pose = m_drivetrain.getPose();

    }

    m_trajectory = PathPlanner.generatePath(m_constraints, new PathPoint(new Translation2d(m_pose.getX(), m_pose.getY()), m_pose.getRotation(), m_pose.getRotation()), m_finalPoint);

    pathDrivingCommand = m_drivetrain.followTrajectoryCommand(m_trajectory, false, m_limelight);
    pathDrivingCommand.schedule();

  }

  @Override
  public void end(boolean interrupted) {

    pathDrivingCommand.cancel();
    m_drivetrain.stopSwerve();
    
  }
  
}