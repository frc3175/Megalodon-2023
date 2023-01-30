package frc.robot.subsystems;

import java.util.Optional;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PoseEstimatorSubsystem extends SubsystemBase {

  private final Limelight m_limelight;
  private final SwerveDrivetrain drivetrainSubsystem;
  private final Timer m_timer;
  
  // Kalman Filter Configuration. These can be "tuned-to-taste" based on how much
  // you trust your various sensors. Smaller numbers will cause the filter to
  // "trust" the estimate from that particular component more than the others. 
  // This in turn means the particualr component will have a stronger influence
  // on the final pose estimate.

  /**
   * Standard deviations of model states. Increase these numbers to trust your model's state estimates less. This
   * matrix is in the form [x, y, theta]ᵀ, with units in meters and radians, then meters.
   */
  private static final Vector<N3> stateStdDevs = VecBuilder.fill(0.05, 0.05, Units.degreesToRadians(5));
  
  /**
   * Standard deviations of the vision measurements. Increase these numbers to trust global measurements from vision
   * less. This matrix is in the form [x, y, theta]ᵀ, with units in meters and radians.
   */
  private static final Vector<N3> visionMeasurementStdDevs = VecBuilder.fill(0.5, 0.5, Units.degreesToRadians(10));

  private final SwerveDrivePoseEstimator poseEstimator;

  private final Field2d field2d = new Field2d();

  public PoseEstimatorSubsystem(Limelight limelight, SwerveDrivetrain drivetrainSubsystem) {

    m_limelight = limelight;
    this.drivetrainSubsystem = drivetrainSubsystem;
    m_timer = new Timer();

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

    poseEstimator.resetPosition(drivetrainSubsystem.getYaw(), drivetrainSubsystem.getModulePositions(), new Pose2d(13.56, (Units.feetToMeters(27) - 5.2), Rotation2d.fromDegrees(0.0)));

  }

  @Override
  public void periodic() {

    if(m_limelight.getConvertedPose().getX() != 0.0) {

      Pose3d robotPose = m_limelight.getConvertedPose().transformBy(Constants.CAMERA_TO_ROBOT);

      var pos2 = robotPose.toPose2d();
      var pos2X = pos2.getX();
      var pos2Y = (Units.feetToMeters(27) - pos2.getY());

      poseEstimator.addVisionMeasurement(new Pose2d(pos2X, pos2Y, pos2.getRotation()), (Timer.getFPGATimestamp() - 0.011));

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
    var estpos = poseEstimator.getEstimatedPosition();
    var estposX = estpos.getX();
    var estposY = Units.feetToMeters(27) - estpos.getY();

    return new Pose2d(estposX, estposY, estpos.getRotation());
  }

  /**
   * Resets the current pose to the specified pose. This should ONLY be called
   * when the robot's position on the field is known, like at the beginning of
   * a match.
   * @param newPose new pose
   */
  public void setCurrentPose(Pose2d newPose) {
    poseEstimator.resetPosition(
      drivetrainSubsystem.getYaw(),
      drivetrainSubsystem.getModulePositions(),
      newPose);
  }

  /**
   * Resets the position on the field to 0,0 0-degrees, with forward being downfield. This resets
   * what "forward" is for field oriented driving.
   */
  public void resetFieldPosition() {
    setCurrentPose(new Pose2d());
  }

}