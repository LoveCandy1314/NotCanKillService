# NotCanKillService
不会被杀死的service

service 防止被杀死方式:

1.提高进程的优先级，其实就是减小进程的p->oomkilladj（越小越重要），如启动Service调用startForeground()尽量提高进程的优先级；

2.当应用推到后台适当释放资源然后降低APP的内存占用量，因为在oom_adj相同的时候，会优先干掉内存消耗大的进程；

3.对于要一直在后台运行的Service我们一定要轻

4.双进程守护,一个主进程,一个守护进程,两个进程相互监督,谁死另外一个启动谁

5.利用JobSheduler 来实现防止被杀死,当双进程守护都被杀死,启动系统service检测是否存活,如果死掉,重新启动
