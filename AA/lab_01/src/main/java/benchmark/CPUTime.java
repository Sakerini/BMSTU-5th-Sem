package benchmark;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class CPUTime {
    public static long getCPUTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                bean.getCurrentThreadCpuTime() : 0L;
    }
}
