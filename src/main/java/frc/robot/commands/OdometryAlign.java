package frc.robot.commands;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.SwerveDrivetrain;

public class OdometryAlign extends CommandBase {
  private final SwerveDrivetrain m_drivetrain;

  private PPSwerveControllerCommand pathDrivingCommand;

  private PathPlannerTrajectory m_trajectory;
  private final PathConstraints m_constraints;
  private final PathPoint m_finalPoint;
  private final PhotonCamera m_camera;


  public OdometryAlign(SwerveDrivetrain drivetrain, PathConstraints constraints, PathPoint finalPoint, PhotonCamera camera) {
   
    m_drivetrain = drivetrain;
    m_constraints = constraints;
    m_finalPoint = finalPoint;
    m_camera = camera;

    addRequirements(m_drivetrain);

  }

  @Override
  public void initialize() {

    Pose2d m_pose;

    if(m_camera.hasTargets()) {

      var pipelineResult = m_camera.getLatestResult();
      PhotonTrackedTarget target = pipelineResult.getBestTarget();
      Pose3d tagPose = new Pose3d(1.524, 1.334, 0.3556, new Rotation3d(0, 0, 180));
      var targetPose = tagPose;
      Transform3d camToTarget = target.getBestCameraToTarget();
      Pose3d camPose = targetPose.transformBy(camToTarget.inverse());
      var visionMeasurement = camPose.transformBy(Constants.CAMERA_TO_ROBOT);
      m_pose = visionMeasurement.toPose2d();

    } else {

      m_pose = m_drivetrain.getPose();

    }

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