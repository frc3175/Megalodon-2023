package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotState;
import frc.robot.RobotState.BotState;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Slide;

public class AutonOuttake extends CommandBase {

    private RobotState m_robotState;
    private Timer m_timer = new Timer();
    private Intake m_intake;
    private Slide m_slide;
    private Elevator m_elevator;

    public AutonOuttake(Intake intake, RobotState robotState, Slide slide, Elevator elevator) {

        m_robotState = robotState;
        m_intake = intake;
        m_slide = slide;
        m_elevator = elevator;

        addRequirements(robotState, m_intake, m_slide, m_elevator);

    }

    @Override
    public void initialize() {
        m_timer.reset();
        m_timer.start();
    }

    @Override
    public void execute() {

        if((m_robotState.getGamepieceState())) {
            if(m_timer.get() < 0.4) {
                m_intake.setIntake(Constants.OUTTAKE_CONE);
            }
        } else {
            if(m_timer.get() < 0.6) {
                m_intake.setIntake(Constants.OUTTAKE_CUBE);
            } 
        }


    }

    @Override
    public void end(boolean isFinished) {

        m_intake.setIntake(0);

        m_robotState.setBotState(BotState.RESET_CONE);
        m_slide.setSlideState(m_robotState.getRobotState().slideState);
        m_intake.setIntakeState(m_robotState.getRobotState().intakeState);
        m_elevator.setElevatorState(m_robotState.getRobotState().elevatorState);


    }

    @Override
    public boolean isFinished() {

        if(m_timer.get() < 0.4) {
            return false;
        } else {
            return true;
        }

    }

    
}