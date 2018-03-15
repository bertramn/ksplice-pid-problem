package com.github.bertramn;

import jnr.posix.POSIX;
import jnr.posix.POSIXFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;

public class PID {

    public static void main(String[] args) {
        POSIX posix = POSIXFactory.getPOSIX();
        System.out.println(String.format("Process ID (must not be 0): %d\n", posix.getpid()));

        System.out.println("Available MBean Memory Pools:");
        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        boolean psOldGenFound = false;
        for (MemoryPoolMXBean pool : pools) {
            String poolName = pool.getName();
            if ("PS Old Gen".equals(poolName)) {
                psOldGenFound = true;
            }
            System.out.println(String.format("  %s", poolName));
        }

        if (!psOldGenFound) {
            System.out.println("\nPS Old Gen pool mbean is missing.");
        }

    }

}
