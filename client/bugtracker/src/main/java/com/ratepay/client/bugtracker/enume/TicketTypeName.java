package com.ratepay.client.bugtracker.enume;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
public enum TicketTypeName {

    BUG, ENHANCEMENT_REQUEST, FEATURE_REQUEST;

    public static TicketTypeName getTicketTypeName(String type) {
        switch (type.toLowerCase()) {
            case "bug":
                return BUG;
            case "enhancement_request":
                return ENHANCEMENT_REQUEST;
            case "feature_request":
                return FEATURE_REQUEST;
            default:
                return BUG;
        }
    }
}
