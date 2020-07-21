package com.lesson.stock4j.spider.util;

import com.lesson.stock4j.quartz.domain.SysJob;
import com.lesson.stock4j.quartz.exception.TaskException;
import com.lesson.stock4j.quartz.mapper.SysJobMapper;
import com.lesson.stock4j.quartz.util.ScheduleUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.8
 * <p>Date: 2020-07-21 13-03
 */
@Component
public class QuartzUtils {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SysJobMapper jobMapper;

    /**
     * 项目启动时，初始化定时器
     * 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void init() throws SchedulerException, TaskException {
        scheduler.clear();
        List<SysJob> jobList = jobMapper.selectJobAll();
        for (SysJob job : jobList) {
            ScheduleUtils.createScheduleJob(scheduler, job);
            System.out.println("加载的job为" + job.toString());
        }
    }
}
