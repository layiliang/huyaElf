package com.lyl.webElf.utils;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import cn.hutool.core.util.NumberUtil;
import oshi.json.SystemInfo;
import oshi.json.hardware.CentralProcessor;
import oshi.json.hardware.GlobalMemory;
import oshi.json.hardware.HWDiskStore;
import oshi.json.hardware.HardwareAbstractionLayer;

/**
 * <p>
 * 类说明
 * </p>
 *
 * @author Shawn
 * @since 2018/06/29
 */
public class SystemUsageUtil {

    private static SystemInfo systemInfo = new SystemInfo();

    /**
     * 获取内存的使用率
     *
     * @return 内存使用率 0.36
     */
    public static double getMemoryUsage() {
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        GlobalMemory memory = hal.getMemory();
        long available = memory.getAvailable();
        long total = memory.getTotal();
        double useRate = NumberUtil.div(available, total, 2);
        return useRate;
    }

    /**
     * 获取CPU的使用率
     *
     * @return CPU使用率 0.36
     */
    public static double getCpuUsage() {
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        CentralProcessor processor = hal.getProcessor();
        double useRate = processor.getSystemCpuLoadBetweenTicks();
        return NumberUtil.div(useRate, 1, 2);
    }

    /**
     * 获取磁盘的使用率
     *
     * @return CPU使用率 0.36
     */
    public static double getDiskUsage() {
        if (isWindows()) {
            return getWinDiskUsage();
        }
        return getUnixDiskUsage();
    }


    /**
     * 判断系统是否为windows
     *
     * @return 是否
     */
    private static boolean isWindows() {
        return System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");
    }

    /**
     * 获取linux 磁盘使用率
     *
     * @return 磁盘使用率
     */
    private static double getUnixDiskUsage() {
        String ioCmdStr = "df -h /";
        String resultInfo = runCommand(ioCmdStr);
        String[] data = resultInfo.split(" +");
        double total = Double.parseDouble(data[10].replace("%", ""));
        return total / 100;
    }

    /**
     * 获取linux 磁盘使用率
     *
     * @return 磁盘使用率
     */
    private static double getWinDiskUsage() {

        HardwareAbstractionLayer hal = systemInfo.getHardware();
        HWDiskStore[] diskStores = hal.getDiskStores();
        long total = 0;
        long used = 0;
        if (diskStores != null && diskStores.length > 0) {
            for (HWDiskStore diskStore : diskStores) {
                long size = diskStore.getSize();
                long writeBytes = diskStore.getWriteBytes();
                total += size;
                used += writeBytes;
            }
        }
        return NumberUtil.div(used, total, 2);
    }

public static void main(String[] args) throws InterruptedException {
	while(true){
		Thread.sleep(1000);
		System.out.println(getCpuUsage() );
	}
}
    /**
     * 执行系统命令
     *
     * @param CMD 命令
     * @return 字符串结果
     */
    private static String runCommand(String CMD) {
        StringBuilder info = new StringBuilder();
        try {
            Process pos = Runtime.getRuntime().exec(CMD);
            pos.waitFor();
            InputStreamReader isr = new InputStreamReader(pos.getInputStream());
            LineNumberReader lnr = new LineNumberReader(isr);
            String line;
            while ((line = lnr.readLine()) != null) {
                info.append(line).append("\n");
            }
        } catch (Exception e) {
            info = new StringBuilder(e.toString());
        }
        return info.toString();
    }
}