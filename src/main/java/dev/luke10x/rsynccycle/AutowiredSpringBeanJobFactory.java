package dev.luke10x.rsynccycle;

import lombok.RequiredArgsConstructor;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * This class extends the Spring-provided {@link SpringBeanJobFactory}
 * and adds the capability to inject dependencies into Quartz Job instances.
 *
 * Quartz normally creates Job instances via a no-arg constructor,
 * which makes it difficult to inject dependencies
 * using Spring's auto-wiring mechanism. To solve this issue,
 * this class overrides the method
 * {@link #createJobInstance(TriggerFiredBundle)}
 * and calls Spring's {@link AutowireCapableBeanFactory#autowireBean(Object)}
 * method to inject dependencies into the newly created Job instance.
 * 
 * By using this class as the JobFactory in the Scheduler,
 * any Job instances that are created by Quartz
 * will have their dependencies automatically injected
 * using Spring's auto-wiring mechanism.
 */

@RequiredArgsConstructor
public class AutowiredSpringBeanJobFactory
        extends SpringBeanJobFactory
{
    private transient AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        autowireCapableBeanFactory = applicationContext
                .getAutowireCapableBeanFactory();
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle)
            throws Exception {
        Object job = super.createJobInstance(bundle);
        autowireCapableBeanFactory.autowireBean(job);
        return job;
    }
}
