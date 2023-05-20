package frc.robot.subsystems;

import frc.lib.geometry.Translation2dPlus;
import frc.robot.Constants;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;

import java.util.Arrays;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDrivetrain extends SubsystemBase {

    private static final Translation2d[] WHEEL_POSITIONS =
            Arrays.copyOf(Constants.moduleTranslations, Constants.moduleTranslations.length);;
            
    public SwerveDriveOdometry m_swerveOdometry;
    public SwerveModule[] m_swerveMods;
    public Pigeon2 m_gyro;
    double pitchDerivative;
    double currentPitch = 0;
    double lastPitch = 0;
    double counter = 0;

    final double DRIVE_BANG_BANG_FWD = .06;
	final double DRIVE_BANG_BANG_BACK = -.04;
	final int DRIVE_BANG_BANG_SP = 10;

    BangBangController m_forward_bang_bang, m_reverse_bang_bang;

    public SwerveDrivetrain() {
        m_gyro = new Pigeon2(Constants.PIGEON);
        m_gyro.configFactoryDefault();
        zeroGyro();

        m_swerveMods = new SwerveModule[] {
            new SwerveModule(0, Constants.Mod0.constants),
            new SwerveModule(1, Constants.Mod1.constants),
            new SwerveModule(2, Constants.Mod2.constants),
            new SwerveModule(3, Constants.Mod3.constants)
        };

        m_swerveOdometry = new SwerveDriveOdometry(Constants.swerveKinematics, getYaw(), getModulePositions());

        setOdometryForOdometryAlign();

        UsbCamera m_camera = CameraServer.startAutomaticCapture();
        m_camera.setResolution(100, 100);

        m_forward_bang_bang = new BangBangController();
		m_forward_bang_bang.setSetpoint(DRIVE_BANG_BANG_SP);
		m_reverse_bang_bang = new BangBangController();
		m_reverse_bang_bang.setSetpoint(-DRIVE_BANG_BANG_SP);

    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop, boolean isEvading) {
        final Translation2d centerOfRotation;

        if (isEvading && fieldRelative) {
            centerOfRotation = getCenterOfRotation(translation.getAngle(), rotation);
        } else {
            centerOfRotation = new Translation2d();
        }

        final ChassisSpeeds chassisSpeeds;

        if (fieldRelative) {
            chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
                    translation.getX(),
                    translation.getY(),
                    rotation,
                    getYaw()
            );
        } else {
            chassisSpeeds = new ChassisSpeeds(
                    translation.getX(),
                    translation.getY(),
                    rotation
            );
        }

        SmartDashboard.putString("CoR", centerOfRotation.toString());

        final var swerveModuleStates = Constants.swerveKinematics.toSwerveModuleStates(chassisSpeeds, centerOfRotation);
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.MAX_SPEED);

        for (final var mod : m_swerveMods) {
            mod.setDesiredState(swerveModuleStates[mod.m_moduleNumber], isOpenLoop);
        }
    }  

    private Translation2d getCenterOfRotation(final Rotation2d direction, final double rotation) {
        final var here = new Translation2dPlus(1.0, direction.minus(getYaw()));

        var cwCenter = WHEEL_POSITIONS[0];
        var ccwCenter = WHEEL_POSITIONS[WHEEL_POSITIONS.length - 1];

        for (int i = 0; i < WHEEL_POSITIONS.length - 1; i++) {
            final var cw = WHEEL_POSITIONS[i];
            final var ccw = WHEEL_POSITIONS[i + 1];

            if (here.isWithinAngle(cw, ccw)) {
                cwCenter = ccw;
                ccwCenter = cw;
            }
        }

        // if clockwise
        if (Math.signum(rotation) == 1.0) {
            return cwCenter;
        } else if (Math.signum(rotation) == -1.0) {
            return ccwCenter;
        } else {
            return new Translation2d();
        }
    }
    
    public void setChassisSpeeds(ChassisSpeeds targetSpeeds) {
        setModuleStates(Constants.swerveKinematics.toSwerveModuleStates(targetSpeeds));
    }


    /* Used by SwerveControllerCommand in Auto */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.MAX_SPEED);
        
        for(SwerveModule mod : m_swerveMods){
            mod.setDesiredState(desiredStates[mod.m_moduleNumber], false);
        }
    }    

    public void teleResetGyro() {

        m_gyro.setYaw(getYaw().getDegrees() + 180);

    }
 
    public Pose2d getPose() {
        return m_swerveOdometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        m_swerveOdometry.resetPosition(getYaw(), getModulePositions(), pose);
    }

    public void resetOdometryAuton(Pose2d pose) {
        m_swerveOdometry.resetPosition(getYaw(), getModulePositions(), pose);
    }

    public void setOdometryToOffset() {
        m_swerveOdometry.resetPosition(Rotation2d.fromDegrees(0.0), getModulePositions(), new Pose2d(-6.14, 1.21, Rotation2d.fromDegrees(0.0)));
    }

    public void setOdometryForOdometryAlign() {
        m_swerveOdometry.resetPosition(Rotation2d.fromDegrees(0.0), getModulePositions(), new Pose2d(13.56, 5.2, Rotation2d.fromDegrees(0.0)));
    }

    public double getPitch() {

        return m_gyro.getPitch();

    }

    public SwerveModuleState[] getModuleStates(){
        SwerveModuleState[] states = new SwerveModuleState[4];
        for(SwerveModule mod : m_swerveMods){
            states[mod.m_moduleNumber] = mod.getState();
        }
        return states;
    }

    public SwerveModulePosition[] getModulePositions(){
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        for(SwerveModule mod : m_swerveMods){
            positions[mod.m_moduleNumber] = mod.getPosition();
        }
        return positions;
    }

    public void stopSwerve() {

        Translation2d stop = new Translation2d(0, 0);
        drive(stop, 0, true, true, false);

    }

    public void zeroGyro(){
        m_gyro.setYaw(0);
    }

    public Rotation2d getYaw() {
        return (Constants.INVERT_GYRO) ? Rotation2d.fromDegrees(360 - m_gyro.getYaw()) : Rotation2d.fromDegrees(m_gyro.getYaw());
    }

    public double getPitchDerivative() {

        return pitchDerivative;

    }

    public void driveBack() {

        if(counter == 0) {

            Timer m_timer = new Timer();
            m_timer.start();
            if(m_timer.get() < 0.1) {
                drive(new Translation2d(-0.3, 0), 0, true, false, false);
            }  else {
                stopSwerve();
                counter++;
            }

        }

    }

    public boolean isPitchDerivativeHigh() {

        return Math.abs(getPitchDerivative()) > (2 * 0.02);

    }

    @Override
    public void periodic(){

        m_swerveOdometry.update(getYaw(), getModulePositions());  

        for(SwerveModule mod : m_swerveMods){
            SmartDashboard.putNumber("Mod " + mod.m_moduleNumber + " Cancoder", mod.getCanCoder().getDegrees());
            SmartDashboard.putNumber("Mod " + mod.m_moduleNumber + " Integrated", mod.getPosition().angle.getDegrees());
            SmartDashboard.putNumber("Mod " + mod.m_moduleNumber + " Velocity", mod.getState().speedMetersPerSecond);
        }

        SmartDashboard.putNumber("real robot pose x", getPose().getX());
        SmartDashboard.putNumber("real robot pose y", getPose().getY());
        SmartDashboard.putNumber("real robot pose rot", getPose().getRotation().getDegrees());

        SmartDashboard.putNumber("Gyro", m_gyro.getYaw());
        SmartDashboard.putNumber("Pitch", getPitch());

        lastPitch = currentPitch;
        currentPitch = getPitch();
        pitchDerivative = lastPitch - currentPitch;

        SmartDashboard.putNumber("pitch rate of change", pitchDerivative);
        SmartDashboard.putBoolean("is pitch derivative high", isPitchDerivativeHigh());


    }

}