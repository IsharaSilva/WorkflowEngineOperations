package org.wso2.carbon.identity.workflow.engine;

import org.wso2.carbon.identity.workflow.engine.util.EventResultState;

public class EventExecutorResult {
    private EventResultState eventResultState;

    private String message;

    public EventExecutorResult(EventResultState eventResultState) {
        this.eventResultState = eventResultState;
    }

    public EventExecutorResult(EventResultState eventResultState, String message) {
        this.message = message;
        this.eventResultState = eventResultState;
    }

    public EventResultState getEventResultState() {

        return eventResultState;
    }

    public void setEventResultState(EventResultState eventResultState) {

        this.eventResultState = eventResultState;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

}
