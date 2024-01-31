package pgdp.shuttle.tasks;

/**
 * Interface needed for TaskDistributer to generate new Tasks.
 * You may implement your own TaskGenerator classes and share them
 * with others, as long as it won't reveal anything regarding the 
 * tasks implementation.
 */
public interface TaskGenerator {
	public ShuttleTask<?, ?> generateTask();
}
