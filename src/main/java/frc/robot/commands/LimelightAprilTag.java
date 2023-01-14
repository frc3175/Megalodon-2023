package frc.robot.commands;

import static frc.robot.Constants.ROBOT_TO_CAMERA;

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
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.SwerveDrivetrain;

public class LimelightAprilTag extends CommandBase {
  
  //private final ProfiledPIDController xController = Constants.AUTO_X_CONTROLLER;
  //private final ProfiledPIDController yController = Constants.AUTO_Y_CONTROLLER;
  private final ProfiledPIDController omegaController = Constants.AUTO_THETA_CONTROLLER;

  private static int TAG_TO_CHASE = 2;
  private static final Transform3d TAG_TO_GOAL = 
      new Transform3d(
          new Translation3d(1, 0.0, 0.0),
          new Rotation3d(0.0, 0.0, Math.PI));

  private final Limelight m_limelight;
  private final SwerveDrivetrain m_drivetrain;

  private int lastTarget;

  public LimelightAprilTag(Limelight limelight, SwerveDrivetrain drivetrain) {

    m_limelight = limelight;
    m_drivetrain = drivetrain;

    //xController.setTolerance(0.2);
    //yController.setTolerance(0.2);
    omegaController.setTolerance(Units.degreesToRadians(2));
    omegaController.enableContinuousInput(-Math.PI, Math.PI);

    addRequirements(m_drivetrain);

  }

  @Override
  public void initialize() {

    lastTarget = 100;

    Pose3d robotPose;

    if(m_limelight.getBotPose().length > 2) {

        double robotPoseX = m_limelight.getBotPose()[0];
        double robotPoseY = m_limelight.getBotPose()[1];
        double robotPoseZ = m_limelight.getBotPose()[2];
        double robotPoseRoll = m_limelight.getBotPose()[3];
        double robotPosePitch = m_limelight.getBotPose()[4];
        double robotPoseYaw = m_limelight.getBotPose()[5];
        robotPose = new Pose3d(robotPoseX, robotPoseY, robotPoseZ, new Rotation3d(robotPoseRoll, robotPosePitch, robotPoseYaw));

    } else {

        var robotPose2d = m_drivetrain.getPose();
        robotPose = new Pose3d(robotPose2d.getX(), robotPose2d.getY(), 0.0, new Rotation3d(0.0, 0.0, robotPose2d.getRotation().getRadians()));

    }

    omegaController.reset((robotPose.getRotation().getZ()) * Math.PI/180);
    //xController.reset(robotPose.getX());
    //yController.reset(robotPose.getY());


  }

  @Override
  public void execute() {

    Pose3d robotPose;

    if(m_limelight.getBotPose().length > 2) {

        double robotPoseX = m_limelight.getBotPose()[0];
        double robotPoseY = m_limelight.getBotPose()[1];
        double robotPoseZ = m_limelight.getBotPose()[2];
        double robotPoseRoll = m_limelight.getBotPose()[3];
        double robotPosePitch = m_limelight.getBotPose()[4];
        double robotPoseYaw = m_limelight.getBotPose()[5];
        robotPose = new Pose3d(robotPoseX, robotPoseY, robotPoseZ, new Rotation3d(robotPoseRoll, robotPosePitch, robotPoseYaw));

    } else {

        var robotPose2d = m_drivetrain.getPose();
        robotPose = new Pose3d(robotPose2d.getX(), robotPose2d.getY(), 0.0, new Rotation3d(0.0, 0.0, robotPose2d.getRotation().getRadians()));

    }

    String status = "executing";

    SmartDashboard.putString("status", status);

    SmartDashboard.putNumber("pose x", robotPose.getX());
    SmartDashboard.putNumber("pose y", robotPose.getY());
    SmartDashboard.putNumber("pose rot", (robotPose.getRotation().getZ() * Math.PI/180));

    if (m_limelight.hasTarget()) {

        lastTarget = TAG_TO_CHASE;

        status = "has target";

        SmartDashboard.putString("status", status);

        SmartDashboard.putNumber("target num", m_limelight.getID());
        
        // Transform the robot's pose to find the camera's pose
        Pose3d cameraPose = robotPose.transformBy(ROBOT_TO_CAMERA);

        SmartDashboard.putNumber("cam pose x", cameraPose.getX());
        SmartDashboard.putNumber("cam pose y", cameraPose.getY());
        SmartDashboard.putNumber("cam pose z", cameraPose.getZ());
        SmartDashboard.putNumber("cam pose rot", cameraPose.getRotation().getAngle());

        // Transform the camera's pose to the target's pose
        var targetPose = m_limelight.getTagPose(TAG_TO_CHASE);
        
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
    
    if (lastTarget == 100) {
      // No target has been visible
      m_drivetrain.stopSwerve();
    } else {
      // Drive to the target
     /*   var xSpeed = xController.calculate(robotPose.getX());
      if (xController.atGoal()) {
        xSpeed = 0;
      } */

      /* var ySpeed = yController.calculate(robotPose.getY());
      if (yController.atGoal()) {
        ySpeed = 0;
      } */

      var omegaSpeed = omegaController.calculate((robotPose.getRotation().getZ()) * Math.PI/180);
      if (omegaController.atGoal()) {
        status = "atGoal";
        SmartDashboard.putString("status", status);
        omegaSpeed = 0;
      }

     // SmartDashboard.putNumber("xspeed", xSpeed);
      //SmartDashboard.putNumber("yspeed", ySpeed);
      SmartDashboard.putNumber("omegaspeed", omegaSpeed);

      m_drivetrain.setChassisSpeeds(ChassisSpeeds.fromFieldRelativeSpeeds(0, 0, omegaSpeed, robotPose.getRotation().toRotation2d()));
      
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