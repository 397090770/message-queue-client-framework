/**
 * <p>Title: MessageConsumerListener.java</p>
 * <p>Description: MessageConsumerListener</p>
 * <p>Package: org.darkphoenixs.activemq.listener</p>
 * <p>Company: www.github.com/DarkPhoenixs</p>
 * <p>Copyright: Dark Phoenixs (Open-Source Organization)</p>
 */
package org.darkphoenixs.activemq.listener;

import java.util.concurrent.ExecutorService;

import org.darkphoenixs.mq.consumer.Consumer;
import org.darkphoenixs.mq.exception.MQException;
import org.darkphoenixs.mq.listener.MessageListener;

/**
 * <p>Title: MessageConsumerListener</p>
 * <p>Description: 消费者监听器</p>
 *
 * @since 2015-06-01
 * @author Victor.Zxy
 * @see MessageListener
 * @version 1.0
 */
public class MessageConsumerListener<T> implements MessageListener<T> {

	/** messageConsumer */
	private Consumer<T> consumer;

	/** threadPool */
	private ExecutorService threadPool;

	/**
	 * @return the consumer
	 */
	public Consumer<T> getConsumer() {
		return consumer;
	}

	/**
	 * @param consumer
	 *            the consumer to set
	 */
	public void setConsumer(Consumer<T> consumer) {
		this.consumer = consumer;
	}

	/**
	 * @return the threadPool
	 */
	public ExecutorService getThreadPool() {
		return threadPool;
	}

	/**
	 * @param threadPool
	 *            the threadPool to set
	 */
	public void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	@Override
	public void onMessage(final T message) throws MQException {

		if (consumer != null)

			if (threadPool != null)

				threadPool.execute(new Runnable() {

					@Override
					public void run() {

						try {
							consumer.receive(message);
						} catch (MQException e) {
							logger.error(e.getMessage());
						}
					}
				});
			else
				consumer.receive(message);
		else
			throw new MQException("Consumer is null !");

	}
}
