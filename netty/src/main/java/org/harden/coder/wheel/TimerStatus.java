package org.harden.coder.wheel;

public enum TimerStatus {
    STOP(1),START(0),STARTED(-1);
    int status;

    TimerStatus(int status){
        this.status=status;
    }
}
