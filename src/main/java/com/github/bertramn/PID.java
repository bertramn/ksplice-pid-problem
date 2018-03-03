package com.github.bertramn;

import jnr.posix.POSIX;
import jnr.posix.POSIXFactory;

public class PID {

    public static void main(String[] args) {
        POSIX posix = POSIXFactory.getPOSIX();
        System.out.println(posix.getpid());
    }

}
