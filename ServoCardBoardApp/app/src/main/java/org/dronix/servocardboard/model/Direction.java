package org.dronix.servocardboard.model;

public class Direction {
    public enum Directions{LEFT, RIGHT, CENTER}

    String direction;

    private Direction(String direction) {
        this.direction = direction;
    }

    public static Direction Builder(String dir){
        return new Direction(dir);
    }
}
