package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PoseEstimator extends SubsystemBase {

    private Limelight m_limelight;
    private SwerveDriveOdometry m_odometry;
    private SwerveDrivetrain m_drivetrain;

    public PoseEstimator(SwerveDriveOdometry odometry, SwerveDrivetrain drivetrain, Limelight limelight) {

        m_limelight = limelight;
        m_odometry = odometry;
        m_drivetrain = drivetrain;

    }

    public Pose2d getBotPose(){

        return m_odometry.getPoseMeters();

    }

    @Override
    public void periodic() {

        if(m_limelight.hasTarget()) {

           // var m_pose = m_limelight.getConvertedPose2dPose();

           // m_odometry.resetPosition(m_drivetrain.getYaw(), m_drivetrain.getModulePositions(), m_pose);
    

        }

        SmartDashboard.putNumber("pose estimator x", getBotPose().getX());
        SmartDashboard.putNumber("pose estimator y", getBotPose().getY());

    }
    
}
