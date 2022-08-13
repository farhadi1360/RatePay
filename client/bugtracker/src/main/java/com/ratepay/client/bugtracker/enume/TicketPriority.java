package com.ratepay.client.bugtracker.enume;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
public enum TicketPriority {
    LOW, MIDDLE, HIGH;

    public static TicketPriority getTicketPriority(String type) {
        switch (type.toLowerCase()) {
            case "low":
                return LOW;
            case "middle":
                return MIDDLE;
            case "high":
                return HIGH;
            default:
                return LOW;
        }
    }
}
