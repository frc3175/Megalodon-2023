package frc.robot.subsystems;

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
    private int id;
    private int v;

    public Limelight() {

        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        tv = table.getEntry("tv");
        tid = table.getEntry("tid");

    }

    public double getYDistance() {

        return((Math.abs(Constants.TAG_HEIGHT - Constants.CAMERA_HEIGHT)) / Math.tan(y));

    }

    public double getXDistance() {

        return(getYDistance() / Math.tan(x));

    }

    public double getTheta() {

        return x;

    }

    public boolean hasTarget() {

        if(v == 0) {
            return false;
        } else if(v == 1) {
            return true;
        } else {
            return false;
        }

    }

    public int getID() {

        return id;

    }

    @Override
    public void periodic() {

        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        v = (int) tv.getInteger(0);
        id = (int) tid.getInteger(0);

        SmartDashboard.putNumber("LimelightX", x);
        SmartDashboard.putNumber("LimelightY", y);

    }
    
}
