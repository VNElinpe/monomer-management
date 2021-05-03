package top.vnelinpe.management.util;

import top.vnelinpe.management.vo.sys.*;

import java.lang.management.*;
import java.util.Date;

/**
 * 虚拟机相关的工具类
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/23 2:26
 */
public class JVMUtil {

    /**
     * 获取类加载信息
     *
     * @return
     */
    public static ClassLoadStatusVO getClassLoadStatusVO() {
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        return ClassLoadStatusVO.builder()
                .loadedClassCount(classLoadingMXBean.getLoadedClassCount())
                .totalLoadedClassCount(classLoadingMXBean.getTotalLoadedClassCount())
                .unloadedClassCount(classLoadingMXBean.getUnloadedClassCount()).build();
    }

    /**
     * 获取即时编译信息
     *
     * @return
     */
    public static CompilationVO getCompilationVO() {
        CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
        return CompilationVO.builder()
                .name(compilationMXBean.getName())
                .totalCompilationTime(compilationMXBean.getTotalCompilationTime()).build();
    }

    /**
     * 获取内存信息
     *
     * @return
     */
    public static MemoryUsageVO getMemoryVO() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        MemoryUsageVO memoryUsageDTO = new MemoryUsageVO();

        MemoryUsageVO.Memory heap = memoryUsageDTO.getHeap();
        heap.setInit(heapMemoryUsage.getInit());
        heap.setUsed(heapMemoryUsage.getUsed());
        heap.setCommitted(heapMemoryUsage.getCommitted());
        heap.setMax(heapMemoryUsage.getMax());

        MemoryUsageVO.Memory nonHeap = memoryUsageDTO.getNonHeap();
        nonHeap.setInit(nonHeapMemoryUsage.getInit());
        nonHeap.setUsed(nonHeapMemoryUsage.getUsed());
        nonHeap.setCommitted(nonHeapMemoryUsage.getCommitted());
        nonHeap.setMax(nonHeapMemoryUsage.getMax());

        return memoryUsageDTO;
    }

    /**
     * 获取操作系统信息
     *
     * @return
     */
    public static OperationSystemVO getOperationSystemVO() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        return OperationSystemVO.builder()
                .arch(operatingSystemMXBean.getArch())
                .availableProcessors(operatingSystemMXBean.getAvailableProcessors())
                .name(operatingSystemMXBean.getName())
                .systemLoadAverage(operatingSystemMXBean.getSystemLoadAverage())
                .version(operatingSystemMXBean.getVersion())
                .build();
    }

    /**
     * 获取运行时虚拟机信息
     *
     * @return
     */
    public static RuntimeVO getRuntimeVO() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return RuntimeVO.builder()
                .name(runtimeMXBean.getVmName())
                .vendor(runtimeMXBean.getVmVendor())
                .version(runtimeMXBean.getVmVersion())
                .startTime(CommonUtil.getLocalDateTimeString(CommonUtil.asLocalDateTime(new Date(runtimeMXBean.getStartTime()))))
                .upTime(CommonUtil.milliSecondtoDateString(runtimeMXBean.getUptime()))
                .build();
    }

    /**
     * 获取线程信息
     *
     * @return
     */
    public static ThreadVO getThreadVO() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        return ThreadVO.builder()
                .totalStartedCount(threadMXBean.getTotalStartedThreadCount())
                .deamonCount(threadMXBean.getDaemonThreadCount())
                .totalCount(threadMXBean.getThreadCount())
                .peakCount(threadMXBean.getPeakThreadCount())
                .build();
    }

}
