package frc.robot.subsystems;

import java.util.Optional;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Limelight extends SubsystemBase{

    private NetworkTableEntry tx;
    private NetworkTableEntry ty;
    private NetworkTableEntry tid;
    private NetworkTableEntry tv;
    private NetworkTable table;
    private double x;
    private double y;
    private double id;
    private double v;
    private NetworkTableEntry tbotpose;
    private double[] botpose;

    public Limelight() {

        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        tv = table.getEntry("tv");
        tid = table.getEntry("tid");
        tbotpose = table.getEntry("botpose");

    }

    public double getYDistance(double y) {

        return((Constants.TAG_HEIGHT -Constants.CAMERA_HEIGHT)/(Math.tan((y) * Math.PI/180)));

    }

    public double getXDistance(double x, double y) {

        return(getYDistance(y) / Math.tan(x * Math.PI / 180));

    }

    public double getTheta() {

        return x;

    }

    public boolean hasTarget() {

        if(botpose.length > 2) {
            return true;
        } else {
            return false;
        }

    }

    public double getID() {

        return id;

    }

    public Pose3d getTagPose(double tagID) {

        return new Pose3d(6.8, -1.21, 0.5, new Rotation3d(0, 0, 0));

    }

    public double[] getBotPose() {

        return botpose;

    }

    public Pose3d getConvertedPose() {

        botpose = tbotpose.getDoubleArray(botpose);

        double x;
        double y;
        double z;
        double yaw;
        double pitch;
        double roll;

        if(botpose.length > 2) {

            x = botpose[0];
            y = botpose[1];
            z = botpose[2];
            roll = botpose[3];
            pitch = botpose[4];
            yaw = botpose[5];
        

            var convertedY = Units.feetToMeters(13.5) - y;
            var convertedX = (-Units.feetToMeters(27) - x) * -1;

            return new Pose3d(new Translation3d(convertedX, convertedY, z), new Rotation3d(roll, pitch, yaw));

        } else {
            
            return new Pose3d(new Translation3d(0, 0, 0), new Rotation3d(0, 0, 0));

        }

    }

    @Override
    public void periodic() {

        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        v = tv.getDouble(0);
        id = tid.getDouble(100);
        double[] emptyArray = new double[0];
        botpose = tbotpose.getDoubleArray(emptyArray);


        if(botpose.length > 2) {

        //SmartDashboard.putNumber("converted pose x", getConvertedPose().getX());
        //SmartDashboard.putNumber("converted pose y", getConvertedPose().getY());

        }

    }
    
}
