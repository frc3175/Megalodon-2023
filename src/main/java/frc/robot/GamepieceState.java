package frc.robot;

public class GamepieceState {

    boolean m_isConeMode;

    public GamepieceState() {

        m_isConeMode = false;

    }

    public void setRobotState(boolean isConeMode) {

        m_isConeMode = isConeMode;

    }

    public boolean isConeMode() {

        return m_isConeMode;

    }


}
