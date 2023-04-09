package dev.luke10x.rsynccycle.configuration;

import dev.luke10x.rsynccycle.AutowiredSpringBeanJobFactory;
import dev.luke10x.rsynccycle.schedule.LastProvisionDurationTimeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final ApplicationContext applicationContext;

    @Bean
    public AutowiredSpringBeanJobFactory jobFactory() {
        AutowiredSpringBeanJobFactory jobFactory = new AutowiredSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    @Scope("singleton")
    public LastProvisionDurationTimeRepository lastProvisionDurationTimeRepository() {
        return new LastProvisionDurationTimeRepository();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setJobFactory(jobFactory());
        schedulerFactory.setAutoStartup(true);
        schedulerFactory.setOverwriteExistingJobs(true);
        return schedulerFactory;
    }

    // Define other beans here
}
