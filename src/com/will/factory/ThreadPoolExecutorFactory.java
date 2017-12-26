package com.will.factory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:ThreadPoolExecutorFactory
 * Description:线程池工厂类--单例模式
 * @Author Will Wu
 * @Email willwu618@gmail.com
 * @Date 2017-12-26
 */
public class ThreadPoolExecutorFactory {
    /**
     * CORE_POOL_SIZE--池中所保存的线程数，包括空闲线程。
     */
    private static final int CORE_POOL_SIZE = 10;
    /**
     * MAXIMUX_POOL_SIZE--池中允许的最大线程数(采用LinkedBlockingQueue时没有作用)。
     */
    private static final int MAXIMUX_POOL_SIZE = 40;
    /**
     * KEEP_ALIVE_TIME--当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间，线程池维护线程所允许的空闲时间
     */
    private static final int KEEP_ALIVE_TIME = 60;

    /**
     * CAPACITY--执行前用于保持任务的队列（缓冲队列）
     */
    private static final int CAPACITY = 300;

    /**
     * 线程池对象
     */
    private static ThreadPoolExecutor threadPoolExecutor = null;

    //构造方法私有化
    private ThreadPoolExecutorFactory() {
    }

    /**
     * 排队有三种通用策略：
     *  1.直接提交:工作队列的默认选项是 SynchronousQueue，它将任务直接提交给线程而不保持它们。
     *   在此，如果不存在可用于立即运行任务的线程，则试图把任务加入队列将失败，因此会构造一个新的线程。
     *  2.无界队列:使用无界队列（例如，不具有预定义容量的 LinkedBlockingQueue）将导致在所有 corePoolSize
     *    线程都忙时新任务在队列中等待。这样，创建的线程就不会超过 corePoolSize。
     *   （因此，maximumPoolSize 的值也就无效了。）当每个任务完全独立于其他任务，即任务执行互不影响时，适合于使用无界队列
     *  3.有界队列:当使用有限的 maximumPoolSizes 时，有界队列（如 ArrayBlockingQueue）有助于防止资源耗尽，但是可能较难调整和控制。
     *    队列大小和最大池大小可能需要相互折衷：使用大型队列和小型池可以最大限度地降低 CPU 使用率、操作系统资源和上下文切换开销，
     *    但是可能导致人工降低吞吐量。如果任务频繁阻塞（例如，如果它们是 I/O 边界），则系统可能为超过您许可的更多线程安排时间。
     *    使用小型队列通常要求较大的池大小，CPU 使用率较高，但是可能遇到不可接受的调度开销，这样也会降低吞吐量。
     * @return
     */
    public static ThreadPoolExecutor getThreadPoolExecutor() {
        if (null == threadPoolExecutor) {
            ThreadPoolExecutor t;
            synchronized (ThreadPoolExecutor.class) {
                t = threadPoolExecutor;
                if (null == t) {
                    synchronized (ThreadPoolExecutor.class) {
                        t = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit
                                .SECONDS, new LinkedBlockingQueue<>(), new ThreadPoolExecutor
                                .DiscardOldestPolicy());
                    }
                    threadPoolExecutor = t;
                }
            }
        }
        return threadPoolExecutor;
    }
}
