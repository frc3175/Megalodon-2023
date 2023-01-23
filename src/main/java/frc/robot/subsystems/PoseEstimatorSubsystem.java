/*package frc.robot.subsystems;

import static frc.robot.Constants.CAMERA_TO_ROBOT;

import java.io.IOException;
import java.util.Optional;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFieldLayout.OriginPosition;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PoseEstimatorSubsystem extends SubsystemBase {

  private final PhotonCamera photonCamera;
  private final SwerveDrivetrain drivetrainSubsystem;
  private final AprilTagFieldLayout aprilTagFieldLayout;
  
  // Kalman Filter Configuration. These can be "tuned-to-taste" based on how much
  // you trust your various sensors. Smaller numbers will cause the filter to
  // "trust" the estimate from that particular component more than the others. 
  // This in turn means the particualr component will have a stronger influence
  // on the final pose estimate.

  /**
   * Standard deviations of model states. Increase these numbers to trust your model's state estimates less. This
   * matrix is in the form [x, y, theta]ᵀ, with units in meters and radians, then meters.
   */  
  //private static final Vector<N3> stateStdDevs = VecBuilder.fill(0.05, 0.05, Units.degreesToRadians(5));

  /**
   * Standard deviations of the vision measurements. Increase these numbers to trust global measurements from vision
   * less. This matrix is in the form [x, y, theta]ᵀ, with units in meters and radians.
   */  
  /* private static final Vector<N3> visionMeasurementStdDevs = VecBuilder.fill(0.5, 0.5, Units.degreesToRadians(10));

  private final SwerveDrivePoseEstimator poseEstimator;

  private final Field2d field2d = new Field2d();

  private double previousPipelineTimestamp = 0;

  PhotonTrackedTarget target;
  public int fiducialId;
  Pose3d tagPose;

  public PoseEstimatorSubsystem(PhotonCamera photonCamera, SwerveDrivetrain drivetrainSubsystem) {
    this.photonCamera = photonCamera;
    this.drivetrainSubsystem = drivetrainSubsystem;
    AprilTagFieldLayout layout;
    try {
      layout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
      var alliance = DriverStation.getAlliance();
      layout.setOrigin(alliance == Alliance.Blue ?
          OriginPosition.kBlueAllianceWallRightSide : OriginPosition.kBlueAllianceWallRightSide);
    } catch(IOException e) {
      DriverStation.reportError("Failed to load AprilTagFieldLayout", e.getStackTrace());
      layout = null;
    }
    this.aprilTagFieldLayout = layout;

    ShuffleboardTab tab = Shuffleboard.getTab("Vision");

    poseEstimator =  new SwerveDrivePoseEstimator(
        Constants.swerveKinematics,
        drivetrainSubsystem.getYaw(),
        drivetrainSubsystem.getModulePositions(),
        new Pose2d(),
        stateStdDevs,
        visionMeasurementStdDevs);
    
    tab.addString("Pose", this::getFomattedPose).withPosition(0, 0).withSize(2, 0);
    tab.add("Field", field2d).withPosition(2, 0).withSize(6, 4);

    SmartDashboard.putNumber("id 2 x", layout.getTagPose(2).get().getX());
    SmartDashboard.putNumber("id 2 y", layout.getTagPose(2).get().getY());

    setCurrentPose(new Pose2d(0, 0, new Rotation2d(180)));

  }

  @Override
  public void periodic() {
    // Update pose estimator with the best visible target
    var pipelineResult = photonCamera.getLatestResult();
    var resultTimestamp = pipelineResult.getTimestampSeconds();
    if (resultTimestamp != previousPipelineTimestamp && pipelineResult.hasTargets()) {
      previousPipelineTimestamp = resultTimestamp;
      target = pipelineResult.getBestTarget();
      fiducialId = target.getFiducialId();
      // Get the tag pose from field layout - consider that the layout will be null if it failed to load
      //tagPose = aprilTagFieldLayout == null ? Optional.empty() : aprilTagFieldLayout.getTagPose(fiducialId);
      tagPose = new Pose3d(2.286, -1.0922, 0.3556, new Rotation3d(0, 0, 180));
      if (target.getPoseAmbiguity() <= .2 && fiducialId >= 0 /* && tagPose.isPresent() *//* ) { 
        var targetPose = tagPose;
        Transform3d camToTarget = target.getBestCameraToTarget();
        Pose3d camPose = targetPose.transformBy(camToTarget.inverse());

        var visionMeasurement = camPose.transformBy(CAMERA_TO_ROBOT);
        poseEstimator.addVisionMeasurement(visionMeasurement.toPose2d(), resultTimestamp);
      }
      
    }
    // Update pose estimator with drivetrain sensors
    poseEstimator.update(
      drivetrainSubsystem.getYaw(),
      drivetrainSubsystem.getModulePositions());

    field2d.setRobotPose(getCurrentPose());

    SmartDashboard.putNumber("pose estimator x", getCurrentPose().getX());
    SmartDashboard.putNumber("pose estimator y", getCurrentPose().getY());
    SmartDashboard.putNumber("pose estimator rot", getCurrentPose().getRotation().getDegrees());

  }

  private String getFomattedPose() {
    var pose = getCurrentPose();
    return String.format("(%.2f, %.2f) %.2f degrees", 
        pose.getX(), 
        pose.getY(),
        pose.getRotation().getDegrees());
  }

  public Pose2d getCurrentPose() {
    //Pose2d poseEstimatorPose2d = poseEstimator.getEstimatedPosition();
    //var ppX = (Units.feetToMeters(54) - poseEstimatorPose2d.getX());
    //var ppY = (Units.feetToMeters(27) - poseEstimatorPose2d.getY());
    //var pprot = poseEstimatorPose2d.getRotation().getDegrees() - 180;
    //return new Pose2d(ppX, ppY, new Rotation2d(pprot));

    return poseEstimator.getEstimatedPosition();

    //return new Pose2d(poseEstimator.getEstimatedPosition().getX(), poseEstimator.getEstimatedPosition().getY(), Rotation2d.fromDegrees(poseEstimator.getEstimatedPosition().getRotation().getDegrees()+180));

  } 

  /** 
   * Resets the current pose to the specified pose. This should ONLY be called
   * when the robot's position on the field is known, like at the beginning of
   * a match.
   * @param newPose new pose
   */  /* 
  public void setCurrentPose(Pose2d newPose) {
    poseEstimator.resetPosition(
      drivetrainSubsystem.getYaw(),
      drivetrainSubsystem.getModulePositions(),
      newPose);
  } 

  /**
   * Resets the position on the field to 0,0 0-degrees, with forward being downfield. This resets
   * what "forward" is for field oriented driving.
   */ /* 
  public void resetFieldPosition() {
    setCurrentPose(new Pose2d());
  }

  public boolean hasTarget() {

    var localTarget = photonCamera.getLatestResult().getBestTarget();

    if(localTarget.getPoseAmbiguity() <= .2 && localTarget.getFiducialId() >= 0) {
      return true;
    } else {
      return false;
    }

  }

  public Pose3d getIDPose() {

    var localTarget = photonCamera.getLatestResult().getBestTarget();

    if(localTarget.getPoseAmbiguity() <= .2 && localTarget.getFiducialId() >= 0) {
      return tagPose;
    } else {
      return null;
    }

  } 

} */