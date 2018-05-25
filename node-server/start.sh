#!/bin/sh

#添加本地执行路径
while true; do
        #启动一个循环，定时检查进程是否存在
        server=`ps aux | grep stanza.js |grep -v grep`
        if [ ! "$server" ]; then
            #如果不存在就重新启动
            nohup node stanza.js &
            #启动后沉睡10s
            sleep 10
        fi
        #每次循环沉睡10s
        sleep 5
done
