package frc.robot.commands;

import static frc.robot.Constants.ROBOT_TO_CAMERA;

import org.photonvision.*;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.SwerveDrivetrain;

public class FollowAprilTag extends CommandBase {
  
  //private final ProfiledPIDController xController = Constants.AUTO_X_CONTROLLER;
  //private final ProfiledPIDController yController = Constants.AUTO_Y_CONTROLLER;
  private final ProfiledPIDController omegaController = Constants.AUTO_THETA_CONTROLLER;

  private static int TAG_TO_CHASE = 2;
  private static final Transform3d TAG_TO_GOAL = 
      new Transform3d(
          new Translation3d(1, 0.0, 0.0),
          new Rotation3d(0.0, 0.0, Math.PI));

  private final PhotonCamera m_photonCamera;
  private final SwerveDrivetrain m_drivetrain;

  private PhotonTrackedTarget lastTarget;

  public FollowAprilTag(PhotonCamera photonCamera, SwerveDrivetrain drivetrain) {

    m_photonCamera = photonCamera;
    m_drivetrain = drivetrain;

    //xController.setTolerance(0.2);
    //yController.setTolerance(0.2);
    omegaController.setTolerance(Units.degreesToRadians(2));
    omegaController.enableContinuousInput(-Math.PI, Math.PI);

    addRequirements(m_drivetrain);

  }

  @Override
  public void initialize() {

    lastTarget = null;
    var robotPose = m_drivetrain.getPose();
    omegaController.reset(robotPose.getRotation().getRadians());
    //xController.reset(robotPose.getX());
    //yController.reset(robotPose.getY());

  }

  @Override
  public void execute() {

    String status = "executing";

    SmartDashboard.putString("status", status);

    var robotPose2d = m_drivetrain.getPose();
    var robotPose = 
        new Pose3d(
            robotPose2d.getX(),
            robotPose2d.getY(),
            0.0,
            new Rotation3d(0.0, 0.0, robotPose2d.getRotation().getRadians()));

    SmartDashboard.putNumber("pose x", robotPose2d.getX());
    SmartDashboard.putNumber("pose y", robotPose2d.getY());
    SmartDashboard.putNumber("pose rot", robotPose2d.getRotation().getDegrees());
    
    var photonRes = m_photonCamera.getLatestResult();
    if (photonRes.hasTargets()) {
      status = "has target";
      // Find the tag we want to chase
      var targetOpt = photonRes.getTargets().stream()
          .filter(t -> t.getFiducialId() == TAG_TO_CHASE)
          .filter(t -> !t.equals(lastTarget) && t.getPoseAmbiguity() <= .2 && t.getPoseAmbiguity() != -1)
          .findFirst();
      if (targetOpt.isPresent()) {
        status = "target opt";
        var target = targetOpt.get();
        // This is new target data, so recalculate the goal
        lastTarget = target;

        SmartDashboard.putNumber("target num", target.getFiducialId());
        
        // Transform the robot's pose to find the camera's pose
        Pose3d cameraPose = robotPose.transformBy(ROBOT_TO_CAMERA);

        SmartDashboard.putNumber("cam pose x", cameraPose.getX());
        SmartDashboard.putNumber("cam pose y", cameraPose.getY());
        SmartDashboard.putNumber("cam pose z", cameraPose.getZ());
        SmartDashboard.putNumber("cam pose rot", cameraPose.getRotation().getAngle());

        // Transform the camera's pose to the target's pose
        var camToTarget = target.getBestCameraToTarget();
        var targetPose = cameraPose.transformBy(camToTarget);
        
        // Transform the tag's pose to set our goal
        var goalPose = targetPose.transformBy(TAG_TO_GOAL).toPose2d();

        SmartDashboard.putNumber("goal pose x", goalPose.getX());
        SmartDashboard.putNumber("goal pose y", goalPose.getY());
        SmartDashboard.putNumber("goal pose rot", goalPose.getRotation().getDegrees());

        // Drive
        //xController.setGoal(goalPose.getX());
        //yController.setGoal(goalPose.getY());
        omegaController.setGoal(goalPose.getRotation().getRadians());
      }
    }
    
    if (lastTarget == null) {
      // No target has been visible
      m_drivetrain.stopSwerve();
    } else {
      // Drive to the target
      /*var xSpeed = xController.calculate(robotPose.getX());
      if (xController.atGoal()) {
        xSpeed = 0;
      } */

      /* var ySpeed = yController.calculate(robotPose.getY());
      if (yController.atGoal()) {
        ySpeed = 0;
      } */

      var omegaSpeed = omegaController.calculate(robotPose2d.getRotation().getRadians());
      if (omegaController.atGoal()) {
        omegaSpeed = 0;
      }

      //SmartDashboard.putNumber("xspeed", xSpeed);
      //SmartDashboard.putNumber("yspeed", ySpeed);
      SmartDashboard.putNumber("omegaspeed", omegaSpeed);

      m_drivetrain.setChassisSpeeds(ChassisSpeeds.fromFieldRelativeSpeeds(0, 0, omegaSpeed, robotPose2d.getRotation()));
      
    }

  }

  @Override
  public void end(boolean interrupted) {
    m_drivetrain.stopSwerve();
  }

  public void setTagToChase(int tagToChase){
    TAG_TO_CHASE = tagToChase;
  }

}