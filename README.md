# 手写Volley框架
支持高扩展高并发的功能，全手写Volley网络访问框架

请求队列的设计

public class ThreadPoolManager {
    private static final String TAG ="dongnao" ;
    private static  ThreadPoolManager instance=new ThreadPoolManager();
    private LinkedBlockingQueue<Future<?>> taskQuene=new LinkedBlockingQueue<>();

    private ThreadPoolExecutor threadPoolExecutor;
    public static ThreadPoolManager getInstance() {

        return instance;
    }
    private ThreadPoolManager()
    {
        threadPoolExecutor=new ThreadPoolExecutor(4,10,10, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(4), handler);
        threadPoolExecutor.execute(runable);
    }

    public <T> boolean removeTask(FutureTask futureTask)
    {
        boolean result=false;
        /**
         * 阻塞式队列是否含有线程
         */
        if(taskQuene.contains(futureTask))
        {
            taskQuene.remove(futureTask);
        }else
        {
            result=threadPoolExecutor.remove(futureTask);
        }
        return  result;
    }


    private Runnable runable =new Runnable() {
        @Override
        public void run() {
            while (true)
            {
                FutureTask futrueTask=null;

                try {
                    /**
                     * 阻塞式函数
                     */
                    Log.i(TAG,"等待队列     "+taskQuene.size());
                    futrueTask= (FutureTask) taskQuene.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(futrueTask!=null)
                {
                    threadPoolExecutor.execute(futrueTask);
                }
                Log.i(TAG,"线程池大小      "+threadPoolExecutor.getPoolSize());
            }
        }
    };
    public <T> void execte(FutureTask<T> futureTask) throws InterruptedException {
        taskQuene.put(futureTask);
    }

    private RejectedExecutionHandler handler=new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                taskQuene.put(new FutureTask<Object>(r,null) {
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
