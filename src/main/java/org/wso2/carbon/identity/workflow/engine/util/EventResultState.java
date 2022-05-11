package org.wso2.carbon.identity.workflow.engine.util;

public enum EventResultState {
    STARTED_ASSOCIATION {
        @Override
        public boolean state() {

            return false;
        }
    },
    FAILED {
        @Override
        public boolean state() {

            return false;
        }
    };

    /**
     * Defautl State of Result
     *
     * @return boolean
     */
    public boolean state() {

        return false;
    }
}