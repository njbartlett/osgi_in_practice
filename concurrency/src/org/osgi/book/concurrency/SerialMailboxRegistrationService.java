package org.osgi.book.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.book.utils.SerialExecutor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class SerialMailboxRegistrationService implements
		MailboxRegistrationService {

	private final Map<String, ServiceRegistration> map =
	      new ConcurrentHashMap<String, ServiceRegistration>();
	private final BundleContext context;

	private final Executor executor = new SerialExecutor();

	public SerialMailboxRegistrationService(BundleContext context) {
		this.context = context;
	}

	public void registerMailbox(final String name,
	         final Mailbox mailbox) {
		Runnable task = new Runnable() {
			public void run() {
				ServiceRegistration priorReg = map.get(name);
				if(priorReg != null) priorReg.unregister();

				Properties props = new Properties();
				props.put(Mailbox.NAME_PROPERTY, name);
				ServiceRegistration reg = context.registerService(
				      Mailbox.class.getName(), mailbox, props);

				map.put(name, reg);
			}
		};
		executor.execute(task);
	}
}