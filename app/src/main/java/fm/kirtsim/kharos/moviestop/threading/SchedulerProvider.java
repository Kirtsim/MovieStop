package fm.kirtsim.kharos.moviestop.threading;

import io.reactivex.Scheduler;

/**
 * Created by kharos on 23/02/2018
 */

@FunctionalInterface
public interface SchedulerProvider {

    Scheduler newScheduler();

}
