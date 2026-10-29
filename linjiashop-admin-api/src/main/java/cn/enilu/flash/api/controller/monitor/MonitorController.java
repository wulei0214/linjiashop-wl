package cn.enilu.flash.api.controller.monitor;

import cn.enilu.flash.bean.core.AuthorizationUser;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.cache.TokenCache;
import cn.enilu.flash.utils.HttpUtil;
import cn.enilu.flash.utils.StringUtil;
import cn.enilu.flash.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 运维监控接口：服务器监控、缓存监控、在线用户管理
 * 对齐主流后台（如RuoYi系统监控模块）的功能设计
 *
 * @version 2026-10-30
 */
@RequestMapping("/monitor")
@RestController
public class MonitorController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(MonitorController.class);

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private TokenCache tokenCache;

    /**
     * 服务器监控：CPU、内存、JVM、磁盘、系统信息
     */
    @RequestMapping(value = "/server", method = RequestMethod.GET)
    public Object server() {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        try {
            result.put("cpu", cpuInfo());
            result.put("memory", memoryInfo());
            result.put("os", osInfo());
            result.put("jvm", jvmInfo());
            result.put("disks", diskInfos());
        } catch (Exception e) {
            logger.error("获取服务器监控信息失败", e);
            return Rets.failure("获取服务器监控信息失败：" + e.getMessage());
        }
        return Rets.success(result);
    }

    /**
     * 缓存监控：各缓存区的条目数、内存占用、命中情况
     */
    @RequestMapping(value = "/cache", method = RequestMethod.GET)
    public Object cache() {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        try {
            net.sf.ehcache.CacheManager ehCacheManager = ehCacheManager();
            List<Map<String, Object>> caches = new ArrayList<Map<String, Object>>();
            long totalSize = 0;
            long totalHits = 0;
            long totalMisses = 0;
            for (String name : ehCacheManager.getCacheNames()) {
                net.sf.ehcache.Cache cache = ehCacheManager.getCache(name);
                if (cache == null) {
                    continue;
                }
                Map<String, Object> item = new LinkedHashMap<String, Object>();
                item.put("name", name);
                item.put("size", cache.getSize());
                item.put("memorySize", formatBytes(cache.calculateInMemorySize()));
                long hits = cache.getStatistics().getCacheHits();
                long misses = cache.getStatistics().getCacheMisses();
                item.put("hits", hits);
                item.put("misses", misses);
                item.put("hitRate", (hits + misses) == 0 ? "0%" :
                        String.format("%.2f%%", hits * 100.0 / (hits + misses)));
                caches.add(item);
                totalSize += cache.getSize();
                totalHits += hits;
                totalMisses += misses;
            }
            result.put("cacheCount", caches.size());
            result.put("totalSize", totalSize);
            result.put("hitRate", (totalHits + totalMisses) == 0 ? "0%" :
                    String.format("%.2f%%", totalHits * 100.0 / (totalHits + totalMisses)));
            result.put("caches", caches);
        } catch (Exception e) {
            logger.error("获取缓存监控信息失败", e);
            return Rets.failure("获取缓存监控信息失败：" + e.getMessage());
        }
        return Rets.success(result);
    }

    /**
     * 在线用户：最近一次登录/刷新token后仍未过期的会话
     */
    @RequestMapping(value = "/online/list", method = RequestMethod.GET)
    public Object onlineList(@RequestParam(value = "account", required = false) String account) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            net.sf.ehcache.Cache sessionCache = sessionCache();
            if (sessionCache != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long now = System.currentTimeMillis();
                for (Object keyObj : sessionCache.getKeys()) {
                    String token = String.valueOf(keyObj);
                    //会话缓存中token为key，token+"user"存放用户信息，这里只遍历token本身
                    if (token.endsWith("user")) {
                        continue;
                    }
                    net.sf.ehcache.Element tokenElement = sessionCache.get(token);
                    if (tokenElement == null) {
                        continue;
                    }
                    //token条目的value为刷新窗口的过期时间戳，已过期的会话不再展示
                    long expireTime = 0;
                    Object val = tokenElement.getObjectValue();
                    if (val instanceof Number) {
                        expireTime = ((Number) val).longValue();
                    } else if (val != null) {
                        try {
                            expireTime = Long.parseLong(String.valueOf(val));
                        } catch (NumberFormatException e) {
                            continue;
                        }
                    }
                    if (expireTime <= 0 || now > expireTime) {
                        continue;
                    }
                    AuthorizationUser user = tokenCache.getUser(token);
                    if (user == null) {
                        continue;
                    }
                    if (StringUtil.isNotEmpty(account) && !user.getAccount().contains(account)) {
                        continue;
                    }
                    Map<String, Object> item = new LinkedHashMap<String, Object>();
                    item.put("token", token);
                    item.put("account", user.getAccount());
                    item.put("name", user.getName());
                    item.put("deptName", user.getDeptName());
                    item.put("roleNames", user.getRoleNames());
                    item.put("loginTime", sdf.format(new Date(tokenElement.getCreationTime())));
                    item.put("expireTime", sdf.format(new Date(expireTime)));
                    list.add(item);
                }
            }
        } catch (Exception e) {
            logger.error("获取在线用户失败", e);
            return Rets.failure("获取在线用户失败：" + e.getMessage());
        }
        return Rets.success(list);
    }

    /**
     * 强制下线：清除会话缓存并拉黑token，该token下一次请求即失效
     */
    @RequestMapping(value = "/online/kick", method = RequestMethod.POST)
    public Object kick(@RequestParam("token") String token) {
        if (StringUtil.isEmpty(token)) {
            return Rets.failure("token不能为空");
        }
        if (token.equals(HttpUtil.getToken())) {
            return Rets.failure("不能强制下线当前登录用户");
        }
        tokenCache.kick(token);
        return Rets.success("已强制下线");
    }

    private Map<String, Object> cpuInfo() throws Exception {
        com.sun.management.OperatingSystemMXBean osMxBean =
                (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Map<String, Object> cpu = new LinkedHashMap<String, Object>();
        cpu.put("cpuNum", osMxBean.getAvailableProcessors());
        double cpuLoad = osMxBean.getSystemCpuLoad();
        if (cpuLoad <= 0) {
            //首次采样JDK会返回0，稍作间隔后重采一次
            try {
                Thread.sleep(150L);
                cpuLoad = osMxBean.getSystemCpuLoad();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        cpu.put("used", toPercent(cpuLoad));
        return cpu;
    }

    private Map<String, Object> memoryInfo() throws Exception {
        com.sun.management.OperatingSystemMXBean osMxBean =
                (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long totalPhysical = osMxBean.getTotalPhysicalMemorySize();
        long freePhysical = osMxBean.getFreePhysicalMemorySize();
        long used = totalPhysical - freePhysical;

        Map<String, Object> memory = new LinkedHashMap<String, Object>();
        memory.put("total", formatBytes(totalPhysical));
        memory.put("used", formatBytes(used));
        memory.put("free", formatBytes(freePhysical));
        memory.put("usage", toPercent(totalPhysical == 0 ? 0 : used * 1.0 / totalPhysical));
        return memory;
    }

    private Map<String, Object> jvmInfo() throws Exception {
        Runtime runtime = Runtime.getRuntime();
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;

        Map<String, Object> jvm = new LinkedHashMap<String, Object>();
        jvm.put("javaVersion", System.getProperty("java.version"));
        jvm.put("javaHome", System.getProperty("java.home"));
        jvm.put("javaName", runtimeMxBean.getVmName() + " " + runtimeMxBean.getVmVersion());
        jvm.put("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date(runtimeMxBean.getStartTime())));
        jvm.put("runTime", formatDuration(runtimeMxBean.getUptime()));
        jvm.put("max", formatBytes(maxMemory));
        jvm.put("total", formatBytes(totalMemory));
        jvm.put("free", formatBytes(freeMemory));
        jvm.put("used", formatBytes(usedMemory));
        jvm.put("usage", toPercent(maxMemory == 0 ? 0 : usedMemory * 1.0 / maxMemory));
        return jvm;
    }

    private List<Map<String, Object>> diskInfos() throws Exception {
        List<Map<String, Object>> disks = new ArrayList<Map<String, Object>>();
        for (File file : File.listRoots()) {
            long total = file.getTotalSpace();
            long free = file.getFreeSpace();
            if (total <= 0) {
                continue;
            }
            long used = total - free;
            Map<String, Object> disk = new LinkedHashMap<String, Object>();
            disk.put("dirName", file.getPath());
            disk.put("total", formatBytes(total));
            disk.put("free", formatBytes(free));
            disk.put("used", formatBytes(used));
            disk.put("usage", toPercent(used * 1.0 / total));
            disks.add(disk);
        }
        return disks;
    }

    private Map<String, Object> osInfo() throws Exception {
        com.sun.management.OperatingSystemMXBean osMxBean =
                (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        InetAddress addr = InetAddress.getLocalHost();
        Map<String, Object> os = new LinkedHashMap<String, Object>();
        os.put("osName", System.getProperty("os.name"));
        os.put("osArch", osMxBean.getArch());
        os.put("computerName", addr.getHostName());
        os.put("computerIp", addr.getHostAddress());
        return os;
    }

    private net.sf.ehcache.CacheManager ehCacheManager() {
        if (cacheManager instanceof EhCacheCacheManager) {
            return ((EhCacheCacheManager) cacheManager).getCacheManager();
        }
        throw new IllegalStateException("缓存管理器不是EhCacheCacheManager：" + cacheManager.getClass());
    }

    private net.sf.ehcache.Cache sessionCache() {
        return ehCacheManager().getCache(cn.enilu.flash.cache.CacheDao.SESSION);
    }

    private String toPercent(double ratio) {
        return String.format("%.2f%%", ratio * 100);
    }

    private String formatBytes(long bytes) {
        if (bytes < 1024L) {
            return bytes + "B";
        }
        double kb = bytes / 1024.0;
        if (kb < 1024) {
            return String.format("%.2fKB", kb);
        }
        double mb = kb / 1024;
        if (mb < 1024) {
            return String.format("%.2fMB", mb);
        }
        double gb = mb / 1024;
        if (gb < 1024) {
            return String.format("%.2fGB", gb);
        }
        return String.format("%.2fTB", gb / 1024);
    }

    private String formatDuration(long millis) {
        long seconds = millis / 1000;
        long days = seconds / 86400;
        long hours = (seconds % 86400) / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days).append("天");
        }
        sb.append(hours).append("小时").append(minutes).append("分钟").append(secs).append("秒");
        return sb.toString();
    }
}
